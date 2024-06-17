package com.kachalova.deal.service.impl;

import com.kachalova.deal.dto.LoanOfferDto;
import com.kachalova.deal.dto.LoanStatementRequestDto;
import com.kachalova.deal.dto.PassportDto;
import com.kachalova.deal.dto.StatementStatusHistoryDto;
import com.kachalova.deal.entities.Client;
import com.kachalova.deal.entities.Statement;
import com.kachalova.deal.enums.ChangeType;
import com.kachalova.deal.repos.ClientRepo;
import com.kachalova.deal.repos.StatementRepo;
import com.kachalova.deal.service.LoanOfferCalculation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static com.kachalova.deal.enums.ApplicationStatus.PREAPPROVAL;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanOfferCalculationImpl implements LoanOfferCalculation {
    private final ClientRepo clientRepo;
    private final RestTemplate restTemplate;
    private final StatementRepo statementRepo;

    private Client addClient(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("loanStatementRequestDto: {}", loanStatementRequestDto);
        Client clientFromDb = clientRepo.findByEmail(loanStatementRequestDto.getEmail());
        log.debug("clientFromDb: {}", clientFromDb);
        if (clientFromDb != null) {
            return clientFromDb;
        }
        clientFromDb = clientRepo.findByAccountNumber(loanStatementRequestDto.getPassportNumber());
        if (clientFromDb != null) {
            return clientFromDb;
        }
        PassportDto passportDto = PassportDto.builder()
                .number(loanStatementRequestDto.getPassportNumber())
                .series(loanStatementRequestDto.getPassportSeries())
                .build();
        Client client = Client.builder()
                .lastName(loanStatementRequestDto.getLastName())
                .firstName(loanStatementRequestDto.getFirstName())
                .middleName(loanStatementRequestDto.getMiddleName())
                .birthDate(loanStatementRequestDto.getBirthdate())
                .email(loanStatementRequestDto.getEmail())
                .passport(passportDto)
                .build();
        log.debug("passportDto: {}", passportDto);
        clientRepo.save(client);
        log.info("client: {}", client);
        return client;
    }

    private Statement addStatement(Client client) {
        log.info("client: {}", client);
        Statement statementFromDb = statementRepo.findByClient(client);
        log.debug("statementFromDb: {}", statementFromDb);
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
        log.info("loanStatementRequestDto: {}", loanStatementRequestDto);
        Client client = addClient(loanStatementRequestDto);
        log.debug("client: {}", client);
        Statement statement = addStatement(client);
        log.debug("statement: {}", statement);
        HttpEntity<LoanStatementRequestDto> httpEntity = new HttpEntity<>(loanStatementRequestDto);
        ResponseEntity<LoanOfferDto[]> response = restTemplate.postForEntity("http://localhost:8080/calculator/offers", httpEntity, LoanOfferDto[].class);
        List<LoanOfferDto> loanOfferDtoList = List.of(Objects.requireNonNull(response.getBody()));
        loanOfferDtoList.get(0).setStatementId(statement.getId());
        loanOfferDtoList.get(1).setStatementId(statement.getId());
        loanOfferDtoList.get(2).setStatementId(statement.getId());
        loanOfferDtoList.get(3).setStatementId(statement.getId());
        log.info("loanOfferDtoList: {}", loanOfferDtoList);
        return loanOfferDtoList;
    }
}
