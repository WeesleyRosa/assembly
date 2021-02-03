package com.assembly.domain.assembly.business;

import com.assembly.domain.assembly.entities.enumerator.AssemblyStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssemblyBO {

    private String subject;
    private Long assemblyIdentifier;
    private Long votingTime;
    private AssemblyStatus assemblyStatus;
}
