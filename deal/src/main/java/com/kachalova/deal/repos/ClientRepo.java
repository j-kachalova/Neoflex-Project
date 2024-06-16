package com.kachalova.deal.repos;

import com.kachalova.deal.entities.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepo extends CrudRepository<Client, Integer>{
    Client findByEmail(String email);
    Client findByAccountNumber(String accountNumber);

}
