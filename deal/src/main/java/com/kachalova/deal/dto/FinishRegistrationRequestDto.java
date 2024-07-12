package com.kachalova.deal.dto;

import com.kachalova.deal.enums.Gender;
import com.kachalova.deal.enums.MaritalStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinishRegistrationRequestDto {
    private Gender gender;
    private MaritalStatus maritalStatus;
    private Integer dependentAmount;
    @Schema(example = "2020-05-10")
    private LocalDate passportIssueDate;
    @Schema(example = "ГУ МВД России по Саратовской области")
    private String passportIssueBrach;
    private EmploymentDto employment;
    private String accountNumber;
}
