package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.CreditDetails;

import java.util.List;

public interface CreditDetailsService {

    List<CreditDetails> getAllCreditDetails();

    CreditDetails getCreditDetailsById(Long id);

    void saveOrUpdateCreditDetails(CreditDetails creditDetails);

    void deleteCreditDetails(Long id);
}
