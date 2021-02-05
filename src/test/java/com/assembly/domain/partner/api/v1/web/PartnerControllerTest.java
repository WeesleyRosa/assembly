package com.assembly.domain.partner.api.v1.web;

import com.assembly.domain.assembly.api.v1.controller.request.CreateAssemblyRequest;
import com.assembly.domain.assembly.api.v1.controller.response.AssemblyResponse;
import com.assembly.domain.assembly.api.v1.controller.response.AssemblyVoteResponse;
import com.assembly.domain.assembly.business.AssemblyBO;
import com.assembly.domain.assembly.entities.Assembly;
import com.assembly.domain.assembly.entities.enumerator.AssemblyStatus;
import com.assembly.domain.assembly.service.AssemblyService;
import com.assembly.domain.partner.api.v1.controller.PartnerController;
import com.assembly.domain.partner.api.v1.controller.request.PartnerVoteRequest;
import com.assembly.domain.partner.business.PartnerBO;
import com.assembly.domain.partner.business.PartnerVoteBO;
import com.assembly.domain.partner.entities.Partner;
import com.assembly.domain.partner.service.PartnerService;
import com.assembly.infra.common.exception.GlobalExceptionHandler;
import com.assembly.infra.common.util.JsonUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PartnerControllerTest {


    @InjectMocks
    private PartnerController partnerController;

    @Mock
    private PartnerService partnerService;

    private MockMvc mockMvc;

    @BeforeEach
    void initEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(partnerController).setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Nested
    @DisplayName("Create Assembly")
    class Create {

        @DisplayName("Success - Successfully created a new partner")
        @Test
        void create_partner() throws Exception {
            Partner partner = TestContextFactory.createPartner();
            when(partnerService.createPartner(any(PartnerBO.class))).thenReturn(TestContextFactory.createPartner());
            mockMvc.perform(post("/v1/partner/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.asJsonString(partner)))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", "http://localhost/v1/partner/create/1000"));
        }

        @DisplayName("Failure - Failed to create new partner")
        @Test
        void failed_create_partner() throws Exception {
            Partner partner = TestContextFactory.createPartner();
            partner.setDocumentNumber(null);
            mockMvc.perform(post("/v1/partner/create")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.asJsonString(partner)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("Vote")
    class Vote {

        @DisplayName("Success - Successfully voted for an existing assembly")
        @Test
        void vote_assembly() throws Exception {
            PartnerVoteRequest partnerVoteRequest = TestContextFactory.createPartnerVote();
            when(partnerService.doVote(any(PartnerVoteBO.class))).thenReturn(TestContextFactory.createAssembly());
            mockMvc.perform(post("/v1/partner/vote")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.asJsonString(partnerVoteRequest)))
                    .andExpect(status().isNoContent());
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

        public static PartnerVoteRequest createPartnerVote() {
            return PartnerVoteRequest
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
                    .build();
        }
    }
}
