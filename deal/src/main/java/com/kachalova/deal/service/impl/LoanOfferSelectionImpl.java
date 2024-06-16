package com.kachalova.deal.service.impl;

import com.kachalova.deal.entities.Statement;
import com.kachalova.deal.dto.LoanOfferDto;
import com.kachalova.deal.dto.StatementStatusHistoryDto;
import com.kachalova.deal.enums.ApplicationStatus;
import com.kachalova.deal.enums.ChangeType;
import com.kachalova.deal.repos.StatementRepo;
import com.kachalova.deal.service.LoanOfferSelection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LoanOfferSelectionImpl implements LoanOfferSelection {
    private final StatementRepo statementRepo;
    @Override
    public void selectOffer(LoanOfferDto loanOfferDto) {
        Statement statementFromDb = statementRepo.findById(loanOfferDto.getStatementId());
        StatementStatusHistoryDto statementStatusHistoryDto = StatementStatusHistoryDto.builder()
                .status(ApplicationStatus.APPROVED)
                .time(LocalDateTime.now())
                .changeType(ChangeType.AUTOMATIC)
                .build();
        statementFromDb.getStatusHistory().add(statementStatusHistoryDto);
        statementFromDb.setAppliedOffer(loanOfferDto);
        statementRepo.save(statementFromDb);
    }
}
