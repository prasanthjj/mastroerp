package com.erp.mastro.service;

import com.erp.mastro.entities.BillingDetails;
import com.erp.mastro.entities.ContactDetails;
import com.erp.mastro.repository.BillingDetailsRepository;
import com.erp.mastro.repository.ContactDetailsRepository;
import com.erp.mastro.service.interfaces.BillingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BillingDetailsServiceImpl implements BillingDetailsService {

    @Autowired
    private BillingDetailsRepository billingDetailsRepository;

    public List<BillingDetails> getAllBillingDetails()
    {
        List<BillingDetails> billingDetailsList = new ArrayList<BillingDetails>();
        billingDetailsRepository.findAll().forEach(billingDetails -> billingDetailsList.add(billingDetails));
        return billingDetailsList;
    }

    public BillingDetails getBillingDetailsById(Long id)
    {
        return billingDetailsRepository.findById(id).get();
    }

    public void saveOrUpdateBillingDetails(BillingDetails billingDetails)
    {
        billingDetailsRepository.save(billingDetails);
    }

    public void deleteBillingDetails(Long id)
    {
        billingDetailsRepository.deleteById(id);
    }
}
