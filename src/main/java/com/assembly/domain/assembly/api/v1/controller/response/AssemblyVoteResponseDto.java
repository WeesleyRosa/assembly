package com.assembly.domain.assembly.api.v1.controller.response;

import com.assembly.domain.assembly.entities.enumerator.AssemblyStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssemblyVoteResponseDto {

    private LocalDateTime voteStartedAt;
    private LocalDateTime voteEndedAt;
    private AssemblyStatus status;
    private String subject;
    /**
     * Map of associates who voted this agenda.. Key = documentNumber and value = value of the vote
     */
    private Map<String, Boolean> votes = new HashMap<>();;
}
