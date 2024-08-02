package com.kachalova.gateway.service;

import com.kachalova.gateway.dto.FinishRegistrationRequestDto;
import com.kachalova.gateway.dto.LoanOfferDto;
import com.kachalova.gateway.dto.LoanStatementRequestDto;

import java.util.List;

public interface StatementService {
    List<LoanOfferDto> createLoanStatement(LoanStatementRequestDto requestDto);

    void chooseOffer(LoanOfferDto requestDto);

    void calculate(FinishRegistrationRequestDto finishRegistrationRequestDto, String statementId);
}
