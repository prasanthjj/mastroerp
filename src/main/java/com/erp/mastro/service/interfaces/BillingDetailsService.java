package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.BillingDetails;

import java.util.List;

public interface BillingDetailsService {

    List<BillingDetails> getAllBillingDetails();

    BillingDetails getBillingDetailsById(Long id);

    void saveOrUpdateBillingDetails(BillingDetails billingDetails);

    void deleteBillingDetails(Long id);
}
