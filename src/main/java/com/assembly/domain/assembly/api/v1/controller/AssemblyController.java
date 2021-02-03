package com.assembly.domain.assembly.api.v1.controller;

import com.assembly.domain.assembly.api.v1.controller.request.CreateAssemblyRequest;
import com.assembly.domain.assembly.api.v1.controller.response.AssemblyResponse;
import com.assembly.domain.assembly.api.v1.controller.response.AssemblyVoteResponseDto;
import com.assembly.domain.assembly.business.AssemblyBO;
import com.assembly.domain.assembly.converter.AssemblyConverter;
import com.assembly.domain.assembly.entities.Assembly;
import com.assembly.domain.assembly.service.AssemblyService;
import com.assembly.infra.common.util.JsonUtil;
import com.assembly.infra.common.util.UriGeneratorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/assembly")
@Slf4j
@RequiredArgsConstructor
public class AssemblyController {

    private final AssemblyService assemblyService;

    @PostMapping("/create/assembly")
    public ResponseEntity<Void> createAssembly(@Valid @RequestBody CreateAssemblyRequest request) {
        log.info("AssemblyController - POST - createAssembly - Start creating assembly: {}", JsonUtil.asJsonString(request));
        AssemblyBO assemblyBO = AssemblyConverter.convertAssemblyBO(request);
        Assembly assembly = assemblyService.createAssembly(assemblyBO);
        return ResponseEntity.created(UriGeneratorUtil.get("/{id}", assembly.getAssemblyIdentifier())).build();
    }

    @PutMapping("/start/{assemblyIdentifier}")
    public ResponseEntity<Void> startAssembly(@PathVariable Long assemblyIdentifier) {
        log.info("AssemblyController - POST - startAssembly - Starting assembly with id: {}", assemblyIdentifier);
        assemblyService.startAssembly(assemblyIdentifier);
        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @GetMapping
    public ResponseEntity<List<AssemblyResponse>> getAllAssemblies() {
        log.info("AssemblyController - GET - getAllAssemblies - Start getting all assemblies.");
        return ResponseEntity.ok(assemblyService.getAllAssemblies());
    }

    @GetMapping("/result/{assemblyIdentifier}")
    public ResponseEntity<AssemblyVoteResponseDto> getAssemblyById(@PathVariable Long assemblyIdentifier) {
        log.info("AssemblyController - GET - getAssemblyById - Getting assembly with id: {}", assemblyIdentifier);
        return ResponseEntity.ok(assemblyService.getAssemblyByAssemblyIdentifierOrThrow(assemblyIdentifier));
    }
}
