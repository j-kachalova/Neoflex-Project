package com.kachalova.deal.service;

import com.kachalova.deal.dto.LoanOfferDto;
import com.kachalova.deal.dto.LoanStatementRequestDto;

import java.util.List;

public interface LoanOfferCalculation {
    List<LoanOfferDto> calculateLoanOffer(LoanStatementRequestDto loanStatementRequestDto);
}
