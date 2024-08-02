package com.kachalova.gateway.service;


public interface DocumentService {
    void createDocuments(String statementId);

    void signDocuments(String statementId);

    void verify(String statementId);
}
