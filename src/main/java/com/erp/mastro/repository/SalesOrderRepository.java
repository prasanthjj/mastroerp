package com.erp.mastro.repository;

import com.erp.mastro.entities.SalesOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesOrderRepository extends CrudRepository<SalesOrder, Long> {

}
