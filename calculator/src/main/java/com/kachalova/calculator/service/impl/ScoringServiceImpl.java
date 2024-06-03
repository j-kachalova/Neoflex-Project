package com.kachalova.calculator.service.impl;

import com.kachalova.calculator.dto.EmploymentDto;
import com.kachalova.calculator.dto.ScoringDataDto;
import com.kachalova.calculator.enums.EmploymentStatus;
import com.kachalova.calculator.enums.Gender;
import com.kachalova.calculator.enums.MaritalStatus;
import com.kachalova.calculator.enums.Position;
import com.kachalova.calculator.exeptions.ScoringDataDtoValidationExc;
import com.kachalova.calculator.service.ScoringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Service
@Validated
public class ScoringServiceImpl implements ScoringService {
    private final BigDecimal selfEmployedRateIncrease = BigDecimal.valueOf(1.0);
    private final BigDecimal businessOwnerRateIncrease = BigDecimal.valueOf(2.0);
    private final BigDecimal middleManagerRateDecrease = BigDecimal.valueOf(2.0);
    private final BigDecimal topManagerRateDecrease = BigDecimal.valueOf(3.0);
    private final BigDecimal marriedRateDecrease = BigDecimal.valueOf(3.0);
    private final BigDecimal divorcedRateIncrease = BigDecimal.valueOf(1.0);
    private final BigDecimal womanAgeRateDecrease = BigDecimal.valueOf(3.0);
    private final BigDecimal manAgeRateDecrease = BigDecimal.valueOf(3.0);
    private final BigDecimal nonBinaryRateIncrease = BigDecimal.valueOf(7.0);

    @Override
    public BigDecimal makeScoring(@Valid ScoringDataDto scoringDataDto, BigDecimal rate) throws ScoringDataDtoValidationExc {
        log.info("got {}{}", scoringDataDto.toString(), rate.toString());
        BigDecimal userRate = rate;
        LocalDate birthday = scoringDataDto.getBirthdate();
        Integer age = birthday.until(LocalDate.now()).getYears();
        log.info("birthday is {}", birthday);
        log.info("age is {}", age);
        userRate = checkEmploymentStatus(scoringDataDto.getEmployment().getEmploymentStatus(), userRate);
        log.info("rate after checkEmploymentStatus is {}", userRate);
        userRate = checkPosition(scoringDataDto.getEmployment().getPosition(), userRate);
        log.info("rate after checkPosition is {}", userRate);
        checkAmount(scoringDataDto.getAmount(), scoringDataDto.getEmployment().getSalary());
        userRate = checkMaritalStatus(scoringDataDto.getMaritalStatus(), userRate);
        log.info("rate after checkMaritalStatus is {}", userRate);
        userRate = checkGenderAge(scoringDataDto.getGender(), age, userRate);
        log.info("rate after checkGenderAge is {}", userRate);
        checkWorkExperience(scoringDataDto.getEmployment());
        userRate = checkSalaryClient(scoringDataDto.getIsSalaryClient(), userRate);
        log.info("rate after checkSalaryClient is {}", userRate);
        userRate = checkInsuranceEnabled(scoringDataDto.getIsInsuranceEnabled(), userRate);
        log.info("rate after checkInsuranceEnabled is {}", userRate);
        return userRate;
    }

    private BigDecimal checkEmploymentStatus(EmploymentStatus status, BigDecimal rate) throws ScoringDataDtoValidationExc {
        switch (status) {
            case SELF_EMPLOYED:
                rate = rate.add(selfEmployedRateIncrease);
                break;
            case BUSINESS_OWNER:
                rate = rate.add(businessOwnerRateIncrease);
                break;
            case UNEMPLOYED:
                log.info("DECLINED :UNEMPLOYED");
                throw new ScoringDataDtoValidationExc("DECLINED :UNEMPLOYED");
            default:
                break;
        }
        return rate;
    }

    private BigDecimal checkPosition(Position position, BigDecimal rate) {
        switch (position) {
            case MIDDLE_MANAGER:
                rate = rate.subtract(middleManagerRateDecrease);
                break;
            case TOP_MANAGER:
                rate = rate.subtract(topManagerRateDecrease);
                break;
            default:
                break;
        }
        return rate;
    }

    private void checkAmount(BigDecimal amount, BigDecimal salary) throws ScoringDataDtoValidationExc {
        if (amount.compareTo(salary.multiply(BigDecimal.valueOf(25))) > 1) {
            log.info("DECLINED :NOT ENOUGH SALARY");
            throw new ScoringDataDtoValidationExc("DECLINED :NOT ENOUGH SALARY");
        }
    }

    private BigDecimal checkMaritalStatus(MaritalStatus status, BigDecimal rate) {
        switch (status) {
            case MARRIED:
                rate = rate.subtract(marriedRateDecrease);
                break;
            case DIVORCED:
                rate = rate.add(divorcedRateIncrease);
                break;
            default:
                break;
        }
        return rate;
    }

    private BigDecimal checkGenderAge(Gender gender, Integer age, BigDecimal rate) throws ScoringDataDtoValidationExc {
        if (age < 20 || age > 65) {
            log.info("DECLINED :Age should be between 20 and 65");
            throw new ScoringDataDtoValidationExc("DECLINED :Age should be between 20 and 65");
        }
        switch (gender) {
            case MALE:
                if (age >= 30 && age <= 55) rate = rate.subtract(manAgeRateDecrease);
                break;
            case FEMALE:
                if (age >= 32 && age <= 60) rate = rate.subtract(womanAgeRateDecrease);
                break;
            case NON_BINARY:
                rate = rate.add(nonBinaryRateIncrease);
            default:
                break;
        }
        return rate;
    }

    private void checkWorkExperience(EmploymentDto employmentDto) throws ScoringDataDtoValidationExc {
        if (employmentDto.getWorkExperienceTotal() < 18) {
            log.info("DECLINED :NOT ENOUGH TOTAL WORK EXPERIENCE ");
            throw new ScoringDataDtoValidationExc("DECLINED :NOT ENOUGH TOTAL WORK EXPERIENCE ");
        }
        if (employmentDto.getWorkExperienceCurrent() < 3) {
            log.info("DECLINED :NOT ENOUGH CURRENT WORK EXPERIENCE ");
            throw new ScoringDataDtoValidationExc("DECLINED :NOT ENOUGH CURRENT WORK EXPERIENCE ");
        }

    }

    public BigDecimal checkInsuranceEnabled(Boolean isInsuranceEnabled, BigDecimal rate) {
        if (isInsuranceEnabled) rate = rate.subtract(BigDecimal.valueOf(3.0));
        return rate;
    }

    public BigDecimal checkSalaryClient(Boolean isSalaryClient, BigDecimal rate) {
        if (isSalaryClient) rate = rate.subtract(BigDecimal.valueOf(1.0));
        return rate;
    }
}
