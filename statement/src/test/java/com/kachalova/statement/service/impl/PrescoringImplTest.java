package com.kachalova.statement.service.impl;

import com.kachalova.statement.client.DealClient;
import com.kachalova.statement.dto.LoanOfferDto;
import com.kachalova.statement.dto.LoanStatementRequestDto;
import com.kachalova.statement.exceptions.ExternalServiceException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class PrescoringImplTest {
    @Mock
    private DealClient dealClient;
    @InjectMocks
    private PrescoringImpl prescoring;

    @Test
    public void sendLoanStatementRequestDto_whenStatusOK() {
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
        LoanOfferDto[] loanOfferDtos = new LoanOfferDto[]{new LoanOfferDto(),
                new LoanOfferDto(),
                new LoanOfferDto(),
                new LoanOfferDto()};
        ResponseEntity<LoanOfferDto[]> response = new ResponseEntity<>(loanOfferDtos, HttpStatus.OK);
        when(dealClient.prescoring(any(LoanStatementRequestDto.class))).thenReturn(response);
        List<LoanOfferDto> list = prescoring.sendLoanStatementRequestDto(loanStatementRequestDto);
        assertNotNull(list.get(0));
        assertNotNull(list.get(1));
        assertNotNull(list.get(2));
        assertNotNull(list.get(3));
    }

    @Test
    public void sendLoanStatementRequestDto_whenStatusNoContent() {
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
        LoanOfferDto[] loanOfferDtos = new LoanOfferDto[]{};
        ResponseEntity<LoanOfferDto[]> response = new ResponseEntity<>(loanOfferDtos, HttpStatus.OK);
        when(dealClient.prescoring(any(LoanStatementRequestDto.class))).thenReturn(response);
        Exception exception = assertThrows(ExternalServiceException.class,
                () -> prescoring.sendLoanStatementRequestDto(loanStatementRequestDto));

        String expectedMessage = "LoanOfferDto[] from external service is null or length == 0";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }
}