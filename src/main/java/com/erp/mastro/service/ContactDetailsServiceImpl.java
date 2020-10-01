package com.erp.mastro.service;

import com.erp.mastro.entities.ContactDetails;
import com.erp.mastro.repository.ContactDetailsRepository;
import com.erp.mastro.service.interfaces.ContactDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContactDetailsServiceImpl implements ContactDetailsService {

    @Autowired
    private ContactDetailsRepository contactDetailsRepository;

    public List<ContactDetails> getAllContactDetails()
    {
        List<ContactDetails> contactDetailsList = new ArrayList<ContactDetails>();
        contactDetailsRepository.findAll().forEach(contactDetails -> contactDetailsList.add(contactDetails));
        return contactDetailsList;
    }

    public ContactDetails getContactDetailsById(Long id)
    {
        return contactDetailsRepository.findById(id).get();
    }

    public void saveOrUpdateContactDetails(ContactDetails contactDetails)
    {
        contactDetailsRepository.save(contactDetails);
    }

    public void deleteContactDetails(Long id)
    {
        contactDetailsRepository.deleteById(id);
    }

}
