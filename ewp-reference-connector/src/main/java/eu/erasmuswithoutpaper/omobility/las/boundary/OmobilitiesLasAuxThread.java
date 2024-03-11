package eu.erasmuswithoutpaper.omobility.las.boundary;

import eu.erasmuswithoutpaper.api.architecture.Empty;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.LearningAgreement;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.OmobilityLasGetResponse;
import eu.erasmuswithoutpaper.api.types.phonenumber.PhoneNumber;
import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import eu.erasmuswithoutpaper.common.boundary.ParamsClass;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.omobility.las.entity.*;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.core.Response;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Stateless
public class OmobilitiesLasAuxThread {

    @PersistenceContext(unitName = "connector")
    EntityManager em;

    @Inject
    RegistryClient registryClient;

    @Inject
    RestClient restClient;

    private static final java.util.logging.Logger LOG = java.util.logging.Logger.getLogger(OmobilitiesLasAuxThread.class.getCanonicalName());

    public void createLas(String heiId, String mobilityId) {
        LOG.fine("OmobilitiesLasAuxThread: Start auxiliary thread to create LAS for mobility " + mobilityId);

        Map<String, String> map = registryClient.getOmobilityLasHeiUrls(heiId);
        LOG.fine("OmobilitiesLasAuxThread: map: " + (map == null ? "null" : map.toString()));
        if (map == null || map.isEmpty()) {
            LOG.fine("OmobilitiesLasAuxThread: No LAS URLs found for HEI " + heiId);
            return;
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
        paramsMap.put("omobility_id", Collections.singletonList(mobilityId));
        ParamsClass paramsClass = new ParamsClass();
        paramsClass.setUnknownFields(paramsMap);
        clientRequest.setParams(paramsClass);

        LOG.fine("OmobilitiesLasAuxThread: params: " + paramsMap.toString());

        ClientResponse omobilityLasGetResponse = restClient.sendRequest(clientRequest, OmobilityLasGetResponse.class);

        LOG.fine("NOTIFY: response: " + omobilityLasGetResponse.getRawResponse());

        if (omobilityLasGetResponse.getStatusCode() != Response.Status.OK.getStatusCode()) {
            //TODO: Handle error, notify monitoring
            return;
        }

        OmobilityLasGetResponse response = (OmobilityLasGetResponse) omobilityLasGetResponse.getResult();

        if (response == null) {
            LOG.fine("OmobilitiesLasAuxThread: response is null");
            return;
        }

        if (response.getLa() == null) {
            LOG.fine("OmobilitiesLasAuxThread: response.getLa() is null");
            return;
        }

        if (response.getLa().isEmpty()) {
            LOG.fine("OmobilitiesLasAuxThread: response.getLa() is empty");
            return;
        }

        LearningAgreement learningAgreement = response.getLa().get(0);

        List<OlearningAgreement> omobilityLasList = em.createNamedQuery(OlearningAgreement.findBySendingHeiId).setParameter("sendingHei", heiId).getResultList();

        omobilityLasList = omobilityLasList.stream().filter(ola -> ola.getOmobilityId().equals(learningAgreement.getOmobilityId())).collect(Collectors.toList());
        if (omobilityLasList.isEmpty()) {
            createOlearningAgreement(learningAgreement);
        } else {
            updateOlearningAgreement(learningAgreement, omobilityLasList.get(0));
        }


    }

    private void createOlearningAgreement(LearningAgreement la) {
        OlearningAgreement ola = convertToOlearningAgreement(la);
        em.persist(ola);
    }

    private void updateOlearningAgreement(LearningAgreement la, OlearningAgreement ola) {
        OlearningAgreement updatedOla = convertToOlearningAgreement(la);
        updatedOla.setId(ola.getId());
        em.merge(updatedOla);
    }

    private OlearningAgreement convertToOlearningAgreement(LearningAgreement la) {
        if(la == null) {
            return null;
        }

        OlearningAgreement ola = new OlearningAgreement();

        ola.setOmobilityId(la.getOmobilityId());
        ola.setReceivingAcademicTermEwpId(la.getReceivingAcademicYearId());
        if(la.getStartDate() != null) {
            ola.setStartDate(la.getStartDate().toGregorianCalendar().getTime());
        }
        if(la.getStartYearMonth() != null) {
            ola.setStartYearMonth(YearMonth.from(la.getStartYearMonth().toGregorianCalendar().getTime().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()));
        }
        if(la.getEndDate() != null) {
            ola.setEndDate(la.getEndDate().toGregorianCalendar().getTime());
        }
        if(la.getEndYearMonth() != null) {
            ola.setEndYearMonth(YearMonth.from(la.getEndYearMonth().toGregorianCalendar().getTime().toInstant()
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()));
        }
        ola.setEqfLevelStudiedAtDeparture(la.getEqfLevelStudiedAtDeparture());
        ola.setIscedFCode(la.getIscedFCode());
        ola.setIscedClarification(la.getIscedClarification());
        ola.setLearningOutcomesUrl(la.getLearningOutcomesUrl());
        ola.setProvisionsUrl(la.getProvisionsUrl());

        ola.setReceivingHei(convertToMobilityInstitution(la.getReceivingHei()));
        ola.setSendingHei(convertToMobilityInstitution(la.getSendingHei()));

        ola.setStudent(convertToStudent(la.getStudent()));

        ola.setStudentLanguageSkill(convertToStudentLanguageSkill(la.getStudentLanguageSkill()));
        ola.setFirstVersion(convertToListOfComponents(la.getFirstVersion()));
        ola.setApprovedChanges(convertToListOfComponents(la.getApprovedChanges()));
        ola.setChangesProposal(convertToChangesProposal(la.getChangesProposal()));

        return ola;
    }

    private MobilityInstitution convertToMobilityInstitution(eu.erasmuswithoutpaper.api.omobilities.las.endpoints.MobilityInstitution mi) {
        if(mi == null) {
            return null;
        }

        MobilityInstitution m = new MobilityInstitution();

        m.setHeiId(mi.getHeiId());
        m.setOunitId(mi.getOunitId());
        m.setOunitName(mi.getOunitName());

        m.setContactPerson(convertToContactPerson(mi.getContactPerson()));

        return m;
    }

    private eu.erasmuswithoutpaper.omobility.las.entity.ContactPerson convertToContactPerson(eu.erasmuswithoutpaper.api.omobilities.las.endpoints.MobilityInstitution.ContactPerson cp) {
        if(cp == null) {
            return null;
        }

        eu.erasmuswithoutpaper.omobility.las.entity.ContactPerson c = new eu.erasmuswithoutpaper.omobility.las.entity.ContactPerson();

        c.setEmail(cp.getEmail());
        c.setFamilyName(cp.getFamilyName());
        c.setGivenNames(cp.getGivenNames());

        c.setPhoneNumber(convertToPhoneNumber(cp.getPhoneNumber()));

        return c;
    }

    private OmobilityPhoneNumber convertToPhoneNumber(PhoneNumber pn) {
        if(pn == null) {
            return null;
        }

        OmobilityPhoneNumber p = new OmobilityPhoneNumber();

        p.setE164(pn.getE164());
        //p.setExtensionNumber(pn.getExt());
        p.setOtherFormat(pn.getOtherFormat());

        return p;
    }

    private Student convertToStudent(eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Student s) {
        if(s == null) {
            return null;
        }

        Student student = new Student();

        student.setFamilyName(s.getFamilyName());
        student.setGivenNames(s.getGivenNames());
        if (s.getBirthDate() != null) {
            student.setBirthDate(s.getBirthDate().toGregorianCalendar().getTime());
        }
        student.setCitizenship(s.getCitizenship());
        student.setGender(s.getGender());
        student.setEmail(s.getEmail());
        student.setGlobalId(s.getGlobalId());

        return student;
    }

    private OlasLanguageSkill convertToStudentLanguageSkill(LearningAgreement.StudentLanguageSkill sls) {
        if(sls == null) {
            return null;
        }

        OlasLanguageSkill studentLanguageSkill = new OlasLanguageSkill();

        studentLanguageSkill.setCefrLevel(sls.getCefrLevel());
        studentLanguageSkill.setLanguage(sls.getLanguage());

        return studentLanguageSkill;
    }

    private ListOfComponents convertToListOfComponents(eu.erasmuswithoutpaper.api.omobilities.las.endpoints.ListOfComponents loc) {
        if(loc == null) {
            return null;
        }

        ListOfComponents listOfComponents = new ListOfComponents();

        if (loc.getComponentsStudied() != null && loc.getComponentsStudied().getComponent() != null) {
            listOfComponents.setComponentsStudied(loc.getComponentsStudied().getComponent().stream().map(this::convertToComponent).collect(Collectors.toList()));
        }
        if (loc.getComponentsRecognized() != null && loc.getComponentsRecognized().getComponent() != null) {
            listOfComponents.setComponentsRecognized(loc.getComponentsRecognized().getComponent().stream().map(this::convertToComponent).collect(Collectors.toList()));
        }
        if (loc.getVirtualComponents() != null && loc.getVirtualComponents().getComponent() != null) {
            listOfComponents.setVirtualComponents(loc.getVirtualComponents().getComponent().stream().map(this::convertToComponent).collect(Collectors.toList()));
        }
        if (loc.getBlendedMobilityComponents() != null && loc.getBlendedMobilityComponents().getComponent() != null) {
            listOfComponents.setBlendedMobilityComponents(loc.getBlendedMobilityComponents().getComponent().stream().map(this::convertToComponent).collect(Collectors.toList()));
        }
        if (loc.getShortTermDoctoralComponents() != null && loc.getShortTermDoctoralComponents().getComponent() != null) {
            listOfComponents.setShortTermDoctoralComponents(loc.getShortTermDoctoralComponents().getComponent().stream().map(this::convertToComponent).collect(Collectors.toList()));
        }

        listOfComponents.setStudentSignature(convertToSignature(loc.getStudentSignature()));
        listOfComponents.setSendingHeiSignature(convertToSignature(loc.getSendingHeiSignature()));
        listOfComponents.setReceivingHeiSignature(convertToSignature(loc.getReceivingHeiSignature()));

        return listOfComponents;
    }

    private Component convertToComponent(eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Component c) {
        if (c == null) {
            return null;
        }

        Component component = new Component();

        component.setLosId(c.getLosId());
        component.setLosCode(c.getLosCode());
        component.setTitle(c.getTitle());
        component.setLoiId(c.getLoiId());
        component.setRecognitionConditions(c.getRecognitionConditions());
        component.setShortDescription(c.getShortDescription());
        component.setStatus(c.getStatus());
        component.setReasonCode(c.getReasonCode());
        component.setReasonText(c.getReasonText());

        component.setTermId(convertToTermId(c.getTermId()));

        if (c.getCredit() != null) {
            component.setCredit(c.getCredit().stream().map(this::convertToCredit).collect(Collectors.toList()));
        }

        return component;
    }

    private TermId convertToTermId(https.github_com.erasmus_without_paper.ewp_specs_types_academic_term.tree.stable_v2.TermId ti) {
        if (ti == null) {
            return null;
        }

        TermId termId = new TermId();

        termId.setTermNumber(ti.getTermNumber());
        termId.setTotalTerms(ti.getTotalTerms());

        return termId;
    }

    private Credit convertToCredit(eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Component.Credit c) {
        if (c == null) {
            return null;
        }

        Credit credit = new Credit();

        credit.setScheme(c.getScheme());
        credit.setValue(c.getValue());

        return credit;
    }

    private Signature convertToSignature(eu.erasmuswithoutpaper.api.omobilities.las.endpoints.Signature s) {
        if(s == null) {
            return null;
        }

        Signature signature = new Signature();

        signature.setSignerApp(s.getSignerApp());
        signature.setSignerName(s.getSignerName());
        signature.setSignerEmail(s.getSignerEmail());
        if (s.getTimestamp() != null) {
            signature.setTimestamp(s.getTimestamp().toGregorianCalendar().getTime());
        }
        signature.setSignerPosition(s.getSignerPosition());

        return signature;
    }

    private ChangesProposal convertToChangesProposal(LearningAgreement.ChangesProposal cp) {
        if(cp == null) {
            return null;
        }

        ChangesProposal changesProposal = new ChangesProposal();

        if (cp.getComponentsStudied() != null && cp.getComponentsStudied().getComponent() != null) {
            changesProposal.setComponentsStudied(cp.getComponentsStudied().getComponent().stream().map(this::convertToComponent).collect(Collectors.toList()));
        }
        if (cp.getComponentsRecognized() != null && cp.getComponentsRecognized().getComponent() != null) {
            changesProposal.setComponentsRecognized(cp.getComponentsRecognized().getComponent().stream().map(this::convertToComponent).collect(Collectors.toList()));
        }
        if (cp.getVirtualComponents() != null && cp.getVirtualComponents().getComponent() != null) {
            changesProposal.setVirtualComponents(cp.getVirtualComponents().getComponent().stream().map(this::convertToComponent).collect(Collectors.toList()));
        }
        if (cp.getBlendedMobilityComponents() != null && cp.getBlendedMobilityComponents().getComponent() != null) {
            changesProposal.setBlendedMobilityComponents(cp.getBlendedMobilityComponents().getComponent().stream().map(this::convertToComponent).collect(Collectors.toList()));
        }
        if (cp.getShortTermDoctoralComponents() != null && cp.getShortTermDoctoralComponents().getComponent() != null) {
            changesProposal.setShortTermDoctoralComponents(cp.getShortTermDoctoralComponents().getComponent().stream().map(this::convertToComponent).collect(Collectors.toList()));
        }

        changesProposal.setStudentSignature(convertToSignature(cp.getStudentSignature()));
        changesProposal.setSendingHeiSignature(convertToSignature(cp.getSendingHeiSignature()));
        changesProposal.setReceivingHeiSignature(convertToSignature(cp.getReceivingHeiSignature()));

        changesProposal.setStudent(convertToStudent(cp.getStudent()));

        return changesProposal;
    }
}
