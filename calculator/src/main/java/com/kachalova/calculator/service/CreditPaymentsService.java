package com.kachalova.calculator.service;

import com.kachalova.calculator.dto.PaymentScheduleElementDto;
import com.kachalova.calculator.dto.ScoringDataDto;
import com.kachalova.calculator.exeptions.ScoringdataDtoValidationExc;
import com.kachalova.calculator.interfaces.CreditPaymentsInt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Service
public class CreditPaymentsService implements CreditPaymentsInt {
    static Logger log = Logger.getLogger(CreditPaymentsService.class.getName());

    @Override
    public BigDecimal calculateMonthlyPayment(BigDecimal rate, BigDecimal amount, Integer term) {

        BigDecimal monthlyRate = rate.divide(BigDecimal.valueOf(1200),7, RoundingMode. HALF_UP);
        BigDecimal x = monthlyRate.add(BigDecimal.valueOf(1)).pow(term);
        BigDecimal annuityRate = monthlyRate.multiply(x).divide(x.subtract(BigDecimal.valueOf(1)), 7, RoundingMode.HALF_UP);
        BigDecimal monthlyPayment = amount.multiply(annuityRate);
        log.info("Monthly payment : " + monthlyPayment.setScale(2, RoundingMode.HALF_UP));
        return monthlyPayment.setScale(2, RoundingMode.HALF_UP);
    }
    @Override
    public BigDecimal calculatePsk(BigDecimal monthlyPayment, Integer term){
        BigDecimal psk = monthlyPayment.multiply(BigDecimal.valueOf(term));
        log.info("psk calculation" +psk.toString());
        return psk;
    }
    @Override
    public List<PaymentScheduleElementDto> getPaymentSchedule(ScoringDataDto scoringDataDto, BigDecimal monthlyPayment, BigDecimal rate, LocalDate date) {

        List<PaymentScheduleElementDto> paymentScheduleElements = new ArrayList<>();
        PaymentScheduleElementDto paymentScheduleElementDto;
        BigDecimal remainingDebt=scoringDataDto.getAmount();
        LocalDate date1 = date.plusMonths(1);

        for(int i = 1;remainingDebt.compareTo(BigDecimal.ZERO)>0;i++){
            Integer number=i;
            LocalDate date2 = date1.minusMonths(1);
            long daysDifference = date2.until(date1, ChronoUnit.DAYS);
            BigDecimal debtPayment = remainingDebt.multiply(rate).multiply(BigDecimal.valueOf(daysDifference*0.01)).divide(BigDecimal.valueOf(date1.lengthOfYear()), 2, RoundingMode.HALF_UP);
            BigDecimal interestPayment = monthlyPayment.subtract(debtPayment);
            remainingDebt=remainingDebt.subtract(interestPayment);
            if(remainingDebt.compareTo(BigDecimal.ZERO)<0) remainingDebt = BigDecimal.ZERO;
            paymentScheduleElementDto = new PaymentScheduleElementDto(number, date1, monthlyPayment, interestPayment, debtPayment, remainingDebt);
            paymentScheduleElements.add(paymentScheduleElementDto);
            date1 = date1.plusMonths(1);
            log.info("paymentScheduleElementDto " + paymentScheduleElementDto);
        }
        return paymentScheduleElements;
    }
}
