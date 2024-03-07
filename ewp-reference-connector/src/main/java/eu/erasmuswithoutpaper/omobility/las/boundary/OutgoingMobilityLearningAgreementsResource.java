package eu.erasmuswithoutpaper.omobility.las.boundary;

import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import eu.erasmuswithoutpaper.api.architecture.Empty;
import eu.erasmuswithoutpaper.api.architecture.MultilineStringWithOptionalLang;
import eu.erasmuswithoutpaper.api.imobilities.Imobilities;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.LasOutgoingStatsResponse;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.LearningAgreement;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasGetResponse;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasIndexResponse;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasUpdateRequest;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasUpdateResponse;
import eu.erasmuswithoutpaper.api.registry.Hei;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.imobility.entity.IMobility;
import eu.erasmuswithoutpaper.imobility.entity.IMobilityStatus;
import eu.erasmuswithoutpaper.omobility.las.control.OutgoingMobilityLearningAgreementsConverter;
import eu.erasmuswithoutpaper.omobility.las.entity.*;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import eu.erasmuswithoutpaper.security.EwpAuthenticate;
import org.apache.commons.logging.Log;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

@Stateless
@Path("omobilities/las")
public class OutgoingMobilityLearningAgreementsResource {

    @PersistenceContext(unitName = "connector")
    EntityManager em;

    @Inject
    GlobalProperties properties;

    @Inject
    OutgoingMobilityLearningAgreementsConverter converter;

    @Inject
    RestClient restClient;

    @Inject
    RegistryClient registryClient;

    @Context
    HttpServletRequest httpRequest;

    @Inject
    OmobilitiesLasAuxThread ait;

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
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response omobilityGetGet(@QueryParam("sending_hei_id") String sendingHeiId, @QueryParam("omobility_id") List<String> mobilityIdList) {
        return mobilityGet(sendingHeiId, mobilityIdList);
    }

    @POST
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response omobilityGetPost(@FormParam("sending_hei_id") String sendingHeiId, @FormParam("omobility_id") List<String> mobilityIdList) {
        return mobilityGet(sendingHeiId, mobilityIdList);
    }

    @POST
    @Path("update")
    @Produces(MediaType.APPLICATION_XML)
    //@EwpAuthenticate
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

            LOG.fine("Starting CNR for " + request.getApproveProposalV1().getOmobilityId() + " omobility learning agreements");

            ChangesProposal changesProposal = em.find(ChangesProposal.class, request.getApproveProposalV1().getChangesProposalId());

            LOG.fine("ChangesProposal: " + (changesProposal == null ? "null" : changesProposal.getId()));

            if (changesProposal == null) {
                throw new EwpWebApplicationException("Learning agreement does not exist", Response.Status.BAD_REQUEST);
            } else {
                LOG.fine("Request.getSendingHeiId: " + (request.getSendingHeiId() == null ? "null" : request.getSendingHeiId()));
                LOG.fine("changesProposal.getOlearningAgreement:" + (changesProposal.getOlearningAgreement() == null ? "null" : changesProposal.getOlearningAgreement().getId()));
                LOG.fine("changesProposal.getOlearningAgreement().getSendingHei():" + (changesProposal.getOlearningAgreement().getSendingHei() == null ? "null" : changesProposal.getOlearningAgreement().getSendingHei()));
                LOG.fine("changesProposal.getOlearningAgreement().getSendingHei().getHeiId():" + (changesProposal.getOlearningAgreement().getSendingHei().getHeiId() == null ? "null" : changesProposal.getOlearningAgreement().getSendingHei().getHeiId()));
                if (!request.getSendingHeiId().equals(changesProposal.getOlearningAgreement().getSendingHei().getHeiId())) {
                    throw new EwpWebApplicationException("Sending Hei Id doesn't match Omobility Id's sending HEI", Response.Status.BAD_REQUEST);
                }
            }
        }

        if (request.getCommentProposalV1() != null) {
            if (request.getCommentProposalV1().getOmobilityId() == null || request.getCommentProposalV1().getOmobilityId().isEmpty()) {
                throw new EwpWebApplicationException("Mising required parameter, omobility-id is required", Response.Status.BAD_REQUEST);
            }

            if (request.getCommentProposalV1().getChangesProposalId() == null) {
                throw new EwpWebApplicationException("Mising required parameter, changes-proposal-id is required", Response.Status.BAD_REQUEST);
            }

            if (request.getCommentProposalV1().getComment() == null) {
                throw new EwpWebApplicationException("Mising required parameter, comment is required", Response.Status.BAD_REQUEST);
            }
        }
    /*
        eu.erasmuswithoutpaper.omobility.las.entity.OmobilityLasUpdateRequest mobilityUpdateRequest = new eu.erasmuswithoutpaper.omobility.las.entity.OmobilityLasUpdateRequest();
        mobilityUpdateRequest.setSendingHeiId(request.getSendingHeiId());

        ApprovedProposal appCmp = approveCmpStudiedDraft(request);
        mobilityUpdateRequest.setApproveComponentsStudiedDraft(appCmp);

        CommentProposal updateComponentsStudied = updateComponentsStudied(request);
        mobilityUpdateRequest.setUpdateComponentsStudied(updateComponentsStudied);

        em.persist(mobilityUpdateRequest);

        OmobilityLasUpdateResponse response = new OmobilityLasUpdateResponse();
        MultilineStringWithOptionalLang message = new MultilineStringWithOptionalLang();
        message.setLang("en");
        message.setValue("Thank you! We will review your suggestion");
        response.getSuccessUserMessage().add(message);*/

        String localHeiId = "";
        List<Institution> internalInstitution = em.createNamedQuery(Institution.findAll, Institution.class).getResultList();

        localHeiId = internalInstitution.get(0).getInstitutionId();

        if (request.getApproveProposalV1() != null) {
            ApprovedProposal appCmp = approveCmpStudiedDraft(request);
            em.persist(appCmp);
            em.flush();

            ChangesProposal changesProposal = em.find(ChangesProposal.class, request.getApproveProposalV1().getChangesProposalId());
            String idLAS = changesProposal.getOlearningAgreement().getId();
            OlearningAgreement omobility = em.find(OlearningAgreement.class, idLAS);
            changesProposal = omobility.getChangesProposal();

            omobility.setChangesProposal(null);
            em.remove(changesProposal);

            em.merge(omobility);
            em.flush();

            ListOfComponents cmp = new ListOfComponents();

            cmp.setId(changesProposal.getId());
            cmp.setBlendedMobilityComponents(changesProposal.getBlendedMobilityComponents());
            cmp.setComponentsStudied(changesProposal.getComponentsStudied());
            cmp.setComponentsRecognized(changesProposal.getComponentsRecognized());
            cmp.setVirtualComponents(changesProposal.getVirtualComponents());
            cmp.setShortTermDoctoralComponents(changesProposal.getShortTermDoctoralComponents());
            cmp.setSendingHeiSignature(changesProposal.getSendingHeiSignature());
            if (appCmp.getSignature() != null) {
                cmp.setReceivingHeiSignature(appCmp.getSignature());
            }
            cmp.setStudentSignature(changesProposal.getStudentSignature());

            em.persist(cmp);

            if (omobility.getFirstVersion() != null && omobility.getFirstVersion().getId() != null){
                omobility.setApprovedChanges(cmp);
            } else {
                omobility.setFirstVersion(cmp);
            }

            em.merge(omobility);
            em.flush();

        } else if (request.getCommentProposalV1() != null) {
            CommentProposal updateComponentsStudied = updateComponentsStudied(request);
            em.persist(updateComponentsStudied);
            em.flush();

            ChangesProposal changesProposal = em.find(ChangesProposal.class, request.getCommentProposalV1().getChangesProposalId());
            changesProposal.setCommentProposal(updateComponentsStudied);
            if (updateComponentsStudied.getSignature() != null) {
                changesProposal.setReceivingHeiSignature(updateComponentsStudied.getSignature());
            }
            em.merge(changesProposal);
            em.flush();
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
        List<Institution> institutionList = em.createNamedQuery(Institution.findAll).getResultList();
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
    public javax.ws.rs.core.Response omobilitiesLasCnr(@FormParam("sending_hei_id") String sendingHeiId, @FormParam("omobility_id") List<String> mobilityIdList) {
        if (sendingHeiId == null || sendingHeiId.trim().isEmpty()) {
            throw new EwpWebApplicationException("Missing argumanets for get.", Response.Status.BAD_REQUEST);
        }

        if (mobilityIdList.size() > properties.getMaxOmobilitylasIds()) {
            throw new EwpWebApplicationException("Max number of omobility learning agreements id's has exceeded.", Response.Status.BAD_REQUEST);
        }

        //TODO: Notify algoria

        LOG.info("Starting CNR for " + mobilityIdList.size() + " omobility learning agreements");

        /*for (String mobilityId : mobilityIdList) {
            CNROmobilitiesLa cnr = new CNROmobilitiesLa(sendingHeiId, mobilityId);
            cnr.start();
        }*/

        Empty response = new Empty();

        return javax.ws.rs.core.Response.ok(response).build();
    }

    private javax.ws.rs.core.Response omobilityStatsGet(String heiId) {
        LasOutgoingStatsResponse response = new LasOutgoingStatsResponse();

        //Filter learning agreement 
        List<OlearningAgreement> omobilityLasList = em.createNamedQuery(OlearningAgreement.findByReceivingHeiId).setParameter("receivingHei", heiId).getResultList();

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
                    List<IMobility> imobilities = em.createNamedQuery(IMobility.findByOmobilityId).setParameter("omobilityId", g.getOmobilityId()).getResultList();
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

    private CommentProposal updateComponentsStudied(OmobilityLasUpdateRequest request) {
        CommentProposal commentProposal = new CommentProposal();

        commentProposal.setChangesProposalId(request.getCommentProposalV1().getChangesProposalId());
        commentProposal.setComment(request.getCommentProposalV1().getComment());
        commentProposal.setOmobilityId(request.getCommentProposalV1().getOmobilityId());

        Signature signature = new Signature();
        signature.setSignerApp(request.getCommentProposalV1().getSignature().getSignerApp());
        signature.setSignerEmail(request.getCommentProposalV1().getSignature().getSignerEmail());
        signature.setSignerName(request.getCommentProposalV1().getSignature().getSignerName());
        signature.setSignerPosition(request.getCommentProposalV1().getSignature().getSignerPosition());
        if (request.getCommentProposalV1().getSignature().getTimestamp() != null) {
            signature.setTimestamp(request.getCommentProposalV1().getSignature().getTimestamp().toGregorianCalendar().getTime());
        }
        commentProposal.setSignature(signature);

        return commentProposal;
    }

    private ApprovedProposal approveCmpStudiedDraft(OmobilityLasUpdateRequest request) {
        ApprovedProposal appCmp = new ApprovedProposal();

        if (request == null || request.getApproveProposalV1() == null) {
            return null;
        }

        String changesProposal = request.getApproveProposalV1().getChangesProposalId();
        appCmp.setChangesProposalId(changesProposal);

        appCmp.setOmobilityId(request.getApproveProposalV1().getOmobilityId());

        Signature signature = new Signature();
        signature.setSignerApp(request.getApproveProposalV1().getSignature().getSignerApp());
        signature.setSignerEmail(request.getApproveProposalV1().getSignature().getSignerEmail());
        signature.setSignerName(request.getApproveProposalV1().getSignature().getSignerName());
        signature.setSignerPosition(request.getApproveProposalV1().getSignature().getSignerPosition());
        if (request.getApproveProposalV1().getSignature().getTimestamp() != null) {
            signature.setTimestamp(request.getApproveProposalV1().getSignature().getTimestamp().toGregorianCalendar().getTime());
        }

        appCmp.setSignature(signature);

        return appCmp;
    }

    private javax.ws.rs.core.Response mobilityGet(String sendingHeiId, List<String> mobilityIdList) {
        if (sendingHeiId == null || sendingHeiId.trim().isEmpty()) {
            throw new EwpWebApplicationException("Missing argumanets for get.", Response.Status.BAD_REQUEST);
        }

        if (mobilityIdList.size() > properties.getMaxOmobilitylasIds()) {
            throw new EwpWebApplicationException("Max number of omobility learning agreements id's has exceeded.", Response.Status.BAD_REQUEST);
        }

        OmobilityLasGetResponse response = new OmobilityLasGetResponse();
        List<OlearningAgreement> omobilityLasList = em.createNamedQuery(OlearningAgreement.findBySendingHeiId).setParameter("sendingHeiId", sendingHeiId).getResultList();

        if (!omobilityLasList.isEmpty()) {

            Collection<String> heisCoveredByCertificate;
            if (httpRequest.getAttribute("EwpRequestRSAPublicKey") != null) {
                heisCoveredByCertificate = registryClient.getHeisCoveredByClientKey((RSAPublicKey) httpRequest.getAttribute("EwpRequestRSAPublicKey"));
            } else {
                heisCoveredByCertificate = registryClient.getHeisCoveredByCertificate((X509Certificate) httpRequest.getAttribute("EwpRequestCertificate"));
            }

            //checking if caller covers the receiving HEI of this mobility,
            omobilityLasList = omobilityLasList.stream().filter(omobility -> heisCoveredByCertificate.contains(omobility.getReceivingHei().getHeiId())).collect(Collectors.toList());
            response.getLa().addAll(omobilitiesLas(omobilityLasList, mobilityIdList));
        }

        return javax.ws.rs.core.Response.ok(response).build();
    }

    private javax.ws.rs.core.Response omobilityLasIndex(List<String> sendingHeiIds, List<String> receivingHeiIdList, List<String> receiving_academic_year_ids, List<String> globalIds, List<String> mobilityTypes, List<String> modifiedSinces) {
        String receiving_academic_year_id;
        String globalId;
        String mobilityType;
        String modifiedSince;

        if (sendingHeiIds.size() != 1) {
            throw new EwpWebApplicationException("Missing argumanets for indexes.", Response.Status.BAD_REQUEST);
        }
        String sendingHeiId = sendingHeiIds.get(0);

        if (receiving_academic_year_ids.size() > 1) {
            throw new EwpWebApplicationException("Missing argumanets for indexes.", Response.Status.BAD_REQUEST);
        } else if (!receiving_academic_year_ids.isEmpty()) {
            receiving_academic_year_id = receiving_academic_year_ids.get(0);
            try {
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

        List<OlearningAgreement> mobilityList = em.createNamedQuery(OlearningAgreement.findBySendingHeiId).setParameter("sendingHei", sendingHeiId).getResultList();

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
                OffsetDateTime dateTime = OffsetDateTime.parse(modifiedSince);
                calendarModifySince.setTime(Date.from(dateTime.toInstant()));
            } catch (Exception e) {
                throw new EwpWebApplicationException("Can not convert date.", Response.Status.BAD_REQUEST);
            }

            Date modified_since = calendarModifySince.getTime();

            List<OlearningAgreement> mobilities = new ArrayList<>();
            mobilityList.stream().forEachOrdered((m) -> {

                Date studentSignatureDate = m.getFirstVersion().getStudentSignature() != null ? m.getFirstVersion().getStudentSignature().getTimestamp() : null;
                Date receivingSignatureDate = m.getFirstVersion().getReceivingHeiSignature() != null ? m.getFirstVersion().getReceivingHeiSignature().getTimestamp() : null;
                Date sendingSignatureDate = m.getFirstVersion().getStudentSignature() != null ? m.getFirstVersion().getStudentSignature().getTimestamp() : null;

                if (studentSignatureDate != null) {
                    if (studentSignatureDate.after(modified_since)) {
                        mobilities.add(m);
                    }
                }

                if (receivingSignatureDate != null) {
                    if (receivingSignatureDate.after(modified_since)) {
                        mobilities.add(m);
                    }
                }

                if (sendingSignatureDate != null) {
                    if (sendingSignatureDate.after(modified_since)) {
                        mobilities.add(m);
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

    private class CNROmobilitiesLa extends Thread {

        private String heiId;
        private String mobilityId;

        public CNROmobilitiesLa(String heiId, String mobilityId) {
            this.heiId = heiId;
            this.mobilityId = mobilityId;
        }

        @Override
        public void run() {
            try {
                ait.createLas(heiId, mobilityId);
            } catch (Exception e) {

            }
        }
    }
}
