package eu.erasmuswithoutpaper.iia.boundary;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;
import java.math.BigInteger;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import eu.erasmuswithoutpaper.api.iias.cnr.ObjectFactory;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasIndexResponse;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasStatsResponse;
import eu.erasmuswithoutpaper.api.iias.endpoints.MobilitySpecification;
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
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.iia.approval.entity.IiaApproval;
import eu.erasmuswithoutpaper.iia.common.IiaTaskService;
import eu.erasmuswithoutpaper.iia.control.HashCalculationUtility;
import eu.erasmuswithoutpaper.iia.control.IiaConverter;
import eu.erasmuswithoutpaper.iia.entity.CooperationCondition;
import eu.erasmuswithoutpaper.iia.entity.Duration;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.iia.entity.IiaPartner;
import eu.erasmuswithoutpaper.iia.entity.MobilityNumber;
import eu.erasmuswithoutpaper.iia.entity.MobilityType;
import eu.erasmuswithoutpaper.iia.entity.SubjectArea;
import eu.erasmuswithoutpaper.notification.entity.Notification;
import eu.erasmuswithoutpaper.notification.entity.NotificationTypes;
import eu.erasmuswithoutpaper.organization.entity.ContactDetails;
import eu.erasmuswithoutpaper.organization.entity.FlexibleAddress;
import eu.erasmuswithoutpaper.organization.entity.Gender;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import eu.erasmuswithoutpaper.organization.entity.Person;
import eu.erasmuswithoutpaper.security.EwpAuthenticate;
import java.io.IOException;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import javax.persistence.EntityTransaction;
import javax.transaction.Transaction;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;

@Stateless
@Path("iias")
public class IiaResource {

    private static final Logger LOG = Logger.getLogger(IiaResource.class.getCanonicalName());

    @PersistenceContext(unitName = "connector")
    EntityManager em;

    @Inject
    GlobalProperties properties;

    @Inject
    RegistryClient registryClient;

    @Inject
    IiaConverter iiaConverter;

    @Context
    HttpServletRequest httpRequest;

    @Inject
    GlobalProperties globalProperties;

    @Inject
    RestClient restClient;

    @GET
    @Path("index")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response indexGet(@QueryParam("hei_id") List<String> heiId, @QueryParam("partner_hei_id") String partner_hei_id, @QueryParam("receiving_academic_year_id") List<String> receiving_academic_year_id, @QueryParam("modified_since") List<String> modified_since) {
        return iiaIndex(heiId, partner_hei_id, receiving_academic_year_id, modified_since);
    }

    @POST
    @Path("index")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response indexPost(@FormParam("hei_id") List<String> heiId, @FormParam("partner_hei_id") String partner_hei_id, @FormParam("receiving_academic_year_id") List<String> receiving_academic_year_id, @FormParam("modified_since") List<String> modified_since) {
        return iiaIndex(heiId, partner_hei_id, receiving_academic_year_id, modified_since);
    }

    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response getGet(@QueryParam("hei_id") List<String> heiId, @QueryParam("iia_id") List<String> iiaIdList, @QueryParam("iia_code") List<String> iiaCodeList) {
        if ((iiaCodeList != null && !iiaCodeList.isEmpty()) && (iiaIdList != null && !iiaIdList.isEmpty())) {
            throw new EwpWebApplicationException("Providing both iia_code and iia_id is not correct", Response.Status.BAD_REQUEST);
        }

        if ((iiaCodeList == null || iiaCodeList.isEmpty()) && (iiaIdList == null || iiaIdList.isEmpty())) {
            throw new EwpWebApplicationException("Missing argumanets, iia_code or iia_id is required", Response.Status.BAD_REQUEST);
        }

        if (iiaIdList.size() > properties.getMaxIiaIds()) {
            throw new EwpWebApplicationException("Max number of IIA ids has exceeded.", Response.Status.BAD_REQUEST);
        }

        if (iiaCodeList.size() > properties.getMaxIiaCodes()) {
            throw new EwpWebApplicationException("Max number of IIA codes has exceeded.", Response.Status.BAD_REQUEST);
        }

        if (heiId == null || heiId.isEmpty()) {
            throw new EwpWebApplicationException("Missing argumanets, hei_id is required", Response.Status.BAD_REQUEST);
        }

        if (heiId.size() > 1) {
            throw new EwpWebApplicationException("Not allow more than one value of hei_id", Response.Status.BAD_REQUEST);
        }

        List<String> iiaIdentifiers = new ArrayList<>();

        //Flag to define the search criteria. It is two possible way for identifying an IIA, by identifiers OR local IIA codes.
        boolean byLocalCodes = false;

        if (iiaIdList != null && !iiaIdList.isEmpty()) {
            iiaIdentifiers.addAll(iiaIdList);
        } else {
            iiaIdentifiers.addAll(iiaCodeList);
            byLocalCodes = true;
        }

        String hei_Id = heiId.get(0);
        return iiaGet(hei_Id, iiaIdentifiers, byLocalCodes);

    }

    @POST
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response getPost(@FormParam("hei_id") List<String> heiId, @FormParam("iia_id") List<String> iiaIdList, @FormParam("iia_code") List<String> iiaCodeList) {
        if ((iiaCodeList != null && !iiaCodeList.isEmpty()) && (iiaIdList != null && !iiaIdList.isEmpty())) {
            throw new EwpWebApplicationException("Providing both iia_code and iia_id is not correct", Response.Status.BAD_REQUEST);
        }

        if ((iiaCodeList == null || iiaCodeList.isEmpty()) && (iiaIdList == null || iiaIdList.isEmpty())) {
            throw new EwpWebApplicationException("Missing argumanets, iia_code or iia_id is required", Response.Status.BAD_REQUEST);
        }

        if (heiId == null || heiId.isEmpty()) {
            throw new EwpWebApplicationException("Missing argumanets, hei_id is required", Response.Status.BAD_REQUEST);
        }

        if (heiId.size() > 1) {
            throw new EwpWebApplicationException("Not allow more than one value of hei_id", Response.Status.BAD_REQUEST);
        }

        if (iiaIdList.size() > properties.getMaxIiaIds()) {
            throw new EwpWebApplicationException("Max number of IIA ids has exceeded.", Response.Status.BAD_REQUEST);
        }

        if (iiaCodeList.size() > properties.getMaxIiaCodes()) {
            throw new EwpWebApplicationException("Max number of IIA codes has exceeded.", Response.Status.BAD_REQUEST);
        }

        List<String> iiaIdentifiers = new ArrayList<>();

        //Flag to define the search criteria. It is two possible way for identifying an IIA, by identifiers OR local IIA codes.
        boolean byLocalCodes = false;

        if (iiaIdList != null && !iiaIdList.isEmpty()) {
            iiaIdentifiers = iiaIdList;
        } else {
            iiaIdentifiers = iiaCodeList;
            byLocalCodes = true;
        }

        String hei_Id = heiId.get(0);
        return iiaGet(hei_Id, iiaIdentifiers, byLocalCodes);
    }

    @POST
    @Path("cnr")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response cnrPost(@FormParam("notifier_hei_id") String notifierHeiId, @FormParam("iia_id") String iiaId) {
        LOG.fine("TEST: START CNR");
        if (notifierHeiId == null || notifierHeiId.isEmpty() || iiaId == null || iiaId.isEmpty()) {
            throw new EwpWebApplicationException("Missing argumanets for notification.", Response.Status.BAD_REQUEST);
        }

        /*Collection<String> heisCoveredByCertificate;
        if (httpRequest.getAttribute("EwpRequestRSAPublicKey") != null) {
            heisCoveredByCertificate = registryClient.getHeisCoveredByClientKey((RSAPublicKey) httpRequest.getAttribute("EwpRequestRSAPublicKey"));
        } else {
            heisCoveredByCertificate = registryClient.getHeisCoveredByCertificate((X509Certificate) httpRequest.getAttribute("EwpRequestCertificate"));
        }*/
        //if (heisCoveredByCertificate.contains(notifierHeiId)) {
        Notification notification = new Notification();
        notification.setType(NotificationTypes.IIA);
        notification.setHeiId(notifierHeiId);
        notification.setChangedElementIds(iiaId);
        notification.setNotificationDate(new Date());
        em.persist(notification);

        //Register and execute Algoria notification
        execNotificationToAlgoria(iiaId, notifierHeiId);

        LOG.fine("TEST: START THREAD");
        CNRGetFirst getThread = new CNRGetFirst(notifierHeiId, iiaId);
        getThread.start();

        /*} else {
            throw new EwpWebApplicationException("The client signature does not cover the notifier_heid.", Response.Status.BAD_REQUEST);
        }*/
        return javax.ws.rs.core.Response.ok(new ObjectFactory().createIiaCnrResponse(new Empty())).build();
    }

    @GET
    @Path("stats")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response iiaGetStats(@QueryParam(value = "hei_id") String hei_id) {
        LOG.fine("------------------------------ START /iias/stats ------------------------------");
        if (hei_id == null || hei_id.isEmpty() || hei_id == null || hei_id.isEmpty()) {
            List<Institution> institutionList = em.createNamedQuery(Institution.findAll).getResultList();
            if (institutionList.size() != 1) {
                LOG.fine("------------------------------ ERROR /iias/stats ------------------------------");
                throw new IllegalStateException("Internal error: more than one insitution covered");
            }
            hei_id = institutionList.get(0).getInstitutionId();
            //throw new EwpWebApplicationException("Missing argumanets for statistics.", Response.Status.BAD_REQUEST);
        }

        /*Collection<String> heisCoveredByCertificate = null;
        if (httpRequest.getAttribute("EwpRequestRSAPublicKey") != null) {
            heisCoveredByCertificate = registryClient.getHeisCoveredByClientKey((RSAPublicKey) httpRequest.getAttribute("EwpRequestRSAPublicKey"));
        }
        
        if(heisCoveredByCertificate == null || heisCoveredByCertificate.isEmpty()) {
            heisCoveredByCertificate = registryClient.getHeisCoveredByCertificate((X509Certificate) httpRequest.getAttribute("EwpRequestCertificate"));
        }
        
        LOG.fine("HEI_ID recibida: "+hei_id);
        LOG.fine("lista recibida de EWP: "+heisCoveredByCertificate);
        if (heisCoveredByCertificate.contains(hei_id)) {*/
        LOG.fine("------------------------------ END /iias/stats ------------------------------");
        return iiaStats(hei_id);
        /*} else {
            LOG.fine("------------------------------ ERROR /iias/stats ------------------------------");
            throw new EwpWebApplicationException("The client signature does not cover the notifier_heid.", Response.Status.BAD_REQUEST);
        }*/
    }

    @POST
    @Path("stats")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response iiaPostStats(@FormParam("hei_id") String hei_id) {

        if (hei_id == null || hei_id.isEmpty() || hei_id == null || hei_id.isEmpty()) {
            List<Institution> institutionList = em.createNamedQuery(Institution.findAll).getResultList();
            if (institutionList.size() != 1) {
                throw new IllegalStateException("Internal error: more than one insitution covered");
            }
            hei_id = institutionList.get(0).getInstitutionId();
            //throw new EwpWebApplicationException("Missing argumanets for statistics.", Response.Status.BAD_REQUEST);
        }

        Collection<String> heisCoveredByCertificate;
        if (httpRequest.getAttribute("EwpRequestRSAPublicKey") != null) {
            heisCoveredByCertificate = registryClient.getHeisCoveredByClientKey((RSAPublicKey) httpRequest.getAttribute("EwpRequestRSAPublicKey"));
        } else {
            heisCoveredByCertificate = registryClient.getHeisCoveredByCertificate((X509Certificate) httpRequest.getAttribute("EwpRequestCertificate"));
        }

        if (heisCoveredByCertificate.contains(hei_id)) {
            return iiaStats(hei_id);
        } else {
            throw new EwpWebApplicationException("The client signature does not cover the notifier_heid.", Response.Status.BAD_REQUEST);
        }
    }

    private javax.ws.rs.core.Response iiaStats(String heid) {

        IiasStatsResponse response = new IiasStatsResponse();

        Predicate<Iia> condition = new Predicate<Iia>() {
            boolean match = false;

            @Override
            public boolean test(Iia iia) {
                List<CooperationCondition> cConditions = iia.getCooperationConditions();

                cConditions.forEach(c -> {
                    if (heid.equals(c.getReceivingPartner().getInstitutionId())) {
                        match = true;
                    }
                });

                return match;
            }

        };

        BiPredicate<Iia, List<Notification>> conditionNotApproved = new BiPredicate<Iia, List<Notification>>() {
            boolean notNotified = true;

            @Override
            public boolean test(Iia iia, List<Notification> iiaApprovalNotifications) {

                for (Notification iiaApprovalNotified : iiaApprovalNotifications) {
                    if (iia.getId().equals(iiaApprovalNotified.getChangedElementIds())) {
                        notNotified = false;
                    }
                }

                return notNotified;
            }

        };

        BiPredicate<Iia, List<IiaApproval>> notApproved = new BiPredicate<Iia, List<IiaApproval>>() {
            boolean notApproved = true;

            @Override
            public boolean test(Iia iia, List<IiaApproval> iiaApprovals) {

                for (IiaApproval iiaApproval : iiaApprovals) {
                    if (iia.getId().equals(iiaApproval.getId())) {
                        notApproved = false;
                    }
                }

                return notApproved;
            }

        };

        //filtrar las iia de la institucion enviada por parametro
        List<Iia> localIias = em.createNamedQuery(Iia.findAll, Iia.class).getResultList().stream().filter(condition).collect(Collectors.toList());
        int iiaFetchable = localIias.size();

        List<Notification> iiaApprovalNotifications = em.createNamedQuery(Notification.findAll, Notification.class).getResultList().stream().filter(n -> (n.getType().equals(NotificationTypes.IIAAPPROVAL) && n.getHeiId().equals(heid))).collect(Collectors.toList());
        List<Iia> localNotApprovedByPartner = localIias.stream().filter(iia -> conditionNotApproved.test(iia, iiaApprovalNotifications)).collect(Collectors.toList());
        int iiaPartnerUnapproved = localNotApprovedByPartner.size();

        List<IiaApproval> localIiasApproval = em.createNamedQuery(IiaApproval.findAll, IiaApproval.class).getResultList().stream().filter(iiaapproval -> {
            for (Iia iia : localIias) {
                if (iia.getId().equals(iiaapproval.getId())) {//De todas las aprobadas solo obtener las que sean de la insitucion enviada por parametro
                    return true;
                }
            }
            return false;
        }).collect(Collectors.toList());
        int iiaApproved = localIiasApproval.size();

        List<Iia> localNotApproved = localIias.stream().filter(iia -> notApproved.test(iia, localIiasApproval)).collect(Collectors.toList());
        int iiaLocallyUnapproved = localNotApproved.size();

        response.setIiaFetchable(BigInteger.valueOf(iiaFetchable));
        response.setIiaLocalApprovedPartnerUnapproved(BigInteger.valueOf(iiaPartnerUnapproved));
        response.setIiaLocalUnapprovedPartnerApproved(BigInteger.valueOf(iiaLocallyUnapproved));
        response.setIiaBothApproved(BigInteger.valueOf(iiaApproved));

        return Response.ok(response).build();
    }

    private void execNotificationToAlgoria(String iiaId, String notifierHeiId) {

        Callable<String> callableTask = IiaTaskService.createTask(iiaId, IiaTaskService.MODIFIED, notifierHeiId);

        //Put the task in the queue
        IiaTaskService.addTask(callableTask);
    }

    private javax.ws.rs.core.Response iiaGet(String heiId, List<String> iiaIdList, boolean byLocalCodes) {

        if (!isInstitutionInEwp(heiId)) {
            throw new EwpWebApplicationException("Not a valid hei_id.", Response.Status.BAD_REQUEST);
        }

        IiasGetResponse response = new IiasGetResponse();

        // TODO: Should IIA hold hei/institution id (if not hei_id will not be used, only use iia id)
        Predicate<Iia> condition = new Predicate<Iia>() {
            boolean match = false;

            @Override
            public boolean test(Iia iia) {
                List<CooperationCondition> cConditions = iia.getCooperationConditions();

                cConditions.forEach(c -> {
                    if (heiId.equals(c.getSendingPartner().getInstitutionId()) || heiId.equals(c.getReceivingPartner().getInstitutionId())) {
                        match = true;
                    }
                });

                return match;
            }

        };

        List<Iia> iiaList = null;
        if (byLocalCodes) {
            iiaList = iiaIdList.stream()
                    .flatMap(iiaCode -> em.createNamedQuery(Iia.findByIiaCode, Iia.class)
                    .setParameter("iiaCode", iiaCode)
                    .getResultList()
                    .stream())
                    .filter(iia -> iia != null)
                    .filter(condition)
                    .collect(Collectors.toList());
        } else {
            iiaList = iiaIdList.stream()
                    .map(id -> em.find(Iia.class, id))
                    .filter(iia -> iia != null)
                    .filter(iia -> condition.test(iia))
                    .collect(Collectors.toList());
        }

        Collection<String> heisCoveredByCertificate;
        if (httpRequest.getAttribute("EwpRequestRSAPublicKey") != null) {
            heisCoveredByCertificate = registryClient.getHeisCoveredByClientKey((RSAPublicKey) httpRequest.getAttribute("EwpRequestRSAPublicKey"));
        } else {
            heisCoveredByCertificate = registryClient.getHeisCoveredByCertificate((X509Certificate) httpRequest.getAttribute("EwpRequestCertificate"));
        }

        if (!iiaList.isEmpty()) {
            List<Iia> iiaCertified = iiaCoveredByCertificate(iiaList, heisCoveredByCertificate);
            response.getIia().addAll(iiaConverter.convertToIias(heiId, iiaCertified));
        }

        return javax.ws.rs.core.Response.ok(response).build();
    }

    private boolean isInstitutionInEwp(String institutionId) {
        List<Institution> institutionList = em.createNamedQuery(Institution.findByInstitutionId).setParameter("institutionId", institutionId).getResultList();
        return !institutionList.isEmpty();
    }

    private javax.ws.rs.core.Response iiaIndex(List<String> heiIds, String partner_hei_id, List<String> receiving_academic_year_id, List<String> modified_since) {

        if (modified_since != null && modified_since.size() > 1) {
            throw new EwpWebApplicationException("Not allow more than one value of modified_since", Response.Status.BAD_REQUEST);
        }

        Collection<String> heisCoveredByCertificate;
        if (httpRequest.getAttribute("EwpRequestRSAPublicKey") != null) {
            heisCoveredByCertificate = registryClient.getHeisCoveredByClientKey((RSAPublicKey) httpRequest.getAttribute("EwpRequestRSAPublicKey"));
        } else {
            heisCoveredByCertificate = registryClient.getHeisCoveredByCertificate((X509Certificate) httpRequest.getAttribute("EwpRequestCertificate"));
        }

        LOG.fine("Heis covered:" + heisCoveredByCertificate);

        boolean validationError = false;
        Iterator<String> iteratorHeids = heiIds.iterator();
        while (iteratorHeids.hasNext() && !validationError) {
            String heiId = iteratorHeids.next();

            if (!isInstitutionInEwp(heiId)) {
                validationError = true;

                throw new EwpWebApplicationException("Not a valid hei_id.", Response.Status.BAD_REQUEST);
            }

            if (partner_hei_id != null) {
                if (partner_hei_id.equals(heiId)) {
                    validationError = true;

                    throw new EwpWebApplicationException("Not a valid partner_hei_id, it must be different from hei_id.", Response.Status.BAD_REQUEST);
                }
            }

            // if (!heisCoveredByCertificate.contains(heiId)) {
            // 	validationError = true;
            // 	throw new EwpWebApplicationException("The client signature does not cover the hei_id .", Response.Status.BAD_REQUEST);
            // }
        }

        //receiving_academic_year_id
        if (receiving_academic_year_id != null) {
            boolean match = true;
            Iterator<String> iterator = receiving_academic_year_id.iterator();
            while (iterator.hasNext() && match) {
                String yearId = (String) iterator.next();

                if (!yearId.matches("\\d{4}\\/\\d{4}")) {
                    match = false;
                }
            }

            if (!match) {
                throw new EwpWebApplicationException("receiving_academic_year_id is not in the correct format", Response.Status.BAD_REQUEST);
            }
        }

        IiasIndexResponse response = new IiasIndexResponse();
        List<Iia> filteredIiaList = em.createNamedQuery(Iia.findAll).getResultList();

        LOG.fine("Filtered:" + filteredIiaList.stream().map(Iia::getId).collect(Collectors.toList()));

        if (!filteredIiaList.isEmpty()) {

            List<Iia> tempIiaList = new ArrayList<>();
            for (String heiId : heiIds) {
                tempIiaList.addAll(filteredIiaList.stream().filter(iia -> sendingHeiId.test(iia, heiId) || receivingHeiId.test(iia, heiId)).collect(Collectors.toList()));
            }

            LOG.fine("Filtered 2:" + tempIiaList.stream().map(Iia::getId).collect(Collectors.toList()));

            filteredIiaList = new ArrayList<Iia>(tempIiaList);

            if (partner_hei_id != null) {
                filteredIiaList = filteredIiaList.stream().filter(iia -> receivingHeiId.test(iia, partner_hei_id) || sendingHeiId.test(iia, partner_hei_id)).collect(Collectors.toList());
            }

            LOG.fine("Filtered 3:" + filteredIiaList.stream().map(Iia::getId).collect(Collectors.toList()));

            List<Iia> filteredIiaByReceivingAcademic = new ArrayList<>();
            if (receiving_academic_year_id != null && !receiving_academic_year_id.isEmpty()) {

                for (String year_id : receiving_academic_year_id) {
                    List<Iia> filterefList = filteredIiaList.stream().filter(iia -> anyMatchReceivingAcademicYear.test(iia, year_id)).collect(Collectors.toList());

                    filteredIiaByReceivingAcademic.addAll(filterefList);
                }

                filteredIiaList = new ArrayList<Iia>(filteredIiaByReceivingAcademic);
            }

            LOG.fine("Filtered 4:" + filteredIiaList.stream().map(Iia::getId).collect(Collectors.toList()));

            if (modified_since != null && !modified_since.isEmpty()) {

                Calendar calendarModifySince = Calendar.getInstance();
                if (modified_since != null) {

                    List<Iia> tempFilteredModifiedSince = new ArrayList<>();
                    Iterator<String> modifiedSinceIter = modified_since.iterator();

                    boolean match = true;
                    while (modifiedSinceIter.hasNext() && match) {
                        String modifiedValue = modifiedSinceIter.next();
                        if (LOG.getLevel() != null) {
                            LOG.log(LOG.getLevel(), "\n\n\n" + modifiedValue + "\n\n\n");
                        } else {
                            LOG.log(Level.FINE, "\n\n\n" + modifiedValue + "\n\n\n");
                        }
                        Date date = javax.xml.bind.DatatypeConverter.parseDateTime(modifiedValue).getTime();
                        calendarModifySince.setTime(date);
                        List<Iia> aux = filteredIiaList.stream().filter(iia -> compareModifiedSince.test(iia, calendarModifySince)).collect(Collectors.toList());
                        if (aux != null) {
                            tempFilteredModifiedSince.addAll(aux);
                        }
                    }
                    filteredIiaList = new ArrayList<>(tempFilteredModifiedSince);
                }
            }

            LOG.fine("Filtered 5:" + filteredIiaList.stream().map(Iia::getId).collect(Collectors.toList()));
        }

        LOG.fine("Filtered 6:" + filteredIiaList.stream().map(Iia::getId).collect(Collectors.toList()));

        if (!filteredIiaList.isEmpty()) {
            List<String> iiaIds = iiaIdsCoveredByCertificate(filteredIiaList, heisCoveredByCertificate);
            LOG.fine("IIA IDs:" + iiaIds);
            response.getIiaId().addAll(iiaIds);
        }

        return javax.ws.rs.core.Response.ok(response).build();
    }

    BiPredicate<Iia, String> sendingHeiId = new BiPredicate<Iia, String>() {
        @Override
        public boolean test(Iia iia, String heiId) {

            List<CooperationCondition> cConditions = iia.getCooperationConditions();

            Stream<CooperationCondition> stream = cConditions.stream().filter(c -> c.getSendingPartner().getInstitutionId().equals(heiId));

            return !stream.collect(Collectors.toList()).isEmpty();
        }
    };

    BiPredicate<Iia, String> receivingHeiId = new BiPredicate<Iia, String>() {
        @Override
        public boolean test(Iia iia, String partner_hei_id) {
            List<CooperationCondition> cConditions = iia.getCooperationConditions();

            Stream<CooperationCondition> stream = cConditions.stream().filter(c -> c.getReceivingPartner().getInstitutionId().equals(partner_hei_id));

            return !stream.collect(Collectors.toList()).isEmpty();
        }
    };

    BiPredicate<Iia, String> anyMatchReceivingAcademicYear = new BiPredicate<Iia, String>() {
        @Override
        public boolean test(Iia iia, String receiving_academic_year_id) {

            List<CooperationCondition> cConditions = iia.getCooperationConditions();

            Stream<CooperationCondition> stream = cConditions.stream().filter(c -> c.getReceivingAcademicYearId().stream().anyMatch(yearId -> yearId.equals(receiving_academic_year_id)));

            return !stream.collect(Collectors.toList()).isEmpty();
        }
    };

    BiPredicate<Iia, Calendar> compareModifiedSince = new BiPredicate<Iia, Calendar>() {
        @Override
        public boolean test(Iia iia, Calendar calendarModifySince) {

            if (iia.getModifyDate() == null) {
                return true;
            }
            Calendar calendarModify = Calendar.getInstance();
            calendarModify.setTime(iia.getModifyDate());

            return calendarModify.after(calendarModifySince);
        }

    };

    private boolean filterByInstitutionId(Collection<String> heisCoveredByCertificate, Iia iia) {
        return iia.getCooperationConditions().stream().anyMatch(
                cond -> heisCoveredByCertificate.contains(cond.getReceivingPartner().getInstitutionId())
                || heisCoveredByCertificate.contains(cond.getSendingPartner().getInstitutionId()));
    }

    private List<String> iiaIdsCoveredByCertificate(List<Iia> iiaList, Collection<String> heisCoveredByCertificate) {
        return iiaList.stream().filter((iia) -> {
            return filterByInstitutionId(heisCoveredByCertificate, iia);
        }).map(iia -> iia.getId()).collect(Collectors.toList());
    }

    private List<Iia> iiaCoveredByCertificate(List<Iia> iiaList, Collection<String> heisCoveredByCertificate) {
        return iiaList.stream().filter((iia) -> {
            return filterByInstitutionId(heisCoveredByCertificate, iia);
        }).map(iia -> iia).collect(Collectors.toList());
    }

    private class CNRGetFirst extends Thread {

        private String heiId;
        private String iiaId;

        public CNRGetFirst(String heiId, String iiaId) {
            this.heiId = heiId;
            this.iiaId = iiaId;
        }

        @Override
        public void run() {
            LOG.fine("CNRGetFirst: Empezando GET tras CNR");
            Map<String, String> map = registryClient.getIiaHeiUrls(heiId);
            if (map == null) {
                return;
            }

            LOG.fine("CNRGetFirst: MAP ENCONTRADO");

            String url = map.get("get-url");
            if (url == null) {
                return;
            }

            LOG.fine("CNRGetFirst: Url encontrada: " + url);

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

            ClientResponse clientResponse = restClient.sendRequest(clientRequest, IiasGetResponse.class);

            LOG.fine("CNRGetFirst: Respuesta del cliente " + clientResponse.getStatusCode());

            if (clientResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
                //NOTIFY
                return;
            }

            List<Iia> iia = em.createNamedQuery(Iia.findByPartnerId, Iia.class).setParameter("idPartner", iiaId).getResultList();
            LOG.fine("CNRGetFirst: Busqueda en bbdd " + (iia == null ? "null" : iia.size()));
            if (iia == null || iia.isEmpty()) {
                eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse.Iia sendIia = ((IiasGetResponse) clientResponse.getResult()).getIia().get(0);
                Iia newIia = new Iia();
                convertToIia(sendIia, newIia);

                LOG.fine("CNRGetFirst: Iia convertsed");

                try {

                    JAXBContext jaxbContext = JAXBContext.newInstance(IiasGetResponse.Iia.CooperationConditions.class);
                    Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                    jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

                    StringWriter sw = new StringWriter();

                    //Create a copy off CooperationConditions to be used in calculateSha256 function
                    IiasGetResponse.Iia.CooperationConditions cc = new IiasGetResponse.Iia.CooperationConditions();
                    cc.getStaffTeacherMobilitySpec().addAll(sendIia.getCooperationConditions().getStaffTeacherMobilitySpec());
                    cc.getStaffTrainingMobilitySpec().addAll(sendIia.getCooperationConditions().getStaffTrainingMobilitySpec());
                    cc.getStudentStudiesMobilitySpec().addAll(sendIia.getCooperationConditions().getStudentStudiesMobilitySpec());
                    cc.getStudentTraineeshipMobilitySpec().addAll(sendIia.getCooperationConditions().getStudentTraineeshipMobilitySpec());

                    cc = iiaConverter.removeContactInfo(cc);

                    QName qName = new QName("cooperation_conditions");
                    JAXBElement<IiasGetResponse.Iia.CooperationConditions> root = new JAXBElement<IiasGetResponse.Iia.CooperationConditions>(qName, IiasGetResponse.Iia.CooperationConditions.class, cc);

                    jaxbMarshaller.marshal(root, sw);
                    String xmlString = sw.toString();

                    String calculatedHash = HashCalculationUtility.calculateSha256(xmlString);

                    newIia.setConditionsHash(calculatedHash);
                } catch (InvalidCanonicalizerException | CanonicalizationException | NoSuchAlgorithmException | SAXException
                        | IOException | ParserConfigurationException | TransformerException | JAXBException e) {
                }

                LOG.fine("CNRGetFirst: Iia hash calculated: " + newIia.getConditionsHash());

                EntityTransaction transaction = em.getTransaction();

                transaction.begin();
                try {
                    em.persist(newIia);
                    em.flush();
                    transaction.commit();
                } catch (Exception e) {
                    transaction.rollback();
                }

                LOG.fine("CNRGetFirst: Iia persisted: " + newIia.getId());

            }

        }

        private void convertToIia(IiasGetResponse.Iia iia, Iia iiaInternal) {

            if (iia.getConditionsHash() != null) {
                iiaInternal.setConditionsHash(iia.getConditionsHash());
            }

            iiaInternal.setInEfect(iia.isInEffect());

            List<CooperationCondition> iiaIternalCooperationConditions = getCooperationConditions(iia.getCooperationConditions());

            List<Institution> institutions = em.createNamedQuery(Institution.findAll, Institution.class).getResultList();
            iia.getPartner().stream().forEach((IiasGetResponse.Iia.Partner partner) -> {

                IiaPartner partnerInternal = new IiaPartner();

                for (Institution institution : institutions) {
                    if (institution.getInstitutionId().equals(partner.getHeiId())) {
                        System.out.println("Found Parter:" + partner.getHeiId());
                        System.out.println("IiaCode:" + iiaInternal.getIiaCode());
                        System.out.println("IiaCode Parter:" + partner.getIiaCode());

                        //iiaInternal.setEndDate(null);
                        //iiaInternal.setStartDate(null);
                        iiaInternal.setIiaCode(partner.getIiaCode());
                        iiaInternal.setId(partner.getIiaId());

                        if (partner.getSigningDate() != null) {
                            iiaInternal.setSigningDate(partner.getSigningDate().toGregorianCalendar().getTime());
                        }

                        break;
                    }
                }

                if (partner.getIiaCode() != null) {
                    partnerInternal.setIiaCode(partner.getIiaCode());
                }

                if (partner.getIiaId() != null) {
                    partnerInternal.setIiaId(partner.getIiaId());
                }

                partnerInternal.setInstitutionId(partner.getHeiId());

                if (partner.getOunitId() != null) {
                    partnerInternal.setOrganizationUnitId(partner.getOunitId());
                }

                if (partner.getSigningContact() != null) {
                    Contact signingContact = partner.getSigningContact();

                    eu.erasmuswithoutpaper.organization.entity.Contact signingContactInternal = convertToContact(signingContact);
                    partnerInternal.setSigningContact(signingContactInternal);
                }

                if (partner.getContact() != null) {
                    List<Contact> contacts = partner.getContact();

                    List<eu.erasmuswithoutpaper.organization.entity.Contact> internalContacts = new ArrayList<>();
                    for (Contact contact : contacts) {
                        eu.erasmuswithoutpaper.organization.entity.Contact internalContact = convertToContact(contact);

                        internalContacts.add(internalContact);
                    }

                    partnerInternal.setContacts(internalContacts);
                }

                for (CooperationCondition cooperationConditionInternal : iiaIternalCooperationConditions) {

                    if (cooperationConditionInternal.getReceivingPartner().getInstitutionId().equals(partner.getHeiId())) {

                        cooperationConditionInternal.setReceivingPartner(partnerInternal);

                    } else if (cooperationConditionInternal.getSendingPartner().getInstitutionId().equals(partner.getHeiId())) {

                        cooperationConditionInternal.setSendingPartner(partnerInternal);

                    }
                }

            });

            iiaInternal.setCooperationConditions(iiaIternalCooperationConditions);
        }

        private eu.erasmuswithoutpaper.organization.entity.Contact convertToContact(Contact pContact) {
            eu.erasmuswithoutpaper.organization.entity.Contact internalContact = new eu.erasmuswithoutpaper.organization.entity.Contact();

//		List<StringWithOptionalLang> contactNames = pContact.getContactName();
//		for (StringWithOptionalLang stringWithOptionalLang : contactNames) {
//			
//		}
            //internalContact.set
            Person personInternal = new Person();
            personInternal.setGender(Gender.getById(pContact.getPersonGender()));
            internalContact.setPerson(personInternal);

            ContactDetails contactDetails = new ContactDetails();

            FlexibleAddress flexibleAddressInternal = convertFlexibleAddress(pContact.getMailingAddress());
            FlexibleAddress streetAddressInternal = convertFlexibleAddress(pContact.getStreetAddress());

            contactDetails.setMailingAddress(flexibleAddressInternal);
            contactDetails.setStreetAddress(streetAddressInternal);

            internalContact.setContactDetails(contactDetails);

            return internalContact;
        }

        private FlexibleAddress convertFlexibleAddress(eu.erasmuswithoutpaper.api.types.address.FlexibleAddress flexible) {
            FlexibleAddress flexibleAddressInternal = new FlexibleAddress();

            flexibleAddressInternal.setBuildingName(flexible.getBuildingName());
            flexibleAddressInternal.setBuildingNumber(flexible.getBuildingNumber());
            flexibleAddressInternal.setCountry(flexible.getCountry());
            flexibleAddressInternal.setFloor(flexible.getFloor());
            flexibleAddressInternal.setLocality(flexible.getLocality());
            flexibleAddressInternal.setPostalCode(flexible.getPostalCode());
            flexibleAddressInternal.setPostOfficeBox(flexible.getPostOfficeBox());
            flexibleAddressInternal.setRegion(flexible.getRegion());
            flexibleAddressInternal.setStreetName(flexible.getStreetName());
            flexibleAddressInternal.setUnit(flexible.getUnit());
            Optional.ofNullable(flexible.getAddressLine()).ifPresent(flexibleAddressInternal.getAddressLine()::addAll);
            Optional.ofNullable(flexible.getRecipientName()).ifPresent(flexibleAddressInternal.getRecipientName()::addAll);

            List<String> codes = flexible.getDeliveryPointCode().stream().map(obj -> {
                String deliveryCode = String.valueOf(obj);
                return deliveryCode;
            }).collect(Collectors.toList());
            flexibleAddressInternal.setDeliveryPointCode(new ArrayList<>());
            flexibleAddressInternal.getDeliveryPointCode().addAll(codes);

            return flexibleAddressInternal;
        }

        private List<CooperationCondition> getCooperationConditions(IiasGetResponse.Iia.CooperationConditions cooperationConditions) {
            List<CooperationCondition> cooperationConditionsInternals = new ArrayList<>();

            List<StaffTeacherMobilitySpec> staffTeacherMobs = cooperationConditions.getStaffTeacherMobilitySpec();
            if (staffTeacherMobs != null) {
                MobilityType mobType = new MobilityType();
                mobType.setMobilityGroup("Staff");
                mobType.setMobilityCategory("Teaching");

                List<CooperationCondition> ccList = staffTeacherMobs.stream().map(staffTeacher -> {
                    CooperationCondition cc = convertFromStaffToCooperationCondition(mobType, staffTeacher);

                    return cc;
                }).collect(Collectors.toList());

                cooperationConditionsInternals.addAll(ccList);
            }

            List<StaffTrainingMobilitySpec> stafftrainingMobs = cooperationConditions.getStaffTrainingMobilitySpec();
            if (stafftrainingMobs != null) {
                MobilityType mobType = new MobilityType();
                mobType.setMobilityGroup("Staff");
                mobType.setMobilityCategory("Training");

                List<CooperationCondition> ccList = stafftrainingMobs.stream().map(stafftraining -> {
                    CooperationCondition cc = convertFromStaffToCooperationCondition(mobType, stafftraining);

                    return cc;
                }).collect(Collectors.toList());

                cooperationConditionsInternals.addAll(ccList);
            }

            List<StudentStudiesMobilitySpec> studentStudiesMobs = cooperationConditions.getStudentStudiesMobilitySpec();
            if (studentStudiesMobs != null) {
                MobilityType mobType = new MobilityType();
                mobType.setMobilityGroup("Student");
                mobType.setMobilityCategory("Studies");

                List<CooperationCondition> ccList = studentStudiesMobs.stream().map(studentStudies -> {
                    CooperationCondition cc = convertFromStudentToCooperationCondition(mobType, studentStudies);

                    convertFromMobilitySpecification(studentStudies, cc);

                    return cc;
                }).collect(Collectors.toList());

                cooperationConditionsInternals.addAll(ccList);
            }

            List<StudentTraineeshipMobilitySpec> studentTraineeshipMobs = cooperationConditions.getStudentTraineeshipMobilitySpec();
            if (studentTraineeshipMobs != null) {
                MobilityType mobType = new MobilityType();
                mobType.setMobilityGroup("Student");
                mobType.setMobilityCategory("Training");

                List<CooperationCondition> ccList = studentTraineeshipMobs.stream().map(studentTraineeship -> {
                    CooperationCondition cc = convertFromStudentToCooperationCondition(mobType, studentTraineeship);

                    convertFromMobilitySpecification(studentTraineeship, cc);

                    return cc;
                }).collect(Collectors.toList());

                cooperationConditionsInternals.addAll(ccList);
            }

            return cooperationConditionsInternals;
        }

        private CooperationCondition convertFromStudentToCooperationCondition(MobilityType mobType,
                StudentMobilitySpecification studentStudies) {
            CooperationCondition cc = new CooperationCondition();

            cc.setMobilityType(mobType);

            Duration duration = new Duration();
            duration.setNumber(studentStudies.getTotalMonthsPerYear());
            cc.setDuration(duration);

            if (studentStudies.getEqfLevel() != null) {
                List<Byte> eqfLevels = studentStudies.getEqfLevel();
                byte[] arrEqfLevel = new byte[eqfLevels.size()];
                for (int i = 0; i < eqfLevels.size(); i++) {
                    arrEqfLevel[i] = eqfLevels.get(i).byteValue();
                }
                cc.setEqfLevel(arrEqfLevel);
            }

            cc.setBlended(studentStudies.isBlended());
            return cc;
        }

        private CooperationCondition convertFromStaffToCooperationCondition(MobilityType mobType,
                StaffMobilitySpecification staffTeacher) {
            CooperationCondition cc = new CooperationCondition();

            cc.setMobilityType(mobType);

            Duration duration = new Duration();
            duration.setNumber(staffTeacher.getTotalDaysPerYear());
            cc.setDuration(duration);

            convertFromMobilitySpecification(staffTeacher, cc);
            return cc;
        }

        private void convertFromMobilitySpecification(MobilitySpecification mobilitySpec, CooperationCondition cc) {
            List<eu.erasmuswithoutpaper.iia.entity.LanguageSkill> langskills = new ArrayList<>();

            if (mobilitySpec.getRecommendedLanguageSkill() != null) {
                List<MobilitySpecification.RecommendedLanguageSkill> recommendedSkills = mobilitySpec.getRecommendedLanguageSkill();
                for (MobilitySpecification.RecommendedLanguageSkill recommendedSkill : recommendedSkills) {
                    eu.erasmuswithoutpaper.iia.entity.LanguageSkill langskill = new eu.erasmuswithoutpaper.iia.entity.LanguageSkill();

                    if (recommendedSkill.getCefrLevel() != null) {
                        langskill.setCefrLevel(recommendedSkill.getCefrLevel());
                    }

                    langskill.setLanguage(recommendedSkill.getLanguage());

                    if (recommendedSkill.getSubjectArea() != null) {
                        SubjectArea subjectArea = new SubjectArea();
                        subjectArea.setIscedClarification(recommendedSkill.getSubjectArea().getIscedClarification());
                        subjectArea.setIscedFCode(recommendedSkill.getSubjectArea().getIscedFCode());
                        langskill.setSubjectArea(subjectArea);
                    }

                    langskills.add(langskill);
                }
                if (cc.getRecommendedLanguageSkill() == null) {
                    cc.setRecommendedLanguageSkill(new ArrayList<>());
                }

                cc.getRecommendedLanguageSkill().addAll(langskills);
            }

            if (cc.getReceivingAcademicYearId() == null) {
                cc.setReceivingAcademicYearId(new ArrayList<>());
            }
            cc.getReceivingAcademicYearId().addAll(mobilitySpec.getReceivingAcademicYearId());

            //Require academic years to be ordered and without gaps
            cc.getReceivingAcademicYearId().sort((c1, c2) -> {
                return c1.compareTo(c2);
            });

            if (mobilitySpec.getOtherInfoTerms() != null) {
                cc.setOtherInfoTerms(mobilitySpec.getOtherInfoTerms());
            }

            IiaPartner receivingPartnerInternal = new IiaPartner();
            if (mobilitySpec.getReceivingHeiId() != null) {
                receivingPartnerInternal.setInstitutionId(mobilitySpec.getReceivingHeiId());
            }

            if (mobilitySpec.getReceivingOunitId() != null) {
                receivingPartnerInternal.setOrganizationUnitId(mobilitySpec.getReceivingOunitId());
            }
            cc.setReceivingPartner(receivingPartnerInternal);

            IiaPartner sendingPartnerInternal = new IiaPartner();
            if (mobilitySpec.getSendingOunitId() != null) {
                sendingPartnerInternal.setOrganizationUnitId(mobilitySpec.getSendingOunitId());
            }

            if (mobilitySpec.getSendingHeiId() != null) {
                sendingPartnerInternal.setInstitutionId(mobilitySpec.getSendingHeiId());
            }
            cc.setSendingPartner(sendingPartnerInternal);

            if (mobilitySpec.getMobilitiesPerYear() != null) {
                MobilityNumber mobNumber = new MobilityNumber();
                mobNumber.setNumber(mobilitySpec.getMobilitiesPerYear().intValue());
                cc.setMobilityNumber(mobNumber);
            }

            List<SubjectArea> subjectAreasInt = new ArrayList<>();
            List<eu.erasmuswithoutpaper.api.iias.endpoints.SubjectArea> subjectAreas = mobilitySpec.getSubjectArea();
            for (eu.erasmuswithoutpaper.api.iias.endpoints.SubjectArea subjectArea : subjectAreas) {
                SubjectArea subjectAreaInt = new SubjectArea();

                if (subjectArea.getIscedClarification() != null) {
                    subjectAreaInt.setIscedClarification(subjectArea.getIscedClarification());
                }
                subjectAreaInt.setIscedFCode(subjectArea.getIscedFCode());

                subjectAreasInt.add(subjectAreaInt);
            }
            cc.setSubjectAreas(subjectAreasInt);

            if (mobilitySpec.getReceivingContact() != null) {
                List<Contact> receivingContacts = mobilitySpec.getReceivingContact();
                List<eu.erasmuswithoutpaper.organization.entity.Contact> contactsReceivigInternal = new ArrayList<>();
                for (Contact contact : receivingContacts) {
                    eu.erasmuswithoutpaper.organization.entity.Contact contactRec = convertToContactDetails(contact);

                    contactsReceivigInternal.add(contactRec);
                }

                if (cc.getReceivingPartner().getContacts() == null) {
                    cc.getReceivingPartner().setContacts(new ArrayList<>());
                }

                cc.getReceivingPartner().getContacts().addAll(contactsReceivigInternal);
            }

            if (mobilitySpec.getSendingContact() != null) {
                List<Contact> sendingContacts = mobilitySpec.getSendingContact();
                List<eu.erasmuswithoutpaper.organization.entity.Contact> contactsSendinginInternal = new ArrayList<>();
                for (Contact contact : sendingContacts) {
                    eu.erasmuswithoutpaper.organization.entity.Contact contactRec = convertToContactDetails(contact);

                    contactsSendinginInternal.add(contactRec);
                }

                if (cc.getSendingPartner().getContacts() == null) {
                    cc.getSendingPartner().setContacts(new ArrayList<>());
                }
                cc.getSendingPartner().getContacts().addAll(contactsSendinginInternal);
            }

        }

        private eu.erasmuswithoutpaper.organization.entity.Contact convertToContactDetails(Contact contact) {
            eu.erasmuswithoutpaper.organization.entity.Contact contactRec = new eu.erasmuswithoutpaper.organization.entity.Contact();

            FlexibleAddress address = convertFlexibleAddress(contact.getMailingAddress());
            FlexibleAddress streetAdd = convertFlexibleAddress(contact.getMailingAddress());

            ContactDetails contactDetails = new ContactDetails();
            contactDetails.setMailingAddress(address);
            contactDetails.setStreetAddress(streetAdd);

            contactRec.setContactDetails(contactDetails);

            Person person = new Person();
            person.setGender(Gender.getById(contact.getPersonGender()));
            return contactRec;
        }

    }
}
