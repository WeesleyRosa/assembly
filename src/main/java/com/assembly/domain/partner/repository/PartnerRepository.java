package com.assembly.domain.partner.repository;

import com.assembly.domain.partner.entities.Partner;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PartnerRepository extends MongoRepository<Partner, String> {

    Optional<Partner> findByDocumentNumber(String documentNumber);
}
