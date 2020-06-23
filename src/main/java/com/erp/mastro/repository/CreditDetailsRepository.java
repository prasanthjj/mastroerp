package com.erp.mastro.repository;

import com.erp.mastro.entities.BankDetails;
import com.erp.mastro.entities.CreditDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditDetailsRepository extends CrudRepository<CreditDetails,Long> {
}
