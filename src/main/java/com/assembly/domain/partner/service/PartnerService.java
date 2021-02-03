package com.assembly.domain.partner.service;

import com.assembly.domain.partner.api.v1.controller.request.PartnerVoteRequest;
import com.assembly.domain.partner.client.response.CpfValidationResponse;

public interface PartnerService {

    void doVote(PartnerVoteRequest request);

    CpfValidationResponse validateCpf(PartnerVoteRequest request);
}
