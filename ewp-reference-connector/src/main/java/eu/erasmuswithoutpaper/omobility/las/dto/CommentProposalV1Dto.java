package eu.erasmuswithoutpaper.omobility.las.dto;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "comment-proposal-v1")
@XmlType(propOrder = { "omobilityId", "changesProposalId", "comment", "signature" })
public class CommentProposalV1Dto implements Serializable {

    @XmlElement(name = "omobility-id", required = true)
    private String omobilityId;

    @XmlElement(name = "changes-proposal-id", required = true)
    private String changesProposalId;

    @XmlElement(required = true)
    private String comment;

    @XmlElement(required = true)
    private SignatureDto signature;

    public String getOmobilityId() {
        return omobilityId;
    }

    public void setOmobilityId(String omobilityId) {
        this.omobilityId = omobilityId;
    }

    public String getChangesProposalId() {
        return changesProposalId;
    }

    public void setChangesProposalId(String changesProposalId) {
        this.changesProposalId = changesProposalId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public SignatureDto getSignature() {
        return signature;
    }

    public void setSignature(SignatureDto signature) {
        this.signature = signature;
    }
}
