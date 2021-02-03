package com.assembly.domain.assembly.service.impl;

import com.assembly.domain.assembly.api.v1.controller.response.AssemblyResponse;
import com.assembly.domain.assembly.api.v1.controller.response.AssemblyVoteResponseDto;
import com.assembly.domain.assembly.business.AssemblyBO;
import com.assembly.domain.assembly.converter.AssemblyConverter;
import com.assembly.domain.assembly.entities.Assembly;
import com.assembly.domain.assembly.entities.enumerator.AssemblyStatus;
import com.assembly.domain.assembly.repository.AssemblyRepository;
import com.assembly.domain.assembly.service.AssemblyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssemblyServiceImpl implements AssemblyService {

    private final AssemblyRepository assemblyRepository;

    @Override
    public List<AssemblyResponse> getAllAssemblies() {
        return AssemblyConverter.convertAssemblyList(assemblyRepository.findAll());
    }

    @Override
    public AssemblyVoteResponseDto getAssemblyByAssemblyIdentifierOrThrow(Long assemblyIdentifier) {
        log.info("AssemblyService - getAssemblyByAssemblyIdentifierOrThrow - Getting assembly by id.");
        return AssemblyConverter.convertAssemblyVotedResponse(getAssemblyById(assemblyIdentifier).orElseThrow());
    }

    private Optional<Assembly> getAssemblyById(Long assemblyIdentifier) {
        return assemblyRepository.findByAssemblyIdentifierAndStatusIs(assemblyIdentifier, AssemblyStatus.VOTE_CLOSED);
    }

    @Override
    public Assembly createAssembly(AssemblyBO assemblyBO) {
        if(assemblyBO.getVotingTime() == null){
            log.info("AssemblyService - createAssembly - Creating not started assembly.");
            return saveNotStartedAssembly(assemblyBO);
        }
        else {
            log.info("AssemblyService - createAssembly - Creating started assembly.");
            return saveStartedAssembly(assemblyBO);
        }
    }

    @Override
    public void startAssembly(Long assemblyIdentifier) {
        log.info("AssemblyService - startAssembly - Getting assembly by identifier.");
        Optional<Assembly> assemblyOpt = assemblyRepository.findByAssemblyIdentifier(assemblyIdentifier);
        assemblyOpt.ifPresent(assembly -> {
            log.info("AssemblyService - startAssembly - Starting vote.");
            handleObjectToStartVote(assembly);
            assemblyRepository.save(assembly);
            log.info("AssemblyService - startAssembly - Finishing vote.");
            runTask(assembly);
        });
    }

    private void runTask(Assembly assembly) {
        TimerTask task = new TimerTask() {
            public void run() {
                Assembly assemblyOpt = assemblyRepository.findByAssemblyIdentifier(assembly.getAssemblyIdentifier()).orElse(new Assembly());
                assemblyOpt.setVoteEndedAt(LocalDateTime.now());
                assemblyOpt.setStatus(AssemblyStatus.VOTE_CLOSED);
                assemblyRepository.save(assemblyOpt);
                log.info("AssemblyService - runTask - Task terminated.");
            }
        };
        Timer timer = new Timer("Timer");
        timer.schedule(task, assembly.getVotingTime());
    }

    private static void handleObjectToStartVote(Assembly assembly) {
        assembly.setStatus(AssemblyStatus.VOTING);
        assembly.setVoteStartedAt(LocalDateTime.now());
    }

    private Assembly saveStartedAssembly(AssemblyBO assemblyBO) {
        return assemblyRepository.save(AssemblyConverter.convertStartedAssembly(assemblyBO));
    }

    private Assembly saveNotStartedAssembly(AssemblyBO assemblyBO) {
        return assemblyRepository.save(AssemblyConverter.convertNotStartedAssembly(assemblyBO));
    }

}
