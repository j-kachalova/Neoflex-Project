package com.kachalova.calculator.dto;

import com.kachalova.calculator.domain.EmploymentStatus;
import com.kachalova.calculator.domain.Position;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmploymentDto {
    private EmploymentStatus employmentStatus;
    private String employerINN;
    private BigDecimal salary;
    private Position position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;

}
