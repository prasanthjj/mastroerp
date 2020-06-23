package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.BankDetails;

import java.util.List;

public interface BankDetailsService {

    List<BankDetails> getAllBankDetails();

    BankDetails getBankDetailsById(Long id);

    void saveOrUpdateBankDetails(BankDetails bankDetails);

    void deleteBankDetails(Long id);
}
