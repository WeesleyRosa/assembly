package com.assembly.domain.assembly.entities;

import com.assembly.domain.assembly.entities.enumerator.AssemblyStatus;
import com.assembly.infra.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document
public class Assembly extends BaseEntity {

    @Indexed(unique = true)
    private Long assemblyIdentifier;
    private String subject;
    private LocalDateTime voteStartedAt;
    private LocalDateTime voteEndedAt;
    private AssemblyStatus status;
    private Long votingTime;
    /**
     * Map of associates who voted this assembly.. Key = documentNumber and value = value of the vote
     */
    private Map<String, Boolean> votes = new HashMap<>();
}
