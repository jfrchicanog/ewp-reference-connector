
package eu.erasmuswithoutpaper.organization.boundary;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import eu.erasmuswithoutpaper.api.ounits.OunitsResponse;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.error.control.EwpWebApplicationException;
import eu.erasmuswithoutpaper.organization.control.OrganizationUnitConverter;
import eu.erasmuswithoutpaper.organization.entity.Institution;
import eu.erasmuswithoutpaper.organization.entity.OrganizationUnit;

@Stateless
@Path("ounits")
public class OrganizationUnitResource {
    @PersistenceContext(unitName = "connector")
    EntityManager em;
    
    @Inject
    OrganizationUnitConverter organizationUnitConverter;
    
    @Inject
    GlobalProperties properties;
    
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response ounitsGet(@QueryParam("hei_id") List<String> heiId, @QueryParam("ounit_id") List<String> organizationUnitIdList, @QueryParam("ounit_code") List<String> organizationUnitCodeList) {
    	return processRequest(heiId, organizationUnitIdList, organizationUnitCodeList);
    }

	private javax.ws.rs.core.Response processRequest(List<String> heiId, List<String> organizationUnitIdList,
			List<String> organizationUnitCodeList) {
		
		if (heiId==null || heiId.isEmpty()) {
			throw new EwpWebApplicationException("Missing arguments: one hei_id should be provided", Response.Status.BAD_REQUEST);
		}
		
		if (heiId.size() > 1) {
			throw new EwpWebApplicationException("Too many heiIds", Response.Status.BAD_REQUEST);
		}
		
		if ( (organizationUnitCodeList != null && !organizationUnitCodeList.isEmpty()) && (organizationUnitIdList != null && !organizationUnitIdList.isEmpty()) ) {
        	throw new EwpWebApplicationException("Providing both ounit_code and ounit_id is not correct", Response.Status.BAD_REQUEST);
        }
        
        if ( (organizationUnitCodeList == null || organizationUnitCodeList.isEmpty()) && (organizationUnitIdList == null || organizationUnitIdList.isEmpty()) ) {
        	throw new EwpWebApplicationException("Missing arguments, ounit_code or ounit_id is required", Response.Status.BAD_REQUEST);
        }
        
        if (organizationUnitIdList.size() > properties.getMaxOunitsIds()) {
            throw new EwpWebApplicationException("Max number of organization unit ids has exceeded.", Response.Status.BAD_REQUEST);
        }
        
        if (organizationUnitCodeList.size() > properties.getMaxOunitsCodes()) {
            throw new EwpWebApplicationException("Max number of organization codes has exceeded.", Response.Status.BAD_REQUEST);
        }
    	
        List<String> ounitIdentifiers = new ArrayList<>();
        
        //Flag to define the search criteria. 
        boolean byLocalCodes = false;
        
        if (organizationUnitIdList != null && !organizationUnitIdList.isEmpty()) {
        	ounitIdentifiers.addAll(organizationUnitIdList);
        } else {
        	ounitIdentifiers.addAll(organizationUnitCodeList);
        	byLocalCodes = true;
        }
    	
    	return organizationUnits(heiId.get(0), ounitIdentifiers, byLocalCodes);
	}
    
    @POST
    @Produces(MediaType.APPLICATION_XML)
    public javax.ws.rs.core.Response ounitsPost(@FormParam("hei_id") List<String> heiId, @FormParam("ounit_id") List<String> organizationUnitIdList, @FormParam("ounit_code") List<String> organizationUnitCodeList) {
    	return processRequest(heiId, organizationUnitIdList, organizationUnitCodeList);
    }
    
    private javax.ws.rs.core.Response organizationUnits(String heiId, List<String> organizationUnitIdList, boolean byLocalCodes) {
        OunitsResponse response = new OunitsResponse();
        
        List<Institution> institutionList =  em.createNamedQuery(Institution.findByInstitutionId).setParameter("institutionId", heiId).getResultList();
        if (institutionList.isEmpty()) {
            throw new EwpWebApplicationException("Institution with id '" + heiId + "' is not found", Response.Status.BAD_REQUEST);
        }
        
        response.getOunit().addAll(ounits(organizationUnitIdList, institutionList.get(0).getOrganizationUnits(), null, heiId, byLocalCodes));
        
        return javax.ws.rs.core.Response.ok(response).build();
    }
    
    private List<OunitsResponse.Ounit> ounits(List<String> organizationUnitIdList, List<OrganizationUnit> organizationUnits, String parentOrganizationUnitId, String parentInstitutionId, boolean findByCode) {
        List<OunitsResponse.Ounit> ounits = new ArrayList<>();
        
        organizationUnits.stream().map((ou) -> {
        	
        	if(findByCode) {
        		if (organizationUnitIdList.contains(ou.getOrganizationUnitCode())) {
                    ounits.add(organizationUnitConverter.convertToOunit(ou, parentOrganizationUnitId, parentInstitutionId));
                }
        	} else {
        		if (organizationUnitIdList.contains(ou.getId())) {
                    ounits.add(organizationUnitConverter.convertToOunit(ou, parentOrganizationUnitId, parentInstitutionId));
                }
        	}
            
            return ou;
        }).forEachOrdered((ou) -> {
            ounits.addAll(ounits(organizationUnitIdList, ou.getOrganizationUnits(), ou.getId(), parentInstitutionId,findByCode));
        });
        
        return ounits;
    }
}
