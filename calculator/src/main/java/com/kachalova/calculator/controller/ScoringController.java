package com.kachalova.calculator.controller;

import com.kachalova.calculator.dto.CreditDto;
import com.kachalova.calculator.dto.PaymentScheduleElementDto;
import com.kachalova.calculator.dto.ScoringDataDto;
import com.kachalova.calculator.exeptions.ScoringdataDtoValidationExc;
import com.kachalova.calculator.service.CreditPaymentsService;
import com.kachalova.calculator.service.ScoringService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@RestController
public class ScoringController {

    @Autowired
    private ScoringService scoringService;
    @Autowired
    private CreditPaymentsService creditPaymentsService;
    static Logger log = Logger.getLogger(ScoringController.class.getName());
    @Value("${app.keyRate}")
    BigDecimal keyRate;
    @ApiOperation(value = "get creditDTO", response = CreditDto.class)
    @PostMapping("/calculator/calc")
    public CreditDto getCredit(@RequestBody ScoringDataDto scoringDataDto) throws ScoringdataDtoValidationExc {
        log.info("Got ScoringDataDto" + scoringDataDto.getFirstName() + " " + scoringDataDto.getLastName() + " amount:" + scoringDataDto.getAmount() + " birthday :" + scoringDataDto.getBirthdate()
                + " term :" + scoringDataDto.getTerm() + "dependent amount" + scoringDataDto.getDependentAmount() + "gender :" + scoringDataDto.getGender()
                + " marital status" + scoringDataDto.getMaritalStatus());
        BigDecimal rate = scoringService.makeScoring(scoringDataDto, keyRate);
        log.info("Got rate" + rate);
        BigDecimal monthlyPayment = creditPaymentsService.calculateMonthlyPayment(rate,scoringDataDto.getAmount(),scoringDataDto.getTerm());
        BigDecimal psk = creditPaymentsService.calculatePsk(monthlyPayment, scoringDataDto.getTerm());
        List<PaymentScheduleElementDto> paymentScheduleElementDtoList = creditPaymentsService.getPaymentSchedule(scoringDataDto,monthlyPayment, rate, LocalDate.now());
        CreditDto creditDto = new CreditDto(scoringDataDto.getAmount(), scoringDataDto.getTerm(), monthlyPayment, rate, psk, scoringDataDto.getIsInsuranceEnabled(),scoringDataDto.getIsSalaryClient(), paymentScheduleElementDtoList);

        log.info("Sended CreditDTO" + creditDto.toString());
        return creditDto;
    }
}
