package eu.erasmuswithoutpaper.iia.entity;

import eu.erasmuswithoutpaper.organization.entity.FlexibleAddress;
import eu.erasmuswithoutpaper.organization.entity.PhoneNumber;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
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
public class IiaContact_NEW implements Serializable {

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
    @JoinTable(name="IIACONTACT_PHONENUMBER",
            joinColumns = @JoinColumn(name="IIACONTACT_ID"),
            inverseJoinColumns = @JoinColumn(name="PHONENUMBER_ID"))
    private List<PhoneNumber> phoneNumber;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="IIACONTACT_FAXNUMBER",
            joinColumns = @JoinColumn(name="IIACONTACT_ID"),
            inverseJoinColumns = @JoinColumn(name="FAXNUMBER_ID"))
    private List<PhoneNumber> faxNumber;
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    private List<String> email;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "STREETADDRESS_ID", referencedColumnName = "ID")
    private FlexibleAddress streetAddress;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "MAILINGADDRESS_ID", referencedColumnName = "ID")
    private FlexibleAddress mailingAddress;
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    private List<String> roleDescription;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "IIAPARTNER_SININGCONTACT", referencedColumnName = "ID")
    private IiaPartner_NEW iiaPartner_contact;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IiaContact_NEW that = (IiaContact_NEW) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
