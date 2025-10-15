package eu.erasmuswithoutpaper.courses.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class AlgoriaLOApiResponse {
    private List<AlgoriaLOElement> elements;

    public List<AlgoriaLOElement> getElements() {
        return elements;
    }

    public void setElements(List<AlgoriaLOElement> elements) {
        this.elements = elements;
    }

    public static class AlgoriaLOElement {
        private long id;
        @JsonProperty("los_id")
        private String losId;
        @JsonProperty("los_code")
        private String losCode;
        private List<AlgoriaLOTitle> title;
        private String type;
        @JsonProperty("organizational_unit_id")
        private String organizationalUnitId;
        @JsonProperty("children_los")
        private List<String> childrenLos;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getLosId() {
            return losId;
        }

        public void setLosId(String losId) {
            this.losId = losId;
        }

        public String getLosCode() {
            return losCode;
        }

        public void setLosCode(String losCode) {
            this.losCode = losCode;
        }

        public List<AlgoriaLOTitle> getTitle() {
            return title;
        }

        public void setTitle(List<AlgoriaLOTitle> title) {
            this.title = title;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOrganizationalUnitId() {
            return organizationalUnitId;
        }

        public void setOrganizationalUnitId(String organizationalUnitId) {
            this.organizationalUnitId = organizationalUnitId;
        }

        public List<String> getChildrenLos() {
            return childrenLos;
        }

        public void setChildrenLos(List<String> childrenLos) {
            this.childrenLos = childrenLos;
        }
    }

    public static class AlgoriaLOTitle {
        private String lang;
        private String value;

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}