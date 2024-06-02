package com.kachalova.calculator.service;

import com.kachalova.calculator.dto.LoanOfferDto;
import com.kachalova.calculator.dto.LoanStatementRequestDto;
import com.kachalova.calculator.interfaces.LoanOffersCounterInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class LoanService implements LoanOffersCounterInt {


    private static final Logger log = LoggerFactory.getLogger(LoanService.class);

    @Override
    public List<LoanOfferDto> generateLoanOffers(LoanStatementRequestDto request, BigDecimal baseInterestRate) {

        List<LoanOfferDto> loanOfferDtoList= List.of(
                createLoanOfferDto(false, false, request, baseInterestRate),
                createLoanOfferDto(false, true, request, baseInterestRate),
                createLoanOfferDto(true, false, request, baseInterestRate),
                createLoanOfferDto(true, true, request, baseInterestRate)

        );
        log.info("Generated loan offers list: {}", loanOfferDtoList);
        return loanOfferDtoList;
    }
  private LoanOfferDto createLoanOfferDto( Boolean isInsuranceEnabled,
                                           Boolean isSalaryClient,
                                           LoanStatementRequestDto requestDto,
                                           BigDecimal baseInterestRate) {
        ScoringService scoringService = new ScoringService();
        CreditPaymentsService creditPaymentsService = new CreditPaymentsService();
        BigDecimal rate = scoringService.checkSalaryClient(isSalaryClient, baseInterestRate);
        rate=scoringService.checkInsuranceEnabled(isInsuranceEnabled, rate);
        BigDecimal monthlyPayment = creditPaymentsService.calculateMonthlyPayment(rate, requestDto.getAmount(), requestDto.getTerm());
        BigDecimal totalAmount = creditPaymentsService.calculatePsk(monthlyPayment,requestDto.getTerm());

        return new LoanOfferDto(UUID.randomUUID(),
                requestDto.getAmount(),
                totalAmount,
                requestDto.getTerm(),
                monthlyPayment,
                rate,isInsuranceEnabled,
                isSalaryClient);
    }
}
