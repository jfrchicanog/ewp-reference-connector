package eu.erasmuswithoutpaper.omobility.las.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.LearningAgreement;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AlgoriaOmobilityLasGetDto {
    private LearningAgreement la;

    public LearningAgreement getLa() {
        return la;
    }

    public void setLa(LearningAgreement la) {
        this.la = la;
    }
}
