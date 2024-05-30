package com.kachalova.calculator.interfaces;

import com.kachalova.calculator.dto.PaymentScheduleElementDto;
import com.kachalova.calculator.dto.ScoringDataDto;

import java.math.BigDecimal;
import java.util.List;

public interface CreditPaymentsInt {
    BigDecimal calculateMonthlyPayment(BigDecimal rate, BigDecimal amount, Integer term);
    BigDecimal calculatePsk(BigDecimal monthlyPayment, Integer term);
    List<PaymentScheduleElementDto> getPaymentSchedule(ScoringDataDto scoringDataDto, BigDecimal psk, BigDecimal monthlyPayment, BigDecimal rate);
}
