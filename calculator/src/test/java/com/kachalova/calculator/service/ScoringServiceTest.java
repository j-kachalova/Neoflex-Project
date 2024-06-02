package com.kachalova.calculator.service;

import com.kachalova.calculator.dto.EmploymentDto;
import com.kachalova.calculator.dto.ScoringDataDto;
import com.kachalova.calculator.enums.EmploymentStatus;
import com.kachalova.calculator.exeptions.ScoringdataDtoValidationExc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;


import java.math.BigDecimal;
import java.time.LocalDate;

import static com.kachalova.calculator.enums.EmploymentStatus.EMPLOYED;
import static com.kachalova.calculator.enums.Gender.*;
import static com.kachalova.calculator.enums.MaritalStatus.DIVORCED;
import static com.kachalova.calculator.enums.MaritalStatus.MARRIED;
import static com.kachalova.calculator.enums.Position.MIDDLE_MANAGER;
import static com.kachalova.calculator.enums.Position.TOP_MANAGER;
import static org.junit.jupiter.api.Assertions.*;

public class ScoringServiceTest {
    private ScoringService calculationCredit;
    @BeforeEach
    void setUp() {
        calculationCredit = new ScoringService();
    }

    @Test
    public void makeScoring_whenGenderFemaleMarried() throws ScoringdataDtoValidationExc {

        ScoringDataDto scoringDataDTO = new ScoringDataDto(BigDecimal.valueOf(300000),6,"test","test",
                "test",FEMALE, LocalDate.now().minusYears(30),"1234","123456",LocalDate.now(),"test",MARRIED,
                0,new EmploymentDto(EMPLOYED,"123",BigDecimal.valueOf(100000),TOP_MANAGER,20,20),"12312",true,true);
        BigDecimal actual = calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0));
        System.out.println(actual);
        assertEquals(BigDecimal.valueOf(6.0),actual);
    }
    @Test
    public void makeScoring_whenGenderMale() throws ScoringdataDtoValidationExc {
        ScoringDataDto scoringDataDTO = new ScoringDataDto(BigDecimal.valueOf(300000),6,"test","test",
                "test",MALE, LocalDate.now().minusYears(30),"1234","123456",LocalDate.now(),"test",MARRIED,
                0,new EmploymentDto(EMPLOYED,"123",BigDecimal.valueOf(100000),TOP_MANAGER,20,20),"12312",true,true);
        BigDecimal actual = calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0));
        System.out.println(actual);
        assertEquals(BigDecimal.valueOf(3.0),actual);
    }
    @Test
    public void makeScoring_whenGenderNonbinary() throws ScoringdataDtoValidationExc {
        ScoringDataDto scoringDataDTO = new ScoringDataDto(BigDecimal.valueOf(300000),6,"test","test",
                "test",NON_BINARY, LocalDate.now().minusYears(30),"1234","123456",LocalDate.now(),"test",MARRIED,
                0,new EmploymentDto(EMPLOYED,"123",BigDecimal.valueOf(100000),TOP_MANAGER,20,20),"12312",true,true);
        BigDecimal actual = calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0));
        System.out.println(actual);
        assertEquals(BigDecimal.valueOf(13.0),actual);
    }
    @Test
    public void makeScoring_whenMaritalStatusDivorced() throws ScoringdataDtoValidationExc {
        ScoringDataDto scoringDataDTO = new ScoringDataDto(BigDecimal.valueOf(300000),6,"test","test",
                "test",MALE, LocalDate.now().minusYears(30),"1234","123456",LocalDate.now(),"test",DIVORCED,
                0,new EmploymentDto(EMPLOYED,"123",BigDecimal.valueOf(100000),TOP_MANAGER,20,20),"12312",true,true);
        BigDecimal actual = calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0));
        System.out.println(actual);
        assertEquals(BigDecimal.valueOf(7.0),actual);
    }
    @Test
    public void makeScoring_whenGenderFemaleAge40() throws ScoringdataDtoValidationExc {
        ScoringDataDto scoringDataDTO = new ScoringDataDto(BigDecimal.valueOf(300000),6,"test","test",
                "test",FEMALE, LocalDate.now().minusYears(40),"1234","123456",LocalDate.now(),"test",MARRIED,
                0,new EmploymentDto(EMPLOYED,"123",BigDecimal.valueOf(100000),TOP_MANAGER,20,20),"12312",true,true);
        BigDecimal actual = calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0));
        System.out.println(actual);
        assertEquals(BigDecimal.valueOf(3.0),actual);
    }
    @Test
    public void makeScoring_whenPositionMiddleManager() throws ScoringdataDtoValidationExc {
        ScoringDataDto scoringDataDTO = new ScoringDataDto(BigDecimal.valueOf(300000),6,"test","test",
                "test",FEMALE, LocalDate.now().minusYears(30),"1234","123456",LocalDate.now(),"test",MARRIED,
                0,new EmploymentDto(EMPLOYED,"123",BigDecimal.valueOf(100000),MIDDLE_MANAGER,20,20),"12312",true,true);
        BigDecimal actual = calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0));
        System.out.println(actual);
        assertEquals(BigDecimal.valueOf(7.0),actual);
    }
    @Test
    public void makeScoring_whenIsInsuranceEnabledFalse() throws ScoringdataDtoValidationExc {
        ScoringDataDto scoringDataDTO = new ScoringDataDto(BigDecimal.valueOf(300000),6,"test","test",
                "test",FEMALE, LocalDate.now().minusYears(30),"1234","123456",LocalDate.now(),"test",MARRIED,
                0,new EmploymentDto(EMPLOYED,"123",BigDecimal.valueOf(100000),MIDDLE_MANAGER,20,20),"12312",false,true);
        BigDecimal actual = calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0));
        System.out.println(actual);
        assertEquals(BigDecimal.valueOf(10.0),actual);
    }
    @Test
    public void makeScoring_whenIsSalaryClientFalse() throws ScoringdataDtoValidationExc {
        ScoringDataDto scoringDataDTO = new ScoringDataDto(BigDecimal.valueOf(300000),6,"test","test",
                "test",FEMALE, LocalDate.now().minusYears(30),"1234","123456",LocalDate.now(),"test",MARRIED,
                0,new EmploymentDto(EMPLOYED,"123",BigDecimal.valueOf(100000),MIDDLE_MANAGER,20,20),"12312",true,false);
        BigDecimal actual = calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0));
        System.out.println(actual);
        assertEquals(BigDecimal.valueOf(8.0),actual);
    }
    @Test
    public void makeScoring_whenAge18() {
        ScoringDataDto scoringDataDTO = new ScoringDataDto(BigDecimal.valueOf(300000),6,"test","test",
                "test",FEMALE, LocalDate.now().minusYears(18),"1234","123456",LocalDate.now(),"test",MARRIED,
                0,new EmploymentDto(EMPLOYED,"123",BigDecimal.valueOf(100000),MIDDLE_MANAGER,20,20),"12312",true,true);
        Exception exception = assertThrows(ScoringdataDtoValidationExc.class, () -> {
            calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0));
        });

        String expectedMessage = "DECLINED :Age should be between 20 and 65";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));


    }
    @Test
    public void makeScoring_whenAge66() {
        ScoringDataDto scoringDataDTO = new ScoringDataDto(BigDecimal.valueOf(300000),6,"test","test",
                "test",FEMALE, LocalDate.now().minusYears(66),"1234","123456",LocalDate.now(),"test",MARRIED,
                0,new EmploymentDto(EMPLOYED,"123",BigDecimal.valueOf(100000),MIDDLE_MANAGER,20,20),"12312",true,true);
        Exception exception = assertThrows(ScoringdataDtoValidationExc.class, () -> {
            calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0));
        });

        String expectedMessage = "DECLINED :Age should be between 20 and 65";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));


    }

}