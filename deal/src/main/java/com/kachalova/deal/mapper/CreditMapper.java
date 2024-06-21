package com.kachalova.deal.mapper;

import com.kachalova.deal.dto.CreditDto;
import com.kachalova.deal.entities.Credit;
import com.kachalova.deal.enums.CreditStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreditMapper {
    public Credit toEntity(CreditDto creditDto) {
        log.info("CreditMapper toEntity creditDto: {}", creditDto);
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
        log.info("CreditMapper toEntity creditDto: {}", creditDto);
        return credit;
    }
}
