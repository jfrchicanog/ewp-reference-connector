package eu.erasmuswithoutpaper.organization.boundary;

import eu.erasmuswithoutpaper.api.architecture.ManifestApiEntryBase;
import eu.erasmuswithoutpaper.api.institutions.Institutions;
import eu.erasmuswithoutpaper.common.control.EwpConstants;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import java.math.BigInteger;
import java.util.logging.Logger;

import javax.inject.Inject;
import eu.erasmuswithoutpaper.common.boundary.ManifestEntryStrategy;

public class InstitutionsManifestEntry implements ManifestEntryStrategy {
	
	private final Logger LOGGER = Logger.getLogger(this.getClass().getCanonicalName());
	
    @Inject
    GlobalProperties globalProperties;
    
    @Override
    public ManifestApiEntryBase getManifestEntry(String baseUri) {
        Institutions institutions = new Institutions();
        institutions.setVersion(EwpConstants.INSTITUTION_VERSION);
        institutions.setUrl(baseUri + "institutions");
        institutions.setMaxHeiIds(BigInteger.valueOf(globalProperties.getMaxInstitutionsIds()));
        
        LOGGER.info(institutions.getVersion());
        
        
        return institutions;
    }
}
