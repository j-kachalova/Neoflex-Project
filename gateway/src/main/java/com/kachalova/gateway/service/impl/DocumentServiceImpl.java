package com.kachalova.gateway.service.impl;

import com.kachalova.gateway.client.DealClient;
import com.kachalova.gateway.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DealClient dealClient;

    @Override
    public void createDocuments(String statementId) {
        log.info("DocumentServiceImpl createDocuments statementId:{}", statementId);
        dealClient.createDocuments(statementId);
    }

    @Override
    public void signDocuments(String statementId) {
        log.info("DocumentServiceImpl signDocuments statementId:{}", statementId);
        dealClient.signDocuments(statementId);
    }

    @Override
    public void verify(String statementId) {
        log.info("DocumentServiceImpl verify statementId:{}", statementId);
        dealClient.verify(statementId);
    }
}
