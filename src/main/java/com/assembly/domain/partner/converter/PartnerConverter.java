package com.assembly.domain.partner.converter;

import com.assembly.domain.partner.api.v1.controller.request.CreatePartnerRequest;
import com.assembly.domain.partner.api.v1.controller.request.PartnerVoteRequest;
import com.assembly.domain.partner.business.PartnerBO;
import com.assembly.domain.partner.business.PartnerVoteBO;
import com.assembly.domain.partner.entities.Partner;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class PartnerConverter {

    public static PartnerVoteBO convertToPartnerBO(PartnerVoteRequest partnerVoteRequest){
        return PartnerVoteBO
                .builder()
                .assemblyIdentifier(partnerVoteRequest.getAssemblyIdentifier())
                .documentNumber(partnerVoteRequest.getDocumentNumber())
                .vote(partnerVoteRequest.getVote())
                .build();

    }

    public static PartnerBO convertToPartnerBO(CreatePartnerRequest createPartnerRequest) {
        return PartnerBO
                .builder()
                .birthday(createPartnerRequest.getBirthday())
                .fullName(createPartnerRequest.getFullName())
                .documentNumber(createPartnerRequest.getDocumentNumber())
                .email(createPartnerRequest.getEmail())
                .phoneNumber(createPartnerRequest.getPhoneNumber())
                .build();
    }

    public static Partner convertToPartner(PartnerBO partnerBO) {
        return Partner
                .builder()
                .birthday(partnerBO.getBirthday())
                .fullName(partnerBO.getFullName())
                .documentNumber(partnerBO.getDocumentNumber())
                .email(partnerBO.getEmail())
                .phoneNumber(partnerBO.getPhoneNumber())
                .build();
    }
}
