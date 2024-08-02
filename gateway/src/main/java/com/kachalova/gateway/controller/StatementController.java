package com.kachalova.gateway.controller;

import com.kachalova.gateway.dto.FinishRegistrationRequestDto;
import com.kachalova.gateway.dto.LoanOfferDto;
import com.kachalova.gateway.dto.LoanStatementRequestDto;
import com.kachalova.gateway.service.StatementService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/statement")
@RequiredArgsConstructor
public class StatementController {
    private final StatementService statementService;
    @Operation(summary = "получение возможных условий по кредиту")
    @PostMapping("")
    public List<LoanOfferDto> createLoanStatement(@RequestBody LoanStatementRequestDto requestDto) {
        log.info("/statement requestDto:{}", requestDto);
        List<LoanOfferDto> responseDto = statementService.createLoanStatement(requestDto);
        log.info("/statement responseDto:{}", responseDto);
        return responseDto;
    }
    @Operation(summary = "выбор условия по кредиту")
    @PostMapping("/select")
    public void chooseOffer(@RequestBody LoanOfferDto requestDto) {
        log.info("/statement/select requestDto:{}", requestDto);
        statementService.chooseOffer(requestDto);

    }
    @Operation(summary = "завершение регистрации + полный подсчёт кредита")
    @PostMapping("/registration/{statementId}")
    public void registration(@RequestBody FinishRegistrationRequestDto requestDto, @PathVariable String statementId) {
        log.info("/statement/registration/{statementId} requestDto:{}, statementId:{}", requestDto, statementId);
        statementService.calculate(requestDto, statementId);
    }

}
