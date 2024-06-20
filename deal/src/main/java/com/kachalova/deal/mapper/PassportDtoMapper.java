package com.kachalova.deal.mapper;

import com.kachalova.deal.dto.LoanStatementRequestDto;
import com.kachalova.deal.dto.PassportDto;
import org.springframework.stereotype.Component;

@Component
public class PassportDtoMapper {
    public PassportDto toPassportDto(LoanStatementRequestDto loanStatementRequestDto) {
        PassportDto passportDto = PassportDto.builder()
                .number(loanStatementRequestDto.getPassportNumber())
                .series(loanStatementRequestDto.getPassportSeries())
                .build();
        return passportDto;
    }
}
