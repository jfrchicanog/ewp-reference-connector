package eu.erasmuswithoutpaper.omobility.las.boundary;

import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.erasmuswithoutpaper.api.architecture.Empty;
import eu.erasmuswithoutpaper.api.architecture.MultilineStringWithOptionalLang;
import eu.erasmuswithoutpaper.api.omobilities.las.OmobilityLas;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.LasOutgoingStatsResponse;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.LearningAgreement;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasGetResponse;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasIndexResponse;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasUpdateRequest;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasUpdateResponse;
import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import eu.erasmuswithoutpaper.common.boundary.ParamsClass;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.imobility.entity.IMobility;
import eu.erasmuswithoutpaper.imobility.entity.IMobilityStatus;
import eu.erasmuswithoutpaper.omobility.las.control.LearningAgreementEJB;
import eu.erasmuswithoutpaper.omobility.las.control.OutgoingMobilityLearningAgreementsConverter;
import eu.erasmuswithoutpaper.omobility.las.entity.*;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import eu.erasmuswithoutpaper.security.EwpAuthenticate;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Path("omobilities/las")
public class OutgoingMobilityLearningAgreementsResource {

    @EJB
    LearningAgreementEJB learningAgreementEJB;

    @Inject
    GlobalProperties properties;

    @Inject
    OutgoingMobilityLearningAgreementsConverter converter;

    @Inject
    RegistryClient registryClient;

    @Context
    HttpServletRequest httpRequest;

    @Inject
    OmobilitiesLasAuxThread ait;

    @Inject
    RestClient restClient;

    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(OutgoingMobilityLearningAgreementsResource.class.getCanonicalName());

    @GET
    @Path("index")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response indexGet(@QueryParam("sending_hei_id") List<String> sendingHeiIds, @QueryParam("receiving_hei_id") List<String> receivingHeiIdList, @QueryParam("receiving_academic_year_id") List<String> receiving_academic_year_ids,
                                              @QueryParam("global_id") List<String> globalIds, @QueryParam("mobility_type") List<String> mobilityTypes, @QueryParam("modified_since") List<String> modifiedSinces) {
        return omobilityLasIndex(sendingHeiIds, receivingHeiIdList, receiving_academic_year_ids, globalIds, mobilityTypes, modifiedSinces);
    }

    @POST
    @Path("index")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response indexPost(@FormParam("sending_hei_id") List<String> sendingHeiIds, @FormParam("receiving_hei_id") List<String> receivingHeiIdList, @FormParam("receiving_academic_year_id") List<String> receiving_academic_year_ids,
                                               @FormParam("global_id") List<String> globalIds, @FormParam("mobility_type") List<String> mobilityTypes, @FormParam("modified_since") List<String> modifiedSinces) {
        return omobilityLasIndex(sendingHeiIds, receivingHeiIdList, receiving_academic_year_ids, globalIds, mobilityTypes, modifiedSinces);
    }

    @GET
    @Path("index_test")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response indexGetTest(@QueryParam("sending_hei_id") List<String> sendingHeiIds, @QueryParam("receiving_hei_id") List<String> receivingHeiIdList, @QueryParam("receiving_academic_year_id") List<String> receiving_academic_year_ids,
                                              @QueryParam("global_id") List<String> globalIds, @QueryParam("mobility_type") List<String> mobilityTypes, @QueryParam("modified_since") List<String> modifiedSinces) {
        return omobilityLasIndexAlgoria(sendingHeiIds, receivingHeiIdList, receiving_academic_year_ids, globalIds, mobilityTypes, modifiedSinces);
    }

    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response omobilityGetGet(@QueryParam("sending_hei_id") List<String> sendingHeiId, @QueryParam("omobility_id") List<String> mobilityIdList) {
        return mobilityGet(sendingHeiId, mobilityIdList);
    }

    @POST
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response omobilityGetPost(@FormParam("sending_hei_id") List<String> sendingHeiId, @FormParam("omobility_id") List<String> mobilityIdList) {
        return mobilityGet(sendingHeiId, mobilityIdList);
    }

    @POST
    @Path("update")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response omobilityLasUpdatePost(OmobilityLasUpdateRequest request) {
        if (request == null) {
            throw new EwpWebApplicationException("No update data was sent", Response.Status.BAD_REQUEST);
        }

        if (request.getSendingHeiId() == null || request.getSendingHeiId().isEmpty()) {
            throw new EwpWebApplicationException("Mising required parameter, sending-hei-id is required", Response.Status.BAD_REQUEST);
        }

        /*Collection<String> heisCoveredByCertificate;
        if (httpRequest.getAttribute("EwpRequestRSAPublicKey") != null) {
            heisCoveredByCertificate = registryClient.getHeisCoveredByClientKey((RSAPublicKey) httpRequest.getAttribute("EwpRequestRSAPublicKey"));
        } else {
            heisCoveredByCertificate = registryClient.getHeisCoveredByCertificate((X509Certificate) httpRequest.getAttribute("EwpRequestCertificate"));
        }

        if (!heisCoveredByCertificate.contains(request.getSendingHeiId())) {
            throw new EwpWebApplicationException("The client signature does not cover the receiving HEI of the mobility.", Response.Status.BAD_REQUEST);
        }*/

        if (request.getCommentProposalV1() == null && request.getApproveProposalV1() == null) {
            throw new EwpWebApplicationException("Mising required parameter, approve-proposal-v1 and comment-proposal-v1 both of them can not be missing", Response.Status.BAD_REQUEST);
        }

        if (request.getApproveProposalV1() != null) {
            if (request.getApproveProposalV1().getOmobilityId() == null || request.getApproveProposalV1().getOmobilityId().isEmpty()) {
                throw new EwpWebApplicationException("Mising required parameter, omobility-id is required", Response.Status.BAD_REQUEST);
            }

            if (request.getApproveProposalV1().getChangesProposalId() == null) {
                throw new EwpWebApplicationException("Mising required parameter, changes-proposal-id is required", Response.Status.BAD_REQUEST);
            }

            LOG.fine("Starting UPD-ACCEPT for " + request.getApproveProposalV1().getOmobilityId() + " omobility learning agreements");

            OlearningAgreement olearningAgreement = learningAgreementEJB.findById(request.getApproveProposalV1().getOmobilityId());

            if (olearningAgreement == null) {
                throw new EwpWebApplicationException("Learning agreement does not exist", Response.Status.BAD_REQUEST);
            } else {
                if (olearningAgreement.getChangesProposal() == null) {
                    throw new EwpWebApplicationException("Changes proposal does not exist", Response.Status.BAD_REQUEST);
                }
                if (!olearningAgreement.getChangesProposal().getId().equals(request.getApproveProposalV1().getChangesProposalId())) {
                    throw new EwpWebApplicationException("Changes proposal does not match", Response.Status.CONFLICT);
                }
            }

            learningAgreementEJB.approveChangesProposal(request, request.getApproveProposalV1().getOmobilityId());
        } else if (request.getCommentProposalV1() != null) {
            if (request.getCommentProposalV1().getOmobilityId() == null || request.getCommentProposalV1().getOmobilityId().isEmpty()) {
                throw new EwpWebApplicationException("Mising required parameter, omobility-id is required", Response.Status.BAD_REQUEST);
            }

            if (request.getCommentProposalV1().getChangesProposalId() == null) {
                throw new EwpWebApplicationException("Mising required parameter, changes-proposal-id is required", Response.Status.BAD_REQUEST);
            }

            if (request.getCommentProposalV1().getComment() == null) {
                throw new EwpWebApplicationException("Mising required parameter, comment is required", Response.Status.BAD_REQUEST);
            }

            LOG.fine("Starting UPD-REJECT for " + request.getCommentProposalV1().getOmobilityId() + " omobility learning agreements");

            OlearningAgreement olearningAgreement = learningAgreementEJB.findById(request.getCommentProposalV1().getOmobilityId());

            if (olearningAgreement == null) {
                throw new EwpWebApplicationException("Learning agreement does not exist", Response.Status.BAD_REQUEST);
            } else {
                if (olearningAgreement.getChangesProposal() == null) {
                    throw new EwpWebApplicationException("Changes proposal does not exist", Response.Status.BAD_REQUEST);
                }
                if (!olearningAgreement.getChangesProposal().getId().equals(request.getCommentProposalV1().getChangesProposalId())) {
                    throw new EwpWebApplicationException("Changes proposal does not match", Response.Status.CONFLICT);
                }
            }

            learningAgreementEJB.rejectChangesProposal(request, request.getCommentProposalV1().getOmobilityId());
        }

        OmobilityLasUpdateResponse response = new OmobilityLasUpdateResponse();
        MultilineStringWithOptionalLang message = new MultilineStringWithOptionalLang();
        message.setLang("en");
        message.setValue("The update request was received with success.");
        response.getSuccessUserMessage().add(message);

        return javax.ws.rs.core.Response.ok(response).build();
    }


    @GET
    @Path("stats")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response omobilityGetStats() {
        List<Institution> institutionList = learningAgreementEJB.getInternalInstitution();
        if (institutionList.size() != 1) {
            throw new IllegalStateException("Internal error: more than one insitution covered");
        }
        String heiId = institutionList.get(0).getId();

        return omobilityStatsGet(heiId);
    }

    @POST
    @Path("cnr")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response omobilitiesLasCnr(@FormParam("sending_hei_id") String sendingHeiId, @FormParam("omobility_id") List<String> omobilityIdList) {
        if (sendingHeiId == null || sendingHeiId.trim().isEmpty()) {
            throw new EwpWebApplicationException("Missing argumanets for get.", Response.Status.BAD_REQUEST);
        }

        if (omobilityIdList.size() > properties.getMaxOmobilitylasIds()) {
            throw new EwpWebApplicationException("Max number of omobility learning agreements id's has exceeded.", Response.Status.BAD_REQUEST);
        }

        //TODO: Notify algoria

        LOG.info("Starting CNR for " + omobilityIdList.size() + " omobility learning agreements");

        for (String omobilityId : omobilityIdList) {
            CompletableFuture.runAsync(() -> {
                try {
                    ait.createLas(sendingHeiId, omobilityId);
                } catch (Exception e) {
                    LOG.fine("Error in AuxIiaApprovalThread: " + e.getMessage());
                }
            });
        }

        eu.erasmuswithoutpaper.api.omobilities.las.cnr.endpoints.ObjectFactory factory = new eu.erasmuswithoutpaper.api.omobilities.las.cnr.endpoints.ObjectFactory();

        return javax.ws.rs.core.Response.ok(factory.createOmobilityLaCnrResponse(new Empty())).build();
    }

    private javax.ws.rs.core.Response omobilityStatsGet(String heiId) {
        LasOutgoingStatsResponse response = new LasOutgoingStatsResponse();

        //Filter learning agreement 
        List<OlearningAgreement> omobilityLasList = learningAgreementEJB.findByReceivingHeiId(heiId);

        if (!omobilityLasList.isEmpty()) {

            Collection<String> heisCoveredByCertificate;
            if (httpRequest.getAttribute("EwpRequestRSAPublicKey") != null) {
                heisCoveredByCertificate = registryClient.getHeisCoveredByClientKey((RSAPublicKey) httpRequest.getAttribute("EwpRequestRSAPublicKey"));
            } else {
                heisCoveredByCertificate = registryClient.getHeisCoveredByCertificate((X509Certificate) httpRequest.getAttribute("EwpRequestCertificate"));
            }

            //checking if caller covers the receiving HEI of this mobility,
            omobilityLasList = omobilityLasList.stream().filter(omobility -> heisCoveredByCertificate.contains(omobility.getReceivingHei().getHeiId())).collect(Collectors.toList());

            //Grouping learning agreement by year             
            Map<String, List<OlearningAgreement>> olearningAgreementGroupByYear = new HashMap<String, List<OlearningAgreement>>();
            omobilityLasList.forEach(olas -> {
                Set<String> keys = olearningAgreementGroupByYear.keySet();

                /*For LAs the smallest reported academic year should be 2021/2022. All earlier academic years, if present in data, will be skipped.*/
                String[] years = olas.getReceivingAcademicTermEwpId().split("/");
                if ((Integer.parseInt(years[0]) >= Integer.parseInt("2021")) && (Integer.parseInt(years[1]) >= Integer.parseInt("2022"))) {
                    /*ReceivingAcademicTermEwpId: Academic year during which this mobility takes place.HEIs MAY use different academic year
                    identifiers (e.g. "2010/2011" vs. "2010/2010" or "2011/2011")*/
                    if (keys.contains(olas.getReceivingAcademicTermEwpId())) {
                        List<OlearningAgreement> group = olearningAgreementGroupByYear.get(olas.getReceivingAcademicTermEwpId());

                        group.add(olas);
                        olearningAgreementGroupByYear.put(olas.getReceivingAcademicTermEwpId(), group);
                    } else {
                        List<OlearningAgreement> group = new ArrayList<>();
                        group.add(olas);
                        olearningAgreementGroupByYear.put(olas.getReceivingAcademicTermEwpId(), group);
                    }
                }

            });

            //Calculate stats
            List<AcademicYearLaStats> stats = new ArrayList<>();
            Set<String> keys = olearningAgreementGroupByYear.keySet();

            keys.forEach(key -> {
                List<OlearningAgreement> group = olearningAgreementGroupByYear.get(key);

                AcademicYearLaStats currentGroupStat = new AcademicYearLaStats();
                currentGroupStat.setReceivingAcademicYearId(key);
                currentGroupStat.setLaOutgoingTotal(BigInteger.valueOf(group.size()));

                int countNotModifiedAfterApproval = 0;
                int countModifiedAfterApproval = 0;
                int countLatestVersionApproved = 0;
                int countLatestVersionRejected = 0;
                int countLatestVersionawaiting = 0;

                for (OlearningAgreement g : group) {
                    if (g.getApprovedChanges() == null) {//Does not have other versions
                        countNotModifiedAfterApproval++;
                    }

                    if (g.getApprovedChanges() != null) {//Does have other versions
                        countModifiedAfterApproval++;
                    }

                    if (g.getApprovedChanges().getReceivingHeiSignature() != null) {
                        countLatestVersionApproved++;
                    }

                    //Find pending and rejected mobilities
                    List<IMobility> imobilities = learningAgreementEJB.findIMobilityByOmobilityId(g.getId());
                    if (imobilities != null & !imobilities.isEmpty()) {
                        for (IMobility imobility : imobilities) {
                            if (imobility.getIstatus().equals(IMobilityStatus.REJECTED)) {
                                countLatestVersionRejected++;
                            }

                            if (imobility.getIstatus().equals(IMobilityStatus.PENDING)) {
                                countLatestVersionawaiting++;
                            }
                        }

                    }
                }

                currentGroupStat.setLaOutgoingLatestVersionApproved(BigInteger.valueOf(countLatestVersionApproved));
                currentGroupStat.setLaOutgoingLatestVersionAwaiting(BigInteger.valueOf(countLatestVersionawaiting));
                currentGroupStat.setLaOutgoingLatestVersionRejected(BigInteger.valueOf(countLatestVersionRejected));
                currentGroupStat.setLaOutgoingModifiedAfterApproval(BigInteger.valueOf(countModifiedAfterApproval));
                currentGroupStat.setLaOutgoingNotModifiedAfterApproval(BigInteger.valueOf(countNotModifiedAfterApproval));

                stats.add(currentGroupStat);
            });

            response.getAcademicYearLaStats().addAll(omobilitiesLasStats(stats));
        }

        return javax.ws.rs.core.Response.ok(response).build();
    }

    private List<LasOutgoingStatsResponse.AcademicYearLaStats> omobilitiesLasStats(List<AcademicYearLaStats> academicYearStats) {
        List<LasOutgoingStatsResponse.AcademicYearLaStats> omobilitiesLasStats = new ArrayList<>();
        academicYearStats.stream().forEachOrdered((m) -> {
            omobilitiesLasStats.add(converter.convertToLearningAgreementsStats(m));
        });

        return omobilitiesLasStats;
    }


    private javax.ws.rs.core.Response mobilityGet(List<String> sendingHeiIds, List<String> mobilityIdList) {
        if (sendingHeiIds != null && sendingHeiIds.size() > 1) {
            throw new EwpWebApplicationException("Only one sending HEI ID is allowed.", Response.Status.BAD_REQUEST);
        }
        if (sendingHeiIds == null || sendingHeiIds.isEmpty()) {
            throw new EwpWebApplicationException("Missing sending HEI ID.", Response.Status.BAD_REQUEST);
        }
        String sendingHeiId = sendingHeiIds.get(0);
        if (sendingHeiId == null || sendingHeiId.trim().isEmpty() || mobilityIdList == null || mobilityIdList.isEmpty()) {
            throw new EwpWebApplicationException("Missing argumanets for get.", Response.Status.BAD_REQUEST);
        }
        LOG.fine("sendingHeiId: " + sendingHeiId);

        if (mobilityIdList.size() > properties.getMaxOmobilitylasIds()) {
            throw new EwpWebApplicationException("Max number of omobility learning agreements id's has exceeded.", Response.Status.BAD_REQUEST);
        }

        LOG.fine("mobilityIdList: " + mobilityIdList.toString());

        OmobilityLasGetResponse response = new OmobilityLasGetResponse();
        List<OlearningAgreement> omobilityLasList = learningAgreementEJB.findBySendingHeiIdFilterd(sendingHeiId);
        LOG.fine("omobilityLasList: " + omobilityLasList.toString());

        if (!omobilityLasList.isEmpty()) {

            /*Collection<String> heisCoveredByCertificate;
            if (httpRequest.getAttribute("EwpRequestRSAPublicKey") != null) {
                heisCoveredByCertificate = registryClient.getHeisCoveredByClientKey((RSAPublicKey) httpRequest.getAttribute("EwpRequestRSAPublicKey"));
            } else {
                heisCoveredByCertificate = registryClient.getHeisCoveredByCertificate((X509Certificate) httpRequest.getAttribute("EwpRequestCertificate"));
            }*/

            //checking if caller covers the receiving HEI of this mobility,
            //omobilityLasList = omobilityLasList.stream().filter(omobility -> heisCoveredByCertificate.contains(omobility.getReceivingHei().getHeiId())).collect(Collectors.toList());
            LOG.fine("GETREQUEST FROM PARTNER: FOR:" + omobilityLasList.get(0).getId());
            /*if (omobilityLasList.get(0).getFromPartner()) {
                return javax.ws.rs.core.Response.ok(getRequestToClient(omobilityLasList.get(0).getOmobilityId(), sendingHeiId)).type(MediaType.APPLICATION_XML).build();
            } else {
                response.getLa().addAll(omobilitiesLas(omobilityLasList, mobilityIdList));
            }*/
            response.getLa().addAll(omobilitiesLas(omobilityLasList, mobilityIdList));
        } else {
            LOG.fine("omobilityLasList is empty");
            return javax.ws.rs.core.Response.status(Response.Status.BAD_REQUEST).build();
        }

        return javax.ws.rs.core.Response.ok(response).build();
    }

    private String getRequestToClient(String omobilityId, String heiId) {
        Map<String, String> map = registryClient.getOmobilityLasHeiUrls(heiId);
        LOG.fine("OmobilitiesLasAuxThread: map: " + (map == null ? "null" : map.toString()));
        if (map == null || map.isEmpty()) {
            LOG.fine("OmobilitiesLasAuxThread: No LAS URLs found for HEI " + heiId);
            return null;
        }

        String url = map.get("get-url");

        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setUrl(url);
        clientRequest.setHeiId(heiId);
        clientRequest.setMethod(HttpMethodEnum.POST);
        clientRequest.setHttpsec(true);

        LOG.fine("OmobilitiesLasAuxThread: url: " + url);

        Map<String, List<String>> paramsMap = new HashMap<>();
        paramsMap.put("sending_hei_id", Collections.singletonList(heiId));
        paramsMap.put("omobility_id", Collections.singletonList(omobilityId));
        ParamsClass paramsClass = new ParamsClass();
        paramsClass.setUnknownFields(paramsMap);
        clientRequest.setParams(paramsClass);

        LOG.fine("OmobilitiesLasAuxThread: params: " + paramsMap.toString());

        ClientResponse omobilityLasGetResponse = restClient.sendRequest(clientRequest, OmobilityLasGetResponse.class);

        LOG.fine("NOTIFY: response: " + omobilityLasGetResponse.getRawResponse());

        if (omobilityLasGetResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
            return null;
        }

        return omobilityLasGetResponse.getRawResponse();

    }

    private javax.ws.rs.core.Response omobilityLasIndex(List<String> sendingHeiIds, List<String> receivingHeiIdList, List<String> receiving_academic_year_ids, List<String> globalIds, List<String> mobilityTypes, List<String> modifiedSinces) {
        String receiving_academic_year_id;
        String globalId;
        String mobilityType;
        String modifiedSince;

        Collection<String> heisCoveredByCertificate;
        if (httpRequest.getAttribute("EwpRequestRSAPublicKey") != null) {
            heisCoveredByCertificate = registryClient.getHeisCoveredByClientKey((RSAPublicKey) httpRequest.getAttribute("EwpRequestRSAPublicKey"));
        } else {
            heisCoveredByCertificate = registryClient.getHeisCoveredByCertificate((X509Certificate) httpRequest.getAttribute("EwpRequestCertificate"));
        }

        if (heisCoveredByCertificate.isEmpty()) {
            return javax.ws.rs.core.Response.ok(new OmobilityLasIndexResponse()).build();
        }

        if (sendingHeiIds.size() != 1) {
            throw new EwpWebApplicationException("Missing argumanets for indexes.", Response.Status.BAD_REQUEST);
        }
        String sendingHeiId = sendingHeiIds.get(0);

        Map<String, String> urls = registryClient.getOmobilityLasHeiUrls(sendingHeiId);
        if (urls == null || urls.isEmpty()) {
            throw new EwpWebApplicationException("Unknown heiId: " + sendingHeiId, Response.Status.BAD_REQUEST);
        }


        if (receiving_academic_year_ids.size() > 1) {
            throw new EwpWebApplicationException("Missing argumanets for indexes.", Response.Status.BAD_REQUEST);
        } else if (!receiving_academic_year_ids.isEmpty()) {
            receiving_academic_year_id = receiving_academic_year_ids.get(0);
            try {
                //String adjustedDateString = receiving_academic_year_id.replaceAll("([\\+\\-]\\d{2}):(\\d{2})", "$1$2");
                System.out.println((new SimpleDateFormat("yyyy/yyyy")).parse(receiving_academic_year_id));
            } catch (ParseException e) {
                throw new EwpWebApplicationException("Can not convert date.", Response.Status.BAD_REQUEST);
            }
        } else {
            receiving_academic_year_id = null;
        }

        if (globalIds.size() > 1) {
            throw new EwpWebApplicationException("Missing argumanets for indexes.", Response.Status.BAD_REQUEST);
        } else if (!globalIds.isEmpty()) {
            globalId = globalIds.get(0);
        } else {
            globalId = null;
        }

        if (mobilityTypes.size() > 1) {
            throw new EwpWebApplicationException("Missing argumanets for indexes.", Response.Status.BAD_REQUEST);
        } else if (!mobilityTypes.isEmpty()) {
            mobilityType = mobilityTypes.get(0);
        } else {
            mobilityType = null;
        }

        if (modifiedSinces.size() > 1) {
            throw new EwpWebApplicationException("Missing argumanets for indexes.", Response.Status.BAD_REQUEST);
        } else if (!modifiedSinces.isEmpty()) {
            modifiedSince = modifiedSinces.get(0);
        } else {
            modifiedSince = null;
        }

        OmobilityLasIndexResponse response = new OmobilityLasIndexResponse();

        List<OlearningAgreement> mobilityList = learningAgreementEJB.findBySendingHeiId(sendingHeiId);

        if (receiving_academic_year_id != null && !receiving_academic_year_id.isEmpty()) {
            mobilityList = mobilityList.stream().filter(omobility -> anyMatchReceivingAcademicYear.test(omobility, receiving_academic_year_id)).collect(Collectors.toList());
        }

        if (globalId != null && !globalId.isEmpty()) {
            mobilityList = mobilityList.stream().filter(omobility -> anyMatchSpecifiedStudent.test(omobility, globalId)).collect(Collectors.toList());
        }

        if (mobilityType != null && !mobilityList.isEmpty()) {
            mobilityList = mobilityList.stream().filter(omobility -> anyMatchSpecifiedType.test(omobility, mobilityType)).collect(Collectors.toList());
        }

        if (modifiedSince != null && !modifiedSince.isEmpty()) {

            //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");//2004-02-12T15:19:21+01:00
            Calendar calendarModifySince = Calendar.getInstance();

            try {
                OffsetDateTime dateTime = OffsetDateTime.parse(modifiedSince, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
                calendarModifySince.setTime(Date.from(dateTime.toInstant()));
            } catch (Exception e) {
                throw new EwpWebApplicationException("Can not convert date.", Response.Status.BAD_REQUEST);
            }

            Date modified_since = calendarModifySince.getTime();

            LOG.info("modified_since: " + modified_since);

            List<OlearningAgreement> mobilities = new ArrayList<>();
            mobilityList.stream().forEachOrdered((m) -> {
                if (m != null) {
                    if (m.getModificateSince() != null) {
                        LOG.info("getModificateSince: " + m.getModificateSince());
                        if (m.getModificateSince().after(modified_since)) {
                            mobilities.add(m);
                        }
                    }
                }
            });


            mobilityList.clear();
            mobilityList.addAll(mobilities);
        }

        response.getOmobilityId().addAll(omobilityLasIds(mobilityList, receivingHeiIdList));
        //}

        return javax.ws.rs.core.Response.ok(response).build();
    }

    private javax.ws.rs.core.Response omobilityLasIndexAlgoria(List<String> sendingHeiIds, List<String> receivingHeiIdList, List<String> receiving_academic_year_ids, List<String> globalIds, List<String> mobilityTypes, List<String> modifiedSinces) {
        LOG.info("omobilityLasIndexAlgoria: Starting index request with parameters: sendingHeiIds=" + sendingHeiIds);
        String receiving_academic_year_id;
        String globalId;
        String mobilityType;
        String modifiedSince;

        Collection<String> heisCoveredByCertificate;
        if (httpRequest.getAttribute("EwpRequestRSAPublicKey") != null) {
            heisCoveredByCertificate = registryClient.getHeisCoveredByClientKey((RSAPublicKey) httpRequest.getAttribute("EwpRequestRSAPublicKey"));
        } else {
            heisCoveredByCertificate = registryClient.getHeisCoveredByCertificate((X509Certificate) httpRequest.getAttribute("EwpRequestCertificate"));
        }

        if (heisCoveredByCertificate.isEmpty()) {
            return javax.ws.rs.core.Response.ok(new OmobilityLasIndexResponse()).build();
        }

        if (sendingHeiIds.size() != 1) {
            throw new EwpWebApplicationException("Missing argumanets for indexes.", Response.Status.BAD_REQUEST);
        }
        String sendingHeiId = sendingHeiIds.get(0);

        Map<String, String> urls = registryClient.getOmobilityLasHeiUrls(sendingHeiId);
        if (urls == null || urls.isEmpty()) {
            throw new EwpWebApplicationException("Unknown heiId: " + sendingHeiId, Response.Status.BAD_REQUEST);
        }


        String fistYear = null;
        if (receiving_academic_year_ids.size() > 1) {
            throw new EwpWebApplicationException("Missing argumanets for indexes.", Response.Status.BAD_REQUEST);
        } else if (!receiving_academic_year_ids.isEmpty()) {
            receiving_academic_year_id = receiving_academic_year_ids.get(0);
            try {
                //String adjustedDateString = receiving_academic_year_id.replaceAll("([\\+\\-]\\d{2}):(\\d{2})", "$1$2");
                System.out.println((new SimpleDateFormat("yyyy/yyyy")).parse(receiving_academic_year_id));
                fistYear = receiving_academic_year_id.split("/")[0];
            } catch (ParseException e) {
                throw new EwpWebApplicationException("Can not convert date.", Response.Status.BAD_REQUEST);
            }
        } else {
            receiving_academic_year_id = null;
        }

        if (globalIds.size() > 1) {
            throw new EwpWebApplicationException("Missing argumanets for indexes.", Response.Status.BAD_REQUEST);
        } else if (!globalIds.isEmpty()) {
            globalId = globalIds.get(0);
        } else {
            globalId = null;
        }

        if (mobilityTypes.size() > 1) {
            throw new EwpWebApplicationException("Missing argumanets for indexes.", Response.Status.BAD_REQUEST);
        } else if (!mobilityTypes.isEmpty()) {
            mobilityType = mobilityTypes.get(0);
        } else {
            mobilityType = null;
        }

        if (modifiedSinces.size() > 1) {
            throw new EwpWebApplicationException("Missing argumanets for indexes.", Response.Status.BAD_REQUEST);
        } else if (!modifiedSinces.isEmpty()) {
            modifiedSince = modifiedSinces.get(0);
        } else {
            modifiedSince = null;
        }

        LOG.info("omobilityLasIndexAlgoria: Parameters parsed");

        OmobilityLasIndexResponse response = new OmobilityLasIndexResponse();
        List<OlearningAgreement> mobilityList = new ArrayList<>();

        String heiId = heisCoveredByCertificate.iterator().next();
        String url = properties.getAlgoriaOmobilityLasUrl(heiId);
        String token = properties.getAlgoriaAuthotizationToken();

        WebTarget target = ClientBuilder.newBuilder().build().target(url.trim());

        if(fistYear != null) {
            target = target.queryParam("receiving_academic_year", fistYear);
        }
        if (globalId != null) {
            target = target.queryParam("global_id", globalId);
        }
        if (mobilityType != null) {
            target = target.queryParam("mobility_type", mobilityType);
        }
        if (modifiedSince != null) {
            target = target.queryParam("modified_since", modifiedSince);
        }

        Response algoriaResponse = target.request().header("Authorization", token).get();
        String rawBody = algoriaResponse.readEntity(String.class);
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(rawBody);
            String pretty = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
            LOG.info("Algoria response (" + algoriaResponse.getStatus() + "):\n" + pretty);
        } catch (Exception e) {
            LOG.warning("Algoria response (" + algoriaResponse.getStatus() + ") raw:\n" + rawBody);
        }

        response.getOmobilityId().addAll(omobilityLasIds(mobilityList, receivingHeiIdList));


        return javax.ws.rs.core.Response.ok(response).build();
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

        lasList.stream().forEachOrdered((m) -> {
            if (receivingHeiIdList.isEmpty() || receivingHeiIdList.contains(m.getReceivingHei().getHeiId())) {
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
            if (omobility.getChangesProposal() == null || omobility.getChangesProposal().getStudent() == null) {
                return false;
            }

            return global_id.equals(omobility.getChangesProposal().getStudent().getGlobalId());
        }
    };

    BiPredicate<OlearningAgreement, String> anyMatchSpecifiedType = new BiPredicate<OlearningAgreement, String>() {
        @Override
        public boolean test(OlearningAgreement omobility, String mobilityType) {

            if (MobilityType.BLENDED.value().equals(mobilityType)) {
                return (omobility.getFirstVersion() != null && omobility.getFirstVersion().getBlendedMobilityComponents() != null && !omobility.getFirstVersion().getBlendedMobilityComponents().isEmpty())
                        || (omobility.getApprovedChanges() != null && omobility.getApprovedChanges().getBlendedMobilityComponents() != null && !omobility.getApprovedChanges().getBlendedMobilityComponents().isEmpty())
                        || (omobility.getChangesProposal() != null && omobility.getChangesProposal().getBlendedMobilityComponents() != null && !omobility.getChangesProposal().getBlendedMobilityComponents().isEmpty());
            } else if (MobilityType.DOCTORAL.value().equals(mobilityType)) {
                return (omobility.getFirstVersion() != null && omobility.getFirstVersion().getShortTermDoctoralComponents() != null && !omobility.getFirstVersion().getShortTermDoctoralComponents().isEmpty())
                        || (omobility.getApprovedChanges() != null && omobility.getApprovedChanges().getShortTermDoctoralComponents() != null && !omobility.getApprovedChanges().getShortTermDoctoralComponents().isEmpty())
                        || (omobility.getChangesProposal() != null && omobility.getChangesProposal().getShortTermDoctoralComponents() != null && !omobility.getChangesProposal().getShortTermDoctoralComponents().isEmpty());
            } else if (MobilityType.SEMESTRE.value().equals(mobilityType)) {
                return (omobility.getFirstVersion() != null && omobility.getFirstVersion().getComponentsStudied() != null && !omobility.getFirstVersion().getComponentsStudied().isEmpty())
                        || (omobility.getApprovedChanges() != null && omobility.getApprovedChanges().getComponentsStudied() != null && !omobility.getApprovedChanges().getComponentsStudied().isEmpty())
                        || (omobility.getChangesProposal() != null && omobility.getChangesProposal().getComponentsStudied() != null && !omobility.getChangesProposal().getComponentsStudied().isEmpty());
            }
            return false;
        }
    };
}
