package eu.erasmuswithoutpaper.courses.dto;

import java.math.BigDecimal;
import java.util.List;

public class AlgoriaLOPKApiResponse {
    private String pk;
    private String type;
    private String mode;
    private AlgoriaLOPKElement element;

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public AlgoriaLOPKElement getElement() {
        return element;
    }

    public void setElement(AlgoriaLOPKElement element) {
        this.element = element;
    }

    public static class AlgoriaLOPKElement {
        private long id;
        private String los_id;
        private String los_code;
        private String type;
        private List<AlgoriaLOTitle> title;
        private BigDecimal ects_credits;
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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<AlgoriaLOTitle> getTitle() {
            return title;
        }

        public void setTitle(List<AlgoriaLOTitle> title) {
            this.title = title;
        }

        public BigDecimal getEcts_credits() {
            return ects_credits;
        }

        public void setEcts_credits(BigDecimal ects_credits) {
            this.ects_credits = ects_credits;
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