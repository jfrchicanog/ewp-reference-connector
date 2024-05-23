package eu.erasmuswithoutpaper.common.entity;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = Config.findByKey, query = "SELECT c FROM Config c WHERE c.key = :key"),
})
public class Config {

    private static final String PREFIX = "eu.erasmuswithoutpaper.common.entity.Config.";
    public static final String findByKey = PREFIX + "findByValue";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String key;

    private String value;

    public Config() {

    }

    public Config(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
