package com.kachalova.calculator.service;

import com.kachalova.calculator.dto.LoanOfferDto;
import com.kachalova.calculator.dto.LoanStatementRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class LoanServiceTest {
    @Autowired
    private LoanService loanService;

    @Test
    public void generateLoanOffers() {
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
        List<LoanOfferDto> loanOfferDtoList = loanService.generateLoanOffers(loanStatementRequestDto, BigDecimal.valueOf(16.0));
        assertEquals(BigDecimal.valueOf(16.0), loanOfferDtoList.get(0).getRate());
        assertEquals(BigDecimal.valueOf(15.0), loanOfferDtoList.get(1).getRate());
        assertEquals(BigDecimal.valueOf(13.0), loanOfferDtoList.get(2).getRate());
        assertEquals(BigDecimal.valueOf(12.0), loanOfferDtoList.get(3).getRate());
    }

}