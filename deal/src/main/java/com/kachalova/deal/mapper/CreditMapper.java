package com.kachalova.deal.mapper;

import com.kachalova.deal.dto.CreditDto;
import com.kachalova.deal.entities.Credit;
import com.kachalova.deal.enums.CreditStatus;
import org.springframework.stereotype.Component;

@Component
public class CreditMapper {
    public Credit toEntity(CreditDto creditDto) {
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
