package com.kachalova.deal.mapper;

import com.kachalova.deal.dto.StatementStatusHistoryDto;
import com.kachalova.deal.enums.ApplicationStatus;
import com.kachalova.deal.enums.ChangeType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class StatementStatusHistoryDtoMapper {
    public StatementStatusHistoryDto toDto(ApplicationStatus applicationStatus) {
        log.info("StatementStatusHistoryDtoMapper toDto: applicationStatus = {}", applicationStatus);
        StatementStatusHistoryDto statementStatusHistoryDto = StatementStatusHistoryDto.builder()
                .status(applicationStatus)
                .changeType(ChangeType.AUTOMATIC)
                .time(LocalDateTime.now())
                .build();
        log.info("StatementStatusHistoryDtoMapper toDto: statementStatusHistoryDto = {} ", statementStatusHistoryDto);
        return statementStatusHistoryDto;
    }
}
