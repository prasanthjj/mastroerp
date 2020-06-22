package com.erp.mastro.repository;

import com.erp.mastro.entities.Branch;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchRepository extends CrudRepository<Branch,Long> {

}
