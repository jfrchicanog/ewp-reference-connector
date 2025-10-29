package eu.erasmuswithoutpaper.omobility.las.control;

import eu.erasmuswithoutpaper.api.omobilities.las.endpoints.*;
import eu.erasmuswithoutpaper.omobility.las.dto.*;

/**
 * Bi-directional mappers between JAXB-generated endpoint classes and DTOs (namespace-free).
 */
public final class OmobilityLasConverters {

    private OmobilityLasConverters() {}

    /* =======================
       OmobilityLasUpdateRequest
       ======================= */

    public static OmobilityLasUpdateRequestDto toDto(OmobilityLasUpdateRequest src) {
        if (src == null) return null;
        OmobilityLasUpdateRequestDto dto = new OmobilityLasUpdateRequestDto();
        dto.setSendingHeiId(src.getSendingHeiId());
        dto.setApproveProposalV1(toDto(src.getApproveProposalV1()));
        dto.setCommentProposalV1(toDto(src.getCommentProposalV1()));
        return dto;
    }

    public static OmobilityLasUpdateRequest fromDto(OmobilityLasUpdateRequestDto dto) {
        if (dto == null) return null;
        OmobilityLasUpdateRequest dst = new OmobilityLasUpdateRequest();
        dst.setSendingHeiId(dto.getSendingHeiId());
        dst.setApproveProposalV1(fromDto(dto.getApproveProposalV1()));
        dst.setCommentProposalV1(fromDto(dto.getCommentProposalV1()));
        return dst;
    }

    /* =======================
       ApproveProposalV1
       ======================= */

    public static ApproveProposalV1Dto toDto(ApproveProposalV1 src) {
        if (src == null) return null;
        ApproveProposalV1Dto dto = new ApproveProposalV1Dto();
        dto.setOmobilityId(src.getOmobilityId());
        dto.setChangesProposalId(src.getChangesProposalId());
        dto.setSignature(toDto(src.getSignature()));
        return dto;
    }

    public static ApproveProposalV1 fromDto(ApproveProposalV1Dto dto) {
        if (dto == null) return null;
        ApproveProposalV1 dst = new ApproveProposalV1();
        dst.setOmobilityId(dto.getOmobilityId());
        dst.setChangesProposalId(dto.getChangesProposalId());
        dst.setSignature(fromDto(dto.getSignature()));
        return dst;
    }

    /* =======================
       CommentProposalV1
       ======================= */

    public static CommentProposalV1Dto toDto(CommentProposalV1 src) {
        if (src == null) return null;
        CommentProposalV1Dto dto = new CommentProposalV1Dto();
        dto.setOmobilityId(src.getOmobilityId());
        dto.setChangesProposalId(src.getChangesProposalId());
        dto.setComment(src.getComment());
        dto.setSignature(toDto(src.getSignature()));
        return dto;
    }

    public static CommentProposalV1 fromDto(CommentProposalV1Dto dto) {
        if (dto == null) return null;
        CommentProposalV1 dst = new CommentProposalV1();
        dst.setOmobilityId(dto.getOmobilityId());
        dst.setChangesProposalId(dto.getChangesProposalId());
        dst.setComment(dto.getComment());
        dst.setSignature(fromDto(dto.getSignature()));
        return dst;
    }

    /* =======================
       Signature
       ======================= */

    public static SignatureDto toDto(Signature src) {
        if (src == null) return null;
        SignatureDto dto = new SignatureDto();
        dto.setSignerName(src.getSignerName());
        dto.setSignerPosition(src.getSignerPosition());
        dto.setSignerEmail(src.getSignerEmail());
        dto.setTimestamp(src.getTimestamp()); // XMLGregorianCalendar is identical in both
        dto.setSignerApp(src.getSignerApp());
        return dto;
    }

    public static Signature fromDto(SignatureDto dto) {
        if (dto == null) return null;
        Signature dst = new Signature();
        dst.setSignerName(dto.getSignerName());
        dst.setSignerPosition(dto.getSignerPosition());
        dst.setSignerEmail(dto.getSignerEmail());
        dst.setTimestamp(dto.getTimestamp()); // XMLGregorianCalendar is identical in both
        dst.setSignerApp(dto.getSignerApp());
        return dst;
    }
}
