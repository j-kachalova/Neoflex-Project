package com.kachalova.deal.service.impl;

import com.kachalova.deal.dto.LoanOfferDto;
import com.kachalova.deal.dto.LoanStatementRequestDto;
import com.kachalova.deal.service.LoanOfferCalculation;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
class LoanOfferCalculationImplTest {
    @Autowired
    private LoanOfferCalculation loanOfferCalculation;

    @Test
    public void calculateLoanOffer() {
        LoanStatementRequestDto loanStatementRequestDto = LoanStatementRequestDto.builder()
                .amount(BigDecimal.valueOf(100000))
                .term(18)
                .firstName("test")
                .lastName("test")
                .middleName("test")
                .email("test@test.com")
                .birthdate(LocalDate.now().minusYears(30))
                .passportSeries("1234")
                .passportNumber("123456")
                .build();
        List<LoanOfferDto> list = loanOfferCalculation.calculateLoanOffer(loanStatementRequestDto);
        assertNotNull(list.get(0).getStatementId());
        assertNotNull(list.get(1).getStatementId());
        assertNotNull(list.get(2).getStatementId());
        assertNotNull(list.get(3).getStatementId());
    }

}