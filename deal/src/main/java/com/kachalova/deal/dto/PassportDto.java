package com.kachalova.deal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PassportDto {
    private String series;
    private String number;
    private String issueBranch;
    private LocalDate issueDate;
}
