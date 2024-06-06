package com.kachalova.calculator.service;

import com.kachalova.calculator.dto.EmploymentDto;
import com.kachalova.calculator.dto.ScoringDataDto;
import com.kachalova.calculator.exeptions.ScoringDataDtoValidationExc;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.kachalova.calculator.enums.EmploymentStatus.EMPLOYED;
import static com.kachalova.calculator.enums.Gender.*;
import static com.kachalova.calculator.enums.MaritalStatus.DIVORCED;
import static com.kachalova.calculator.enums.MaritalStatus.MARRIED;
import static com.kachalova.calculator.enums.Position.MIDDLE_MANAGER;
import static com.kachalova.calculator.enums.Position.TOP_MANAGER;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class ScoringServiceTest {
    @Autowired
    private ScoringService calculationCredit;

    @Test
    public void makeScoring_whenGenderFemaleMarried() {
        EmploymentDto employmentDto = EmploymentDto.builder()
                .employmentStatus(EMPLOYED)
                .employerINN("123")
                .salary(BigDecimal.valueOf(100000))
                .position(TOP_MANAGER)
                .workExperienceTotal(20)
                .workExperienceCurrent(20)
                .build();
        ScoringDataDto scoringDataDTO = ScoringDataDto.builder()
                .amount(BigDecimal.valueOf(300000))
                .term(6)
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
        BigDecimal actual = calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0));
        log.debug("rate when gender female married {}", actual);
        assertEquals(BigDecimal.valueOf(6.0), actual);
    }

    @Test
    public void makeScoring_whenGenderMale() {
        EmploymentDto employmentDto = EmploymentDto.builder()
                .employmentStatus(EMPLOYED)
                .employerINN("123")
                .salary(BigDecimal.valueOf(100000))
                .position(TOP_MANAGER)
                .workExperienceTotal(20)
                .workExperienceCurrent(20)
                .build();
        ScoringDataDto scoringDataDTO = ScoringDataDto.builder()
                .amount(BigDecimal.valueOf(300000))
                .term(6)
                .firstName("test")
                .lastName("test")
                .middleName("test")
                .gender(MALE)
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
        BigDecimal actual = calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0));
        log.debug("rate when gender male {}", actual);
        assertEquals(BigDecimal.valueOf(3.0), actual);
    }

    @Test
    public void makeScoring_whenGenderNonBinary() {
        EmploymentDto employmentDto = EmploymentDto.builder()
                .employmentStatus(EMPLOYED)
                .employerINN("123")
                .salary(BigDecimal.valueOf(100000))
                .position(TOP_MANAGER)
                .workExperienceTotal(20)
                .workExperienceCurrent(20)
                .build();
        ScoringDataDto scoringDataDTO = ScoringDataDto.builder()
                .amount(BigDecimal.valueOf(300000))
                .term(6)
                .firstName("test")
                .lastName("test")
                .middleName("test")
                .gender(NON_BINARY)
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
        BigDecimal actual = calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0));
        log.debug("rate when gender nonBinary {}", actual);
        assertEquals(BigDecimal.valueOf(13.0), actual);
    }

    @Test
    public void makeScoring_whenMaritalStatusDivorced() {
        EmploymentDto employmentDto = EmploymentDto.builder()
                .employmentStatus(EMPLOYED)
                .employerINN("123")
                .salary(BigDecimal.valueOf(100000))
                .position(TOP_MANAGER)
                .workExperienceTotal(20)
                .workExperienceCurrent(20)
                .build();
        ScoringDataDto scoringDataDTO = ScoringDataDto.builder()
                .amount(BigDecimal.valueOf(300000))
                .term(6)
                .firstName("test")
                .lastName("test")
                .middleName("test")
                .gender(MALE)
                .birthdate(LocalDate.now().minusYears(30))
                .passportSeries("1234")
                .passportNumber("123456")
                .passportIssueDate(LocalDate.now())
                .maritalStatus(DIVORCED)
                .employment(employmentDto)
                .accountNumber("12312")
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();

        BigDecimal actual = calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0));
        log.debug("rate when marital status divorced {}", actual);
        assertEquals(BigDecimal.valueOf(7.0), actual);
    }

    @Test
    public void makeScoring_whenGenderFemaleAge40() {
        EmploymentDto employmentDto = EmploymentDto.builder()
                .employmentStatus(EMPLOYED)
                .employerINN("123")
                .salary(BigDecimal.valueOf(100000))
                .position(TOP_MANAGER)
                .workExperienceTotal(20)
                .workExperienceCurrent(20)
                .build();
        ScoringDataDto scoringDataDTO = ScoringDataDto.builder()
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
        BigDecimal actual = calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0));
        log.debug("rate when gender female age 40 {}", actual);
        assertEquals(BigDecimal.valueOf(3.0), actual);
    }

    @Test
    public void makeScoring_whenPositionMiddleManager() {
        EmploymentDto employmentDto = EmploymentDto.builder()
                .employmentStatus(EMPLOYED)
                .employerINN("123")
                .salary(BigDecimal.valueOf(100000))
                .position(MIDDLE_MANAGER)
                .workExperienceTotal(20)
                .workExperienceCurrent(20)
                .build();
        ScoringDataDto scoringDataDTO = ScoringDataDto.builder()
                .amount(BigDecimal.valueOf(300000))
                .term(6)
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
        BigDecimal actual = calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0));
        log.debug("rate when position middle manager {}", actual);
        assertEquals(BigDecimal.valueOf(7.0), actual);
    }

    @Test
    public void makeScoring_whenIsInsuranceEnabledFalse() {
        EmploymentDto employmentDto = EmploymentDto.builder()
                .employmentStatus(EMPLOYED)
                .employerINN("123")
                .salary(BigDecimal.valueOf(100000))
                .position(MIDDLE_MANAGER)
                .workExperienceTotal(20)
                .workExperienceCurrent(20)
                .build();
        ScoringDataDto scoringDataDTO = ScoringDataDto.builder()
                .amount(BigDecimal.valueOf(300000))
                .term(6)
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
                .isInsuranceEnabled(false)
                .isSalaryClient(true)
                .build();
        BigDecimal actual = calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0));
        log.debug("rate when IsInsuranceEnabled false {}", actual);
        assertEquals(BigDecimal.valueOf(10.0), actual);
    }

    @Test
    public void makeScoring_whenIsSalaryClientFalse() {
        EmploymentDto employmentDto = EmploymentDto.builder()
                .employmentStatus(EMPLOYED)
                .employerINN("123")
                .salary(BigDecimal.valueOf(100000))
                .position(MIDDLE_MANAGER)
                .workExperienceTotal(20)
                .workExperienceCurrent(20)
                .build();
        ScoringDataDto scoringDataDTO = ScoringDataDto.builder()
                .amount(BigDecimal.valueOf(300000))
                .term(6)
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
                .isSalaryClient(false)
                .build();
        BigDecimal actual = calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0));
        log.debug("rate when IsSalaryClient false {}", actual);
        assertEquals(BigDecimal.valueOf(8.0), actual);
    }

    @Test
    public void makeScoring_whenAge18() {
        EmploymentDto employmentDto = EmploymentDto.builder()
                .employmentStatus(EMPLOYED)
                .employerINN("123")
                .salary(BigDecimal.valueOf(100000))
                .position(MIDDLE_MANAGER)
                .workExperienceTotal(20)
                .workExperienceCurrent(20)
                .build();
        ScoringDataDto scoringDataDTO = ScoringDataDto.builder()
                .amount(BigDecimal.valueOf(300000))
                .term(6)
                .firstName("test")
                .lastName("test")
                .middleName("test")
                .gender(FEMALE)
                .birthdate(LocalDate.now().minusYears(18))
                .passportSeries("1234")
                .passportNumber("123456")
                .passportIssueDate(LocalDate.now())
                .maritalStatus(MARRIED)
                .employment(employmentDto)
                .accountNumber("12312")
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();
        Exception exception = assertThrows(ScoringDataDtoValidationExc.class,
                () -> calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0)));

        String expectedMessage = "DECLINED: Age should be between 20 and 65";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void makeScoring_whenAge66() {
        EmploymentDto employmentDto = EmploymentDto.builder()
                .employmentStatus(EMPLOYED)
                .employerINN("123")
                .salary(BigDecimal.valueOf(100000))
                .position(MIDDLE_MANAGER)
                .workExperienceTotal(20)
                .workExperienceCurrent(20)
                .build();
        ScoringDataDto scoringDataDTO = ScoringDataDto.builder()
                .amount(BigDecimal.valueOf(300000))
                .term(6)
                .firstName("test")
                .lastName("test")
                .middleName("test")
                .gender(FEMALE)
                .birthdate(LocalDate.now().minusYears(66))
                .passportSeries("1234")
                .passportNumber("123456")
                .passportIssueDate(LocalDate.now())
                .maritalStatus(MARRIED)
                .employment(employmentDto)
                .accountNumber("12312")
                .isInsuranceEnabled(true)
                .isSalaryClient(true)
                .build();
        Exception exception = assertThrows(ScoringDataDtoValidationExc.class,
                () -> calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0)));

        String expectedMessage = "DECLINED: Age should be between 20 and 65";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

}