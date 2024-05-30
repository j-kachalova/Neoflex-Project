package com.kachalova.calculator.dto;

import com.kachalova.calculator.enums.EmploymentStatus;
import com.kachalova.calculator.enums.Position;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentDto {
    private EmploymentStatus employmentStatus;
    private String employerINN;
    private BigDecimal salary;
    private Position position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;

}
