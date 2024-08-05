package com.kachalova.deal.service;

import com.kachalova.deal.entities.Statement;

import java.util.UUID;

public interface AdminService {
    Statement getStatementById(String statementId);
    Iterable<Statement> getAllStatement();
}
