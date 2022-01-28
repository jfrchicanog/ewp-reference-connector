package eu.erasmuswithoutpaper.iia.approval.boundary;

import java.math.BigInteger;
import java.util.logging.Logger;

import javax.inject.Inject;

import eu.erasmuswithoutpaper.PublicAPI;
import eu.erasmuswithoutpaper.api.architecture.ManifestApiEntryBase;
import eu.erasmuswithoutpaper.api.client.auth.methods.cliauth.httpsig.CliauthHttpsig;
import eu.erasmuswithoutpaper.api.client.auth.methods.cliauth.tlscert.CliauthTlscert;
import eu.erasmuswithoutpaper.api.client.auth.methods.srvauth.httpsig.SrvauthHttpsig;
import eu.erasmuswithoutpaper.api.client.auth.methods.srvauth.tlscert.SrvauthTlscert;
import eu.erasmuswithoutpaper.api.iias.approval.IiasApproval;
import eu.erasmuswithoutpaper.api.specs.sec.intro.HttpSecurityOptions;
import eu.erasmuswithoutpaper.common.boundary.ManifestEntryStrategy;
import eu.erasmuswithoutpaper.common.control.EwpConstants;
import eu.erasmuswithoutpaper.common.control.GlobalProperties;

@PublicAPI
public class IiaApprovalManifestEntry implements ManifestEntryStrategy {
    
	private final Logger LOGGER = Logger.getLogger(this.getClass().getCanonicalName());
	
	@Inject
    GlobalProperties globalProperties;
    
    @Override
    public ManifestApiEntryBase getManifestEntry(String baseUri) {
        IiasApproval iiasApproval = new IiasApproval();
        iiasApproval.setVersion(EwpConstants.IIAS_APPROVAL_VERSION);
        iiasApproval.setMaxIiaIds(BigInteger.valueOf(globalProperties.getMaxIiaIds()));
        iiasApproval.setUrl(baseUri + "iiasApproval/get");
        
        HttpSecurityOptions httpSecurityOptions = new HttpSecurityOptions();
        HttpSecurityOptions.ClientAuthMethods clientAuthMethods = new HttpSecurityOptions.ClientAuthMethods();
        
        CliauthTlscert cliauthtlscert = new CliauthTlscert();
        cliauthtlscert.setAllowsSelfSigned(true);
        clientAuthMethods.getAny().add(cliauthtlscert);
        
//        clientAuthMethods.getAny().add(new Anonymous());
        
        clientAuthMethods.getAny().add(new CliauthHttpsig());
        
        httpSecurityOptions.setClientAuthMethods(clientAuthMethods);
        
        HttpSecurityOptions.ServerAuthMethods serverAuthMethods = new HttpSecurityOptions.ServerAuthMethods();
        
        serverAuthMethods.getAny().add(new SrvauthTlscert());
        serverAuthMethods.getAny().add(new SrvauthHttpsig());
        
        httpSecurityOptions.setServerAuthMethods(serverAuthMethods);
        iiasApproval.setHttpSecurity(httpSecurityOptions);

        LOGGER.info(iiasApproval.getVersion());

        return iiasApproval;
    }
}
