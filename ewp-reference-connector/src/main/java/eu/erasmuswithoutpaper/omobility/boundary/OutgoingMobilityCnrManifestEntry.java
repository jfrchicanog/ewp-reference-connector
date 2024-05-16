package eu.erasmuswithoutpaper.omobility.boundary;

import java.math.BigInteger;

import javax.inject.Inject;

import eu.erasmuswithoutpaper.PublicAPI;
import eu.erasmuswithoutpaper.api.architecture.ManifestApiEntryBase;
import eu.erasmuswithoutpaper.api.omobilities.cnr.OmobilityCnr;
import eu.erasmuswithoutpaper.common.boundary.ManifestEntryStrategy;
import eu.erasmuswithoutpaper.common.control.EwpConstants;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;

public class OutgoingMobilityCnrManifestEntry implements ManifestEntryStrategy {
    @Inject
    GlobalProperties globalProperties;
    
    @Override
    public ManifestApiEntryBase getManifestEntry(String baseUri) {
        OmobilityCnr omobilityCnr = new OmobilityCnr();
        /*omobilityCnr.setVersion(EwpConstants.OUTGOING_MOBILITY_CNR_VERSION);
        omobilityCnr.setMaxOmobilityIds(BigInteger.valueOf(globalProperties.getMaxMobilityIds()));
        omobilityCnr.setUrl(baseUri + "omobilities/cnr");*/
        return omobilityCnr;
    }
}
