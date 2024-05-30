package com.kachalova.calculator.service;

import com.kachalova.calculator.dto.PaymentScheduleElementDto;
import com.kachalova.calculator.dto.ScoringDataDto;
import com.kachalova.calculator.exeptions.ScoringdataDtoValidationExc;
import com.kachalova.calculator.interfaces.CreditPaymentsInt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class CreditPaymentsService implements CreditPaymentsInt {
   public void calculateCredit(){

    }
    static Logger log = Logger.getLogger(CreditPaymentsService.class.getName());

    @Override
    public BigDecimal calculateMonthlyPayment(BigDecimal rate, BigDecimal amount, Integer term) {
        BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(12), RoundingMode.HALF_UP);
        BigDecimal x = monthlyRate.add(BigDecimal.valueOf(1)).pow(term);
        BigDecimal annuityRate = monthlyRate.multiply(x).divide(x.subtract(BigDecimal.valueOf(1)), RoundingMode.HALF_UP);
        BigDecimal monthlyPayment = amount.multiply(annuityRate);
        return monthlyPayment;
    }
    @Override
    public BigDecimal calculatePsk(BigDecimal monthlyPayment, Integer term){
        return monthlyPayment.multiply(BigDecimal.valueOf(term));
    }
    @Override
    public List<PaymentScheduleElementDto> getPaymentSchedule(ScoringDataDto scoringDataDto, BigDecimal psk, BigDecimal monthlyPayment, BigDecimal rate) {
        List<PaymentScheduleElementDto> paymentScheduleElements = new ArrayList<>();
        BigDecimal totalPayment = psk;
        BigDecimal remainingDebt=scoringDataDto.getAmount();
        for (int i = 1; i <= scoringDataDto.getTerm(); i++) {
            Integer number=i;
            LocalDate date = LocalDate.now().plusMonths(i);

            BigDecimal debtPayment = remainingDebt.multiply(rate).multiply(BigDecimal.valueOf(date.getDayOfMonth())).divide(BigDecimal.valueOf(36500), RoundingMode.HALF_UP);
            BigDecimal interestPayment = monthlyPayment.subtract(debtPayment);
            PaymentScheduleElementDto paymentScheduleElementDto = new PaymentScheduleElementDto(number, date, totalPayment, interestPayment, debtPayment, remainingDebt);
            paymentScheduleElements.add(paymentScheduleElementDto);
            remainingDebt=remainingDebt.subtract(monthlyPayment);
        }
        return paymentScheduleElements;
    }
}
