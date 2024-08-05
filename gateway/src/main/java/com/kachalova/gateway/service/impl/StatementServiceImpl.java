package com.kachalova.gateway.service.impl;

import com.kachalova.gateway.client.DealClient;
import com.kachalova.gateway.client.StatementClient;
import com.kachalova.gateway.dto.FinishRegistrationRequestDto;
import com.kachalova.gateway.dto.LoanOfferDto;
import com.kachalova.gateway.dto.LoanStatementRequestDto;
import com.kachalova.gateway.exception.ExternalServiceException;
import com.kachalova.gateway.service.StatementService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatementServiceImpl implements StatementService {
    private final StatementClient statementClient;
    private final DealClient dealClient;

    @Override
    public List<LoanOfferDto> createLoanStatement(LoanStatementRequestDto requestDto) {
        log.info("StatementServiceImpl createLoanStatement requestDto: {}", requestDto);
        ResponseEntity<LoanOfferDto[]> response = statementClient.createLoanStatement(requestDto);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.error("Failed to fetch loan offers: {}", response.getStatusCode());
            throw new ExternalServiceException("Error from external service", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        LoanOfferDto[] loanOfferDtos = response.getBody();
        if (loanOfferDtos == null || loanOfferDtos.length == 0) {
            log.error("StatementServiceImpl: createLoanStatement failed");
            throw new ExternalServiceException("LoanOfferDto[] from external service is null or length == 0",
                    HttpStatus.NO_CONTENT);
        }
        List<LoanOfferDto> loanOfferDtoList = List.of(loanOfferDtos);
        log.info("StatementServiceImpl createLoanStatement loanOfferDtoList: {}", loanOfferDtoList);
        return loanOfferDtoList;
    }

    @Override
    public void chooseOffer(LoanOfferDto requestDto) {
        log.info("StatementServiceImpl chooseOffer requestDto:{}", requestDto);
        statementClient.chooseOffer(requestDto);
    }

    public void calculate(FinishRegistrationRequestDto finishRegistrationRequestDto, String statementId) {
        log.info("StatementServiceImpl calculate finishRegistrationRequestDto:{}, statementId:{}",
                finishRegistrationRequestDto, statementId);
        dealClient.calculate(finishRegistrationRequestDto, statementId);
    }

}
