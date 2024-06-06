package com.kachalova.calculator.service.impl;

import com.kachalova.calculator.dto.LoanOfferDto;
import com.kachalova.calculator.dto.LoanStatementRequestDto;
import com.kachalova.calculator.service.LoanService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {
    private final ScoringServiceImpl scoringService;
    private final CreditPaymentsServiceImpl creditPaymentsService;

    @Override
    public List<LoanOfferDto> generateLoanOffers(@Valid LoanStatementRequestDto request, BigDecimal baseInterestRate) {
        log.info("request with LoanStatementRequestDto: {}, baseInterestRate: {}", request, baseInterestRate);
        List<LoanOfferDto> loanOfferDtoList = List.of(
                createLoanOfferDto(false, false, request, baseInterestRate),
                createLoanOfferDto(false, true, request, baseInterestRate),
                createLoanOfferDto(true, false, request, baseInterestRate),
                createLoanOfferDto(true, true, request, baseInterestRate)

        );
        log.info("LoanOfferDtoList response: {}", loanOfferDtoList);
        return loanOfferDtoList;
    }

    private LoanOfferDto createLoanOfferDto(Boolean isInsuranceEnabled,
                                            Boolean isSalaryClient,
                                            LoanStatementRequestDto requestDto,
                                            BigDecimal baseInterestRate) {

        BigDecimal rate = scoringService.checkSalaryClient(isSalaryClient, baseInterestRate);
        rate = scoringService.checkInsuranceEnabled(isInsuranceEnabled, rate);
        BigDecimal monthlyPayment = creditPaymentsService.calculateMonthlyPayment(rate,
                requestDto.getAmount(), requestDto.getTerm());
        BigDecimal totalAmount = creditPaymentsService.calculatePsk(monthlyPayment, requestDto.getTerm());

        return LoanOfferDto.builder()
                .statementId(UUID.randomUUID())
                .requestedAmount(requestDto.getAmount())
                .totalAmount(totalAmount)
                .term(requestDto.getTerm())
                .monthlyPayment(monthlyPayment)
                .rate(rate)
                .isInsuranceEnabled(isInsuranceEnabled)
                .isSalaryClient(isSalaryClient)
                .build();
    }
}
