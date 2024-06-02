package com.kachalova.calculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kachalova.calculator.dto.EmploymentDto;
import com.kachalova.calculator.dto.ScoringDataDto;
import com.kachalova.calculator.exeptions.ScoringdataDtoValidationExc;
import com.kachalova.calculator.service.CreditPaymentsService;
import com.kachalova.calculator.service.ScoringService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.kachalova.calculator.enums.EmploymentStatus.EMPLOYED;
import static com.kachalova.calculator.enums.Gender.FEMALE;
import static com.kachalova.calculator.enums.MaritalStatus.MARRIED;
import static com.kachalova.calculator.enums.Position.TOP_MANAGER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class ScoringControllerTest {
    @Mock
    private ScoringService scoringService;
    @Mock
    private CreditPaymentsService creditPaymentsService;
    @InjectMocks
    private ScoringController scoringController;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(scoringController).build();
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }
    @Test
    public void scoringControllerTest() throws Exception {
        ScoringDataDto sd = new ScoringDataDto(BigDecimal.valueOf(300000),6,"test","test",
                "test",FEMALE, LocalDate.now().minusYears(40),"1234","123456",LocalDate.now(),"test",MARRIED,
                0,new EmploymentDto(EMPLOYED,"123",BigDecimal.valueOf(100000),TOP_MANAGER,20,20),"12312",true,true);
        String scoringJson = objectMapper.writeValueAsString(sd);
        mockMvc.perform(post("/calculator/calc").contentType(MediaType.APPLICATION_JSON)
                .content(scoringJson))
                .andExpect(status().isOk());

    }


}