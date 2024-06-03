package com.kachalova.calculator.service.impl;

import com.kachalova.calculator.dto.LoanOfferDto;
import com.kachalova.calculator.dto.LoanStatementRequestDto;
import com.kachalova.calculator.service.LoanService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Validated
public class LoanServiceImpl implements LoanService {

    @Override
    public List<LoanOfferDto> generateLoanOffers(@Valid LoanStatementRequestDto request, BigDecimal baseInterestRate) {

        List<LoanOfferDto> loanOfferDtoList = List.of(
                createLoanOfferDto(false, false, request, baseInterestRate),
                createLoanOfferDto(false, true, request, baseInterestRate),
                createLoanOfferDto(true, false, request, baseInterestRate),
                createLoanOfferDto(true, true, request, baseInterestRate)

        );
        log.info("Generated loan offers list: {}", loanOfferDtoList);
        return loanOfferDtoList;
    }

    private LoanOfferDto createLoanOfferDto(Boolean isInsuranceEnabled,
                                            Boolean isSalaryClient,
                                            LoanStatementRequestDto requestDto,
                                            BigDecimal baseInterestRate) {
        ScoringServiceImpl scoringService = new ScoringServiceImpl();
        CreditPaymentsServiceImpl creditPaymentsService = new CreditPaymentsServiceImpl();
        BigDecimal rate = scoringService.checkSalaryClient(isSalaryClient, baseInterestRate);
        rate = scoringService.checkInsuranceEnabled(isInsuranceEnabled, rate);
        BigDecimal monthlyPayment = creditPaymentsService.calculateMonthlyPayment(rate, requestDto.getAmount(), requestDto.getTerm());
        BigDecimal totalAmount = creditPaymentsService.calculatePsk(monthlyPayment, requestDto.getTerm());

        return new LoanOfferDto(UUID.randomUUID(),
                requestDto.getAmount(),
                totalAmount,
                requestDto.getTerm(),
                monthlyPayment,
                rate, isInsuranceEnabled,
                isSalaryClient);
    }
}
