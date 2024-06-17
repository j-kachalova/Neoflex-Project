package com.kachalova.deal.service.impl;

import com.kachalova.deal.dto.LoanOfferDto;
import com.kachalova.deal.entities.Statement;
import com.kachalova.deal.enums.ApplicationStatus;
import com.kachalova.deal.repos.StatementRepo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class LoanOfferSelectionImplTest {
    @Mock
    private StatementRepo statementRepo;
    @InjectMocks
    private LoanOfferSelectionImpl loanOfferSelection;

    @Test
    public void selectOffer() {
        Statement statement = Statement.builder()
                .statusHistory(new ArrayList<>())
                .build();

        LoanOfferDto loanOfferDto = LoanOfferDto.builder()
                .statementId(statement.getId())
                .requestedAmount(BigDecimal.valueOf(3000000))
                .totalAmount(BigDecimal.valueOf(3394245.60))
                .term(18)
                .monthlyPayment(BigDecimal.valueOf(188569.20))
                .rate(BigDecimal.valueOf(16.0))
                .isInsuranceEnabled(false)
                .isSalaryClient(false)
                .build();
        when(statementRepo.findById(statement.getId())).thenReturn(statement);
        loanOfferSelection.selectOffer(loanOfferDto);
        assertEquals(ApplicationStatus.APPROVED, statement.getStatus());
        assertEquals(loanOfferDto, statement.getAppliedOffer());
    }
}