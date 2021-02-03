package com.assembly.domain.assembly.repository;

import com.assembly.domain.assembly.entities.Assembly;
import com.assembly.domain.assembly.entities.enumerator.AssemblyStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AssemblyRepository extends MongoRepository<Assembly, String> {

    Optional<Assembly> findByAssemblyIdentifier(Long assemblyIdentifier);

    Optional<Assembly> findByAssemblyIdentifierAndStatusIs(Long assemblyIdentifier, AssemblyStatus status);
}
