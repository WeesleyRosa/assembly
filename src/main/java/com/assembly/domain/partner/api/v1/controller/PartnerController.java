package com.assembly.domain.partner.api.v1.controller;

import com.assembly.domain.partner.api.v1.controller.request.CreatePartnerRequest;
import com.assembly.domain.partner.api.v1.controller.request.PartnerVoteRequest;
import com.assembly.domain.partner.business.PartnerBO;
import com.assembly.domain.partner.business.PartnerVoteBO;
import com.assembly.domain.partner.converter.PartnerConverter;
import com.assembly.domain.partner.service.PartnerService;
import com.assembly.infra.common.util.UriGeneratorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/partner")
@Slf4j
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;

    @PostMapping("/vote")
    public ResponseEntity<Void> voteForAgenda(@Valid @RequestBody PartnerVoteRequest request) {
        log.info("PartnerController - voteForAssembly - Start voting.");
        PartnerVoteBO partnerVoteBO = PartnerConverter.convertToPartnerBO(request);
        partnerService.doVote(partnerVoteBO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createPartner(@Valid @RequestBody CreatePartnerRequest request) {
        log.info("PartnerController - createPartner - Create partner: {}.", request);
        PartnerBO partnerVoteBO = PartnerConverter.convertToPartnerBO(request);
        partnerService.createPartner(partnerVoteBO);
        return ResponseEntity.created(UriGeneratorUtil.get("/{documentNumber}", request.getDocumentNumber())).build();

    }
}
