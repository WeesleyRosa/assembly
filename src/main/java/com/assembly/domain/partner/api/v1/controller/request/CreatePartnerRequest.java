package com.assembly.domain.partner.api.v1.controller.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePartnerRequest {

    @NotBlank
    private String documentNumber;
    @NotBlank
    @Pattern(regexp = "^([a-zA-Z0-9_\\-.]+)@([a-zA-Z0-9_\\-.]+)\\.([a-zA-Z]{2,5})$")
    private String email;
    @NotBlank
    @Pattern(regexp = "^[0-9]{1,20}$")
    private String phoneNumber;
    @NotBlank
    private String fullName;
    @NotNull
    private LocalDate birthday;
}
