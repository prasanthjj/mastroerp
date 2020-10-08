package com.erp.mastro.service;

import com.erp.mastro.entities.CreditDetails;
import com.erp.mastro.repository.CreditDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreditDetailsServiceImpl {

    @Autowired
    private CreditDetailsRepository creditDetailsRepository;

    public List<CreditDetails> getAllCreditDetails()
    {
        List<CreditDetails> creditDetailsList = new ArrayList<CreditDetails>();
        creditDetailsRepository.findAll().forEach(creditDetails -> creditDetailsList.add(creditDetails));
        return creditDetailsList;
    }

    public CreditDetails getCreditDetailsById(Long id)
    {
        return creditDetailsRepository.findById(id).get();
    }

    public void saveOrUpdateCreditDetails(CreditDetails creditDetails)
    {
        creditDetailsRepository.save(creditDetails);
    }

    public void deleteCreditDetails(Long id)
    {
        creditDetailsRepository.deleteById(id);
    }
}
