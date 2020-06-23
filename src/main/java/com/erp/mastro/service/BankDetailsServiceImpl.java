package com.erp.mastro.service;

import com.erp.mastro.entities.BankDetails;
import com.erp.mastro.entities.BillingDetails;
import com.erp.mastro.repository.BankDetailsRepository;
import com.erp.mastro.repository.BillingDetailsRepository;
import com.erp.mastro.service.interfaces.BankDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankDetailsServiceImpl implements BankDetailsService {

    @Autowired
    private BankDetailsRepository bankDetailsRepository;

    public List<BankDetails> getAllBankDetails()
    {
        List<BankDetails> bankDetailsList = new ArrayList<BankDetails>();
        bankDetailsRepository.findAll().forEach(bankDetails -> bankDetailsList.add(bankDetails));
        return bankDetailsList;
    }

    public BankDetails getBankDetailsById(Long id)
    {
        return bankDetailsRepository.findById(id).get();
    }

    public void saveOrUpdateBankDetails(BankDetails bankDetails)
    {
        bankDetailsRepository.save(bankDetails);
    }

    public void deleteBankDetails(Long id)
    {
        bankDetailsRepository.deleteById(id);
    }
}
