package com.kachalova.gateway.client;


import com.kachalova.gateway.dto.FinishRegistrationRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "deal", url = "http://deal:8081/deal")
public interface DealClient {
    @RequestMapping(method = RequestMethod.POST, value = "/document/{statementId}/send", consumes = MediaType.APPLICATION_JSON_VALUE)
    void createDocuments(@PathVariable String statementId);

    @RequestMapping(method = RequestMethod.POST, value = "/document/{statementId}/sign", consumes = MediaType.APPLICATION_JSON_VALUE)
    void signDocuments(@PathVariable String statementId);

    @PostMapping("/document/{statementId}/code")
    void verify(@PathVariable String statementId);

    @PostMapping("/calculate/{statementId}")
    void calculate(@RequestBody FinishRegistrationRequestDto finishRegistrationRequestDto, @PathVariable String statementId);
}
