package com.kachalova.deal.mapper;

import com.kachalova.deal.dto.LoanStatementRequestDto;
import com.kachalova.deal.dto.PassportDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PassportDtoMapper {
    public PassportDto toPassportDto(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("PassportDtoMapper toPassportDto: loanStatementRequestDto: {}", loanStatementRequestDto);
        PassportDto passportDto = PassportDto.builder()
                .number(loanStatementRequestDto.getPassportNumber())
                .series(loanStatementRequestDto.getPassportSeries())
                .build();
        log.info("PassportDtoMapper toPassportDto: passportDto: {}", passportDto);
        return passportDto;
    }
}
