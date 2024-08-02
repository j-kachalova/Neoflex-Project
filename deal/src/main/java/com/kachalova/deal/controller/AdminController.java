package com.kachalova.deal.controller;

import com.kachalova.deal.entities.Statement;
import com.kachalova.deal.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/deal/admin/statement")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    @Operation(summary = "получение заявки по ID")
    @GetMapping("/{statementId}")
    public Statement getStatementById(@PathVariable String statementId) {
        log.info("/deal/admin/statement/{statementId} getStatementById statementId:{}", statementId);
        Statement statement = adminService.getStatementById(statementId);
        log.info("/deal/admin/statement/{statementId} getStatementById statement:{}", statement);
        return statement;
    }

    @Operation(summary = "получение всех заявок")
    @GetMapping("")
    public Iterable<Statement> getAllStatement() {
        Iterable<Statement> statements = adminService.getAllStatement();
        log.info("/deal/admin/statement/{statementId} getAllStatement statements:{}", statements);
        return statements;
    }
}
