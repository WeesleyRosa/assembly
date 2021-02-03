package com.assembly.domain.assembly.service;

import com.assembly.domain.assembly.api.v1.controller.request.AssemblyRequest;
import com.assembly.domain.assembly.api.v1.controller.response.AssemblyResponse;
import com.assembly.domain.assembly.api.v1.controller.response.AssemblyVoteResponseDto;
import com.assembly.domain.assembly.business.AssemblyBO;
import com.assembly.domain.assembly.entities.Assembly;

import java.util.List;
import java.util.Optional;

public interface AssemblyService {

    AssemblyVoteResponseDto getAssemblyByAssemblyIdentifierOrThrow(Long assemblyIdentifier);

    Optional<Assembly> getAssemblyById(Long assemblyIdentifier);

    Assembly createAssembly(AssemblyBO assemblyBO);

    void startAssembly(Long assemblyIdentifier);

    List<AssemblyResponse> getAllAssemblies();
}
