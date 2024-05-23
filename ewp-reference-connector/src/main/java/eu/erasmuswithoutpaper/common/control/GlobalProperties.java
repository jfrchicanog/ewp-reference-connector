package eu.erasmuswithoutpaper.common.control;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;

import eu.erasmuswithoutpaper.common.entity.ConfigEJB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class GlobalProperties {

    @EJB
    ConfigEJB configEJB;

    private static final Logger logger = LoggerFactory.getLogger(GlobalProperties.class);
    public static enum University {
        IKEA_U,
        POMODORO_U,
        UMA_U
    }
    
    Properties properties;
    University university;
    String defaultUniversityName;
    String defaultAlgoriaToken = "675701176db0293a8cac23814481f8e50b320fbd";
    
    String defaultAlgoriaApprovalURL = "https://relacionesi.uma.es/algoria/ewp_approved_agreement_notifications/";
    String defaultAlgoriaCreatedURL = "https://relacionesi.uma.es/algoria/ewp_created_agreement_notifications/";
    String defaultAlgoriaModifyURL = "https://relacionesi.uma.es/algoria/ewp_modified_agreement_notifications/";
    String defaultAlgoriaDeleteURL = "https://relacionesi.uma.es/algoria/ewp_deleted_agreement_notifications/";
    String defaultAlgoriaRevertURL = "https://relacionesi.uma.es/algoria/ewp_revert_agreement_notifications/";
    String defaultAlgoriaTerminateURL = "https://relacionesi.uma.es/algoria/ewp_terminated_agreement_notifications/";
    String defaultAlgoriaAuthorizationToken = "Token 6076263cedb35ca6c8abcfc6f872fb471130fdd7";
    //String defaultAlgoriaAuthorizationToken = "Token ab1a997c0f38eddbfb64c24b9e0162d366832f29";
    //String defaultAlgoriaAuthorizationToken = "Token aa38ee014e1ce693c30b399aab9668ebc13f21fd";

    public GlobalProperties() {
        loadProperties();
    }
    
    
    
    @PostConstruct
    private void loadProperties() {
        if (configEJB.getValue("algoria.approval.url") == null) {
            logger.info("Setting default values for Algoria URLs");
            configEJB.saveValue("algoria.approval.url", defaultAlgoriaApprovalURL);
            configEJB.saveValue("algoria.created.url", defaultAlgoriaCreatedURL);
            configEJB.saveValue("algoria.modify.url", defaultAlgoriaModifyURL);
            configEJB.saveValue("algoria.delete.url", defaultAlgoriaDeleteURL);
            configEJB.saveValue("algoria.revert.url", defaultAlgoriaRevertURL);
            configEJB.saveValue("algoria.terminate.url", defaultAlgoriaTerminateURL);
            configEJB.saveValue("algoria.tokens.authorization", defaultAlgoriaAuthorizationToken);
        }

        properties = new Properties();
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("ewp.properties")) {
            properties.load(in);
            logger.info("Loaded properties from resource.");
        } catch (IOException ex) {
            logger.error("Can't get default properties", ex);
        }
        
        String overrideProperties = System.getProperty("ewp.override.properties");
        if (overrideProperties != null) {
            try {
                properties.load(new FileInputStream(overrideProperties));
                logger.info("Override properties from file {}.", overrideProperties);
            } catch (IOException ex) {
                logger.error("Can't get override properties", ex);
            }
        }
        
        logger.info("ewp.instance= "+properties.getProperty("ewp.instance"));
        switch(properties.getProperty("ewp.instance")) {
            case "POMODORO":
                university = University.POMODORO_U;
                defaultUniversityName = "Pomodoro University";
                break;
            case "UMA":
            	university = University.UMA_U;
                defaultUniversityName = "University of Malaga";
                break;
            default:
                university = University.IKEA_U;
                defaultUniversityName = "IKEA University";
                break;
        }
        
        logger.info("Using demo data from "+defaultUniversityName);
        
    }
    
    public University getUniversity() {
        return university;
    }
    
    public String getUniversityName() {
        return getProperty("ewp.name", defaultUniversityName);
    }

    public Optional<String> getHostname() {
        return Optional.ofNullable(getProperty("ewp.host.name"));
    }
    
    public Optional<String> getBaseUri() {
        return Optional.ofNullable(getProperty("ewp.base.uri"));
    }
    
    public Optional<String> getTruststoreLocation() {
        return Optional.ofNullable(getProperty("ewp.truststore.location"));
    }
    public Optional<String> getTruststorePassword() {
        return Optional.ofNullable(getProperty("ewp.truststore.password"));
    }
    
    public Optional<String> getKeystoreLocation() {
        return Optional.ofNullable(getProperty("ewp.keystore.location"));
    }
    public Optional<String> getKeystorePassword() {
        return Optional.ofNullable(getProperty("ewp.keystore.password"));
    }
    public Optional<String> getKeystoreCertificateAlias() {
        return Optional.ofNullable(getProperty("ewp.keystore.certificate.alias"));
    }
    
    public boolean isAllowMissingClientCertificate() {
        return "true".equalsIgnoreCase(getProperty("ewp.allow.missing.client.certificate"));
    }
    
    public String getRegistryUrl() {
        return getProperty("ewp.registry.url", "registry.erasmuswithoutpaper.eu");
    }
    
    public boolean isRegistryAutoRefreshing() {
        return "true".equalsIgnoreCase(getProperty("ewp.registry.auto.refreshing"));
    }
    
    public int getRegistryTimeBetweenRetries() {
        return getIntProperty("ewp.registry.time.between.retries", 180000);
    }

    public int getMaxInstitutionsIds() {
        return getIntProperty("ewp.api.institutions.max.ids", 1);
    }

    public int getMaxOunitsIds() {
        return getIntProperty("ewp.api.ounits.max.ids", 1);
    }
    
    public int getMaxOunitsCodes() {
        return getIntProperty("ewp.api.ounits.max.codes", 1);
    }
    
    public int getMaxLosIds() {
        return getIntProperty("ewp.api.los.max.ids", 1);
    }
    
    public int getMaxMobilityIds() {
        return getIntProperty("ewp.api.mobilities.max.ids", 1);
    }
    
    public int getMaxIiaIds() {
        return getIntProperty("ewp.api.iias.max.ids", 1);
    }
    
    public int getMaxIiaCodes() {
        return getIntProperty("ewp.api.iias.max.codes", 1);
    }
    
    public int getMaxFactsheetIds() {
        return getIntProperty("ewp.api.factsheet.max.ids", 1);
    }

    public int getMaxOmobilitylasIds() {
        return getIntProperty("ewp.api.omobility.las.max.ids", 1);
    }

    public String getAlgoriaToken() {
        return configEJB.getValue("ewp.algoria.token", defaultAlgoriaToken);
    }
    
    public String getAlgoriaApprovalURL() {
    	return configEJB.getValue("algoria.approval.url", defaultAlgoriaApprovalURL);
    }

    public String getAlgoriaCreatedURL() {
        return configEJB.getValue("algoria.created.url", defaultAlgoriaCreatedURL);
    }
    
    public String getAlgoriaModifyURL() {
    	return configEJB.getValue("algoria.modify.url", defaultAlgoriaModifyURL);
    }

    public String getAlgoriaDeleteURL() {
        return configEJB.getValue("algoria.delete.url", defaultAlgoriaDeleteURL);
    }

    public String getAlgoriaRevertURL() {
        return configEJB.getValue("algoria.revert.url", defaultAlgoriaRevertURL);
    }

    public String getAlgoriaTerminateURL() {
        return configEJB.getValue("algoria.terminate.url", defaultAlgoriaTerminateURL);
    }
    
    public String getAlgoriaAuthotizationToken() {
    	return configEJB.getValue("algoria.tokens.authorization", defaultAlgoriaAuthorizationToken);
    }
            
    public int getAlgoriaTaskDelay() {
    	return getIntProperty("algoria.task.delay", 2);
    }
    
    private int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key);
        if (value != null) {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                logger.error("Not a number " + key, e);
            }
        }
        return defaultValue;
    }

    private String getProperty(String key) {
        return getProperty(key, null);
    }
    
    private String getProperty(String key, String defaultValue) {
        String property = properties.getProperty(key, defaultValue);
        return property;
    }
}
