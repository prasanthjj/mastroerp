package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.ContactDetails;

import java.util.List;

public interface ContactDetailsService {

    List<ContactDetails> getAllContactDetails();

    ContactDetails getContactDetailsById(Long id);

    void saveOrUpdateContactDetails(ContactDetails contactDetails);

    void deleteContactDetails(Long id);
}
