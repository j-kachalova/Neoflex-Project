package com.kachalova.deal.service.impl;

import com.kachalova.deal.dto.CreditDto;
import com.kachalova.deal.dto.FinishRegistrationRequestDto;
import com.kachalova.deal.dto.LoanOfferDto;
import com.kachalova.deal.dto.ScoringDataDto;
import com.kachalova.deal.entities.Client;
import com.kachalova.deal.entities.Credit;
import com.kachalova.deal.entities.Statement;
import com.kachalova.deal.mapper.CreditMapper;
import com.kachalova.deal.mapper.ScoringDataDtoMapper;
import com.kachalova.deal.mapper.StatementMapper;
import com.kachalova.deal.mapper.StatementStatusHistoryDtoMapper;
import com.kachalova.deal.repos.CreditRepo;
import com.kachalova.deal.repos.StatementRepo;
import com.kachalova.deal.service.ExternalService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;


@SpringBootTest
class FinishRegistrationImplTest {

    @Mock
    private StatementRepo statementRepo;
    @Mock
    private StatementMapper statementMapper;

    @Mock
    private ScoringDataDtoMapper scoringDataDtoMapper;

    @Mock
    private ExternalService externalService;

    @Mock
    private CreditMapper creditMapper;

    @Mock
    private StatementStatusHistoryDtoMapper statementStatusHistoryDtoMapper;
    @Mock
    private CreditRepo creditRepo;


    @InjectMocks
    private FinishRegistrationImpl finishRegistrationImpl;

    @Mock
    private ResponseEntity<CreditDto> response;

    @Test
    void saveCredit_success() {
        UUID statementId = UUID.randomUUID();
        FinishRegistrationRequestDto finishRegistrationRequestDto = new FinishRegistrationRequestDto();
        Statement statement = new Statement();
        statement.setStatusHistory(new ArrayList<>());
        statement.setClient(new Client());
        statement.setAppliedOffer(new LoanOfferDto());
        ScoringDataDto scoringDataDto = new ScoringDataDto();
        CreditDto creditDto = new CreditDto();
        Credit credit = new Credit();

        when(statementRepo.findById(any(UUID.class))).thenReturn(statement);
        when(scoringDataDtoMapper.toScoringDataDto(any(FinishRegistrationRequestDto.class), any(Statement.class)))
                .thenReturn(scoringDataDto);
        when(externalService.getResponse(any(ScoringDataDto.class), anyString(), eq(CreditDto.class)))
                .thenReturn(new ResponseEntity<>(creditDto, HttpStatus.OK));
        when(response.getBody()).thenReturn(creditDto);
        when(creditMapper.toEntity(any(CreditDto.class))).thenReturn(credit);

        finishRegistrationImpl.finishRegistration(finishRegistrationRequestDto, statementId);

        assertEquals(credit, statement.getCredit());
    }
}