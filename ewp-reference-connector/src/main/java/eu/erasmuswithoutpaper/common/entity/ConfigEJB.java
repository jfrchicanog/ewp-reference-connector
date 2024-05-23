package eu.erasmuswithoutpaper.common.entity;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Singleton
public class ConfigEJB {


    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public String getValue(String key) {
        Config config = (Config) em.createNamedQuery(Config.findByKey).setParameter("key", key).getSingleResult();
        if (config == null) {
            return null;
        }
        return config.getValue();
    }

    public String getValue(String key, String defaultValue) {
        String value = getValue(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public void saveValue(String key, String value) {
        Config config = (Config) em.createNamedQuery(Config.findByKey).setParameter("key", key).getSingleResult();
        if (config == null) {
            config = new Config();
            config.setKey(key);
            config.setValue(value);
            em.persist(config);
        } else {
            config.setValue(value);
            em.merge(config);
        }
    }
}
