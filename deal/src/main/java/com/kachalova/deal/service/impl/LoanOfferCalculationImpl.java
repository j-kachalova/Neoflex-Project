package com.kachalova.deal.service.impl;

import com.kachalova.deal.dto.LoanOfferDto;
import com.kachalova.deal.dto.LoanStatementRequestDto;
import com.kachalova.deal.dto.PassportDto;
import com.kachalova.deal.dto.StatementStatusHistoryDto;
import com.kachalova.deal.entities.Client;
import com.kachalova.deal.entities.Statement;
import com.kachalova.deal.enums.ChangeType;
import com.kachalova.deal.mapper.ClientMapper;
import com.kachalova.deal.mapper.PassportDtoMapper;
import com.kachalova.deal.mapper.StatementMapper;
import com.kachalova.deal.mapper.StatementStatusHistoryDtoMapper;
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
    private final ClientMapper clientMapper;
    private final PassportDtoMapper passportDtoMapper;
    private final StatementStatusHistoryDtoMapper statementStatusHistoryDtoMapper;
    private final StatementMapper statementMapper;

    private Client addClient(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("LoanOfferCalculationImpl: addClient loanStatementRequestDto: {}", loanStatementRequestDto);
        Client clientFromDb = clientRepo.findByEmail(loanStatementRequestDto.getEmail());
        log.debug("LoanOfferCalculationImpl: addClient clientFromDb: {}", clientFromDb);
        if (clientFromDb != null) {
            return clientFromDb;
        }
        clientFromDb = clientRepo.findByAccountNumber(loanStatementRequestDto.getPassportNumber());
        if (clientFromDb != null) {
            return clientFromDb;
        }
        PassportDto passportDto = passportDtoMapper.toPassportDto(loanStatementRequestDto);
        Client client = clientMapper.toEntity(loanStatementRequestDto, passportDto);
        log.debug("LoanOfferCalculationImpl: addClient passportDto: {}", passportDto);
        clientRepo.save(client);
        log.info("LoanOfferCalculationImpl: addClient client: {}", client);
        return client;
    }

    private Statement addStatement(Client client) {
        log.info("LoanOfferCalculationImpl: addStatement client: {}", client);
        Statement statementFromDb = statementRepo.findByClient(client);
        log.debug("LoanOfferCalculationImpl: addStatement statementFromDb: {}", statementFromDb);
        if (statementFromDb != null) {
            return statementFromDb;
        }
        StatementStatusHistoryDto statementStatusHistoryDto = statementStatusHistoryDtoMapper.toDto(PREAPPROVAL);


        Statement statement = statementMapper.toStatement(client, statementStatusHistoryDto);

        statementRepo.save(statement);
        log.info("LoanOfferCalculationImpl: addStatement statement: {}", statement);
        return statement;
    }

    @Override
    public List<LoanOfferDto> calculateLoanOffer(LoanStatementRequestDto loanStatementRequestDto) {
        log.info("LoanOfferCalculationImpl: calculateLoanOffer loanStatementRequestDto: {}", loanStatementRequestDto);
        Client client = addClient(loanStatementRequestDto);
        log.debug("LoanOfferCalculationImpl: calculateLoanOffer client: {}", client);
        Statement statement = addStatement(client);
        log.debug("LoanOfferCalculationImpl: calculateLoanOffer statement: {}", statement);
        HttpEntity<LoanStatementRequestDto> httpEntity = new HttpEntity<>(loanStatementRequestDto);
        ResponseEntity<LoanOfferDto[]> response = restTemplate.postForEntity("http://localhost:8080/calculator/offers", httpEntity, LoanOfferDto[].class);
        List<LoanOfferDto> loanOfferDtoList = List.of(Objects.requireNonNull(response.getBody()));
        loanOfferDtoList.get(0).setStatementId(statement.getId());
        loanOfferDtoList.get(1).setStatementId(statement.getId());
        loanOfferDtoList.get(2).setStatementId(statement.getId());
        loanOfferDtoList.get(3).setStatementId(statement.getId());
        log.info("LoanOfferCalculationImpl: calculateLoanOffer loanOfferDtoList: {}", loanOfferDtoList);
        return loanOfferDtoList;
    }
}
