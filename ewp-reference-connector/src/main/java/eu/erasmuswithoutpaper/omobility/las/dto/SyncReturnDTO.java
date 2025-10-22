package eu.erasmuswithoutpaper.omobility.las.dto;

import java.util.List;

public class SyncReturnDTO {
    private List<SyncReturnItemDTO> omobilityIds;

    public List<SyncReturnItemDTO> getOmobilityIds() {
        return omobilityIds;
    }

    public void setOmobilityIds(List<SyncReturnItemDTO> omobilityIds) {
        this.omobilityIds = omobilityIds;
    }

    public static class SyncReturnItemDTO {
        private String omobilityId;
        private String ourewpid;

        public String getOmobilityId() {
            return omobilityId;
        }

        public void setOmobilityId(String omobilityId) {
            this.omobilityId = omobilityId;
        }

        public String getOurewpid() {
            return ourewpid;
        }

        public void setOurewpid(String ourewpid) {
            this.ourewpid = ourewpid;
        }
    }
}
