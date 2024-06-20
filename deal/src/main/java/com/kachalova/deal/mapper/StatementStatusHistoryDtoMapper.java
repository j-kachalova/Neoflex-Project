package com.kachalova.deal.mapper;

import com.kachalova.deal.dto.StatementStatusHistoryDto;
import com.kachalova.deal.enums.ApplicationStatus;
import com.kachalova.deal.enums.ChangeType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.kachalova.deal.enums.ApplicationStatus.PREAPPROVAL;

@Component
public class StatementStatusHistoryDtoMapper {
    public StatementStatusHistoryDto toDto(ApplicationStatus applicationStatus){
        StatementStatusHistoryDto statementStatusHistoryDto = StatementStatusHistoryDto.builder()
                .status(applicationStatus)
                .changeType(ChangeType.AUTOMATIC)
                .time(LocalDateTime.now())
                .build();
        return statementStatusHistoryDto;
    }
}
