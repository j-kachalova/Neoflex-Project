package com.kachalova.calculator.service.impl;

import com.kachalova.calculator.config.PropertiesConfig;
import com.kachalova.calculator.dto.EmploymentDto;
import com.kachalova.calculator.dto.ScoringDataDto;
import com.kachalova.calculator.enums.EmploymentStatus;
import com.kachalova.calculator.enums.Gender;
import com.kachalova.calculator.enums.MaritalStatus;
import com.kachalova.calculator.enums.Position;
import com.kachalova.calculator.exeptions.ScoringDataDtoValidationExc;
import com.kachalova.calculator.service.ScoringService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class ScoringServiceImpl implements ScoringService {
    private final PropertiesConfig properties;
    private static final Integer MIN_FEMALE_AGE = 32;
    private static final Integer MAX_FEMALE_AGE = 60;
    private static final Integer MIN_MALE_AGE = 20;
    private static final Integer MAX_MALE_AGE = 55;
    private static final Integer MIN_AGE = 20;
    private static final Integer MAX_AGE = 65;

    @Override
    public BigDecimal makeScoring(@Valid ScoringDataDto scoringDataDto, BigDecimal rate) {
        log.info("request with scoringDataDto: {}, rate: {}", scoringDataDto, rate);
        BigDecimal userRate = rate;
        LocalDate birthday = scoringDataDto.getBirthdate();
        Integer age = birthday.until(LocalDate.now()).getYears();
        log.debug("birthday: {},age: {}", birthday, age);
        userRate = checkEmploymentStatus(scoringDataDto.getEmployment().getEmploymentStatus(), userRate);
        log.debug("rate after checkEmploymentStatus: {}", userRate);
        userRate = checkPosition(scoringDataDto.getEmployment().getPosition(), userRate);
        log.debug("rate after checkPosition: {}", userRate);
        checkAmount(scoringDataDto.getAmount(), scoringDataDto.getEmployment().getSalary());
        userRate = checkMaritalStatus(scoringDataDto.getMaritalStatus(), userRate);
        log.debug("rate after checkMaritalStatus: {}", userRate);
        userRate = checkGenderAge(scoringDataDto.getGender(), age, userRate);
        log.debug("rate after checkGenderAge: {}", userRate);
        checkWorkExperience(scoringDataDto.getEmployment());
        userRate = checkSalaryClient(scoringDataDto.getIsSalaryClient(), userRate);
        log.debug("rate after checkSalaryClient: {}", userRate);
        userRate = checkInsuranceEnabled(scoringDataDto.getIsInsuranceEnabled(), userRate);
        log.info("userRate: {}", userRate);
        return userRate;
    }

    private BigDecimal checkEmploymentStatus(EmploymentStatus status, BigDecimal rate) {
        switch (status) {
            case SELF_EMPLOYED:
                rate = rate.add(properties.getSelfEmployedRate());
                break;
            case BUSINESS_OWNER:
                rate = rate.add(properties.getBusinessOwnerRate());
                break;
            case UNEMPLOYED:
                log.error("DECLINED: UNEMPLOYED");
                throw new ScoringDataDtoValidationExc("DECLINED: UNEMPLOYED");
        }
        return rate;
    }

    private BigDecimal checkPosition(Position position, BigDecimal rate) {
        switch (position) {
            case MID_MANAGER:
                rate = rate.subtract(properties.getMiddleManagerRate());
                break;
            case TOP_MANAGER:
                rate = rate.subtract(properties.getTopManagerRate());
                break;
        }
        return rate;
    }

    private void checkAmount(BigDecimal amount, BigDecimal salary) {
        if (amount.compareTo(salary.multiply(BigDecimal.valueOf(properties.getNumberSalaries()))) > 1) {
            log.error("DECLINED: NOT ENOUGH SALARY");
            throw new ScoringDataDtoValidationExc("DECLINED: NOT ENOUGH SALARY");
        }
    }

    private BigDecimal checkMaritalStatus(MaritalStatus status, BigDecimal rate) {
        switch (status) {
            case MARRIED:
                rate = rate.subtract(properties.getMarriedRate());
                break;
            case DIVORCED:
                rate = rate.add(properties.getDivorcedRate());
                break;
        }
        return rate;
    }

    private BigDecimal checkGenderAge(Gender gender, Integer age, BigDecimal rate) {
        checkAge(age);
        switch (gender) {
            case MALE:
                rate = checkAgeMale(age, rate);
                break;
            case FEMALE:
                rate = checkAgeFemale(age, rate);
                break;
            case NON_BINARY:
                rate = rate.add(properties.getNonBinaryRate());
        }
        return rate;
    }

    private void checkWorkExperience(EmploymentDto employmentDto) {
        if (employmentDto.getWorkExperienceTotal() < properties.getMinTotalWorkExperience()) {
            log.error("DECLINED: NOT ENOUGH TOTAL WORK EXPERIENCE ");
            throw new ScoringDataDtoValidationExc("DECLINED: NOT ENOUGH TOTAL WORK EXPERIENCE ");
        }
        if (employmentDto.getWorkExperienceCurrent() < properties.getMinCurrentWorkExperience()) {
            log.error("DECLINED: NOT ENOUGH CURRENT WORK EXPERIENCE ");
            throw new ScoringDataDtoValidationExc("DECLINED: NOT ENOUGH CURRENT WORK EXPERIENCE ");
        }

    }

    public BigDecimal checkInsuranceEnabled(Boolean isInsuranceEnabled, BigDecimal rate) {
        if (isInsuranceEnabled) {
            rate = rate.subtract(properties.getInsuranceRate());
        }
        return rate;
    }

    public BigDecimal checkSalaryClient(Boolean isSalaryClient, BigDecimal rate) {
        if (isSalaryClient) {
            rate = rate.subtract(properties.getSalaryRate());
        }
        return rate;
    }

    private BigDecimal checkAgeMale(Integer age, BigDecimal rate) {
        if (age >= MIN_MALE_AGE && age <= MAX_MALE_AGE) {
            rate = rate.subtract(properties.getManAgeRate());
        }
        return rate;
    }

    private BigDecimal checkAgeFemale(Integer age, BigDecimal rate) {
        if (age >= MIN_FEMALE_AGE && age <= MAX_FEMALE_AGE) {
            rate = rate.subtract(properties.getWomanAgeRate());
        }
        return rate;
    }

    private void checkAge(Integer age) {
        if (age < MIN_AGE || age > MAX_AGE) {
            log.error("DECLINED: Age should be between 20 and 65");
            throw new ScoringDataDtoValidationExc("DECLINED: Age should be between 20 and 65");
        }
    }
}
