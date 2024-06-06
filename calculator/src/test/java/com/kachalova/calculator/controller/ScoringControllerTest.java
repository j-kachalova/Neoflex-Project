package com.kachalova.calculator.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kachalova.calculator.dto.EmploymentDto;
import com.kachalova.calculator.dto.ScoringDataDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.kachalova.calculator.enums.EmploymentStatus.EMPLOYED;
import static com.kachalova.calculator.enums.Gender.FEMALE;
import static com.kachalova.calculator.enums.MaritalStatus.MARRIED;
import static com.kachalova.calculator.enums.Position.TOP_MANAGER;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ScoringControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void scoringControllerTest() throws Exception {
        EmploymentDto employmentDto = EmploymentDto.builder()
                .employmentStatus(EMPLOYED)
                .employerINN("123")
                .salary(BigDecimal.valueOf(100000))
                .position(TOP_MANAGER)
                .workExperienceTotal(20)
                .workExperienceCurrent(20)
                .build();
        ScoringDataDto sd = ScoringDataDto.builder()
                .amount(BigDecimal.valueOf(300000))
                .term(6)
                .firstName("test")
                .lastName("test")
                .middleName("test")
                .gender(FEMALE)
                .birthdate(LocalDate.now().minusYears(40))
                .passportSeries("1234")
                .passportNumber("123456")
                .passportIssueDate(LocalDate.now())
                .maritalStatus(MARRIED)
                .employment(employmentDto)
                .accountNumber("12312")
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();
        String scoringJson = objectMapper.writeValueAsString(sd);
        mockMvc.perform(post("/calculator/calc")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(scoringJson))
                .andExpect(status().isOk());

    }


}