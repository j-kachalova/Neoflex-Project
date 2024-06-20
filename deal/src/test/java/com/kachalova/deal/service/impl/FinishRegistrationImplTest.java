package com.kachalova.deal.service.impl;

import com.kachalova.deal.dto.*;
import com.kachalova.deal.entities.Client;
import com.kachalova.deal.entities.Credit;
import com.kachalova.deal.entities.Statement;
import com.kachalova.deal.enums.MaritalStatus;
import com.kachalova.deal.mapper.*;
import com.kachalova.deal.repos.CreditRepo;
import com.kachalova.deal.repos.StatementRepo;
import com.kachalova.deal.service.ExternalService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static com.kachalova.deal.enums.ApplicationStatus.CC_APPROVED;
import static org.mockito.ArgumentMatchers.any;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static com.kachalova.deal.enums.EmploymentPosition.MID_MANAGER;
import static com.kachalova.deal.enums.EmploymentStatus.EMPLOYED;
import static com.kachalova.deal.enums.Gender.MALE;
import static com.kachalova.deal.enums.MaritalStatus.MARRIED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
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

    @Mock
    private ClientMapper clientMapper;

    @Mock
    private PassportDtoMapper passportDtoMapper;

    @InjectMocks
    private FinishRegistrationImpl finishRegistrationImpl;

    @Mock
    private RestTemplate restTemplate;
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
        when(scoringDataDtoMapper.toScoringDataDto(any(FinishRegistrationRequestDto.class),any(Statement.class)))
                .thenReturn(scoringDataDto);
        when(externalService.getResponse(any(ScoringDataDto.class), anyString(), any(Class.class)))
                .thenReturn(new ResponseEntity<>(creditDto, HttpStatus.OK));
        when(response.getBody()).thenReturn(creditDto);
        when(creditMapper.toEntity(any(CreditDto.class))).thenReturn(credit);

        finishRegistrationImpl.finishRegistration(finishRegistrationRequestDto, statementId);

        assertEquals(credit, statement.getCredit());
    }
}