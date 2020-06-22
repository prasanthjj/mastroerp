package com.erp.mastro.repository;

import com.erp.mastro.entities.Organisation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganisationRepository extends CrudRepository<Organisation,Long> {

}
