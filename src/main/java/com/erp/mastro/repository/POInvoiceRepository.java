package com.erp.mastro.repository;

import com.erp.mastro.entities.POInvoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface POInvoiceRepository extends CrudRepository<POInvoice, Long> {
}
