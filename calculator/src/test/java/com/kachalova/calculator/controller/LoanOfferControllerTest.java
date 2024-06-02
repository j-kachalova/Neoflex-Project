package com.kachalova.calculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kachalova.calculator.config.Properties;
import com.kachalova.calculator.dto.LoanStatementRequestDto;
import com.kachalova.calculator.service.CreditPaymentsService;
import com.kachalova.calculator.service.LoanService;
import com.kachalova.calculator.service.ScoringService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LoanOfferControllerTest {
    @Mock
    private LoanService loanService;
    @Mock
    private ScoringService scoringService;
    @Mock
    private CreditPaymentsService creditPaymentsService;
    @InjectMocks
    private LoanOfferController loanOfferController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(loanOfferController).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();

    }
  @Test
  public void loanControllerTest() throws Exception {
      LoanStatementRequestDto loanStatementRequestDto = new LoanStatementRequestDto(BigDecimal.valueOf(300000),
              18,"test","test","test","test@test.com",
              LocalDate.now().minusYears(40),"1234","123456");
      String statementsJson = objectMapper.writeValueAsString(loanStatementRequestDto);
      mockMvc.perform(post("/calculator/offers").contentType(MediaType.APPLICATION_JSON)
                      .content(statementsJson))
              .andExpect(status().isOk());
  }
}