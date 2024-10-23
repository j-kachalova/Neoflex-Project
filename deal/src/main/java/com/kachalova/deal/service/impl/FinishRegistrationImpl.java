package com.kachalova.deal.service.impl;

import com.kachalova.deal.dto.CreditDto;
import com.kachalova.deal.dto.FinishRegistrationRequestDto;
import com.kachalova.deal.dto.ScoringDataDto;
import com.kachalova.deal.dto.StatementStatusHistoryDto;
import com.kachalova.deal.entities.Credit;
import com.kachalova.deal.entities.Statement;
import com.kachalova.deal.exceptions.ExternalServiceException;
import com.kachalova.deal.mapper.CreditMapper;
import com.kachalova.deal.mapper.ScoringDataDtoMapper;
import com.kachalova.deal.mapper.StatementMapper;
import com.kachalova.deal.mapper.StatementStatusHistoryDtoMapper;
import com.kachalova.deal.repos.CreditRepo;
import com.kachalova.deal.repos.StatementRepo;
import com.kachalova.deal.service.ExternalService;
import com.kachalova.deal.service.FinishRegistration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static com.kachalova.deal.enums.ApplicationStatus.CC_APPROVED;
import static com.kachalova.deal.enums.ApplicationStatus.CC_DENIED;

@Slf4j
@Service
@RequiredArgsConstructor
public class FinishRegistrationImpl implements FinishRegistration {
    private final StatementRepo statementRepo;
    private final CreditRepo creditRepo;
    private final RestTemplate restTemplate;
    private final ScoringDataDtoMapper scoringDataDtoMapper;
    private final StatementStatusHistoryDtoMapper statementStatusHistoryDtoMapper;
    private final CreditMapper creditMapper;
    private final ExternalService externalService;
    private final StatementMapper statementMapper;

    @Override
    public void finishRegistration(FinishRegistrationRequestDto requestDto, UUID statementId) {
        log.info("FinishRegistrationImpl: finishRegistration requestDto: {}", requestDto);
        Statement statementFromDb = statementRepo.findById(statementId);
        ScoringDataDto scoringDataDto = scoringDataDtoMapper.toScoringDataDto(requestDto, statementFromDb);
        log.debug("FinishRegistrationImpl: finishRegistration scoringDataDto: {}", scoringDataDto);
        Credit credit = creditCalculation(scoringDataDto, statementFromDb);
        log.debug("FinishRegistrationImpl: finishRegistration credit: {}", credit);
        creditRepo.save(credit);
        statementFromDb.setCredit(credit);

        StatementStatusHistoryDto statementStatusHistoryDto = statementStatusHistoryDtoMapper.toDto(CC_APPROVED);
        statementFromDb = statementMapper.updateStatement(statementFromDb, statementStatusHistoryDto);
        statementRepo.save(statementFromDb);
        log.info("FinishRegistrationImpl: finishRegistration statementFromDb:{}", statementFromDb);
    }

    private Credit creditCalculation(ScoringDataDto scoringDataDto, Statement statement) {
        log.info("FinishRegistrationImpl: creditCalculation scoringDataDto: {}", scoringDataDto);
        ResponseEntity<CreditDto> response;
        try {
            response = externalService.getResponse(scoringDataDto, "http://calculator:8080/calculator/calc", CreditDto.class);
        } catch (ExternalServiceException e) {
            if (e.getStatus().equals(HttpStatus.SERVICE_UNAVAILABLE)) {
                StatementStatusHistoryDto statementStatusHistoryDto = statementStatusHistoryDtoMapper.toDto(CC_DENIED);
                statement = statementMapper.updateStatement(statement, statementStatusHistoryDto);
                statementRepo.save(statement);
            }
            throw e;
        }
        if (response.getStatusCode() != HttpStatus.OK) {
            log.error("Failed to fetch CreditDto: {}", response.getStatusCode());
            throw new ExternalServiceException("Error from external service", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        CreditDto creditDto = response.getBody();
        if (creditDto == null) {
            log.error("creditCalculation failed");
            throw new ExternalServiceException("CreditDto from external service is null", HttpStatus.NO_CONTENT);
        }
        Credit credit = creditMapper.toEntity(creditDto);
        log.info("FinishRegistrationImpl: creditCalculation credit: {}", credit);
        return credit;
    }
}
