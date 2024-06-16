package com.kachalova.deal.repos;

import com.kachalova.deal.entities.Client;
import com.kachalova.deal.entities.Statement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StatementRepo extends CrudRepository<Statement, Integer> {
    Statement findById(UUID statementId);
    Statement findByClient(Client client);
}
