package eu.erasmuswithoutpaper.imobility.control;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.datatype.DatatypeConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.erasmuswithoutpaper.api.imobilities.endpoints.NominationStatus;
import eu.erasmuswithoutpaper.api.imobilities.endpoints.StudentMobilityForStudies;
import eu.erasmuswithoutpaper.api.imobilities.tors.endpoints.ImobilityTorsGetResponse;
import eu.erasmuswithoutpaper.common.control.ConverterHelper;
import eu.erasmuswithoutpaper.imobility.entity.IMobility;
import eu.erasmuswithoutpaper.imobility.entity.IMobilityStatus;
import eu.erasmuswithoutpaper.omobility.entity.Mobility;

public class IncomingMobilityConverter {
    private static final Logger logger = LoggerFactory.getLogger(IncomingMobilityConverter.class);
    
    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public StudentMobilityForStudies convertToStudentMobilityForStudies(IMobility imobility) {
        StudentMobilityForStudies studentMobilityForStudies = new StudentMobilityForStudies();
        
        try {
            studentMobilityForStudies.setActualArrivalDate(ConverterHelper.convertToXmlGregorianCalendar(imobility.getActualArrivalDate()));
        } catch (DatatypeConfigurationException ex) {
            logger.error("Can't convert date", ex);
        }
        try {
            studentMobilityForStudies.setActualDepartureDate(ConverterHelper.convertToXmlGregorianCalendar(imobility.getActualDepartureDate()));
        } catch (DatatypeConfigurationException ex) {
            logger.error("Can't convert date", ex);
        }
        studentMobilityForStudies.setOmobilityId(imobility.getId());
        
        studentMobilityForStudies.setStatus(convertToIMobilityStatus(imobility.getIstatus()));
        studentMobilityForStudies.setComment(imobility.getComment());
        
        return studentMobilityForStudies;
    }

    private NominationStatus convertToIMobilityStatus(IMobilityStatus status) {
    	NominationStatus nominationStatus = NominationStatus.valueOf(status.value());
		return nominationStatus;
	}

	public ImobilityTorsGetResponse.Tor convertToTor(Mobility mobility) {
        ImobilityTorsGetResponse.Tor tor = new ImobilityTorsGetResponse.Tor();
        tor.setOmobilityId(mobility.getId());
        return tor;
    }
    
}
