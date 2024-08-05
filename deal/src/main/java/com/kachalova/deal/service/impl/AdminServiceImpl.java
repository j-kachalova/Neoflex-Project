package com.kachalova.deal.service.impl;

import com.kachalova.deal.entities.Statement;
import com.kachalova.deal.repos.StatementRepo;
import com.kachalova.deal.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {
    private final StatementRepo statementRepo;
    @Override
    public Statement getStatementById(String statementId){
        log.info("AdminServiceImpl getStatementById statementId:{}", statementId);
        Statement statement = statementRepo.findById(UUID.fromString(statementId));
        log.info("AdminServiceImpl getStatementById statement:{}", statement);
        return statement;
    }
    @Override
    public Iterable<Statement> getAllStatement(){
        Iterable<Statement> statements = statementRepo.findAll();
        log.info("AdminServiceImpl getStatementById statements:{}", statements);
        return statements;
    }
}
