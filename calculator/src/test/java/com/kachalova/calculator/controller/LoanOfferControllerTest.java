package com.kachalova.calculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kachalova.calculator.dto.LoanStatementRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class LoanOfferControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void loanControllerTest() throws Exception {
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
        String statementsJson = objectMapper.writeValueAsString(loanStatementRequestDto);
        mockMvc.perform(post("/calculator/offers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(statementsJson))
                .andExpect(status().isOk());
    }
}