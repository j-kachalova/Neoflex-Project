package com.kachalova.gateway.dto;

import com.kachalova.gateway.enums.EmploymentPosition;
import com.kachalova.gateway.enums.EmploymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmploymentDto {
    private EmploymentStatus employmentStatus;
    private String employerINN;
    @Schema(example = "50000")
    private BigDecimal salary;
    private EmploymentPosition position;
    @Schema(description = "общий стаж работы в месяцах", example = "38")
    private Integer workExperienceTotal;
    @Schema(description = "текущий стаж работы в месяцах", example = "14")
    private Integer workExperienceCurrent;

}
