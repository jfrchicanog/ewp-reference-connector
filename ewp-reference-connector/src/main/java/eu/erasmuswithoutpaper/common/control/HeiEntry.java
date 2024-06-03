package eu.erasmuswithoutpaper.common.control;

import java.util.Map;

public class HeiEntry {
    private String id;
    private String name;
    private String erasmusId;
    private Map<String, String> urls;

    public HeiEntry() {
    }
    
    public HeiEntry(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public HeiEntry(String id, String name, String erasmusId) {
        this.id = id;
        this.name = name;
        this.erasmusId = erasmusId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getErasmusId() {
        return erasmusId;
    }

    public void setErasmusId(String erasmusId) {
        this.erasmusId = erasmusId;
    }

    public Map<String, String> getUrls() {
        return this.urls;
    }

    public void setUrls(Map<String, String> urls) {
        this.urls = urls;
    }

    public void addUrls(Map<String, String> urls) {
        this.urls.putAll(urls);
    }
}
