package com.kachalova.calculator.service;

import com.kachalova.calculator.dto.LoanOfferDto;
import com.kachalova.calculator.dto.LoanStatementRequestDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {
    @InjectMocks
    private LoanService loanService;
    @Test
    public void generateLoanOffers(){
        LoanStatementRequestDto loanStatementRequestDto = new LoanStatementRequestDto(BigDecimal.valueOf(100000.0),
                18, "test", "test", "test", "test@test.com",
                LocalDate.now().minusYears(30), "1324", "984532");
        List<LoanOfferDto> loanOfferDtoList = loanService.generateLoanOffers(loanStatementRequestDto, BigDecimal.valueOf(16.0));
        assertEquals(BigDecimal.valueOf(16.0),loanOfferDtoList.get(0).getRate());
        assertEquals(BigDecimal.valueOf(15.0),loanOfferDtoList.get(1).getRate());
        assertEquals(BigDecimal.valueOf(13.0),loanOfferDtoList.get(2).getRate());
        assertEquals(BigDecimal.valueOf(12.0),loanOfferDtoList.get(3).getRate());
    }

}