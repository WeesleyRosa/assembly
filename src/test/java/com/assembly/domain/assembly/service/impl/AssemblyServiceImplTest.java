package com.assembly.domain.assembly.service.impl;

import com.assembly.domain.assembly.api.v1.controller.request.CreateAssemblyRequest;
import com.assembly.domain.assembly.api.v1.controller.response.AssemblyResponse;
import com.assembly.domain.assembly.api.v1.controller.response.AssemblyVoteResponse;
import com.assembly.domain.assembly.business.AssemblyBO;
import com.assembly.domain.assembly.entities.Assembly;
import com.assembly.domain.assembly.entities.enumerator.AssemblyStatus;
import com.assembly.domain.assembly.repository.AssemblyRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssemblyServiceImplTest {

    @InjectMocks
    private AssemblyServiceImpl assemblyService;

    @Mock
    private AssemblyRepository assemblyRepository;

    @Nested
    @DisplayName("Assembly Service")
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

        @DisplayName("Success - Successfully started assembly")
        @Test
        void start_assembly() {
            Optional<Assembly> assemblyOptional = Optional.of(TestContextFactory.createAssembly());

            when(assemblyRepository.findByAssemblyIdentifier(anyLong())).thenReturn(assemblyOptional);
            when(assemblyRepository.save(any(Assembly.class))).thenReturn(assemblyOptional.get());

            assemblyService.startAssembly(10L);
            verify(assemblyRepository, times(1)).save(any());
        }

        @DisplayName("Success - Successfully created new assembly")
        @Test
        void list_assemblies() {
            when(assemblyRepository.findAll()).thenReturn(List.of(TestContextFactory.createAssembly()));

            List<AssemblyResponse> assemblyResponses = assemblyService.getAllAssemblies();

            verify(assemblyRepository, times(1)).findAll();
        }
    }

    static class TestContextFactory {

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
    }
}
