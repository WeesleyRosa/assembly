package com.assembly.domain.partner.service.impl;

import com.assembly.domain.assembly.api.v1.controller.response.AssemblyResponse;
import com.assembly.domain.assembly.api.v1.controller.response.AssemblyVoteResponse;
import com.assembly.domain.assembly.business.AssemblyBO;
import com.assembly.domain.assembly.entities.Assembly;
import com.assembly.domain.assembly.entities.enumerator.AssemblyStatus;
import com.assembly.domain.assembly.repository.AssemblyRepository;
import com.assembly.domain.partner.api.v1.controller.request.PartnerVoteRequest;
import com.assembly.domain.partner.business.PartnerBO;
import com.assembly.domain.partner.business.PartnerVoteBO;
import com.assembly.domain.partner.client.CpfValidationClient;
import com.assembly.domain.partner.client.response.CpfValidationResponse;
import com.assembly.domain.partner.entities.Partner;
import com.assembly.domain.partner.repository.PartnerRepository;
import com.assembly.infra.common.exception.DataNotFoundException;
import com.assembly.infra.common.exception.UserVoteException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PartnerServiceImplTest {

    @InjectMocks
    private PartnerServiceImpl partnerService;

    @Mock
    private PartnerRepository partnerRepository;

    @Mock
    private AssemblyRepository assemblyRepository;

    @Mock
    private CpfValidationClient cpfValidationClient;

    @Nested
    @DisplayName("Partner Service")
    class PartnerTest {

        @DisplayName("Success - Successfully created new partner")
        @Test
        void create_partner() {
            when(partnerRepository.save(any(Partner.class))).thenReturn(TestContextFactory.createPartner());

            Partner partner = partnerService.createPartner(TestContextFactory.createPartnerBO());

            assertEquals("1000", partner.getDocumentNumber());
            assertEquals("wesley@teste.com", partner.getEmail());
        }

        @DisplayName("Success - Successfully voted for assembly")
        @Test
        void vote_assembly() {
            when(assemblyRepository.findByAssemblyIdentifierAndStatusIs(any(), any())).thenReturn(Optional.of(TestContextFactory.createAssembly()));
            when(cpfValidationClient.validateCpf(anyString())).thenReturn(TestContextFactory.createCpfValidationResponseFound());

            PartnerVoteBO partnerVoteBO = TestContextFactory.createPartnerVoteBO();
            Assembly assembly = partnerService.doVote(partnerVoteBO);

            assertEquals(10L, assembly.getAssemblyIdentifier());
            assertEquals(600000L, assembly.getVotingTime());
        }

        @DisplayName("Fail - Failed to vote in assembly")
        @Test
        void fail_vote_assembly() {
            when(cpfValidationClient.validateCpf(anyString())).thenReturn(TestContextFactory.createCpfValidationResponseNotFound());

            PartnerVoteBO partnerVoteBO = TestContextFactory.createPartnerVoteBO();
            Executable methodExecution = () -> partnerService.doVote(partnerVoteBO);

            assertThrows(UserVoteException.class, methodExecution);
        }
    }

    static class TestContextFactory {

        public static Partner createPartner() {
            return Partner.builder()
                    .birthday(LocalDate.of(2000, 10, 20))
                    .createdAt(LocalDateTime.of(2000, 10, 20, 10, 50))
                    .documentNumber("1000")
                    .email("wesley@teste.com")
                    .fullName("Wesley Teste")
                    .phoneNumber("51998823645")
                    .build();
        }

        public static CpfValidationResponse createCpfValidationResponseNotFound() {
            return CpfValidationResponse
                    .builder()
                    .status("UNABLE_TO_VOTE")
                    .build();
        }

        public static CpfValidationResponse createCpfValidationResponseFound() {
            return CpfValidationResponse
                    .builder()
                    .status("ABLE_TO_VOTE")
                    .build();
        }

        public static PartnerVoteBO createPartnerVoteBO(){
            return PartnerVoteBO
                    .builder()
                    .assemblyIdentifier(100L)
                    .documentNumber("123456")
                    .vote(Boolean.TRUE)
                    .build();
        }

        public static Assembly createAssembly() {
            return Assembly.builder()
                    .assemblyIdentifier(10L)
                    .subject("Subject test")
                    .votingTime(600000L)
                    .votes(new HashMap<>())
                    .build();
        }

        public static PartnerBO createPartnerBO() {
            return PartnerBO
                    .builder()
                    .birthday(LocalDate.of(2000, 10, 20))
                    .documentNumber("1000")
                    .email("wesley@teste.com")
                    .fullName("Wesley Teste")
                    .phoneNumber("51998823645")
                    .build();
        }
    }
}
