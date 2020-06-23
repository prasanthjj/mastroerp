package com.erp.mastro.repository;

import com.erp.mastro.entities.Branch;
import com.erp.mastro.entities.ContactDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactDetailsRepository extends CrudRepository<ContactDetails,Long> {
}
