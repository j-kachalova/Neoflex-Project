package com.kachalova.calculator.service;

import com.kachalova.calculator.domain.EmploymentStatus;
import com.kachalova.calculator.domain.Gender;
import com.kachalova.calculator.domain.MaritalStatus;
import com.kachalova.calculator.domain.Position;
import com.kachalova.calculator.dto.ScoringDataDto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ScoringService {
    private final BigDecimal baseInterestRate = BigDecimal.valueOf(16.0);

    private final BigDecimal selfEmployedRateIncrease = BigDecimal.valueOf(1.0);
    private final BigDecimal businessOwnerRateIncrease = BigDecimal.valueOf(2.0);
    private final BigDecimal middleManagerRateDecrease = BigDecimal.valueOf(2.0);
    private final BigDecimal topManagerRateDecrease = BigDecimal.valueOf(3.0);
    private final BigDecimal marriedRateDecrease = BigDecimal.valueOf(3.0);
    private final BigDecimal divorcedRateIncrease = BigDecimal.valueOf(1.0);
    private final BigDecimal womanAgeRateDecrease = BigDecimal.valueOf(3.0);
    private final BigDecimal manAgeRateDecrease = BigDecimal.valueOf(3.0);
    private final BigDecimal nonBinaryRateIncrease = BigDecimal.valueOf(7.0);

    public void makeScoring(ScoringDataDto scoringDataDto) {
        BigDecimal rate=baseInterestRate;
        LocalDate birthday = scoringDataDto.getBirthdate();
        Integer age = birthday.until(LocalDate.now()).getYears();

        checkEmploymentStatus(scoringDataDto.getEmployment().getEmploymentStatus(), rate);
        checkPosition(scoringDataDto.getEmployment().getPosition(), rate);
        checkMaritalStatus(scoringDataDto.getMaritalStatus(),rate);
        checkGenderAge(scoringDataDto.getGender(), age, rate);

    }
    private void checkEmploymentStatus(EmploymentStatus status, BigDecimal rate){
        switch (status){
            case EMPLOYED:
                rate=rate.add(selfEmployedRateIncrease);
                break;
            case BUSINESS_OWNER:
                rate=rate.add(businessOwnerRateIncrease);
                break;
            default:
                break;
        }
    }
    private void checkPosition(Position position, BigDecimal rate){
        switch (position){
            case MANAGER:
                rate=rate.subtract(middleManagerRateDecrease);
                break;
            case TOP_MANAGER:
                rate=rate.subtract(topManagerRateDecrease);
                break;
            default: break;
        }
    }
    private void checkMaritalStatus(MaritalStatus status, BigDecimal rate){
        switch (status){
            case MARRIED:
                rate=rate.subtract(marriedRateDecrease);
                break;
            case DIVORCED:
                rate = rate.add(divorcedRateIncrease);
                break;
            default:
                break;
        }
    }
    private void checkGenderAge(Gender gender, Integer age, BigDecimal rate){
        switch (gender){
            case MALE:
                if(age>=30 && age<=55) rate=rate.subtract(manAgeRateDecrease);
                break;
            case FEMALE:
                    if(age>=32 && age<=60) rate=rate.subtract(womanAgeRateDecrease);
                    break;
            case NON_BINARY:
                rate=rate.subtract(nonBinaryRateIncrease);
            default:break;
        }
    }
}
