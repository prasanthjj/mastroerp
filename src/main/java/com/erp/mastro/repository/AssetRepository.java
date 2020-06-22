package com.erp.mastro.repository;

import com.erp.mastro.entities.Assets;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends CrudRepository<Assets,Long> {

}
