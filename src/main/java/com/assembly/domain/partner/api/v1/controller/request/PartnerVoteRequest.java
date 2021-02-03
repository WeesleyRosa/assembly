package com.assembly.domain.partner.api.v1.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartnerVoteRequest {

    @NotBlank
    private String documentNumber;
    @NotNull
    private Long assemblyIdentifier;
    @NotNull
    private Boolean vote;
}
