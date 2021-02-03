package com.assembly.domain.partner.service.impl;

import com.assembly.domain.assembly.entities.Assembly;
import com.assembly.domain.assembly.entities.enumerator.AssemblyStatus;
import com.assembly.domain.assembly.repository.AssemblyRepository;
import com.assembly.domain.partner.api.v1.controller.request.PartnerVoteRequest;
import com.assembly.domain.partner.client.CpfValidationClient;
import com.assembly.domain.partner.client.response.CpfValidationResponse;
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

    @Override
    public CpfValidationResponse validateCpf(PartnerVoteRequest request) {
        log.info("AssociateService - CpfValidationResponse - Sending request to validate cpf.");
        return cpfValidationClient.validateCpf(request.getDocumentNumber());
    }

    @Override
    public void doVote(PartnerVoteRequest request) {
        log.info("AssociateService - doVote - Getting objects from database.");
        if(validateCpf(request).getStatus().equalsIgnoreCase("UNABLE_TO_VOTE")) {
            log.info("AssociateService - doVote - Cpf unable to vote.");
            throw new UserVoteException(HttpStatus.BAD_REQUEST, ErrorCodeEnum.USER_UNABLE_TO_VOTE,
                    "Assembly not found.");
        }
        Optional<Assembly> assembly = assemblyRepository.findByAssemblyIdentifierAndStatusIs(request.getAssemblyIdentifier(), AssemblyStatus.VOTING);
        assembly.ifPresent(assemblyObj -> {
            log.info("AssociateService - doVote - Assembly found.");
            saveAssemblyVote(assemblyObj, request);
            Optional<Partner> partnerOptional = partnerRepository.findByDocumentNumber(request.getDocumentNumber());
            partnerOptional.ifPresent(partner -> saveAssociateVote(partner, request)); 
        });
    }

    private void saveAssemblyVote(Assembly assembly, PartnerVoteRequest request) {
        assembly.getVotes().put(request.getDocumentNumber(), request.getVote());
        log.info("AssociateService - saveAssemblyVote - Saving new vote in database. associate voting: " + request.getDocumentNumber());
        assemblyRepository.save(assembly);
    }

    private void saveAssociateVote(Partner partner, PartnerVoteRequest request) {
        partner.getVotedAssemblies().put(request.getAssemblyIdentifier(), request.getVote());
        log.info("AssociateService - saveAssociateVote - Saving new vote in database. voted assembly: " + request.getAssemblyIdentifier());
        partnerRepository.save(partner);
    }
}
