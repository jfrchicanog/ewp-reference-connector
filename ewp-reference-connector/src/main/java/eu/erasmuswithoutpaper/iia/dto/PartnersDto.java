package eu.erasmuswithoutpaper.iia.dto;

import java.util.*;

public class PartnersDto {
    private List<Hei> heis = new ArrayList<>();

    public List<Hei> getHeis() { return heis; }
    public void setHeis(List<Hei> heis) { this.heis = heis; }

    public static class Hei {
        private String heiId;

        private List<Iia> iias = new ArrayList<>();

        public Hei() {}
        public Hei(String heiId) { this.heiId = heiId; }

        public String getHeiId() { return heiId; }
        public List<Iia> getIias() { return iias; }

        public static class Iia {
            private String iiaId;

            private List<String> ourIds = new ArrayList<>();

            public Iia() {}
            public Iia(String iiaId) { this.iiaId = iiaId; }

            public String getIiaId() { return iiaId; }
            public List<String> getOurIds() { return ourIds; }
        }
    }
}
