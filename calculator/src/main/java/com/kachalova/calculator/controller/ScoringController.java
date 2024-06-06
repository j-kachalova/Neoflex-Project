package com.kachalova.calculator.controller;

import com.kachalova.calculator.config.PropertiesConfig;
import com.kachalova.calculator.dto.CreditDto;
import com.kachalova.calculator.dto.PaymentScheduleElementDto;
import com.kachalova.calculator.dto.ScoringDataDto;
import com.kachalova.calculator.exeptions.ScoringDataDtoValidationExc;
import com.kachalova.calculator.service.CreditPaymentsService;
import com.kachalova.calculator.service.ScoringService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ScoringController {
    private final PropertiesConfig properties;
    private final ScoringService scoringService;
    private final CreditPaymentsService creditPaymentsService;

    @ApiOperation(value = "get creditDTO", response = CreditDto.class)
    @PostMapping("/calculator/calc")
    public CreditDto getCredit(@RequestBody ScoringDataDto scoringDataDto) throws ScoringDataDtoValidationExc {
        log.info("ScoringDataDto request: {}", scoringDataDto);
        log.debug("keyRate: {}", properties.getKeyRate());
        BigDecimal rate = properties.getKeyRate();
        log.debug("rate after getKeyRate: {}", rate);
        rate = scoringService.makeScoring(scoringDataDto, rate);
        log.debug("rate after makeScoring: {}", rate);
        BigDecimal monthlyPayment = creditPaymentsService.calculateMonthlyPayment(rate, scoringDataDto.getAmount(), scoringDataDto.getTerm());
        BigDecimal psk = creditPaymentsService.calculatePsk(monthlyPayment, scoringDataDto.getTerm());
        List<PaymentScheduleElementDto> paymentScheduleElementDtoList = creditPaymentsService.getPaymentSchedule(scoringDataDto, monthlyPayment, rate, LocalDate.now());
        CreditDto creditDto = CreditDto.builder()
                .amount(scoringDataDto.getAmount())
                .term(scoringDataDto.getTerm())
                .monthlyPayment(monthlyPayment)
                .rate(rate)
                .psk(psk)
                .isInsuranceEnabled(scoringDataDto.getIsInsuranceEnabled())
                .isSalaryClient(scoringDataDto.getIsSalaryClient())
                .paymentSchedule(paymentScheduleElementDtoList)
                .build();
        log.info("CreditDTO response: {}", creditDto);
        return creditDto;
    }
}
