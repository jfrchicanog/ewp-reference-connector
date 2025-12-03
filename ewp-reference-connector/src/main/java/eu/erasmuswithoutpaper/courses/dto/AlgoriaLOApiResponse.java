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
        private String los_id;
        private String los_code;
        private List<AlgoriaLOTitle> title;
        private String type;
        private String organizational_unit_id;
        private List<String> children_los;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getLos_id() {
            return los_id;
        }

        public void setLos_id(String los_id) {
            this.los_id = los_id;
        }

        public String getLos_code() {
            return los_code;
        }

        public void setLos_code(String los_code) {
            this.los_code = los_code;
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

        public String getOrganizational_unit_id() {
            return organizational_unit_id;
        }

        public void setOrganizational_unit_id(String organizational_unit_id) {
            this.organizational_unit_id = organizational_unit_id;
        }

        public List<String> getChildren_los() {
            return children_los;
        }

        public void setChildren_los(List<String> children_los) {
            this.children_los = children_los;
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