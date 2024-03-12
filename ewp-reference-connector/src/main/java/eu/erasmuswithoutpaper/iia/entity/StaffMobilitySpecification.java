package eu.erasmuswithoutpaper.iia.entity;

import lombok.*;

import javax.persistence.Entity;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class StaffMobilitySpecification extends MobilitySpecification {

    private BigDecimal totalDaysPerYear;
}
