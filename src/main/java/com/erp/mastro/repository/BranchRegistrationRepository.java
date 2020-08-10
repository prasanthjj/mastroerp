package com.erp.mastro.repository;

import com.erp.mastro.entities.BranchRegistration;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRegistrationRepository extends CrudRepository<BranchRegistration, Long> {

}
