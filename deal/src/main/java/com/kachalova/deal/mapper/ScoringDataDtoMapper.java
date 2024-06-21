package com.kachalova.deal.mapper;

import com.kachalova.deal.dto.FinishRegistrationRequestDto;
import com.kachalova.deal.dto.PassportDto;
import com.kachalova.deal.dto.ScoringDataDto;
import com.kachalova.deal.entities.Client;
import com.kachalova.deal.entities.Statement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ScoringDataDtoMapper {
    public ScoringDataDto toScoringDataDto(FinishRegistrationRequestDto requestDto, Statement statement) {
        log.info("ScoringDataDtoMapper toScoringDataDto requestDto: {}, statement: {}",
                requestDto, statement);
        Client client = statement.getClient();
        log.debug("ScoringDataDtoMapper toScoringDataDto client: {}", client);
        PassportDto passport = client.getPassport();
        log.debug("ScoringDataDtoMapper toScoringDataDto passport: {}", passport);
        ScoringDataDto scoringDataDto = ScoringDataDto.builder()
                .amount(statement.getAppliedOffer().getRequestedAmount())
                .term(statement.getAppliedOffer().getTerm())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .middleName(client.getMiddleName())
                .gender(requestDto.getGender())
                .birthdate(client.getBirthDate())
                .passportSeries(passport.getSeries())
                .passportNumber(passport.getNumber())
                .passportIssueDate(passport.getIssueDate())
                .passportIssueBranch(passport.getIssueBranch())
                .maritalStatus(requestDto.getMaritalStatus())
                .dependentAmount(requestDto.getDependentAmount())
                .employment(requestDto.getEmployment())
                .accountNumber(requestDto.getAccountNumber())
                .isInsuranceEnabled(statement.getAppliedOffer().getIsInsuranceEnabled())
                .isSalaryClient(statement.getAppliedOffer().getIsSalaryClient())
                .build();
        log.info("ScoringDataDtoMapper toScoringDataDto scoringDataDto: {}", scoringDataDto);
        return scoringDataDto;
    }
}
