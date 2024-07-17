package com.kachalova.deal.dto;


import com.kachalova.deal.enums.EmploymentPosition;
import com.kachalova.deal.enums.EmploymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "50000")
    private BigDecimal salary;
    @Enumerated(EnumType.STRING)
    private EmploymentPosition position;
    @Schema(description = "общий стаж работы в месяцах", example = "38")
    private Integer workExperienceTotal;
    @Schema(description = "текущий стаж работы в месяцах", example = "14")
    private Integer workExperienceCurrent;

}
