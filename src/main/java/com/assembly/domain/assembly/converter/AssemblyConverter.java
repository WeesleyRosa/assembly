package com.assembly.domain.assembly.converter;

import com.assembly.domain.assembly.api.v1.controller.request.CreateAssemblyRequest;
import com.assembly.domain.assembly.api.v1.controller.response.AssemblyResponse;
import com.assembly.domain.assembly.api.v1.controller.response.AssemblyVoteResponse;
import com.assembly.domain.assembly.business.AssemblyBO;
import com.assembly.domain.assembly.entities.Assembly;
import com.assembly.domain.assembly.entities.enumerator.AssemblyStatus;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public final class AssemblyConverter {

    public static final Long DEFAULT_VOTING_TIME = 60000L;

    public static Assembly convertStartedAssembly(AssemblyBO assemblyBO ) {
        return Assembly.builder()
                .subject(assemblyBO.getSubject())
                .createdAt(LocalDateTime.now())
                .voteStartedAt(LocalDateTime.now())
                .status(AssemblyStatus.VOTING)
                .votingTime(assemblyBO.getVotingTime())
                .assemblyIdentifier(assemblyBO.getAssemblyIdentifier())
                .build();
    }

    public static Assembly convertNotStartedAssembly(AssemblyBO assemblyBO) {
        return Assembly.builder()
                .subject(assemblyBO.getSubject())
                .status(AssemblyStatus.CREATED)
                .createdAt(LocalDateTime.now())
                .votingTime(assemblyBO.getVotingTime())
                .assemblyIdentifier(DEFAULT_VOTING_TIME)
                .build();
    }

    public static AssemblyVoteResponse convertAssemblyVotedResponse(Assembly assembly) {
        return AssemblyVoteResponse.builder()
                .status(assembly.getStatus())
                .subject(assembly.getSubject())
                .voteEndedAt(assembly.getVoteEndedAt())
                .votes(assembly.getVotes())
                .voteStartedAt(assembly.getVoteStartedAt())
                .build();
    }

    public static List<AssemblyResponse> convertAssemblyList(List<Assembly> assemblyList) {
        return assemblyList.stream().map(AssemblyConverter::convertAssembly).collect(Collectors.toList());
    }

    public static AssemblyResponse convertAssembly(Assembly assembly) {
        return AssemblyResponse
                .builder()
                .assemblyIdentifier(assembly.getAssemblyIdentifier())
                .status(assembly.getStatus())
                .subject(assembly.getSubject())
                .voteEndedAt(assembly.getVoteEndedAt())
                .votes(assembly.getVotes())
                .voteStartedAt(assembly.getVoteStartedAt())
                .votingTime(assembly.getVotingTime())
                .build();

    }

    public static AssemblyBO convertAssemblyBO(CreateAssemblyRequest createAssemblyRequest) {
        return AssemblyBO
                .builder()
                .assemblyStatus(createAssemblyRequest.getAssemblyStatus())
                .assemblyIdentifier(createAssemblyRequest.getAssemblyIdentifier())
                .subject(createAssemblyRequest.getSubject())
                .votingTime(createAssemblyRequest.getVotingTime())
                .build();

    }
}
