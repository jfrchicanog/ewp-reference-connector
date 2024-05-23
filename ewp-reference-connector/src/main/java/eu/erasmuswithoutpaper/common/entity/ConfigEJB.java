package eu.erasmuswithoutpaper.common.entity;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Singleton
public class ConfigEJB {


    @PersistenceContext(unitName = "connector")
    EntityManager em;

    public String getValue(String key) {
        List<Config> list = em.createNamedQuery(Config.findByKey).setParameter("key", key).getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0).getValue();
    }

    public String getValue(String key, String defaultValue) {
        String value = getValue(key);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }

    public void saveValue(String key, String value) {
        List<Config> list = em.createNamedQuery(Config.findByKey).setParameter("key", key).getResultList();
        Config config;
        if (list == null || list.isEmpty()) {
            config = new Config();
            config.setClave(key);
            config.setValue(value);
            em.persist(config);
        } else {
            config = list.get(0);
            config.setValue(value);
            em.merge(config);
        }
    }
}
