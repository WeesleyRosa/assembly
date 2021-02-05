package com.assembly.domain.partner.service;

import com.assembly.domain.assembly.entities.Assembly;
import com.assembly.domain.partner.business.PartnerBO;
import com.assembly.domain.partner.business.PartnerVoteBO;
import com.assembly.domain.partner.entities.Partner;

public interface PartnerService {

    Assembly doVote(PartnerVoteBO partnerVoteBO);

    Partner createPartner(PartnerBO partnerBO);

}
