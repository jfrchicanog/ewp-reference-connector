package eu.erasmuswithoutpaper.common.entity;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = Config.findByKey, query = "SELECT c FROM Config c WHERE c.clave = :key"),
})
public class Config {

    private static final String PREFIX = "eu.erasmuswithoutpaper.common.entity.Config.";
    public static final String findByKey = PREFIX + "findByValue";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clave;

    private String value;

    public Config() {

    }

    public Config(String clave, String value) {
        this.clave = clave;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
