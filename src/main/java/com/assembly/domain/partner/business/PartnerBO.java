package com.assembly.domain.partner.business;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PartnerBO {

    private String documentNumber;
    private String email;
    private String phoneNumber;
    private String fullName;
    private LocalDate birthday;
}
