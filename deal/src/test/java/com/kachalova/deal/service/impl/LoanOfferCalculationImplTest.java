package com.kachalova.deal.service.impl;

import com.kachalova.deal.dto.LoanOfferDto;
import com.kachalova.deal.dto.LoanStatementRequestDto;
import com.kachalova.deal.dto.RequestDto;
import com.kachalova.deal.entities.Client;
import com.kachalova.deal.entities.Statement;
import com.kachalova.deal.repos.ClientRepo;
import com.kachalova.deal.repos.StatementRepo;
import com.kachalova.deal.service.ExternalService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class LoanOfferCalculationImplTest {
    @Mock
    private ClientRepo clientRepo;
    @Mock
    private StatementRepo statementRepo;
    @Mock
    private ExternalService externalService;
    @InjectMocks
    private LoanOfferCalculationImpl loanOfferCalculation;

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
        Statement statement = Statement.builder()
                .id(UUID.randomUUID())
                .build();
        System.out.println("STATEMENT" + statement);
        LoanOfferDto[] loanOfferDtos = new LoanOfferDto[]{new LoanOfferDto(),
                new LoanOfferDto(),
                new LoanOfferDto(),
                new LoanOfferDto()};
        ResponseEntity<LoanOfferDto[]> response = new ResponseEntity<>(loanOfferDtos, HttpStatus.OK);
        when(clientRepo.findByEmail(any(String.class))).thenReturn(new Client());
        when(statementRepo.findByClient(any(Client.class))).thenReturn(statement);
        when(externalService.getResponse(any(RequestDto.class), any(String.class),
                eq(LoanOfferDto[].class))).thenReturn(response);
        List<LoanOfferDto> list = loanOfferCalculation.calculateLoanOffer(loanStatementRequestDto);
        assertNotNull(list.get(0).getStatementId());
        assertNotNull(list.get(1).getStatementId());
        assertNotNull(list.get(2).getStatementId());
        assertNotNull(list.get(3).getStatementId());
    }

}