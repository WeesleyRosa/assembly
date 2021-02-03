package com.assembly.domain.partner.service;

import com.assembly.domain.partner.business.PartnerBO;
import com.assembly.domain.partner.business.PartnerVoteBO;
import com.assembly.domain.partner.entities.Partner;

public interface PartnerService {

    void doVote(PartnerVoteBO partnerVoteBO);

    Partner createPartner(PartnerBO partnerBO);

}
