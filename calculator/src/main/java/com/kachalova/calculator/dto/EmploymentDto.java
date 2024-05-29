package com.kachalova.calculator.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmploymentDto {
    Enum employmentStatus;
    String employerINN;
    BigDecimal salary;
    Enum position;
    Integer workExperienceTotal;
    Integer workExperienceCurrent;

}
