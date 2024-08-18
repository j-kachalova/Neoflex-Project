package com.kachalova.deal.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kachalova.deal.dto.LoanOfferDto;
import com.kachalova.deal.dto.LoanStatementRequestDto;
import com.kachalova.deal.service.KafkaProducer;
import com.kachalova.deal.service.impl.FinishRegistrationImpl;
import com.kachalova.deal.service.impl.LoanOfferCalculationImpl;
import com.kachalova.deal.service.impl.LoanOfferSelectionImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DealController.class)
class DealControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private LoanOfferCalculationImpl loanOfferCalculation;
    @MockBean
    private LoanOfferSelectionImpl loanOfferSelection;
    @MockBean
    private FinishRegistrationImpl finishRegistration;
    @MockBean
    private KafkaProducer kafkaProducer;

    @Test
    public void calculateLoanOffer() throws Exception {
        LoanStatementRequestDto loanStatementRequestDto = LoanStatementRequestDto.builder()
                .amount(BigDecimal.valueOf(300000))
                .term(18)
                .firstName("test")
                .lastName("test")
                .middleName("test")
                .email("test@test.com")
                .birthdate(LocalDate.now().minusYears(40))
                .passportSeries("1234")
                .passportNumber("123456")
                .build();
        List<LoanOfferDto> loanOfferDtoList = new ArrayList<>();
        when(loanOfferCalculation.calculateLoanOffer(loanStatementRequestDto)).thenReturn(loanOfferDtoList);
        String statementsJson = objectMapper.writeValueAsString(loanStatementRequestDto);
        mockMvc.perform(post("/deal/statement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(statementsJson))
                .andExpect(status().isOk());
    }
}