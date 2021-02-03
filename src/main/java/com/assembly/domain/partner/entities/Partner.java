package com.assembly.domain.partner.entities;

import com.assembly.infra.common.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Document
public class Partner extends BaseEntity {

    @Indexed(unique = true)
    private String documentNumber;
    private String fullName;
    private String email;
    private String phoneNumber;
    private LocalDate birthday;
    /**
     * Map of voted agendas. Key = assemblyIdentifier and value = value of the vote
     */
    private Map<Long, Boolean> votedAssemblies = new HashMap<>();

}
