package com.assembly.domain.assembly.service.impl;

import com.assembly.domain.assembly.api.v1.controller.request.CreateAssemblyRequest;
import com.assembly.domain.assembly.api.v1.controller.response.AssemblyVoteResponseDto;
import com.assembly.domain.assembly.business.AssemblyBO;
import com.assembly.domain.assembly.entities.Assembly;
import com.assembly.domain.assembly.entities.enumerator.AssemblyStatus;
import com.assembly.domain.assembly.repository.AssemblyRepository;
import com.assembly.domain.assembly.service.AssemblyService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssemblyServiceImplTest {

    @InjectMocks
    private AssemblyServiceImpl assemblyService;

    @Mock
    private AssemblyRepository assemblyRepository;

    @Nested
    @DisplayName("Assembly Controller")
    class Create {

        @DisplayName("Success - Successfully created new assembly")
        @Test
        void create_initialized_assembly() {
            when(assemblyRepository.save(any(Assembly.class))).thenReturn(TestContextFactory.createAssembly());

            Assembly assembly = assemblyService.createAssembly(TestContextFactory.createAssemblyBO());

            assertEquals(10L, assembly.getAssemblyIdentifier());
            assertEquals("Subject test", assembly.getSubject());
        }

        @DisplayName("Success - Successfully created new assembly")
        @Test
        void create_not_initialized_assembly() {
            when(assemblyRepository.save(any(Assembly.class))).thenReturn(TestContextFactory.createAssembly());

            AssemblyBO assemblyBO = TestContextFactory.createAssemblyBO();
            assemblyBO.setVotingTime(null);
            Assembly assembly = assemblyService.createAssembly(assemblyBO);

            assertEquals(10L, assembly.getAssemblyIdentifier());
            assertEquals(600000L, assembly.getVotingTime());
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

        public static AssemblyBO createAssemblyBO() {
            return AssemblyBO.builder()
                    .assemblyIdentifier(10L)
                    .assemblyStatus(AssemblyStatus.CREATED)
                    .subject("Subject test")
                    .votingTime(600000L)
                    .build();
        }

        public static AssemblyVoteResponseDto createAssemblyVote() {
            return AssemblyVoteResponseDto.builder().build();
        }
    }

}
