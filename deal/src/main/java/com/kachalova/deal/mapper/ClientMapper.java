package com.kachalova.deal.mapper;

import com.kachalova.deal.dto.LoanStatementRequestDto;
import com.kachalova.deal.dto.PassportDto;
import com.kachalova.deal.entities.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    public Client toEntity(LoanStatementRequestDto loanStatementRequestDto, PassportDto passportDto) {
        Client client = Client.builder()
                .lastName(loanStatementRequestDto.getLastName())
                .firstName(loanStatementRequestDto.getFirstName())
                .middleName(loanStatementRequestDto.getMiddleName())
                .birthDate(loanStatementRequestDto.getBirthdate())
                .email(loanStatementRequestDto.getEmail())
                .passport(passportDto)
                .build();
        return client;
    }
}
