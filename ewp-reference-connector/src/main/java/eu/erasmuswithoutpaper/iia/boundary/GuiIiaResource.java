package eu.erasmuswithoutpaper.iia.boundary;

import java.io.IOException;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import eu.erasmuswithoutpaper.common.control.*;
import eu.erasmuswithoutpaper.iia.approval.entity.IiaApproval;
import eu.erasmuswithoutpaper.iia.control.IiasEJB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;

import eu.erasmuswithoutpaper.api.architecture.Empty;
import eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse;
import eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse.Approval;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse.Iia.CooperationConditions;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse.Iia.Partner;
import eu.erasmuswithoutpaper.api.iias.endpoints.MobilitySpecification;
import eu.erasmuswithoutpaper.api.iias.endpoints.RecommendedLanguageSkill;
import eu.erasmuswithoutpaper.api.iias.endpoints.StaffMobilitySpecification;
import eu.erasmuswithoutpaper.api.iias.endpoints.StaffTeacherMobilitySpec;
import eu.erasmuswithoutpaper.api.iias.endpoints.StaffTrainingMobilitySpec;
import eu.erasmuswithoutpaper.api.iias.endpoints.StudentMobilitySpecification;
import eu.erasmuswithoutpaper.api.iias.endpoints.StudentStudiesMobilitySpec;
import eu.erasmuswithoutpaper.api.iias.endpoints.StudentTraineeshipMobilitySpec;
import eu.erasmuswithoutpaper.api.types.contact.Contact;
import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import eu.erasmuswithoutpaper.common.boundary.ParamsClass;
import eu.erasmuswithoutpaper.iia.control.HashCalculationUtility;
import eu.erasmuswithoutpaper.iia.control.IiaConverter;
import eu.erasmuswithoutpaper.iia.entity.CooperationCondition;
import eu.erasmuswithoutpaper.iia.entity.Duration;
import eu.erasmuswithoutpaper.iia.entity.DurationUnitVariants;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.iia.entity.IiaPartner;
import eu.erasmuswithoutpaper.iia.entity.IiaResponse;
import eu.erasmuswithoutpaper.iia.entity.MobilityNumber;
import eu.erasmuswithoutpaper.iia.entity.MobilityNumberVariants;
import eu.erasmuswithoutpaper.iia.entity.MobilityType;
import eu.erasmuswithoutpaper.iia.entity.SubjectArea;
import eu.erasmuswithoutpaper.monitoring.SendMonitoringService;
import eu.erasmuswithoutpaper.organization.entity.ContactDetails;
import eu.erasmuswithoutpaper.organization.entity.FlexibleAddress;
import eu.erasmuswithoutpaper.organization.entity.Gender;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import eu.erasmuswithoutpaper.organization.entity.Person;
import eu.erasmuswithoutpaper.security.InternalAuthenticate;

@Path("iia")
public class GuiIiaResource {

    @EJB
    IiasEJB iiasEJB;

    @Inject
    RegistryClient registryClient;

    @Inject
    RestClient restClient;

    @Inject
    IiaConverter iiaConverter;

    @Inject
    GlobalProperties properties;

    @Inject
    SendMonitoringService sendMonitoringService;

    private static final Logger logger = LoggerFactory.getLogger(GuiIiaResource.class);
    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(AuxIiaThread.class.getCanonicalName());


    @GET
    @Path("get")
    @InternalAuthenticate
    public Response get(@QueryParam("iia_id") String iiaId, @QueryParam("partner_id") String partnerId) {
        if (iiaId != null) {
            Iia iia = iiasEJB.findById(iiaId);
            if (iia != null) {
                String heiId = iiasEJB.getHeiId();
                List<IiasGetResponse.Iia> iiaResponse = iiaConverter.convertToIias(heiId, Collections.singletonList(iia));
                return Response.ok(iiaResponse).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } else if (partnerId != null) {
            List<Iia> iiaList = iiasEJB.findByPartnerId(partnerId);
            if (iiaList != null && !iiaList.isEmpty()) {
                List<IiasGetResponse.Iia> iiaResponseList = iiaConverter.convertToIias(partnerId, iiaList);
                GenericEntity<List<IiasGetResponse.Iia>> entity = new GenericEntity<List<IiasGetResponse.Iia>>(iiaResponseList) {
                };
                return Response.ok(entity).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("get-approved")
    @InternalAuthenticate
    public Response getApproved(@QueryParam("iia_id") String iiaId) {
        if (iiaId != null) {
            Iia iia = iiasEJB.findApprovedVersion(iiaId);
            if (iia != null) {
                String heiId = iiasEJB.getHeiId();
                List<IiasGetResponse.Iia> iiaResponse = iiaConverter.convertToIias(heiId, Collections.singletonList(iia));
                return Response.ok(iiaResponse).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
        }  else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("get_other")
    @InternalAuthenticate
    public Response getOther(@QueryParam("iia_id") String iiaId, @QueryParam("hei_id") String heiId) {
        LOG.fine("AuxIiaThread_ADDEDIT: Empezando GET tras CNR");
        Map<String, String> map = registryClient.getIiaHeiUrls(heiId);
        if (map == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        LOG.fine("AuxIiaThread_ADDEDIT: MAP ENCONTRADO");

        String url = map.get("get-url");
        if (url == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        LOG.fine("AuxIiaThread_ADDEDIT: Url encontrada: " + url);

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setHeiId(heiId);
        clientRequest.setHttpsec(true);
        clientRequest.setMethod(HttpMethodEnum.GET);
        clientRequest.setUrl(url);

        Map<String, List<String>> paramsMap = new HashMap<>();
        paramsMap.put("iia_id", Arrays.asList(iiaId));
        ParamsClass params = new ParamsClass();
        params.setUnknownFields(paramsMap);
        clientRequest.setParams(params);

        LOG.fine("AuxIiaThread_ADDEDIT: Parametros encontrados: ");

        paramsMap.forEach((key, value) -> {
            LOG.fine("\t\t\t\t" + key + ":" + value);
        });

        ClientResponse clientResponse = restClient.sendRequest(clientRequest, IiasGetResponse.class);

        Response response = Response.status(clientResponse.getStatusCode()).entity(clientResponse.getResult()).build();
        return response;
    }

    @GET
    @Path("get_all")
    @InternalAuthenticate
    // TODO: fix the default value
    public Response getAll(@QueryParam("hei_id") @DefaultValue("uma.es") String hei_id) {
        List<Iia> iiaList = iiasEJB.findAll();
        List<IiasGetResponse.Iia> result = iiaConverter.convertToIias(hei_id, iiaList);//It was required to use IiaConverter to avoid a recursive problem when the iia object was converted to json

        GenericEntity<List<IiasGetResponse.Iia>> entity = new GenericEntity<List<IiasGetResponse.Iia>>(result) {
        };

        return Response.ok(entity).build();
    }

    @GET
    @Path("get_heiid")
    @InternalAuthenticate
    public Response getHei(@QueryParam("hei_id") String heiId) {
        List<Iia> iiaList = iiasEJB.findAll();

        Predicate<Iia> condition = new Predicate<Iia>() {
            @Override
            public boolean test(Iia iia) {
                List<CooperationCondition> cooperationConditions = iia.getCooperationConditions();

                List<CooperationCondition> filtered = cooperationConditions.stream().filter(c -> heiId.equals(c.getSendingPartner().getInstitutionId())).collect(Collectors.toList());
                return !filtered.isEmpty();
            }
        };

        if (!iiaList.isEmpty()) {
            List<Iia> filteredList = iiaList.stream().filter(condition).collect(Collectors.toList());

            if (!filteredList.isEmpty()) {
                List<IiasGetResponse.Iia> iiasGetResponseList = iiaConverter.convertToIias(heiId, iiaList);

                GenericEntity<List<IiasGetResponse.Iia>> entity = new GenericEntity<List<IiasGetResponse.Iia>>(iiasGetResponseList) {
                };
                return Response.ok(entity).build();
            }
        }

        return javax.ws.rs.core.Response.ok().build();
    }

    @GET
    @Path("mobility_types")
    @InternalAuthenticate
    public Response getMobilityTypes() {
        List<MobilityType> mobilityTypeList = iiasEJB.findMobilityTypes();
        GenericEntity<List<MobilityType>> entity = new GenericEntity<List<MobilityType>>(mobilityTypeList) {
        };

        return Response.ok(entity).build();
    }

    @GET
    @Path("mobility_unit_variants")
    @InternalAuthenticate
    public Response getMobilityNumberVariants() {
        String[] statuses = MobilityNumberVariants.names();
        GenericEntity<String[]> entity = new GenericEntity<String[]>(statuses) {
        };

        return Response.ok(entity).build();
    }

    @GET
    @Path("duration_unit_variants")
    @InternalAuthenticate
    public Response getDurationUnitVariants() {
        String[] statuses = DurationUnitVariants.names();
        GenericEntity<String[]> entity = new GenericEntity<String[]>(statuses) {
        };

        return Response.ok(entity).build();
    }

    @POST
    @Path("add")
    @InternalAuthenticate
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(IiasGetResponse.Iia iia) throws Exception {
        LOG.fine("ADD: Add start");
        LOG.fine("ADD: Iia: " + iia);
        Iia iiaInternal = new Iia();

        iiaConverter.convertToIia(iia, iiaInternal, iiasEJB.findAllInstitutions());

        iiasEJB.insertIia(iiaInternal);

        System.out.println("ADD: Created Iia Id:" + iiaInternal.getId());

        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            notifyPartner(iiaInternal);
        });

        LOG.fine("ADD: Notification send");

        IiaResponse response = new IiaResponse(iiaInternal.getId(), iiaInternal.getConditionsHash());

        return Response.ok(response).build();
    }

    @GET
    @Path("heis")
    @InternalAuthenticate
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response iiaHeis() {
        List<HeiEntry> heis = registryClient.getIiaHeisWithUrls();

        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(heis) {
        };
        return javax.ws.rs.core.Response.ok(entity).build();
    }

    @POST
    @Path("iias-index")
    @InternalAuthenticate
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response iiasIndex(ClientRequest clientRequest) {
        ClientResponse iiaResponse = restClient.sendRequest(clientRequest, eu.erasmuswithoutpaper.api.iias.endpoints.IiasIndexResponse.class);

        try {
            if (iiaResponse.getStatusCode() <= 599 && iiaResponse.getStatusCode() >= 400) {
                sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "iias", "index", Integer.toString(iiaResponse.getStatusCode()), iiaResponse.getErrorMessage(), null);
            } else if (iiaResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
                sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "iias", "index", Integer.toString(iiaResponse.getStatusCode()), iiaResponse.getErrorMessage(), "Error");
            }
        } catch (Exception e) {

        }

        GenericEntity<eu.erasmuswithoutpaper.api.iias.endpoints.IiasIndexResponse> entity = null;
        try {
            eu.erasmuswithoutpaper.api.iias.endpoints.IiasIndexResponse index = (eu.erasmuswithoutpaper.api.iias.endpoints.IiasIndexResponse) iiaResponse.getResult();
            entity = new GenericEntity<eu.erasmuswithoutpaper.api.iias.endpoints.IiasIndexResponse>(index) {
            };
        } catch (Exception e) {
            return javax.ws.rs.core.Response.serverError().entity(iiaResponse.getErrorMessage()).build();
        }

        return javax.ws.rs.core.Response.ok(entity).build();
    }

    @POST
    @Path("iias")
    @InternalAuthenticate
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response iias(ClientRequest clientRequest) {
        ClientResponse iiaResponse = restClient.sendRequest(clientRequest, eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse.class);

        try {
            if (iiaResponse.getStatusCode() <= 599 && iiaResponse.getStatusCode() >= 400) {
                sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "iias", "get", Integer.toString(iiaResponse.getStatusCode()), iiaResponse.getErrorMessage(), null);
            } else if (iiaResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
                sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "iias", "get", Integer.toString(iiaResponse.getStatusCode()), iiaResponse.getErrorMessage(), "Error");
            }
        } catch (Exception e) {

        }

        return javax.ws.rs.core.Response.ok(iiaResponse).build();
    }

    @POST
    @Path("update")
    @InternalAuthenticate
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response update(IiasGetResponse.Iia iia) {
        LOG.fine("UPDATE: Start Update");
        Iia iiaInternal = new Iia();

        iiaConverter.convertToIia(iia, iiaInternal, iiasEJB.findAllInstitutions());

        //LOG.fine("UPDATE: Iia Code: " + iiaInternal.getCooperationConditions().stream().map(c -> c.getDuration().getNumber().toString()).collect(Collectors.joining(", ")));

        //Find the iia by code
        List<Iia> foundIias = iiasEJB.findByIiaCode(iiaInternal.getIiaCode());

        Iia foundIia = (foundIias != null && !foundIias.isEmpty()) ? foundIias.get(0) : null;

        //Check if the iia exists
        if (foundIia == null) {
            //Find the iia by id
            foundIias = iiasEJB.findByIdList(iiaInternal.getId());

            foundIia = (foundIias != null && !foundIias.isEmpty()) ? foundIias.get(0) : null;

            if (foundIia == null) {
                return javax.ws.rs.core.Response.status(Response.Status.NOT_FOUND).build();
            }
        }

        if (iiaInternal.getIiaCode() == null || iiaInternal.getIiaCode().isEmpty()) {
            System.err.println("Update Algoria: Mising iiaCode");
            return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();
        }

        if (iiaInternal.getCooperationConditions().size() == 0) {
            System.err.println("Update Algoria: Mising Cooperation Conditions");
            return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();

        } else if (!validateConditions(iiaInternal.getCooperationConditions())) {
            System.err.println("Update Algoria: Invalids Cooperation Conditions");
            return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();
        }
        LOG.fine("OLODOLD Hash: " + foundIia.getConditionsHash());
        String oldHash = foundIia.getConditionsHash();

        String newHash = iiasEJB.updateIia(iiaInternal, foundIia, foundIia.getHashPartner());

        LOG.fine("OLD HASH: " + oldHash);
        LOG.fine("NEW HASH: " + newHash);
        if (!oldHash.equals(newHash)) {
            iiasEJB.deleteAssociatedIiaApprovals(foundIia.getId());
        }

        //Notify the partner about the modification using the API GUI IIA CNR
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            notifyPartner(iiaInternal);
        });

        IiaResponse response = new IiaResponse(foundIia.getId(), foundIia.getConditionsHash());
        return javax.ws.rs.core.Response.ok(response).build();
    }

    private List<ClientResponse> notifyPartner(Iia iia) {
        LOG.fine("NOTIFY: Send notification");

        List<ClientResponse> partnersResponseList = new ArrayList<>();

        //Getting agreement partners
        IiaPartner partnerSending = null;
        IiaPartner partnerReceiving = null;

        Set<NotifyAux> cnrUrls = new HashSet<>();

        List<Institution> institutions = iiasEJB.findAllInstitutions();
        for (CooperationCondition condition : iia.getCooperationConditions()) {
            partnerSending = condition.getSendingPartner();
            partnerReceiving = condition.getReceivingPartner();

            LOG.fine("NOTIFY: partnerSending: " + partnerSending.getInstitutionId());
            LOG.fine("NOTIFY: partnerReceiving: " + partnerReceiving.getInstitutionId());

            Map<String, String> urls = null;
            for (Institution institution : institutions) {

                if (!institution.getInstitutionId().equals(partnerSending.getInstitutionId())) {

                    //Get the url for notify the institute not supported by our EWP
                    urls = registryClient.getIiaCnrHeiUrls(partnerSending.getInstitutionId());
                    LOG.fine("NOTIFY: urls: " + urls);

                    if (urls != null) {
                        for (Map.Entry<String, String> entry : urls.entrySet()) {
                            cnrUrls.add(new NotifyAux(partnerSending.getInstitutionId(), entry.getValue()));
                        }
                    }

                } else if (!institution.getInstitutionId().equals(partnerReceiving.getInstitutionId())) {

                    //Get the url for notify the institute not supported by our EWP
                    urls = registryClient.getIiaCnrHeiUrls(partnerReceiving.getInstitutionId());
                    LOG.fine("NOTIFY: urls: " + urls);

                    if (urls != null) {
                        for (Map.Entry<String, String> entry : urls.entrySet()) {
                            cnrUrls.add(new NotifyAux(partnerReceiving.getInstitutionId(), entry.getValue()));
                        }

                    }
                }

            }
        }

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
            paramsMap.put("iia_id", Collections.singletonList(iia.getId()));
            ParamsClass paramsClass = new ParamsClass();
            paramsClass.setUnknownFields(paramsMap);
            clientRequest.setParams(paramsClass);

            ClientResponse iiaResponse = restClient.sendRequest(clientRequest, Empty.class);

            try {
                if (iiaResponse.getStatusCode() <= 599 && iiaResponse.getStatusCode() >= 400) {
                    sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "iia-cnr", null, Integer.toString(iiaResponse.getStatusCode()), iiaResponse.getErrorMessage(), null);
                } else if (iiaResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
                    sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "iia-cnr", null, Integer.toString(iiaResponse.getStatusCode()), iiaResponse.getErrorMessage(), "Error");
                }
            } catch (Exception e) {

            }

            partnersResponseList.add(iiaResponse);
        });

        return partnersResponseList;

    }

    private List<ClientResponse> notifyPartnerApproval(Iia iia) {
        LOG.fine("NOTIFY: Send notification");

        List<ClientResponse> partnersResponseList = new ArrayList<>();

        //Getting agreement partners
        IiaPartner partnerSending = null;
        IiaPartner partnerReceiving = null;

        Set<NotifyAux> cnrUrls = new HashSet<>();

        List<Institution> institutions = iiasEJB.findAllInstitutions();
        for (CooperationCondition condition : iia.getCooperationConditions()) {
            partnerSending = condition.getSendingPartner();
            partnerReceiving = condition.getReceivingPartner();

            LOG.fine("NOTIFY: partnerSending: " + partnerSending.getInstitutionId());
            LOG.fine("NOTIFY: partnerReceiving: " + partnerReceiving.getInstitutionId());

            Map<String, String> urls = null;
            for (Institution institution : institutions) {

                if (!institution.getInstitutionId().equals(partnerSending.getInstitutionId())) {

                    //Get the url for notify the institute not supported by our EWP
                    urls = registryClient.getIiaApprovalCnrHeiUrls(partnerSending.getInstitutionId());

                    LOG.fine("NOTIFY: urls: " + urls);

                    if (urls != null) {
                        for (Map.Entry<String, String> entry : urls.entrySet()) {
                            cnrUrls.add(new NotifyAux(partnerSending.getInstitutionId(), entry.getValue(), partnerSending.getIiaId()));
                        }
                    }

                } else if (!institution.getInstitutionId().equals(partnerReceiving.getInstitutionId())) {

                    //Get the url for notify the institute not supported by our EWP
                    urls = registryClient.getIiaApprovalCnrHeiUrls(partnerReceiving.getInstitutionId());

                    LOG.fine("NOTIFY: urls: " + urls);

                    if (urls != null) {
                        for (Map.Entry<String, String> entry : urls.entrySet()) {
                            cnrUrls.add(new NotifyAux(partnerReceiving.getInstitutionId(), entry.getValue(), partnerReceiving.getIiaId()));
                        }

                    }
                }

            }
        }

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
            paramsMap.put("iia_id", Collections.singletonList(url.getIiaId()));
            ParamsClass paramsClass = new ParamsClass();
            paramsClass.setUnknownFields(paramsMap);
            clientRequest.setParams(paramsClass);

            ClientResponse iiaResponse = restClient.sendRequest(clientRequest, Empty.class);

            try {
                if (iiaResponse.getStatusCode() <= 599 && iiaResponse.getStatusCode() >= 400) {
                    sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "iia-cnr", null, Integer.toString(iiaResponse.getStatusCode()), iiaResponse.getErrorMessage(), null);
                } else if (iiaResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
                    sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "iia-cnr", null, Integer.toString(iiaResponse.getStatusCode()), iiaResponse.getErrorMessage(), "Error");
                }
            } catch (Exception e) {

            }

            partnersResponseList.add(iiaResponse);
        });

        return partnersResponseList;

    }


    @POST
    @Path("iias-approve")
    @InternalAuthenticate
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response iiasApprove(@FormParam("hei_id") String heiId, @FormParam("iia_code") String
            iiaCode) {

        String localHeiId = iiasEJB.getHeiId();
        //seek the iia by code and by the ouid of the sending institution
        List<Iia> foundIia = iiasEJB.findByIiaCode(iiaCode);

        Predicate<Iia> condition = new Predicate<Iia>() {
            @Override
            public boolean test(Iia iia) {
                List<CooperationCondition> cooperationConditions = iia.getCooperationConditions();

                List<CooperationCondition> filtered = cooperationConditions.stream().filter(c -> localHeiId.equals(c.getSendingPartner().getInstitutionId())).collect(Collectors.toList());
                return !filtered.isEmpty();
            }
        };

        foundIia.stream().filter(condition).collect(Collectors.toList());

        if (foundIia.isEmpty()) {
            return javax.ws.rs.core.Response.status(Response.Status.NOT_FOUND).build();
        }

        //get the first one found
        Iia theIia = foundIia.get(0);

        /*if(!hashSitEquals(theIia)) {
            return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();
        }*/

        LOG.fine("Iia found: " + theIia.getId());

        IiaApproval approval = new IiaApproval();
        approval.setIiaCode(iiaCode);
        approval.setIia(theIia);
        approval.setHeiId(localHeiId);
        approval.setConditionsHash(theIia.getConditionsHash());

        iiasEJB.insertIiaApproval(approval);

        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            notifyPartnerApproval(theIia);
        });

        return javax.ws.rs.core.Response.ok().build();
        /*
        //Getting agreement partners
        IiaPartner partnerReceiving = null;

        for (CooperationCondition cooCondition : theIia.getCooperationConditions()) {
            partnerReceiving = cooCondition.getReceivingPartner();
        }

        //Verify if the agreement is approved by the other institution.
        Map<String, String> urlsGet = registryClient.getIiaApprovalHeiUrls(heiId);
        List<String> urlGetValues = new ArrayList<String>(urlsGet.values());

        ClientRequest clientRequestGetIia = new ClientRequest();
        clientRequestGetIia.setUrl(urlGetValues.get(0));//get the first and only one url
        clientRequestGetIia.setHeiId(partnerReceiving.getInstitutionId());
        clientRequestGetIia.setMethod(HttpMethodEnum.GET);
        clientRequestGetIia.setHttpsec(true);

        List<String> iiaIds = new ArrayList<>();
        iiaIds.add(theIia.getId());

        Map<String, List<String>> params = new HashMap<>();
        params.put("approving_hei_id", Arrays.asList(partnerReceiving.getInstitutionId()));
        params.put("owner_hei_id", Arrays.asList(heiId));
        params.put("iia_id", iiaIds);

        ParamsClass pc = new ParamsClass();
        pc.setUnknownFields(params);

        clientRequestGetIia.setParams(pc);

        ClientResponse iiaApprovalResponse = restClient.sendRequest(clientRequestGetIia, eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse.class);

        try {
            if (iiaApprovalResponse.getStatusCode() <= 599 && iiaApprovalResponse.getStatusCode() >= 400) {
                sendMonitoringService.sendMonitoring(clientRequestGetIia.getHeiId(), "iias-approval", null, Integer.toString(iiaApprovalResponse.getStatusCode()), iiaApprovalResponse.getErrorMessage(), null);
            } else if (iiaApprovalResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
                sendMonitoringService.sendMonitoring(clientRequestGetIia.getHeiId(), "iias-approval", null, Integer.toString(iiaApprovalResponse.getStatusCode()), iiaApprovalResponse.getErrorMessage(), "Error");
            }
        } catch (Exception e) {

        }

        eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse response = (IiasApprovalResponse) iiaApprovalResponse.getResult();

        Approval approval = response.getApproval().stream()
                .filter(app -> theIia.getIiaCode().equals(app.getIiaId()))
                .findAny()
                .orElse(null);

        if (approval != null) {
            theIia.setInEfect(true);//it was approved
            //em.persist(theIia);
        }

        //Get the url for notify the institute
        Map<String, String> urls = registryClient.getIiaApprovalCnrHeiUrls(heiId);
        List<String> urlValues = new ArrayList<String>(urls.values());

        //Notify the other institution about the approval
        ClientRequest clientRequestNotifyApproval = new ClientRequest();
        clientRequestNotifyApproval.setUrl(urlValues.get(0));//get the first and only one url
        clientRequestNotifyApproval.setHeiId(partnerReceiving.getInstitutionId());
        clientRequestNotifyApproval.setMethod(HttpMethodEnum.POST);
        clientRequestNotifyApproval.setHttpsec(true);

        Map<String, List<String>> paramsCnr = new HashMap<>();
        paramsCnr.put("approving_hei_id", Arrays.asList(partnerReceiving.getInstitutionId()));
        paramsCnr.put("owner_hei_id", Arrays.asList(heiId));
        paramsCnr.put("iia_id", iiaIds);

        ParamsClass pc2 = new ParamsClass();
        pc2.setUnknownFields(paramsCnr);

        clientRequestNotifyApproval.setParams(pc2);

        ClientResponse iiaResponse = restClient.sendRequest(clientRequestNotifyApproval, Object.class);

        try {
            if (iiaResponse.getStatusCode() <= 599 && iiaResponse.getStatusCode() >= 400) {
                sendMonitoringService.sendMonitoring(clientRequestNotifyApproval.getHeiId(), "iia-approval-cnr", null, Integer.toString(iiaResponse.getStatusCode()), iiaResponse.getErrorMessage(), null);
            } else if (iiaResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
                sendMonitoringService.sendMonitoring(clientRequestNotifyApproval.getHeiId(), "iia-approval-cnr", null, Integer.toString(iiaResponse.getStatusCode()), iiaResponse.getErrorMessage(), "Error");
            }
        } catch (Exception e) {

        }

        return javax.ws.rs.core.Response.ok(iiaResponse).build();*/
    }
    @DELETE
    @Path("delete")
    @InternalAuthenticate
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response delete(@FormParam("iia_id") String iiaId) {
        if (iiaId == null || iiaId.isEmpty()) {
            return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();
        }

        Iia iia = iiasEJB.findById(iiaId);

        if (iia == null) {
            return javax.ws.rs.core.Response.status(Response.Status.NOT_FOUND).build();
        }

        if(iiasEJB.findApprovedVersion(iiaId) != null) {
            return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).entity("The IIA is approved").build();
        }

        iiasEJB.deleteIia(iia);

        // Notify the partner about the deletion
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            notifyPartner(iia);
        });

        return javax.ws.rs.core.Response.ok().build();
    }

    @DELETE
    @Path("delete/all")
    @InternalAuthenticate
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response deleteAll() {
        iiasEJB.deleteAllIias();

        return javax.ws.rs.core.Response.ok().build();
    }

    private boolean validateConditions(List<CooperationCondition> conditions) {

        Predicate<CooperationCondition> condition = new Predicate<CooperationCondition>() {
            @Override
            public boolean test(CooperationCondition c) {
                if (c.getSendingPartner() == null || c.getReceivingPartner() == null) {
                    return true;
                } else if (c.getSendingPartner().getInstitutionId() == null || c.getSendingPartner().getInstitutionId().isEmpty()) {
                    return true;
                } else if (c.getReceivingPartner().getInstitutionId() == null || c.getReceivingPartner().getInstitutionId().isEmpty()) {
                    return true;
                }
                return false;
            }

        };

        List<CooperationCondition> wrongConditions = conditions.stream().filter(condition).collect(Collectors.toList());
        return wrongConditions.isEmpty();
    }

    @GET
    @Path("clone")
    @InternalAuthenticate
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response clone(@QueryParam("iia_id") String iiaId) {
        if (iiaId == null || iiaId.isEmpty()) {
            return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();
        }

        Iia iia = iiasEJB.findById(iiaId);

        if (iia == null) {
            return javax.ws.rs.core.Response.status(Response.Status.NOT_FOUND).build();
        }

        String heiId = iiasEJB.getHeiId();

        List<IiasGetResponse.Iia> iiaResponse = iiaConverter.convertToIias(heiId, Collections.singletonList(iia));
        Iia newIia = new Iia();
        iiaConverter.convertToIia(iiaResponse.get(0), newIia, iiasEJB.findAllInstitutions());

        Iia clonedIia = iiasEJB.saveApprovedVersion(newIia, iia.getModifyDate(), iia.getHashPartner());

        return javax.ws.rs.core.Response.ok(clonedIia).build();
    }

    @GET
    @Path("revert")
    @InternalAuthenticate
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response revert(@QueryParam("iia_id") String iiaId) {
        if (iiaId == null || iiaId.isEmpty()) {
            return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();
        }

        Iia iia = iiasEJB.findById(iiaId);

        if (iia == null) {
            return javax.ws.rs.core.Response.status(Response.Status.NOT_FOUND).build();
        }

        Iia clonedIia = iiasEJB.findApprovedVersion(iiaId);

        if (clonedIia == null) {
            return javax.ws.rs.core.Response.status(Response.Status.NOT_FOUND).build();
        }

        iiasEJB.revertIia(iia.getId(), clonedIia.getId());

        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            notifyPartner(iia);
        });

        return javax.ws.rs.core.Response.ok().build();
    }

    @GET
    @Path("terminate")
    @InternalAuthenticate
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response terminate(@QueryParam("iia_id") String iiaId) {
        if (iiaId == null || iiaId.isEmpty()) {
            return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();
        }

        Iia iia = iiasEJB.findById(iiaId);

        if (iia == null) {
            return javax.ws.rs.core.Response.status(Response.Status.NOT_FOUND).build();
        }

        if(iiasEJB.findApprovedVersion(iiaId) == null) {
            return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).entity("The IIA is not approved").build();
        }

        LOG.fine("GuiIiaRecource: Terminating IIA: " + iia.getId());

        LOG.fine("GuiIiaRecource: Before hash: " + iia.getConditionsHash());

        iiasEJB.terminateIia(iia.getId());

        // Notify the partner about the deletion
        CompletableFuture.runAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            notifyPartner(iia);
        });

        return javax.ws.rs.core.Response.ok().build();
    }

    @GET
    @Path("get-partner")
    @InternalAuthenticate
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response getPartner(@QueryParam("iia_id") String iiaId, @QueryParam("partner_iia_id") String partnerIiaId, @QueryParam("partner_hei_id") String partnerHeiId) {
        if(iiaId != null && !iiaId.isEmpty()) {

            String localHeiId = iiasEJB.getHeiId();
            Iia iia = iiasEJB.findById(iiaId);
            if (iia == null) {
                return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();
            }
            for (CooperationCondition c : iia.getCooperationConditions()) {
                LOG.fine("GuiIiaRecource: Sending Partner: " + c.getSendingPartner().getInstitutionId());
                LOG.fine("GuiIiaRecource: Receiving Partner: " + c.getReceivingPartner().getInstitutionId());
                if (c.getSendingPartner().getInstitutionId().equals(localHeiId)) {
                    partnerHeiId = c.getReceivingPartner().getInstitutionId();
                } else if (c.getReceivingPartner().getInstitutionId().equals(localHeiId)) {
                    partnerHeiId = c.getSendingPartner().getInstitutionId();
                }
            }
        }else {
            iiaId = partnerIiaId;
        }

        Map<String, String> map = registryClient.getIiaHeiUrls(partnerHeiId);
        if (map == null) {
            return javax.ws.rs.core.Response.status(Response.Status.NOT_FOUND).build();
        }

        String url = map.get("get-url");
        if (url == null) {
            return javax.ws.rs.core.Response.status(Response.Status.NOT_FOUND).build();
        }

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setHeiId(partnerHeiId);
        clientRequest.setHttpsec(true);
        clientRequest.setMethod(HttpMethodEnum.GET);
        clientRequest.setUrl(url);

        Map<String, List<String>> paramsMap = new HashMap<>();
        paramsMap.put("iia_id", Arrays.asList(iiaId));
        ParamsClass params = new ParamsClass();
        params.setUnknownFields(paramsMap);
        clientRequest.setParams(params);

        ClientResponse clientResponse = restClient.sendRequest(clientRequest, IiasGetResponse.class);

        IiasGetResponse responseEnity = (IiasGetResponse) clientResponse.getResult();

        if (responseEnity == null) {
            return javax.ws.rs.core.Response.status(Response.Status.NOT_FOUND).build();
        }

        List<IiasGetResponse.Iia> iias = responseEnity.getIia();

        return javax.ws.rs.core.Response.ok(iias).build();
    }

    @GET
    @Path("get-approval")
    @InternalAuthenticate
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response getApproval(@QueryParam("iia_id") String iiaId) {
        if (iiaId == null || iiaId.isEmpty()) {
            return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();
        }
        String localHeiId = iiasEJB.getHeiId();
        String partnerHeiId = "";
        Iia iia = iiasEJB.findById(iiaId);
        if (iia == null) {
            return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();
        }
        for (CooperationCondition c : iia.getCooperationConditions()) {
            LOG.fine("GuiIiaRecource: Sending Partner: " + c.getSendingPartner().getInstitutionId());
            LOG.fine("GuiIiaRecource: Receiving Partner: " + c.getReceivingPartner().getInstitutionId());
            if (c.getSendingPartner().getInstitutionId().equals(localHeiId)) {
                partnerHeiId = c.getReceivingPartner().getInstitutionId();
            } else if (c.getReceivingPartner().getInstitutionId().equals(localHeiId)) {
                partnerHeiId = c.getSendingPartner().getInstitutionId();
            }
        }

        Map<String, String> map = registryClient.getIiaApprovalHeiUrls(partnerHeiId);
        if (map == null) {
            return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();
        }

        String url = map.get("url");
        if (url == null) {
            return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();
        }

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setHeiId(partnerHeiId);
        clientRequest.setHttpsec(true);
        clientRequest.setMethod(HttpMethodEnum.GET);
        clientRequest.setUrl(url);

        Map<String, List<String>> paramsMap = new HashMap<>();
        paramsMap.put("iia_id", Arrays.asList(iiaId));
        ParamsClass params = new ParamsClass();
        params.setUnknownFields(paramsMap);
        clientRequest.setParams(params);

        ClientResponse clientResponse = restClient.sendRequest(clientRequest, IiasApprovalResponse.class);
        IiasApprovalResponse responseEnity = (IiasApprovalResponse) clientResponse.getResult();

        if (responseEnity == null) {
            return javax.ws.rs.core.Response.status(Response.Status.NOT_FOUND).build();
        }

        if (responseEnity.getApproval() == null || responseEnity.getApproval().isEmpty()) {
            return javax.ws.rs.core.Response.status(Response.Status.NOT_FOUND).build();
        }

        IiasApprovalResponse.Approval approval = responseEnity.getApproval().get(0);


        return javax.ws.rs.core.Response.ok(approval).build();
    }


    private boolean hashSitEquals(Iia iia) {
        String localHeiId = iiasEJB.getHeiId();
        if(iia.getCooperationConditions() == null || iia.getCooperationConditions().isEmpty()) {
            return false;
        }
        String partnerHeiId = null;
        String partnerIiaId = null;
        if(localHeiId.equals(iia.getCooperationConditions().get(0).getSendingPartner().getInstitutionId())) {
            partnerHeiId = iia.getCooperationConditions().get(0).getReceivingPartner().getInstitutionId();
            partnerIiaId = iia.getCooperationConditions().get(0).getReceivingPartner().getIiaId();
        }
        if (localHeiId.equals(iia.getCooperationConditions().get(0).getReceivingPartner().getInstitutionId())) {
            partnerHeiId = iia.getCooperationConditions().get(0).getSendingPartner().getInstitutionId();
            partnerIiaId = iia.getCooperationConditions().get(0).getSendingPartner().getIiaId();
        }
        IiasGetResponse.Iia sendIia = null;
        try {
            sendIia = sendGet(partnerHeiId, partnerIiaId);
        } catch (Exception e) {

        }
        if (sendIia == null || sendIia.getIiaHash() == null) {
            return false;
        }

        LOG.fine("GuiIiaRecource: SendHash: " + sendIia.getIiaHash());
        LOG.fine("GuiIiaRecource: LocalPartnerHash: " + iia.getHashPartner());

        return sendIia.getIiaHash().equals(iia.getHashPartner());

    }

    private IiasGetResponse.Iia sendGet(String heiId, String iiaId) throws Exception {
        LOG.fine("GuiIiaRecource: Empezando GET tras CNR");
        Map<String, String> map = registryClient.getIiaHeiUrls(heiId);
        if (map == null) {
            return null;
        }

        LOG.fine("GuiIiaRecource: MAP ENCONTRADO");

        String url = map.get("get-url");
        if (url == null) {
            return null;
        }

        LOG.fine("GuiIiaRecource: Url encontrada: " + url);

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setHeiId(heiId);
        clientRequest.setHttpsec(true);
        clientRequest.setMethod(HttpMethodEnum.GET);
        clientRequest.setUrl(url);

        Map<String, List<String>> paramsMap = new HashMap<>();
        paramsMap.put("iia_id", Arrays.asList(iiaId));
        ParamsClass params = new ParamsClass();
        params.setUnknownFields(paramsMap);
        clientRequest.setParams(params);

        LOG.fine("GuiIiaRecource: Parametros encontrados: ");

        paramsMap.forEach((key, value) -> {
            LOG.fine("\t\t\t\t" + key + ":" + value);
        });

        ClientResponse clientResponse = restClient.sendRequest(clientRequest, IiasGetResponse.class);

        LOG.fine("GuiIiaRecource: Respuesta del cliente " + clientResponse.getStatusCode());

        if (clientResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
            if (clientResponse.getStatusCode() <= 599 && clientResponse.getStatusCode() >= 400) {
                sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "iias", "get", Integer.toString(clientResponse.getStatusCode()), clientResponse.getErrorMessage(), null);
            } else if (clientResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
                sendMonitoringService.sendMonitoring(clientRequest.getHeiId(), "iias", "get", Integer.toString(clientResponse.getStatusCode()), clientResponse.getErrorMessage(), "Error");
            }
            return null;
        }

        LOG.fine("GuiIiaRecource: Respuesta raw: " + clientResponse.getRawResponse());

        IiasGetResponse responseEnity = (IiasGetResponse) clientResponse.getResult();

        return responseEnity.getIia().get(0);
    }

}
