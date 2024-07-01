package com.kachalova.statement.service;

import com.kachalova.statement.dto.LoanOfferDto;
import com.kachalova.statement.dto.LoanStatementRequestDto;

import java.util.List;

public interface Prescoring {
    List<LoanOfferDto> sendLoanStatementRequestDto(LoanStatementRequestDto requestDto);
}
