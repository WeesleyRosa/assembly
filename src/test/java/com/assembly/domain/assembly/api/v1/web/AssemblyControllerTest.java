package com.assembly.domain.assembly.api.v1.web;

import com.assembly.domain.assembly.api.v1.controller.AssemblyController;
import com.assembly.domain.assembly.api.v1.controller.request.CreateAssemblyRequest;
import com.assembly.domain.assembly.api.v1.controller.response.AssemblyResponse;
import com.assembly.domain.assembly.api.v1.controller.response.AssemblyVoteResponse;
import com.assembly.domain.assembly.business.AssemblyBO;
import com.assembly.domain.assembly.entities.Assembly;
import com.assembly.domain.assembly.entities.enumerator.AssemblyStatus;
import com.assembly.domain.assembly.service.AssemblyService;
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

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AssemblyControllerTest {


    @InjectMocks
    private AssemblyController assemblyController;

    @Mock
    private AssemblyService assemblyService;

    private MockMvc mockMvc;

    @BeforeEach
    void initEach() {
        mockMvc = MockMvcBuilders.standaloneSetup(assemblyController).setControllerAdvice(new GlobalExceptionHandler()).build();
    }

    @Nested
    @DisplayName("Create Cashback Campaign")
    class Create {

        @DisplayName("Success - Successfully created a new assembly")
        @Test
        void create_assembly() throws Exception {
            CreateAssemblyRequest createAssemblyRequest = TestContextFactory.createCreateCampaignRequest();
            when(assemblyService.createAssembly(any(AssemblyBO.class))).thenReturn(TestContextFactory.createAssembly());
            mockMvc.perform(post("/v1/assembly/create/assembly")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.asJsonString(createAssemblyRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", "http://localhost/v1/assembly/create/assembly/10"));
        }

        @DisplayName("Failure - Failed to create new assembly")
        @Test
        void failed_create_assembly() throws Exception {
            CreateAssemblyRequest createAssemblyRequest = TestContextFactory.createCreateCampaignRequest();
            createAssemblyRequest.setAssemblyIdentifier(null);
            mockMvc.perform(post("/v1/assembly/create/assembly")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(JsonUtil.asJsonString(createAssemblyRequest)))
                    .andExpect(status().isBadRequest());
        }
    }

    @Nested
    @DisplayName("Retrieve Specific Cashback Campaign")
    class RetrieveSpecificCampaign {

        @DisplayName("Success - Successfully retrieved an existing assembly")
        @Test
        void retrieve_assembly() throws Exception {
            when(assemblyService.getAssemblyByAssemblyIdentifier(1L)).thenReturn(TestContextFactory.createAssemblyVote());
            mockMvc.perform(get("/v1/assembly/result/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }

    @Nested
    class RetrieveActiveCampaigns {

        @DisplayName("Success - Successfully retrieved all the assemblies")
        @Test
        void list_assemblies() throws Exception {
            when(assemblyService.getAllAssemblies()).thenReturn(new ArrayList<AssemblyResponse>());
            mockMvc.perform(get("/v1/assembly")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());
        }
    }

    @Nested
    class RetrieveAllCampaigns {

        @DisplayName("Success - Successfully started assembly")
        @Test
        void start_assembly() throws Exception {
            mockMvc.perform(put("/v1/assembly/start/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent());
        }
    }

    static class TestContextFactory {

        public static CreateAssemblyRequest createCreateCampaignRequest() {
            return CreateAssemblyRequest.builder()
                    .assemblyIdentifier(10L)
                    .assemblyStatus(AssemblyStatus.CREATED)
                    .subject("Subject test")
                    .votingTime(600000L)
                    .build();
        }

        public static Assembly createAssembly() {
            return Assembly.builder()
                    .assemblyIdentifier(10L)
                    .subject("Subject test")
                    .votingTime(600000L)
                    .build();
        }

        public static AssemblyVoteResponse createAssemblyVote() {
            return AssemblyVoteResponse.builder().build();
        }
    }
}
