package eu.erasmuswithoutpaper.iia.boundary;

import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.function.BiPredicate;
import java.util.logging.Logger;
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
import eu.erasmuswithoutpaper.api.iias.cnr.ObjectFactory;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasIndexResponse;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasStatsResponse;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.iia.approval.entity.IiaApproval;
import eu.erasmuswithoutpaper.iia.common.IiaTaskService;
import eu.erasmuswithoutpaper.iia.control.IiaConverter;
import eu.erasmuswithoutpaper.iia.entity.CooperationCondition;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.iia.entity.Iia;
import eu.erasmuswithoutpaper.notification.entity.Notification;
import eu.erasmuswithoutpaper.notification.entity.NotificationTypes;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import eu.erasmuswithoutpaper.security.EwpAuthenticate;

import java.util.logging.Level;
import java.util.stream.Stream;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

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

    @Inject
    AuxIiaThread ait;

    @GET
    @Path("index")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response indexGet(@QueryParam("receiving_academic_year_id") List<String> receiving_academic_year_id, @QueryParam("modified_since") List<String> modified_since) {
        return iiaIndex(receiving_academic_year_id, modified_since);
    }

    @POST
    @Path("index")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response indexPost(@FormParam("receiving_academic_year_id") List<String> receiving_academic_year_id, @FormParam("modified_since") List<String> modified_since) {
        return iiaIndex(receiving_academic_year_id, modified_since);
    }

    private javax.ws.rs.core.Response iiaIndex(List<String> receiving_academic_year_id, List<String> modified_since) {

        if (modified_since != null && modified_since.size() > 1) {
            throw new EwpWebApplicationException("Not allow more than one value of modified_since", Response.Status.BAD_REQUEST);
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

            filteredIiaList = new ArrayList<>(filteredIiaList);

            List<Iia> filteredIiaByReceivingAcademic = new ArrayList<>();
            if (receiving_academic_year_id != null && !receiving_academic_year_id.isEmpty()) {

                for (String year_id : receiving_academic_year_id) {
                    List<Iia> filterefList = filteredIiaList.stream().filter(iia -> anyMatchBetweenReceivingAcademicYear.test(iia, year_id)).collect(Collectors.toList());

                    filteredIiaByReceivingAcademic.addAll(filterefList);
                }

                filteredIiaList = new ArrayList<Iia>(filteredIiaByReceivingAcademic);
            }

            LOG.fine("Filtered 1:" + filteredIiaList.stream().map(Iia::getId).collect(Collectors.toList()));

            if (modified_since != null && !modified_since.isEmpty()) {

                Calendar calendarModifySince = Calendar.getInstance();

                List<Iia> tempFilteredModifiedSince = new ArrayList<>();

                String modifiedValue = modified_since.get(0);
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

                filteredIiaList = new ArrayList<>(tempFilteredModifiedSince);

            }

            LOG.fine("Filtered 2:" + filteredIiaList.stream().map(Iia::getId).collect(Collectors.toList()));
        }

        LOG.fine("Filtered 3:" + filteredIiaList.stream().map(Iia::getId).collect(Collectors.toList()));

        if (!filteredIiaList.isEmpty()) {
            List<String> iiaIds = filteredIiaList.stream().map(Iia::getId).collect(Collectors.toList());
            LOG.fine("IIA IDs:" + iiaIds);
            response.getIiaId().addAll(iiaIds);
        }

        return javax.ws.rs.core.Response.ok(response).build();
    }

    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response getGet(@QueryParam("iia_id") List<String> iiaIdList) {
        if (iiaIdList == null || iiaIdList.isEmpty()) {
            throw new EwpWebApplicationException("No iia_id provided", Response.Status.BAD_REQUEST);
        }

        if (iiaIdList.size() > properties.getMaxIiaIds()) {
            throw new EwpWebApplicationException("Max number of IIA ids has exceeded.", Response.Status.BAD_REQUEST);
        }

        return iiaGet(iiaIdList);

    }

    @POST
    @Path("get")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    public javax.ws.rs.core.Response getPost(@FormParam("iia_id") List<String> iiaIdList) {
        if (iiaIdList == null || iiaIdList.isEmpty()) {
            throw new EwpWebApplicationException("No iia_id provided", Response.Status.BAD_REQUEST);
        }

        if (iiaIdList.size() > properties.getMaxIiaIds()) {
            throw new EwpWebApplicationException("Max number of IIA ids has exceeded.", Response.Status.BAD_REQUEST);
        }

        return iiaGet(iiaIdList);
    }

    private javax.ws.rs.core.Response iiaGet(List<String> iiaIdList) {
        IiasGetResponse response = new IiasGetResponse();

        List<Iia> iiaList = iiaIdList.stream()
                .map(id -> em.find(Iia.class, id))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        LOG.fine("GET: iiaList.isEmpty(): " + iiaList.isEmpty());
        if (!iiaList.isEmpty()) {
            String localHeiId = "";
            List<Institution> institutions = em.createNamedQuery(Institution.findAll, Institution.class).getResultList();

            localHeiId = institutions.get(0).getInstitutionId();

            response.getIia().addAll(iiaConverter.convertToIias(localHeiId, iiaList));
        }


        return javax.ws.rs.core.Response.ok(response).build();
    }

    @GET
    @Path("stats")
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response iiaGetStats() {
        LOG.fine("------------------------------ START /iias/stats ------------------------------");
        return iiaStats();
    }

    private javax.ws.rs.core.Response iiaStats() {

        IiasStatsResponse response = new IiasStatsResponse();

        BiPredicate<Iia, List<Notification>> conditionNotApproved = new BiPredicate<Iia, List<Notification>>() {
            boolean notNotified = true;

            @Override
            public boolean test(Iia iia, List<Notification> iiaApprovalNotifications) {

                for (Notification iiaApprovalNotified : iiaApprovalNotifications) {
                    if (iia.getId().equals(iiaApprovalNotified.getChangedElementIds())) {
                        notNotified = false;
                        break;
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
        List<Iia> localIias = em.createNamedQuery(Iia.findAll, Iia.class).getResultList();
        int iiaFetchable = localIias.size();

        List<Notification> iiaApprovalNotifications = em.createNamedQuery(Notification.findAll, Notification.class).getResultList().stream().filter(n -> (n.getType().equals(NotificationTypes.IIAAPPROVAL))).collect(Collectors.toList());
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


    @POST
    @Path("cnr")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public javax.ws.rs.core.Response cnrPost(@FormParam("notifier_hei_id") String notifierHeiId, @FormParam("iia_id") String iiaId) {
        LOG.fine("TEST: START CNR");
        if (notifierHeiId == null || notifierHeiId.isEmpty() || iiaId == null || iiaId.isEmpty()) {
            throw new EwpWebApplicationException("Missing argumanets for notification.", Response.Status.BAD_REQUEST);
        }

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

        return javax.ws.rs.core.Response.ok(new ObjectFactory().createIiaCnrResponse(new Empty())).build();
    }

    private void execNotificationToAlgoria(String iiaId, String notifierHeiId) {

        Callable<String> callableTask = IiaTaskService.createTask(iiaId, IiaTaskService.MODIFIED, notifierHeiId);

        //Put the task in the queue
        IiaTaskService.addTask(callableTask);
    }

    BiPredicate<Iia, String> anyMatchBetweenReceivingAcademicYear = new BiPredicate<Iia, String>() {
        @Override
        public boolean test(Iia iia, String receiving_academic_year_id) {

            List<CooperationCondition> cConditions = iia.getCooperationConditions();

            Stream<CooperationCondition> stream = cConditions.stream().filter(c -> {
                if (c.getReceivingAcademicYearId() != null) {
                    if(c.getReceivingAcademicYearId().isEmpty() || c.getReceivingAcademicYearId().size() <= 1){
                        return false;
                    }
                    String firtsYear = c.getReceivingAcademicYearId().get(0).split("/")[0];
                    String lastYear = c.getReceivingAcademicYearId().get(0).split("/")[1];

                    return (receiving_academic_year_id.compareTo(firtsYear) >= 0 && receiving_academic_year_id.compareTo(lastYear) <= 0);
                }
                return false;
            });

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

    private class CNRGetFirst extends Thread {

        private String heiId;
        private String iiaId;

        public CNRGetFirst(String heiId, String iiaId) {
            this.heiId = heiId;
            this.iiaId = iiaId;
        }

        @Override
        public void run() {
            try {
                ait.addEditIia(heiId, iiaId);
            } catch (Exception e) {

            }
        }
    }
}
