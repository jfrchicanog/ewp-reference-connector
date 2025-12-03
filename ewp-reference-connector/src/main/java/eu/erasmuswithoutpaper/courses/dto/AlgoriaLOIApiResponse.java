package eu.erasmuswithoutpaper.courses.dto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

public class AlgoriaLOIApiResponse {
    private List<AlgoriaLOElement> elements;

    public List<AlgoriaLOElement> getElements() {
        return elements;
    }

    public void setElements(List<AlgoriaLOElement> elements) {
        this.elements = elements;
    }

    public static class AlgoriaLOElement {
        private long id;
        private String loi_id;
        private String loi_code;
        private String parent_los_id;
        private String parent_los_code;
        private String type;
        private List<AlgoriaLOITitle> title;
        private BigDecimal ects_credits;
        private AcademicTermLOI academic_term;
        private List<AlgoriaLOITitle> language_of_instruction;
        private String web_link_to_course_catalogue;
        private String description;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getLoi_id() {
            return loi_id;
        }

        public void setLoi_id(String loi_id) {
            this.loi_id = loi_id;
        }

        public String getLoi_code() {
            return loi_code;
        }

        public void setLoi_code(String loi_code) {
            this.loi_code = loi_code;
        }

        public String getParent_los_id() {
            return parent_los_id;
        }

        public void setParent_los_id(String parent_los_id) {
            this.parent_los_id = parent_los_id;
        }

        public String getParent_los_code() {
            return parent_los_code;
        }

        public void setParent_los_code(String parent_los_code) {
            this.parent_los_code = parent_los_code;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<AlgoriaLOITitle> getTitle() {
            return title;
        }

        public void setTitle(List<AlgoriaLOITitle> title) {
            this.title = title;
        }

        public BigDecimal getEcts_credits() {
            return ects_credits;
        }

        public void setEcts_credits(BigDecimal ects_credits) {
            this.ects_credits = ects_credits;
        }

        public AcademicTermLOI getAcademic_term() {
            return academic_term;
        }

        public void setAcademic_term(AcademicTermLOI academic_term) {
            this.academic_term = academic_term;
        }

        public List<AlgoriaLOITitle> getLanguage_of_instruction() {
            return language_of_instruction;
        }

        public void setLanguage_of_instruction(List<AlgoriaLOITitle> language_of_instruction) {
            this.language_of_instruction = language_of_instruction;
        }

        public String getWeb_link_to_course_catalogue() {
            return web_link_to_course_catalogue;
        }

        public void setWeb_link_to_course_catalogue(String web_link_to_course_catalogue) {
            this.web_link_to_course_catalogue = web_link_to_course_catalogue;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class AlgoriaLOITitle {
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

    public static class AcademicTermLOI {
        private String academic_year_id;
        private List<AlgoriaLOITitle> display_name;
        private String start_date;
        private String end_date;
        private TermIdLOI term_id;

        public String getAcademic_year_id() {
            return academic_year_id;
        }

        public void setAcademic_year_id(String academic_year_id) {
            this.academic_year_id = academic_year_id;
        }

        public List<AlgoriaLOITitle> getDisplay_name() {
            return display_name;
        }

        public void setDisplay_name(List<AlgoriaLOITitle> display_name) {
            this.display_name = display_name;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public TermIdLOI getTerm_id() {
            return term_id;
        }

        public void setTerm_id(TermIdLOI term_id) {
            this.term_id = term_id;
        }

        public static class TermIdLOI {
            private BigInteger term_number;
            private BigInteger total_terms;

            public BigInteger getTerm_number() {
                return term_number;
            }

            public void setTerm_number(BigInteger term_number) {
                this.term_number = term_number;
            }

            public BigInteger getTotal_terms() {
                return total_terms;
            }

            public void setTotal_terms(BigInteger total_terms) {
                this.total_terms = total_terms;
            }
        }
    }
}