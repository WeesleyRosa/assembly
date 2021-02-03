package com.assembly.domain.partner.client;

import com.assembly.domain.partner.client.response.CpfValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "cpfValidation", url = "${integration.cpf}")
public interface CpfValidationClient {

    @GetMapping("/users/{cpf}")
    CpfValidationResponse validateCpf(@PathVariable String cpf);
}
