package com.kachalova.statement.client;


import com.kachalova.statement.dto.LoanOfferDto;
import com.kachalova.statement.dto.LoanStatementRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "deal", url = "http://localhost:8081/deal")
public interface DealClient {
    @RequestMapping(method = RequestMethod.POST, value = "/statement", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<LoanOfferDto[]> prescoring(@RequestBody LoanStatementRequestDto requestDto);

    @RequestMapping(method = RequestMethod.POST, value = "/offer/select", consumes = "application/json")
    void selectOffer(@RequestBody LoanOfferDto loanOfferDto);
}
