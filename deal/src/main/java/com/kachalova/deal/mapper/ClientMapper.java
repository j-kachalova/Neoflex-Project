package com.kachalova.deal.mapper;

import com.kachalova.deal.dto.LoanStatementRequestDto;
import com.kachalova.deal.dto.PassportDto;
import com.kachalova.deal.entities.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ClientMapper {
    public Client toEntity(LoanStatementRequestDto loanStatementRequestDto, PassportDto passportDto) {
        log.info("ClientMapper toEntity: loanStatementRequestDto: {}, passportDto: {}",
                loanStatementRequestDto, passportDto);
        Client client = Client.builder()
                .lastName(loanStatementRequestDto.getLastName())
                .firstName(loanStatementRequestDto.getFirstName())
                .middleName(loanStatementRequestDto.getMiddleName())
                .birthDate(loanStatementRequestDto.getBirthdate())
                .email(loanStatementRequestDto.getEmail())
                .passport(passportDto)
                .build();
        log.info("ClientMapper toEntity: client: {}", client);
        return client;
    }
}
