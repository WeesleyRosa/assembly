package com.assembly.domain.partner.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartnerVoteBO {

    private String documentNumber;
    private Long assemblyIdentifier;
    private Boolean vote;
    private String email;
    private String phoneNumber;
}
