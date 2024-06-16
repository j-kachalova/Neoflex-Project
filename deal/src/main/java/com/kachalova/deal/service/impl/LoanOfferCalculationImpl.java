package com.kachalova.deal.service.impl;

import com.kachalova.deal.entities.Client;
import com.kachalova.deal.entities.Statement;
import com.kachalova.deal.dto.LoanOfferDto;
import com.kachalova.deal.dto.LoanStatementRequestDto;
import com.kachalova.deal.dto.StatementStatusHistoryDto;
import com.kachalova.deal.enums.ChangeType;
import com.kachalova.deal.repos.ClientRepo;
import com.kachalova.deal.repos.StatementRepo;
import com.kachalova.deal.service.LoanOfferCalculation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.kachalova.deal.enums.ApplicationStatus.PREAPPROVAL;

@Service
@RequiredArgsConstructor
public class LoanOfferCalculationImpl implements LoanOfferCalculation {
    private final ClientRepo clientRepo;
    private final RestTemplate restTemplate;
    private final StatementRepo statementRepo;

    private Client addClient(LoanStatementRequestDto loanStatementRequestDto) {
        Client clientFromDb = clientRepo.findByEmail(loanStatementRequestDto.getEmail());

        if (clientFromDb != null) {
            return clientFromDb;
        }
        clientFromDb = clientRepo.findByAccountNumber(loanStatementRequestDto.getPassportNumber());
        if (clientFromDb != null) {
            return clientFromDb;
        }

        Client client = Client.builder()
                .lastName(loanStatementRequestDto.getLastName())
                .firstName(loanStatementRequestDto.getFirstName())
                .middleName(loanStatementRequestDto.getMiddleName())
                .birthDate(loanStatementRequestDto.getBirthdate())
                .email(loanStatementRequestDto.getEmail())
                .build();
        clientRepo.save(client);
        return client;
    }

    private Statement addStatement(Client client) {
        Statement statementFromDb = statementRepo.findByClient(client);
        if (statementFromDb != null) {
            return statementFromDb;
        }
        StatementStatusHistoryDto statementStatusHistoryDto = StatementStatusHistoryDto.builder()
                .status(PREAPPROVAL)
                .changeType(ChangeType.AUTOMATIC)
                .time(LocalDateTime.now())
                .build();

        Statement statement = Statement.builder()
                .client(client)
                .status(statementStatusHistoryDto.getStatus())
                .creationDate(LocalDateTime.now())
                .signDate(LocalDateTime.now())
                .statusHistory(List.of(statementStatusHistoryDto))
                .build();

        statementRepo.save(statement);
        return statement;
    }

    @Override
    public List<LoanOfferDto> calculateLoanOffer(LoanStatementRequestDto loanStatementRequestDto) {
        Client client = addClient(loanStatementRequestDto);
        Statement statement = addStatement(client);
        HttpEntity<LoanStatementRequestDto> httpEntity = new HttpEntity<>(loanStatementRequestDto);
        ResponseEntity<LoanOfferDto[]> responseEntity = restTemplate.postForEntity("http://localhost:8080/calculator/offers", httpEntity, LoanOfferDto[].class);
        List<LoanOfferDto> loanOfferDtoList = List.of(Objects.requireNonNull(responseEntity.getBody()));
        loanOfferDtoList.get(0).setStatementId(statement.getId());
        loanOfferDtoList.get(1).setStatementId(statement.getId());
        loanOfferDtoList.get(2).setStatementId(statement.getId());
        loanOfferDtoList.get(3).setStatementId(statement.getId());
        return loanOfferDtoList;
    }
}
