package com.kachalova.deal.service.impl;

import com.kachalova.deal.dto.LoanOfferDto;
import com.kachalova.deal.dto.StatementStatusHistoryDto;
import com.kachalova.deal.entities.Statement;
import com.kachalova.deal.enums.ApplicationStatus;
import com.kachalova.deal.enums.ChangeType;
import com.kachalova.deal.mapper.StatementMapper;
import com.kachalova.deal.mapper.StatementStatusHistoryDtoMapper;
import com.kachalova.deal.repos.StatementRepo;
import com.kachalova.deal.service.LoanOfferSelection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static com.kachalova.deal.enums.ApplicationStatus.APPROVED;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoanOfferSelectionImpl implements LoanOfferSelection {
    private final StatementRepo statementRepo;
    private final StatementStatusHistoryDtoMapper statementStatusHistoryDtoMapper;

    @Override
    public void selectOffer(LoanOfferDto loanOfferDto) {
        log.info("selectOffer loanOfferDto: {}", loanOfferDto);
        Statement statementFromDb = statementRepo.findById(loanOfferDto.getStatementId());
        StatementStatusHistoryDto statementStatusHistoryDto = statementStatusHistoryDtoMapper.toDto(APPROVED);
        statementFromDb.getStatusHistory().add(statementStatusHistoryDto);
        statementFromDb.setStatus(statementStatusHistoryDto.getStatus());
        statementFromDb.setAppliedOffer(loanOfferDto);
        statementRepo.save(statementFromDb);
        log.info("selectOffer statementFromDb: {}", statementFromDb);
    }
}
