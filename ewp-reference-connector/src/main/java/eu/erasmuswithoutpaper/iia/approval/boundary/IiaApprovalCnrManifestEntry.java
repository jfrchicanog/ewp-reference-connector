package eu.erasmuswithoutpaper.iia.approval.boundary;

import javax.inject.Inject;

import eu.erasmuswithoutpaper.api.architecture.ManifestApiEntryBase;
import eu.erasmuswithoutpaper.api.iias.approval.cnr.IiaApprovalCnr;
import eu.erasmuswithoutpaper.common.boundary.ManifestEntryStrategy;
import eu.erasmuswithoutpaper.common.control.EwpConstants;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;

public class IiaApprovalCnrManifestEntry implements ManifestEntryStrategy {
    @Inject
    GlobalProperties globalProperties;
    
    @Override
    public ManifestApiEntryBase getManifestEntry(String baseUri) {
        IiaApprovalCnr iiaApprovalCnr = new IiaApprovalCnr();
        iiaApprovalCnr.setVersion(EwpConstants.IIA_APPROVAL_CNR_VERSION);
        iiaApprovalCnr.setUrl(baseUri + "iias/approval/cnr");
        return iiaApprovalCnr;	
    }
}
