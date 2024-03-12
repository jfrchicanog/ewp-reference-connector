package eu.erasmuswithoutpaper.iia.entity;

import eu.erasmuswithoutpaper.organization.entity.FlexibleAddress;
import eu.erasmuswithoutpaper.organization.entity.PhoneNumber;
import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
public class IiaContact {

    @Id
    @GeneratedValue(generator = "system-uuid")
    String id;

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    private List<String> contactName;
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    private List<String> personGivenName;
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    private List<String> personFamilyName;
    private BigInteger personGender;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PhoneNumber> phoneNumber;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PhoneNumber> faxNumber;
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    private List<String> email;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private FlexibleAddress streetAddress;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private FlexibleAddress mailingAddress;
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    private List<String> roleDescription;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IiaContact that = (IiaContact) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
