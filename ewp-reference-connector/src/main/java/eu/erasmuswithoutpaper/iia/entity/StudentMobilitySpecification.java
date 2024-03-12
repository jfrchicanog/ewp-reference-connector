package eu.erasmuswithoutpaper.iia.entity;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Lob;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class StudentMobilitySpecification extends MobilitySpecification {

    private BigDecimal totalMonthsPerYear;
    private Boolean blended;
    @Lob()
    private Byte[] eqfLevel;
}
