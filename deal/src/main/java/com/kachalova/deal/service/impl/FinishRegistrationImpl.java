package com.kachalova.deal.service.impl;

import com.kachalova.deal.entities.Client;
import com.kachalova.deal.entities.Credit;
import com.kachalova.deal.entities.Statement;
import com.kachalova.deal.dto.*;
import com.kachalova.deal.enums.ApplicationStatus;
import com.kachalova.deal.enums.ChangeType;
import com.kachalova.deal.enums.CreditStatus;
import com.kachalova.deal.repos.CreditRepo;
import com.kachalova.deal.repos.StatementRepo;
import com.kachalova.deal.service.FinishRegistration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FinishRegistrationImpl implements FinishRegistration {
    private final StatementRepo statementRepo;
    private final CreditRepo creditRepo;
    private final RestTemplate restTemplate;
    @Override
    public void finishRegistration(FinishRegistrationRequestDto requestDto, String statementId) {
        Statement statementFromDb = statementRepo.findById(UUID.fromString(statementId));
        Client client = statementFromDb.getClient();
        PassportDto passport = client.getPassport();
        ScoringDataDto scoringDataDto = ScoringDataDto.builder()
//                .amount()
//                .term()
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
//                .isInsuranceEnabled()
//                .isSalaryClient()
                .build();
        Credit credit = creditCalculation(scoringDataDto);
        creditRepo.save(credit);
        statementFromDb.setCredit(credit);

        StatementStatusHistoryDto statementStatusHistoryDto = StatementStatusHistoryDto.builder()
                .status(ApplicationStatus.CC_APPROVED)
                .time(LocalDateTime.now())
                .changeType(ChangeType.AUTOMATIC)
                .build();

        statementFromDb.setStatus(statementStatusHistoryDto.getStatus());
        statementFromDb.getStatusHistory().add(statementStatusHistoryDto);
        statementRepo.save(statementFromDb);
    }

    private Credit creditCalculation(ScoringDataDto scoringDataDto) {
        HttpEntity<ScoringDataDto> httpEntity = new HttpEntity<>(scoringDataDto);
        ResponseEntity<CreditDto> responseEntity =  restTemplate.postForEntity("http://localhost:8080/calculator/calc", httpEntity, CreditDto.class);
        CreditDto creditDto = responseEntity.getBody();
        Credit credit = Credit.builder()
                .amount(creditDto.getAmount())
                .term(creditDto.getTerm())
                .monthlyPayment(creditDto.getMonthlyPayment())
                .rate(creditDto.getRate())
                .psk(creditDto.getPsk())
                .insurableEnabled(creditDto.getIsInsuranceEnabled())
                .salaryClient(creditDto.getIsSalaryClient())
                .creditStatus(CreditStatus.CALCULATED)
                .build();
        return credit;
    }
}
