package com.kachalova.calculator.service.impl;

import com.kachalova.calculator.dto.PaymentScheduleElementDto;
import com.kachalova.calculator.dto.ScoringDataDto;
import com.kachalova.calculator.service.CreditPaymentsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CreditPaymentsServiceImpl implements CreditPaymentsService {
    private static final BigDecimal PERCENT = BigDecimal.valueOf(100);
    private static final BigDecimal MONTHS_OF_THE_YEAR = BigDecimal.valueOf(12);
    private static final BigDecimal COEFFICIENT_ANNUITY_PAYMENT_FORMULA = BigDecimal.valueOf(1);
    private static final Integer SCALE = 7;
    private static final Integer END_SCALE = 2;

    @Override
    public BigDecimal calculateMonthlyPayment(BigDecimal rate, BigDecimal amount, Integer term) {
        log.info("request with rate: {}, amount: {}, term: {}", rate, amount, term);
        BigDecimal monthlyRate = rate.divide(PERCENT.multiply(MONTHS_OF_THE_YEAR), SCALE, RoundingMode.HALF_UP);
        BigDecimal partOfFormula = monthlyRate.add(COEFFICIENT_ANNUITY_PAYMENT_FORMULA).pow(term);
        BigDecimal annuityRate = monthlyRate.multiply(partOfFormula)
                .divide(partOfFormula.subtract(COEFFICIENT_ANNUITY_PAYMENT_FORMULA), SCALE, RoundingMode.HALF_UP);
        BigDecimal monthlyPayment = amount.multiply(annuityRate);
        log.info("monthlyPayment : {}", monthlyPayment.setScale(END_SCALE, RoundingMode.HALF_UP));
        return monthlyPayment.setScale(END_SCALE, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal calculatePsk(BigDecimal monthlyPayment, Integer term) {
        log.info("request with monthlyPayment: {}, term: {}", monthlyPayment, term);
        BigDecimal psk = monthlyPayment.multiply(BigDecimal.valueOf(term));
        log.info("psk: {}", psk);
        return psk;
    }

    @Override
    public List<PaymentScheduleElementDto> getPaymentSchedule(ScoringDataDto scoringDataDto, BigDecimal monthlyPayment, BigDecimal rate, LocalDate date) {
        log.info("request with scoringDataDto: {}, monthlyPayment: {}, rate: {}, date: {}",
                scoringDataDto, monthlyPayment, rate, date);
        List<PaymentScheduleElementDto> paymentScheduleElements = new ArrayList<>();
        PaymentScheduleElementDto paymentScheduleElementDto;
        BigDecimal remainingDebt = scoringDataDto.getAmount();
        LocalDate date1 = date.plusMonths(1);

        for (int number = 1; remainingDebt.compareTo(BigDecimal.ZERO) > 0; number++) {

            LocalDate date2 = date1.minusMonths(1);
            long daysDifference = date2.until(date1, ChronoUnit.DAYS);
            BigDecimal debtPayment = remainingDebt.multiply(rate)
                    .multiply(BigDecimal.valueOf(daysDifference))
                    .divide(BigDecimal.valueOf(date1.lengthOfYear())
                            .multiply(PERCENT), END_SCALE, RoundingMode.HALF_UP);
            BigDecimal interestPayment = monthlyPayment.subtract(debtPayment);
            remainingDebt = remainingDebt.subtract(interestPayment);
            if (remainingDebt.compareTo(BigDecimal.ZERO) < 0) {
                remainingDebt = BigDecimal.ZERO;
            }
            paymentScheduleElementDto = PaymentScheduleElementDto.builder()
                    .number(number)
                    .date(date1)
                    .totalPayment(monthlyPayment)
                    .interestPayment(interestPayment)
                    .debtPayment(debtPayment)
                    .remainingDebt(remainingDebt)
                    .build();

            paymentScheduleElements.add(paymentScheduleElementDto);
            date1 = date1.plusMonths(1);
            log.info("PaymentScheduleElementDto response: {}", paymentScheduleElementDto);
        }
        return paymentScheduleElements;
    }
}
