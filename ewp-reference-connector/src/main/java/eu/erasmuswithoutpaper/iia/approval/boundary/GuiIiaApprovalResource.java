package eu.erasmuswithoutpaper.iia.approval.boundary;

import java.io.IOException;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

import eu.erasmuswithoutpaper.iia.control.IiaConverter;
import eu.erasmuswithoutpaper.iia.control.IiasEJB;
import eu.erasmuswithoutpaper.iia.entity.*;
import eu.erasmuswithoutpaper.security.InternalAuthenticate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.sun.org.apache.xml.internal.security.c14n.CanonicalizationException;
import com.sun.org.apache.xml.internal.security.c14n.InvalidCanonicalizerException;

import eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse;
import eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse.Approval;
import eu.erasmuswithoutpaper.api.iias.approval.cnr.IiaApprovalCnr;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse;
import eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse.Iia.CooperationConditions;
import eu.erasmuswithoutpaper.common.boundary.ClientRequest;
import eu.erasmuswithoutpaper.common.boundary.ClientResponse;
import eu.erasmuswithoutpaper.common.boundary.HttpMethodEnum;
import eu.erasmuswithoutpaper.common.control.HeiEntry;
import eu.erasmuswithoutpaper.common.control.RegistryClient;
import eu.erasmuswithoutpaper.common.control.RestClient;
import eu.erasmuswithoutpaper.iia.approval.control.IiaApprovalConverter;
import eu.erasmuswithoutpaper.iia.approval.entity.IiaApproval;
import eu.erasmuswithoutpaper.iia.control.HashCalculationUtility;
import eu.erasmuswithoutpaper.organization.entity.Institution;

@Stateless
@Path("iiaApproval")
public class GuiIiaApprovalResource {
	private static final Logger logger = LoggerFactory.getLogger(GuiIiaApprovalResource.class);
	
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @Inject
    RegistryClient registryClient;
    
    @Inject
    RestClient restClient;
    
    @Inject
    IiaApprovalConverter converter;
    
    @Inject
	IiaConverter iiaConverter;

    @GET
    @Path("get_all")
    public Response getAll() {
        List<IiaApproval> iiaApprovalList = em.createNamedQuery(IiaApproval.findAll).getResultList();
        
        List<Approval> approvals = converter.convertToIiasApproval(iiaApprovalList);
        GenericEntity<List<Approval>> entity = new GenericEntity<List<Approval>>(approvals) {};
        
        return Response.ok(entity).build();
    }

    @POST
    @Path("add")
    @Consumes(MediaType.APPLICATION_JSON)
    public void add(IiaApproval iiaApproval) {
        em.persist(iiaApproval);
        
        //Sent notification to partners
        String iia_Id = iiaApproval.getId();
        Iia iia = em.find(Iia.class, iia_Id);
        
        notifyCnrPartner(iia);
    }

    @POST
    @Path("sendCnrApproval")
    @InternalAuthenticate
    public void resendCnr(@QueryParam("iiaId") String iiaId) {
        Iia iia = em.find(Iia.class, iiaId);
        if (iia == null) {
            return;
        }

        notifyCnrPartner(iia);
    }

    @GET
    @Path("heis")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response iiaHeis() {
        List<HeiEntry> heis = registryClient.getIiaApprovalHeisWithUrls();
        
        GenericEntity<List<HeiEntry>> entity = new GenericEntity<List<HeiEntry>>(heis) {};
        return javax.ws.rs.core.Response.ok(entity).build();
    }
    
    @POST
    @Path("iias_approval")
    @Produces(MediaType.APPLICATION_JSON)
    public javax.ws.rs.core.Response iiasApproval(ClientRequest clientRequest) {
        ClientResponse iiaApprovalResponse = restClient.sendRequest(clientRequest, eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse.class);
       
        //Verify that the agreements received are in their latest version through hash and then I return the correct ones to Algoria
        /*IiasApprovalResponse iiaApproval = (IiasApprovalResponse) iiaApprovalResponse.getResult();
        List<Approval> iiaApprovals = iiaApproval.getApproval();
        
        List<Approval> correctIiaApproval = new ArrayList<>();
        for (Approval approval : iiaApprovals) {
        	String hashCode = approval.getIiaHash();
        	
        	String iiaId = approval.getIiaId();
        	Iia iia = em.find(Iia.class, iiaId);
        	
        	eu.erasmuswithoutpaper.api.iias.endpoints.IiasGetResponse.Iia iiaresponse = new IiasGetResponse.Iia();
        	iiaresponse.setCooperationConditions(iiaConverter.convertToCooperationConditions(iia.getCooperationConditions(), iia.getConditionsTerminatedAsAWhole(), "uma.es"));
        	
        	try {
        		
	        	JAXBContext jaxbContext = JAXBContext.newInstance(IiasGetResponse.Iia.CooperationConditions.class);
	        	Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
	        	jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	        	
	        	StringWriter sw = new StringWriter();
	        	
	        	//Create a copy off CooperationConditions to be used in calculateSha256 function
	        	CooperationConditions cc = new CooperationConditions();
	        	cc.getStaffTeacherMobilitySpec().addAll(iiaresponse.getCooperationConditions().getStaffTeacherMobilitySpec());
	        	cc.getStaffTrainingMobilitySpec().addAll(iiaresponse.getCooperationConditions().getStaffTrainingMobilitySpec());
	        	cc.getStudentStudiesMobilitySpec().addAll(iiaresponse.getCooperationConditions().getStudentStudiesMobilitySpec());
	        	cc.getStudentTraineeshipMobilitySpec().addAll(iiaresponse.getCooperationConditions().getStudentTraineeshipMobilitySpec());
	        	
	        	cc = iiaConverter.removeContactInfo(cc);
	        	
	        	QName qName = new QName("cooperation_conditions");
	        	JAXBElement<IiasGetResponse.Iia.CooperationConditions> root = new JAXBElement<IiasGetResponse.Iia.CooperationConditions>(qName, IiasGetResponse.Iia.CooperationConditions.class, cc);
        	
        	
	        	jaxbMarshaller.marshal(root, sw);
	        	String xmlString = sw.toString();
	        	
				String calculatedHash = HashCalculationUtility.calculateSha256(xmlString);
				
				if (hashCode.equals(calculatedHash)) {
					correctIiaApproval.add(approval);					
				}
        	} catch (InvalidCanonicalizerException | CanonicalizationException | NoSuchAlgorithmException | SAXException
					| IOException | ParserConfigurationException | TransformerException | JAXBException e) {
				logger.error("Can't calculate sha256", e);
			}
		}
        
        iiaApproval.getApproval().clear();
        iiaApproval.getApproval().addAll(correctIiaApproval);
        iiaApprovalResponse.setResult(iiaApproval);*/
        
        return javax.ws.rs.core.Response.ok(iiaApprovalResponse).build();
    }
    
    private List<ClientResponse> notifyCnrPartner(Iia iia) {
    	List<ClientResponse> partnersResponseList = new ArrayList<>();
    	
    	//Getting agreement partners
		IiaPartner partnerSending = null;
		IiaPartner partnerReceiving = null;
		
		List<Institution> institutions = em.createNamedQuery(Institution.findAll, Institution.class).getResultList();
		for (CooperationCondition condition : iia.getCooperationConditions()) {
			partnerSending = condition.getSendingPartner();
			partnerReceiving = condition.getReceivingPartner();
			
			Map<String, String> urls = null;
			for (Institution institution : institutions){
				
				if (!institution.getInstitutionId().equals(partnerSending.getInstitutionId())) {
					
					//Get the url for notify the institute not supported by our EWP
			    	urls = registryClient.getIiaApprovalCnrHeiUrls(partnerSending.getInstitutionId());
			    	
				} else if (!institution.getInstitutionId().equals(partnerReceiving.getInstitutionId())) {
					
					//Get the url for notify the institute not supported by our EWP
			    	urls = registryClient.getIiaApprovalCnrHeiUrls(partnerReceiving.getInstitutionId());
			    	
				}
			}
			
			if (urls != null) {
				List<String> urlValues = new ArrayList<String>(urls.values());
		    	
		    	//Notify the other institution about the modification 
		    	ClientRequest clientRequest = new ClientRequest();
		    	clientRequest.setUrl(urls.get(urlValues.get(0)));//get the first and only one url
		    	clientRequest.setHeiId(partnerReceiving.getInstitutionId());
		    	clientRequest.setMethod(HttpMethodEnum.POST);
		    	clientRequest.setHttpsec(true);
				
		    	ClientResponse iiaResponse = restClient.sendRequest(clientRequest, IiaApprovalCnr.class);
		    	
		    	partnersResponseList.add(iiaResponse);
			}
			
        }
		
		return partnersResponseList;
    	
	}
}
