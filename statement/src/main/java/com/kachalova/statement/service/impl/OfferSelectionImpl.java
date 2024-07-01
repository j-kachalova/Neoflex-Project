package com.kachalova.statement.service.impl;

import com.kachalova.statement.client.DealClient;
import com.kachalova.statement.dto.LoanOfferDto;
import com.kachalova.statement.service.OfferSelection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfferSelectionImpl implements OfferSelection {
    private final DealClient dealClient;

    @Override
    public void sendLoanOfferDto(LoanOfferDto loanOfferDto) {
        log.info("OfferSelectionImpl sendLoanOfferDto loanOfferDto:{}", loanOfferDto);
        dealClient.selectOffer(loanOfferDto);
    }
}
