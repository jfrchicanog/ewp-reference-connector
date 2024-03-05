package eu.erasmuswithoutpaper.omobility.las.boundary;

import eu.erasmuswithoutpaper.api.architecture.Empty;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.LearningAgreement;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasGetResponse;
import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import eu.erasmuswithoutpaper.common.boundary.ParamsClass;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.iia.boundary.NotifyAux;
import eu.erasmuswithoutpaper.monitoring.SendMonitoringService;
import eu.erasmuswithoutpaper.omobility.las.control.OutgoingMobilityLearningAgreementsConverter;
import eu.erasmuswithoutpaper.omobility.las.entity.MobilityInstitution;
import eu.erasmuswithoutpaper.omobility.las.entity.OlearningAgreement;
import eu.erasmuswithoutpaper.omobility.las.entity.Student;
import eu.erasmuswithoutpaper.organization.entity.Institution;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.*;
import java.util.function.BiPredicate;

@Stateless
@Path("omobilities/las/test")
public class TestEndpointsOLAS {

    @PersistenceContext(unitName = "connector")
    EntityManager em;

    @Inject
    OutgoingMobilityLearningAgreementsConverter converter;

    @Inject
    RegistryClient registryClient;

    @Inject
    RestClient restClient;

    @Inject
    SendMonitoringService sendMonitoringService;

    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(TestEndpointsOLAS.class.getCanonicalName());

    @GET
    @Path("")
    public Response hello(@QueryParam("sending_hei_id") String sendingHeiId, @QueryParam("omobility_id") List<String> mobilityIdList) {
        OmobilityLasGetResponse response = new OmobilityLasGetResponse();
        List<OlearningAgreement> omobilityLasList = em.createNamedQuery(OlearningAgreement.findBySendingHeiId).setParameter("sendingHei", sendingHeiId).getResultList();

        if (!omobilityLasList.isEmpty()) {

            response.getLa().addAll(omobilitiesLas(omobilityLasList, mobilityIdList));
        }

        return javax.ws.rs.core.Response.ok(response).build();

    }


    @POST
    @Path("create")
    @Consumes("application/json")
    public Response create(OlearningAgreement olearningAgreement) {

        em.persist(olearningAgreement);
        em.flush();

        LOG.fine("NOTIFY: Send notification");

        List<ClientResponse> partnersResponseList = notifyPartner(olearningAgreement);

        LOG.fine("NOTIFY: notification sent");

        return Response.ok(em.find(OlearningAgreement.class, olearningAgreement.getId())).build();
    }

    private List<ClientResponse> notifyPartner(OlearningAgreement olearningAgreement) {
        LOG.fine("NOTIFY: Send notification");

        String localHeiId = "";
        List<Institution> internalInstitution = em.createNamedQuery(Institution.findAll, Institution.class).getResultList();

        localHeiId = internalInstitution.get(0).getInstitutionId();

        List<ClientResponse> partnersResponseList = new ArrayList<>();

        Set<NotifyAux> cnrUrls = new HashSet<>();

        List<Institution> institutions = em.createNamedQuery(Institution.findAll, Institution.class).getResultList();
        MobilityInstitution partnerSending = olearningAgreement.getSendingHei();
        MobilityInstitution partnerReceiving = olearningAgreement.getReceivingHei();

        LOG.fine("NOTIFY: partnerSending: " + partnerSending.getHeiId());
        LOG.fine("NOTIFY: partnerReceiving: " + partnerReceiving.getHeiId());

        Map<String, String> urls = null;

        if (!localHeiId.equals(partnerSending.getHeiId())) {

            //Get the url for notify the institute not supported by our EWP
            urls = registryClient.getIiaCnrHeiUrls(partnerSending.getHeiId());

            if (urls != null) {
                for (Map.Entry<String, String> entry : urls.entrySet()) {
                    cnrUrls.add(new NotifyAux(partnerSending.getHeiId(), entry.getValue()));
                }
            }

        }
        if (!localHeiId.equals(partnerReceiving.getHeiId())) {

            //Get the url for notify the institute not supported by our EWP
            urls = registryClient.getIiaCnrHeiUrls(partnerReceiving.getHeiId());

            if (urls != null) {
                for (Map.Entry<String, String> entry : urls.entrySet()) {
                    cnrUrls.add(new NotifyAux(partnerReceiving.getHeiId(), entry.getValue()));
                }

            }
        }


        String finalLocalHeiId = localHeiId;
        cnrUrls.forEach(url -> {
            LOG.fine("NOTIFY: url: " + url.getUrl());
            LOG.fine("NOTIFY: heiId: " + url.getHeiId());
            //Notify the other institution about the modification
            ClientRequest clientRequest = new ClientRequest();
            clientRequest.setUrl(url.getUrl());//get the first and only one url
            clientRequest.setHeiId(url.getHeiId());
            clientRequest.setMethod(HttpMethodEnum.POST);
            clientRequest.setHttpsec(true);

            Map<String, List<String>> paramsMap = new HashMap<>();
            paramsMap.put("sending_hei_id", Collections.singletonList(finalLocalHeiId));
            paramsMap.put("omobility_id", Collections.singletonList(olearningAgreement.getId()));
            ParamsClass paramsClass = new ParamsClass();
            paramsClass.setUnknownFields(paramsMap);
            clientRequest.setParams(paramsClass);

            ClientResponse iiaResponse = restClient.sendRequest(clientRequest, Empty.class);

            try {
                if (iiaResponse.getStatusCode() <= 599 && iiaResponse.getStatusCode() >= 400) {
                    sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "ola-cnr", null, Integer.toString(iiaResponse.getStatusCode()), iiaResponse.getErrorMessage(), null);
                } else if (iiaResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
                    sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "ola-cnr", null, Integer.toString(iiaResponse.getStatusCode()), iiaResponse.getErrorMessage(), "Error");
                }
            } catch (Exception e) {

            }

            partnersResponseList.add(iiaResponse);
        });

        return partnersResponseList;

    }

    private List<LearningAgreement> omobilitiesLas(List<OlearningAgreement> omobilityLasList, List<String> omobilityLasIdList) {
        List<LearningAgreement> omobilitiesLas = new ArrayList<>();
        omobilityLasList.stream().forEachOrdered((m) -> {
            if (omobilityLasIdList.contains(m.getId())) {
                omobilitiesLas.add(converter.convertToLearningAgreements(m));
            }
        });

        return omobilitiesLas;
    }

    private List<String> omobilityLasIds(List<OlearningAgreement> lasList, List<String> receivingHeiIdList) {
        List<String> omobilityLasIds = new ArrayList<>();
        if (lasList == null) {
            return omobilityLasIds;
        }
        lasList.stream().forEachOrdered((m) -> {
            if (receivingHeiIdList.isEmpty() || receivingHeiIdList.contains(m.getReceivingHei())) {
                omobilityLasIds.add(m.getId());
            }
        });

        return omobilityLasIds;
    }

    BiPredicate<OlearningAgreement, String> anyMatchReceivingAcademicYear = new BiPredicate<OlearningAgreement, String>() {
        @Override
        public boolean test(OlearningAgreement omobility, String receiving_academic_year_id) {
            return receiving_academic_year_id.equals(omobility.getReceivingAcademicTermEwpId());
        }
    };

    BiPredicate<OlearningAgreement, String> anyMatchSpecifiedStudent = new BiPredicate<OlearningAgreement, String>() {
        @Override
        public boolean test(OlearningAgreement omobility, String global_id) {
            Student student = omobility.getChangesProposal().getStudent();

            return global_id.equals(student.getGlobalId());
        }
    };

    BiPredicate<OlearningAgreement, String> anyMatchSpecifiedType = new BiPredicate<OlearningAgreement, String>() {
        @Override
        public boolean test(OlearningAgreement omobility, String mobilityType) {

            if (MobilityType.BLENDED.value().equals(mobilityType)) {

                return omobility.getFirstVersion().getBlendedMobilityComponents() != null && !omobility.getFirstVersion().getBlendedMobilityComponents().isEmpty();
            } else if (MobilityType.DOCTORAL.value().equals(mobilityType)) {

                return omobility.getFirstVersion().getShortTermDoctoralComponents() != null && !omobility.getFirstVersion().getShortTermDoctoralComponents().isEmpty();
            } else if (MobilityType.SEMESTRE.value().equals(mobilityType)) {

                if (omobility.getFirstVersion() != null) {

                    return (omobility.getFirstVersion().getComponentsStudied() != null && !omobility.getFirstVersion().getComponentsStudied().isEmpty());
                } else if (omobility.getApprovedChanges() != null) {

                    return (omobility.getApprovedChanges().getComponentsStudied() != null && !omobility.getApprovedChanges().getComponentsStudied().isEmpty());
                }
            }

            return false;
        }
    };
}
