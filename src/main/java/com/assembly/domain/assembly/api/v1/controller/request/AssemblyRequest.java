package com.assembly.domain.assembly.api.v1.controller.request;

import com.assembly.domain.assembly.entities.enumerator.AssemblyStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssemblyRequest {

    @NotBlank
    private String subject;
    @NotNull
    private Long assemblyIdentifier;
    @Builder.Default
    private Long votingTime = 60000L;
    private AssemblyStatus assemblyStatus;
}
