package eu.erasmuswithoutpaper.omobility.las.boundary;

import java.math.BigInteger;

import javax.inject.Inject;

import eu.erasmuswithoutpaper.api.architecture.Empty;
import eu.erasmuswithoutpaper.api.architecture.ManifestApiEntryBase;
import eu.erasmuswithoutpaper.api.client.auth.methods.cliauth.httpsig.CliauthHttpsig;
import eu.erasmuswithoutpaper.api.client.auth.methods.cliauth.tlscert.CliauthTlscert;
import eu.erasmuswithoutpaper.api.client.auth.methods.srvauth.httpsig.SrvauthHttpsig;
import eu.erasmuswithoutpaper.api.client.auth.methods.srvauth.tlscert.SrvauthTlscert;
import eu.erasmuswithoutpaper.api.omobilities.las.OmobilityLas;
import eu.erasmuswithoutpaper.api.specs.sec.intro.HttpSecurityOptions;
import eu.erasmuswithoutpaper.common.boundary.ManifestEntryStrategy;
import eu.erasmuswithoutpaper.common.control.EwpConstants;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;

public class OutgoingMobilityLearningAgreementsManifestEntry implements ManifestEntryStrategy {
    @Inject
    GlobalProperties globalProperties;
    
    @Override
    public ManifestApiEntryBase getManifestEntry(String baseUri) {
    	OmobilityLas mobilitieslas = new OmobilityLas();
        mobilitieslas.setVersion(EwpConstants.OUTGOING_MOBILITIES_LAS_VERSION);
        mobilitieslas.setIndexUrl(baseUri + "omobilities/las/index");
        mobilitieslas.setGetUrl(baseUri + "omobilities/las/get");
        mobilitieslas.setUpdateUrl(baseUri + "omobilities/las/update");
        
        mobilitieslas.setMaxOmobilityIds(BigInteger.valueOf(globalProperties.getMaxOmobilitylasIds()));

        mobilitieslas.setSendsNotifications(new Empty());
        
        OmobilityLas.SupportedUpdateTypes supportedUpdateTypes = new OmobilityLas.SupportedUpdateTypes();
        supportedUpdateTypes.setApproveComponentsStudiedDraftV1(new Empty());
        supportedUpdateTypes.setUpdateComponentsStudiedV1(new Empty());
        mobilitieslas.setSupportedUpdateTypes(supportedUpdateTypes);
        
        HttpSecurityOptions httpSecurityOptions = new HttpSecurityOptions();
        
        HttpSecurityOptions.ClientAuthMethods clientAuthMethods = new HttpSecurityOptions.ClientAuthMethods();
        
        CliauthTlscert cliauthtlscert = new CliauthTlscert();
        cliauthtlscert.setAllowsSelfSigned(true);
        clientAuthMethods.getAny().add(cliauthtlscert);
        
        clientAuthMethods.getAny().add(new CliauthHttpsig());
        
        httpSecurityOptions.setClientAuthMethods(clientAuthMethods);
        
        HttpSecurityOptions.ServerAuthMethods serverAuthMethods = new HttpSecurityOptions.ServerAuthMethods();
        
        serverAuthMethods.getAny().add(new SrvauthTlscert());
        serverAuthMethods.getAny().add(new SrvauthHttpsig());
        
        httpSecurityOptions.setServerAuthMethods(serverAuthMethods);
        mobilitieslas.setHttpSecurity(httpSecurityOptions);
        return mobilitieslas;
    }
}
