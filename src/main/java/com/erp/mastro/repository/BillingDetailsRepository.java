package com.erp.mastro.repository;

import com.erp.mastro.entities.BillingDetails;
import com.erp.mastro.entities.ContactDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingDetailsRepository extends CrudRepository<BillingDetails,Long> {
}
