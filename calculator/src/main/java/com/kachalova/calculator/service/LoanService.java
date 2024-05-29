package com.kachalova.calculator.service;

import com.kachalova.calculator.dto.LoanOfferDto;
import com.kachalova.calculator.dto.LoanStatementRequestDto;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class LoanService {
    private final BigDecimal baseInterestRate = BigDecimal.valueOf(16.0); // Базовая процентная ставка
    private final BigDecimal insuranceRateDecrease = BigDecimal.valueOf(3.0); // Уменьшение ставки при наличии страховки
    private final BigDecimal salaryClientRateDecrease = BigDecimal.valueOf(1.0); // Уменьшение ставки для зарплатного клиента

    public List<LoanOfferDto> generateLoanOffers(LoanStatementRequestDto request) {
        List<LoanOfferDto> loanOffers = new ArrayList<>();

        boolean[] insuranceOptions = {false, true};
        boolean[] salaryClientOptions = {false, true};

        for (boolean isInsuranceEnabled : insuranceOptions) {
            for (boolean isSalaryClient : salaryClientOptions) {
                BigDecimal interestRate = baseInterestRate;

                if (isInsuranceEnabled) {
                    interestRate = interestRate.subtract(insuranceRateDecrease);
                }
                if (isSalaryClient) {
                    interestRate = interestRate.subtract(salaryClientRateDecrease);
                }

                LoanOfferDto offer = new LoanOfferDto();
                offer.setStatementId(UUID.randomUUID());
                offer.setRequestedAmount(request.getAmount());
                // Логика расчета totalAmount, monthlyPayment и других параметров
                offer.setTerm(request.getTerm());
                offer.setRate(interestRate);
                offer.setIsInsuranceEnabled(isInsuranceEnabled);
                offer.setIsSalaryClient(isSalaryClient);

                loanOffers.add(offer);
            }
        }

        loanOffers.sort(Comparator.comparing(LoanOfferDto::getRate));

        return loanOffers;
    }
}
