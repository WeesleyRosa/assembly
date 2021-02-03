package com.assembly.domain.partner.service.impl;

import com.assembly.domain.assembly.entities.Assembly;
import com.assembly.domain.assembly.entities.enumerator.AssemblyStatus;
import com.assembly.domain.assembly.repository.AssemblyRepository;
import com.assembly.domain.partner.api.v1.controller.request.PartnerVoteRequest;
import com.assembly.domain.partner.business.PartnerBO;
import com.assembly.domain.partner.business.PartnerVoteBO;
import com.assembly.domain.partner.client.CpfValidationClient;
import com.assembly.domain.partner.client.response.CpfValidationResponse;
import com.assembly.domain.partner.converter.PartnerConverter;
import com.assembly.domain.partner.entities.Partner;
import com.assembly.domain.partner.repository.PartnerRepository;
import com.assembly.domain.partner.service.PartnerService;
import com.assembly.infra.common.enums.ErrorCodeEnum;
import com.assembly.infra.common.exception.UserVoteException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PartnerServiceImpl implements PartnerService {

    private final PartnerRepository partnerRepository;
    private final AssemblyRepository assemblyRepository;
    private final CpfValidationClient cpfValidationClient;

    private CpfValidationResponse validateCpf(PartnerVoteBO partnerVoteBO) {
        log.info("AssociateService - CpfValidationResponse - Sending request to validate cpf.");
        return cpfValidationClient.validateCpf(partnerVoteBO.getDocumentNumber());
    }

    @Override
    public void doVote(PartnerVoteBO partnerVoteBO) {
        log.info("AssociateService - doVote - Getting objects from database.");
        if(validateCpf(partnerVoteBO).getStatus().equalsIgnoreCase("UNABLE_TO_VOTE")) {
            log.info("AssociateService - doVote - Cpf unable to vote.");
            throw new UserVoteException(HttpStatus.BAD_REQUEST, ErrorCodeEnum.USER_UNABLE_TO_VOTE,
                    "Assembly not found.");
        }
        Optional<Assembly> assembly = assemblyRepository.findByAssemblyIdentifierAndStatusIs(partnerVoteBO.getAssemblyIdentifier(), AssemblyStatus.VOTING);
        assembly.ifPresent(assemblyObj -> {
            log.info("AssociateService - doVote - Assembly found.");
            saveAssemblyVote(assemblyObj, partnerVoteBO);
            Optional<Partner> partnerOptional = partnerRepository.findByDocumentNumber(partnerVoteBO.getDocumentNumber());
            partnerOptional.ifPresent(partner -> saveAssociateVote(partner, partnerVoteBO));
        });
    }

    @Override
    public Partner createPartner(PartnerBO partnerBO) {
        log.info("AssociateService - saveAssemblyVote - Saving new partner in database: {} ", partnerBO);
        Partner partner = PartnerConverter.convertToPartner(partnerBO);
        partnerRepository.save(partner);
        return partner;
    }

    private void saveAssemblyVote(Assembly assembly, PartnerVoteBO partnerVoteBO) {
        assembly.getVotes().put(partnerVoteBO.getDocumentNumber(), partnerVoteBO.getVote());
        log.info("AssociateService - saveAssemblyVote - Saving new vote in database. associate voting: " + partnerVoteBO.getDocumentNumber());
        assemblyRepository.save(assembly);
    }

    private void saveAssociateVote(Partner partner, PartnerVoteBO partnerVoteBO) {
        partner.getVotedAssemblies().put(partnerVoteBO.getAssemblyIdentifier(), partnerVoteBO.getVote());
        log.info("AssociateService - saveAssociateVote - Saving new vote in database. voted assembly: " + partnerVoteBO.getAssemblyIdentifier());
        partnerRepository.save(partner);
    }
}
