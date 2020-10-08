package com.erp.mastro.repository;

import com.erp.mastro.entities.SalesSlipInvoice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesSlipInvoiceRepository extends CrudRepository<SalesSlipInvoice, Long> {
}
