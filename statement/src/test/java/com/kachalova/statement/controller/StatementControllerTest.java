package com.kachalova.statement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kachalova.statement.dto.LoanOfferDto;
import com.kachalova.statement.dto.LoanStatementRequestDto;
import com.kachalova.statement.service.Prescoring;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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


@AutoConfigureMockMvc
@SpringBootTest
class StatementControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private Prescoring prescoring;

    @Test
    public void makePrescoring() throws Exception {

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
        when(prescoring.sendLoanStatementRequestDto(loanStatementRequestDto)).thenReturn(loanOfferDtoList);
        String statementsJson = objectMapper.writeValueAsString(loanStatementRequestDto);
        mockMvc.perform(post("/statement")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(statementsJson))
                .andExpect(status().isOk());
    }
}
