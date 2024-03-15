package eu.erasmuswithoutpaper.iia.approval.control;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import eu.erasmuswithoutpaper.api.iias.approval.IiasApprovalResponse;
import eu.erasmuswithoutpaper.iia.approval.entity.IiaApproval;

public class IiaApprovalConverter {
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public List<IiasApprovalResponse.Approval> convertToIiasApproval(List<IiaApproval> iiaApprovalList) {
    	return iiaApprovalList.stream().map(iiaApproval -> {
    		IiasApprovalResponse.Approval converted = new IiasApprovalResponse.Approval();
    		
    		converted.setIiaHash(iiaApproval.getConditionsHash());
    		converted.setIiaId(iiaApproval.getIiaCode());
    		return converted;
    	}).collect(Collectors.toList());
    }
}
