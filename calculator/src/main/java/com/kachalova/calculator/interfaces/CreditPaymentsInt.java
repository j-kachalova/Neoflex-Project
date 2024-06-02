package com.kachalova.calculator.interfaces;

import com.kachalova.calculator.dto.PaymentScheduleElementDto;
import com.kachalova.calculator.dto.ScoringDataDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface CreditPaymentsInt {
    BigDecimal calculateMonthlyPayment(BigDecimal rate, BigDecimal amount, Integer term);
    BigDecimal calculatePsk(BigDecimal monthlyPayment, Integer term);
    List<PaymentScheduleElementDto> getPaymentSchedule(ScoringDataDto scoringDataDto, BigDecimal monthlyPayment, BigDecimal rate, LocalDate date);
}
