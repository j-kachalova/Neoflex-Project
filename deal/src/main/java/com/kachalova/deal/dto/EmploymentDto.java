package com.kachalova.deal.dto;


import com.kachalova.deal.enums.EmploymentPosition;
import com.kachalova.deal.enums.EmploymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentDto {
    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus;
    private String employerINN;
    private BigDecimal salary;
    @Enumerated(EnumType.STRING)
    private EmploymentPosition position;
    private Integer workExperienceTotal;
    private Integer workExperienceCurrent;

}
