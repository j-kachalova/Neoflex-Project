package com.kachalova.calculator.controller;

import com.kachalova.calculator.dto.CreditDto;
import com.kachalova.calculator.dto.PaymentScheduleElementDto;
import com.kachalova.calculator.dto.ScoringDataDto;
import com.kachalova.calculator.exeptions.ScoringDataDtoValidationExc;
import com.kachalova.calculator.service.CreditPaymentsService;
import com.kachalova.calculator.service.ScoringService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
public class ScoringController {

    @Autowired
    private ScoringService scoringService;
    @Autowired
    private CreditPaymentsService creditPaymentsService;
    @Value("${app.keyRate}")
    BigDecimal keyRate;

    @ApiOperation(value = "get creditDTO", response = CreditDto.class)
    @PostMapping("/calculator/calc")
    public CreditDto getCredit(@RequestBody ScoringDataDto scoringDataDto) throws ScoringDataDtoValidationExc {
        log.info("Got ScoringDataDto{}", scoringDataDto.toString());
        log.info("keyRate: {}", keyRate);
        BigDecimal rate = keyRate;
        log.info("rate1: {}", rate);
        rate = scoringService.makeScoring(scoringDataDto, keyRate);
        log.info("rate2 {}", rate);
        BigDecimal monthlyPayment = creditPaymentsService.calculateMonthlyPayment(rate, scoringDataDto.getAmount(), scoringDataDto.getTerm());
        BigDecimal psk = creditPaymentsService.calculatePsk(monthlyPayment, scoringDataDto.getTerm());
        List<PaymentScheduleElementDto> paymentScheduleElementDtoList = creditPaymentsService.getPaymentSchedule(scoringDataDto, monthlyPayment, rate, LocalDate.now());
        CreditDto creditDto = new CreditDto(scoringDataDto.getAmount(), scoringDataDto.getTerm(), monthlyPayment, rate, psk, scoringDataDto.getIsInsuranceEnabled(), scoringDataDto.getIsSalaryClient(), paymentScheduleElementDtoList);
        log.info("Sent CreditDTO {}", creditDto.toString());
        return creditDto;
    }
}
