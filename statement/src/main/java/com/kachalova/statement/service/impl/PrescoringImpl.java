package com.kachalova.statement.service.impl;

import com.kachalova.statement.client.DealClient;
import com.kachalova.statement.dto.LoanOfferDto;
import com.kachalova.statement.dto.LoanStatementRequestDto;
import com.kachalova.statement.exceptions.ExternalServiceException;
import com.kachalova.statement.service.Prescoring;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PrescoringImpl implements Prescoring {
    private final DealClient dealClient;

    @Override
    public List<LoanOfferDto> sendLoanStatementRequestDto(LoanStatementRequestDto requestDto) {
        log.info("PrescoringImpl sendLoanStatementRequestDto requestDto: {}", requestDto);
        ResponseEntity<LoanOfferDto[]> response = dealClient.prescoring(requestDto);
        if (response.getStatusCode() != HttpStatus.OK) {
            log.error("Failed to fetch loan offers: {}", response.getStatusCode());
            throw new ExternalServiceException("Error from external service", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        LoanOfferDto[] loanOfferDtos = response.getBody();
        if (loanOfferDtos == null || loanOfferDtos.length == 0) {
            log.error("PrescoringImpl: sendLoanStatementRequestDto failed");
            throw new ExternalServiceException("LoanOfferDto[] from external service is null or length == 0", HttpStatus.NO_CONTENT);
        }
        List<LoanOfferDto> loanOfferDtoList = List.of(loanOfferDtos);
        log.info("PrescoringImpl sendLoanStatementRequestDto loanOfferDtoList: {}", loanOfferDtoList);
        return loanOfferDtoList;
    }
}
