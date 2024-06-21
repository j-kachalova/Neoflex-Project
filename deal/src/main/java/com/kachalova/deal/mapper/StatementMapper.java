package com.kachalova.deal.mapper;

import com.kachalova.deal.dto.StatementStatusHistoryDto;
import com.kachalova.deal.entities.Client;
import com.kachalova.deal.entities.Statement;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class StatementMapper {
    public Statement toStatement(Client client, StatementStatusHistoryDto statementStatusHistoryDto) {
        log.info("StatementMapper toStatement client {}, statementStatusHistoryDto: {}",
                client, statementStatusHistoryDto);
        Statement statement = Statement.builder()
                .client(client)
                .status(statementStatusHistoryDto.getStatus())
                .creationDate(LocalDateTime.now())
                .signDate(LocalDateTime.now())
                .statusHistory(List.of(statementStatusHistoryDto))
                .build();
        log.info("StatementMapper toStatement statement {}", statement);
        return statement;
    }

    public Statement updateStatement(Statement statement, StatementStatusHistoryDto statementStatusHistoryDto) {
        log.info("StatementMapper updateStatement statement: {},statementStatusHistoryDto: {}",
                statement, statementStatusHistoryDto);
        statement.setStatus(statementStatusHistoryDto.getStatus());
        statement.getStatusHistory().add(statementStatusHistoryDto);
        log.info("StatementMapper updateStatement statement {}", statement);
        return statement;
    }

}
