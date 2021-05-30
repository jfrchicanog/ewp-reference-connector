package eu.erasmuswithoutpaper.iia.approval.boundary;

import java.math.BigInteger;
import java.util.logging.Logger;

import javax.inject.Inject;

import eu.erasmuswithoutpaper.api.architecture.ManifestApiEntryBase;
import eu.erasmuswithoutpaper.api.iias.approval.IiasApproval;
import eu.erasmuswithoutpaper.common.boundary.ManifestEntryStrategy;
import eu.erasmuswithoutpaper.common.control.EwpConstants;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;

public class IiaApprovalManifestEntry implements ManifestEntryStrategy {
    
	private final Logger LOGGER = Logger.getLogger(this.getClass().getCanonicalName());
	
	@Inject
    GlobalProperties globalProperties;
    
    @Override
    public ManifestApiEntryBase getManifestEntry(String baseUri) {
        IiasApproval iiasApproval = new IiasApproval();
        iiasApproval.setVersion(EwpConstants.IIAS_VERSION);
        iiasApproval.setMaxIiaIds(BigInteger.valueOf(globalProperties.getMaxIiaIds()));
        
        LOGGER.info(iiasApproval.getVersion());

        return iiasApproval;
    }
}
