package com.kachalova.gateway.client;

import com.kachalova.gateway.dto.LoanOfferDto;
import com.kachalova.gateway.dto.LoanStatementRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "statement", url = "http://localhost:8082/statement")
public interface StatementClient {
    @PostMapping()
    ResponseEntity<LoanOfferDto[]> createLoanStatement(@RequestBody LoanStatementRequestDto requestDto);

    @PostMapping("/offer")
    void chooseOffer(@RequestBody LoanOfferDto requestDto);

}
