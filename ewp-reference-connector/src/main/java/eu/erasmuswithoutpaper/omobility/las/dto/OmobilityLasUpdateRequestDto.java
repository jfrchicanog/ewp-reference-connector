package eu.erasmuswithoutpaper.omobility.las.dto;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "omobility-las-update-request")
@XmlType(propOrder = { "sendingHeiId", "approveProposalV1", "commentProposalV1" })
public class OmobilityLasUpdateRequestDto implements Serializable {

    @XmlElement(name = "sending-hei-id", required = true)
    private String sendingHeiId;

    @XmlElement(name = "approve-proposal-v1")
    private ApproveProposalV1Dto approveProposalV1;

    @XmlElement(name = "comment-proposal-v1")
    private CommentProposalV1Dto commentProposalV1;

    public String getSendingHeiId() {
        return sendingHeiId;
    }

    public void setSendingHeiId(String sendingHeiId) {
        this.sendingHeiId = sendingHeiId;
    }

    public ApproveProposalV1Dto getApproveProposalV1() {
        return approveProposalV1;
    }

    public void setApproveProposalV1(ApproveProposalV1Dto approveProposalV1) {
        this.approveProposalV1 = approveProposalV1;
    }

    public CommentProposalV1Dto getCommentProposalV1() {
        return commentProposalV1;
    }

    public void setCommentProposalV1(CommentProposalV1Dto commentProposalV1) {
        this.commentProposalV1 = commentProposalV1;
    }
}
