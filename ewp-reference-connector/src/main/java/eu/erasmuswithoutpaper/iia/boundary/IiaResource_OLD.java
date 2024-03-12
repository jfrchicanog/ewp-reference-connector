package eu.erasmuswithoutpaper.iia.boundary;

//@Stateless
//@Path("iias")
public class IiaResource_OLD {
/*
    private static final Logger LOG = Logger.getLogger(IiaResource_OLD.class.getCanonicalName());

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
        LOG.fine("GET: heiID: " + heiId.stream().map(h -> h + " ").collect(Collectors.toList()));
        LOG.fine("GET: iiaIdList: " + iiaIdList.stream().map(h -> h + " ").collect(Collectors.toList()));
        if (iiaIdList != null && !iiaIdList.isEmpty()) {
            iiaIdentifiers = iiaIdList;
        } else {
            iiaIdentifiers = iiaCodeList;
            byLocalCodes = true;
        }

        LOG.fine("GET: byLocalCodes: " + byLocalCodes);

        String hei_Id = heiId.get(0);
        return iiaGet(hei_Id, iiaIdentifiers, byLocalCodes);
    }


    @POST
    @Path("cnr")
    @Produces(MediaType.APPLICATION_XML)
    @EwpAuthenticate
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Response cnrPost(@FormParam("notifier_hei_id") String notifierHeiId, @FormParam("iia_id") String iiaId) {
        LOG.fine("TEST: START CNR");
        if (notifierHeiId == null || notifierHeiId.isEmpty() || iiaId == null || iiaId.isEmpty()) {
            throw new EwpWebApplicationException("Missing argumanets for notification.", Response.Status.BAD_REQUEST);
        }

        //Collection<String> heisCoveredByCertificate;
        //if (httpRequest.getAttribute("EwpRequestRSAPublicKey") != null) {
        //    heisCoveredByCertificate = registryClient.getHeisCoveredByClientKey((RSAPublicKey) httpRequest.getAttribute("EwpRequestRSAPublicKey"));
        //} else {
        //    heisCoveredByCertificate = registryClient.getHeisCoveredByCertificate((X509Certificate) httpRequest.getAttribute("EwpRequestCertificate"));
        //}
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

        //} else {
        //    throw new EwpWebApplicationException("The client signature does not cover the notifier_heid.", Response.Status.BAD_REQUEST);
        //}
        return Response.ok(new ObjectFactory().createIiaCnrResponse(new Empty())).build();
    }

    /*@GET
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

        //if (httpRequest.getAttribute("EwpRequestRSAPublicKey") != null) {
        //    heisCoveredByCertificate = registryClient.getHeisCoveredByClientKey((RSAPublicKey) httpRequest.getAttribute("EwpRequestRSAPublicKey"));
        //}
        
        //if(heisCoveredByCertificate == null || heisCoveredByCertificate.isEmpty()) {
        //    heisCoveredByCertificate = registryClient.getHeisCoveredByCertificate((X509Certificate) httpRequest.getAttribute("EwpRequestCertificate"));
        //}
        
        //LOG.fine("HEI_ID recibida: "+hei_id);
        //LOG.fine("lista recibida de EWP: "+heisCoveredByCertificate);
        //if (heisCoveredByCertificate.contains(hei_id)) {
        LOG.fine("------------------------------ END /iias/stats ------------------------------");
        return iiaStats(hei_id);
        //} else {
        //    LOG.fine("------------------------------ ERROR /iias/stats ------------------------------");
        //    throw new EwpWebApplicationException("The client signature does not cover the notifier_heid.", Response.Status.BAD_REQUEST);
        //}
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
    }*/

    /*private javax.ws.rs.core.Response iiaStats(String heid) {

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
        LOG.fine("GET: iiaList.isEmpty(): " + iiaList.isEmpty());
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
            try {
                ait.addEditIia(heiId, iiaId);
            } catch (Exception e) {

            }
        }
    }*/
}
