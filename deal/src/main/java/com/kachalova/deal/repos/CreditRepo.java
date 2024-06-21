package com.kachalova.deal.repos;

import com.kachalova.deal.entities.Credit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepo extends CrudRepository<Credit, Integer> {
}
