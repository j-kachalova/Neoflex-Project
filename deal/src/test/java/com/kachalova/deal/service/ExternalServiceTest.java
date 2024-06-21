package com.kachalova.deal.service;

import com.kachalova.deal.dto.CreditDto;
import com.kachalova.deal.dto.EmploymentDto;
import com.kachalova.deal.dto.ScoringDataDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.kachalova.deal.enums.EmploymentPosition.MID_MANAGER;
import static com.kachalova.deal.enums.EmploymentStatus.EMPLOYED;
import static com.kachalova.deal.enums.Gender.FEMALE;
import static com.kachalova.deal.enums.MaritalStatus.MARRIED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ExternalServiceTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ExternalService externalService;

    @Test
    void getResponse_whenSuccess() {
        EmploymentDto employmentDto = EmploymentDto.builder()
                .employmentStatus(EMPLOYED)
                .employerINN("123")
                .salary(BigDecimal.valueOf(100000))
                .position(MID_MANAGER)
                .workExperienceTotal(20)
                .workExperienceCurrent(20)
                .build();
        ScoringDataDto requestDto = ScoringDataDto.builder()
                .amount(BigDecimal.valueOf(300000))
                .term(18)
                .firstName("test")
                .lastName("test")
                .middleName("test")
                .gender(FEMALE)
                .birthdate(LocalDate.now().minusYears(30))
                .passportSeries("1234")
                .passportNumber("123456")
                .passportIssueDate(LocalDate.now())
                .maritalStatus(MARRIED)
                .employment(employmentDto)
                .accountNumber("12312")
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();
        String url = "http://localhost:8080/calculator/calc";
        ResponseEntity<CreditDto> responseEntity = new ResponseEntity<>(new CreditDto(), HttpStatus.OK);

        when(restTemplate.postForEntity(any(String.class), any(HttpEntity.class), eq(CreditDto.class))).thenReturn(responseEntity);

        ResponseEntity<CreditDto> response = externalService.getResponse(requestDto, url, CreditDto.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(CreditDto.class, response.getBody().getClass());
    }
}