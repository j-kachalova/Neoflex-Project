package com.kachalova.gateway.controller;

import com.kachalova.gateway.service.DocumentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/document/{statementId}")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;
    @Operation(summary = "запрос на отправку документов")
    @PostMapping()
    public void createDocuments(@PathVariable String statementId) {
        log.info("/document/{statementId} statementId:{}", statementId);
        documentService.createDocuments(statementId);
    }
    @Operation(summary = "запрос на подписание документов")
    @PostMapping("/sign")
    public void signDocuments(@PathVariable String statementId) {
        log.info("/document/{statementId}/sign statementId:{}", statementId);
        documentService.signDocuments(statementId);
    }
    @Operation(summary = "подписание документов")
    @PostMapping("/sign/code")
    public void verify(@PathVariable String statementId) {
        log.info("/document/{statementId}/sign/code statementId:{}", statementId);
        documentService.verify(statementId);
    }
}
