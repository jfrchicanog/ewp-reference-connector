package eu.erasmuswithoutpaper.internal;

import eu.erasmuswithoutpaper.common.control.GlobalProperties;
import eu.erasmuswithoutpaper.iia.preload.IiaLoader;
import eu.erasmuswithoutpaper.iia.preload.MobilityTypeLoader;
import eu.erasmuswithoutpaper.omobility.preload.MobilityLoader;
import eu.erasmuswithoutpaper.organization.preload.ContactLoader;
import eu.erasmuswithoutpaper.organization.preload.InstitutionLoader;
import eu.erasmuswithoutpaper.organization.preload.MobilityParticipantLoader;
import eu.erasmuswithoutpaper.organization.preload.PersonLoader;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
@Startup
public class StartupLoader {
    private static final Logger logger = LoggerFactory.getLogger(StartupLoader.class);

    @Inject
    GlobalProperties properties;
    
    @Inject
    InstitutionLoader institutionLoader;
    
    @Inject
    PersonLoader personLoader;
    
    @Inject
    ContactLoader contactLoader;

    @Inject
    MobilityParticipantLoader mobilityParticipantLoader;

//    @Inject
//    AcademicTermLoader academicTermLoader;
//    
//    @Inject
//    AcademicYearLoader academicYearLoader;
    
    @Inject
    MobilityTypeLoader mobilityTypeLoader;
   
    @Inject
    MobilityLoader mobilityLoader;
    
//    @Inject
//    LosLoader learningOppSpecLoader;

//    @Inject
//    LoiLoader learningOppInstLoader;

    @Inject
    IiaLoader iiaLoader;
    
    @PostConstruct
    public void loadDemoData() {
        try {
            if (institutionLoader.dataAlreadyExist()) {
                logger.info("Database already exist, no default data will be loaded. Remove DB to restore database content.");
                return;
            } else {
                logger.info("Database is created, default data will be loaded.");
            } 

            switch (properties.getUniversity()) {
                case IKEA_U:
                    institutionLoader.createDemoDataIkea();
                    personLoader.createDemoDataIkea();
                    contactLoader.createDemoDataIkea();
                    mobilityParticipantLoader.createDemoDataIkea();
                   // academicYearLoader.createDemoDataIkea();
                   // academicTermLoader.createDemoDataIkea();
                    mobilityTypeLoader.createDemoDataIkea();
                   // learningOppSpecLoader.createDemoDataIkea();
                   // learningOppInstLoader.createDemoDataIkea();
                    iiaLoader.createDemoDataIkea();
                    mobilityLoader.createDemoDataIkea();
                    break;
                case POMODORO_U:
                    institutionLoader.createDemoDataPomodoro();
                    personLoader.createDemoDataPomodoro();
                    contactLoader.createDemoDataPomodoro();
                    mobilityParticipantLoader.createDemoDataPomodoro();
                  //  academicYearLoader.createDemoDataPomodoro();
                  //  academicTermLoader.createDemoDataPomodoro();
                    mobilityTypeLoader.createDemoDataPomodoro();
                   // learningOppSpecLoader.createDemoDataPomodoro();
                   // learningOppInstLoader.createDemoDataPomodoro();
                    iiaLoader.createDemoDataPomodoro();
                    mobilityLoader.createDemoDataPomodoro();
                    break;
            }
        } catch (IOException ex) {
            logger.error("Can't load initial data", ex);
            throw new RuntimeException(ex);
        }
    }

    /**
     * Override injected Entity Managers in loader classes.
     * For test purpose
     * @param em 
     */
    public void setEntityManger(EntityManager em) {
        institutionLoader.setEntityManger(em);
        personLoader.setEntityManger(em);
        contactLoader.setEntityManger(em);
        mobilityParticipantLoader.setEntityManger(em);
        //academicTermLoader.setEntityManger(em);
       // academicYearLoader.setEntityManger(em);
        mobilityTypeLoader.setEntityManger(em);
        mobilityLoader.setEntityManger(em);
       // learningOppSpecLoader.setEntityManger(em);
        //learningOppInstLoader.setEntityManger(em);
        iiaLoader.setEntityManger(em);
    }
}
