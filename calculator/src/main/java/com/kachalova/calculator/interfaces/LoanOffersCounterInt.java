package com.kachalova.calculator.interfaces;

import com.kachalova.calculator.dto.LoanOfferDto;
import com.kachalova.calculator.dto.LoanStatementRequestDto;

import java.math.BigDecimal;
import java.util.List;

public interface LoanOffersCounterInt {
    List<LoanOfferDto> generateLoanOffers(LoanStatementRequestDto request, BigDecimal baseInterestRate);
}
