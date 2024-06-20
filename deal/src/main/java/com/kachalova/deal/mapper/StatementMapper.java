package com.kachalova.deal.mapper;

import com.kachalova.deal.dto.StatementStatusHistoryDto;
import com.kachalova.deal.entities.Client;
import com.kachalova.deal.entities.Statement;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class StatementMapper {
    public Statement toStatement(Client client, StatementStatusHistoryDto statementStatusHistoryDto) {
        Statement statement = Statement.builder()
                .client(client)
                .status(statementStatusHistoryDto.getStatus())
                .creationDate(LocalDateTime.now())
                .signDate(LocalDateTime.now())
                .statusHistory(List.of(statementStatusHistoryDto))
                .build();
        return statement;
    }
    public Statement updateStatement( Statement statement, StatementStatusHistoryDto statementStatusHistoryDto) {
        statement.setStatus(statementStatusHistoryDto.getStatus());
        statement.getStatusHistory().add(statementStatusHistoryDto);
        return statement;
    }

}
