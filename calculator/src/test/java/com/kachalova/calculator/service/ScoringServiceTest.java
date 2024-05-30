package com.kachalova.calculator.service;

import com.kachalova.calculator.dto.EmploymentDto;
import com.kachalova.calculator.dto.ScoringDataDto;
import com.kachalova.calculator.enums.EmploymentStatus;
import com.kachalova.calculator.exeptions.ScoringdataDtoValidationExc;
import org.junit.jupiter.api.Test;


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
    @Test
    public void makeScoringFemaleMarried() throws ScoringdataDtoValidationExc {
        ScoringService calculationCredit = new ScoringService();
        ScoringDataDto scoringDataDTO = new ScoringDataDto(BigDecimal.valueOf(300000),6,"test","test",
                "test",FEMALE, LocalDate.now().minusYears(30),"1234","123456",LocalDate.now(),"test",MARRIED,
                0,new EmploymentDto(EMPLOYED,"123",BigDecimal.valueOf(100000),TOP_MANAGER,20,20),"12312",true,true);
        BigDecimal actual = calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0));
        System.out.println(actual);
        assertEquals(BigDecimal.valueOf(10.0),actual);
    }
    @Test
    public void makeScoringMale() throws ScoringdataDtoValidationExc {
        ScoringService calculationCredit = new ScoringService();
        ScoringDataDto scoringDataDTO = new ScoringDataDto(BigDecimal.valueOf(300000),6,"test","test",
                "test",MALE, LocalDate.now().minusYears(30),"1234","123456",LocalDate.now(),"test",MARRIED,
                0,new EmploymentDto(EMPLOYED,"123",BigDecimal.valueOf(100000),TOP_MANAGER,20,20),"12312",true,true);
        BigDecimal actual = calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0));
        System.out.println(actual);
        assertEquals(BigDecimal.valueOf(7.0),actual);
    }
    @Test
    public void makeScoringNonbinary() throws ScoringdataDtoValidationExc {
        ScoringService calculationCredit = new ScoringService();
        ScoringDataDto scoringDataDTO = new ScoringDataDto(BigDecimal.valueOf(300000),6,"test","test",
                "test",NON_BINARY, LocalDate.now().minusYears(30),"1234","123456",LocalDate.now(),"test",MARRIED,
                0,new EmploymentDto(EMPLOYED,"123",BigDecimal.valueOf(100000),TOP_MANAGER,20,20),"12312",true,true);
        BigDecimal actual = calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0));
        System.out.println(actual);
        assertEquals(BigDecimal.valueOf(17.0),actual);
    }
    @Test
    public void makeScoringFemaleDivorced() throws ScoringdataDtoValidationExc {
        ScoringService calculationCredit = new ScoringService();
        ScoringDataDto scoringDataDTO = new ScoringDataDto(BigDecimal.valueOf(300000),6,"test","test",
                "test",MALE, LocalDate.now().minusYears(30),"1234","123456",LocalDate.now(),"test",DIVORCED,
                0,new EmploymentDto(EMPLOYED,"123",BigDecimal.valueOf(100000),TOP_MANAGER,20,20),"12312",true,true);
        BigDecimal actual = calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0));
        System.out.println(actual);
        assertEquals(BigDecimal.valueOf(11.0),actual);
    }
    @Test
    public void makeScoringFemaleAge() throws ScoringdataDtoValidationExc {
        ScoringService calculationCredit = new ScoringService();
        ScoringDataDto scoringDataDTO = new ScoringDataDto(BigDecimal.valueOf(300000),6,"test","test",
                "test",MALE, LocalDate.now().minusYears(40),"1234","123456",LocalDate.now(),"test",MARRIED,
                0,new EmploymentDto(EMPLOYED,"123",BigDecimal.valueOf(100000),TOP_MANAGER,20,20),"12312",true,true);
        BigDecimal actual = calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0));
        System.out.println(actual);
        assertEquals(BigDecimal.valueOf(7.0),actual);
    }
    @Test
    public void makeScoringMiddleManager() throws ScoringdataDtoValidationExc {
        ScoringService calculationCredit = new ScoringService();
        ScoringDataDto scoringDataDTO = new ScoringDataDto(BigDecimal.valueOf(300000),6,"test","test",
                "test",FEMALE, LocalDate.now().minusYears(30),"1234","123456",LocalDate.now(),"test",MARRIED,
                0,new EmploymentDto(EMPLOYED,"123",BigDecimal.valueOf(100000),MIDDLE_MANAGER,20,20),"12312",true,true);
        BigDecimal actual = calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0));
        System.out.println(actual);
        assertEquals(BigDecimal.valueOf(11.0),actual);
    }
   /* @Test(expected = ScoringdataDtoValidationExc.class)
    public void makeScoringAge18() throws ScoringdataDtoValidationExc {
        ScoringService calculationCredit = new ScoringService();
        ScoringDataDto scoringDataDTO = new ScoringDataDto(BigDecimal.valueOf(300000),6,"test","test",
                "test",FEMALE, LocalDate.now().minusYears(18),"1234","123456",LocalDate.now(),"test",MARRIED,
                0,new EmploymentDto(EMPLOYED,"123",BigDecimal.valueOf(100000),MIDDLE_MANAGER,20,20),"12312",true,true);
        BigDecimal actual = calculationCredit.makeScoring(scoringDataDTO, BigDecimal.valueOf(16.0));
        System.out.println(actual);

    }*/

}