package com.erp.mastro.service;

import com.erp.mastro.entities.Organisation;
import com.erp.mastro.repository.OrganisationRepository;
import com.erp.mastro.service.interfaces.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class OrganisationServiceImpl implements OrganisationService {

    @Autowired
    private OrganisationRepository organisationRepository;

    public List <Organisation> getAllOrganisation() {
        List<Organisation> organisationList = new ArrayList<Organisation>();
        organisationRepository.findAll().forEach(organisation -> organisationList.add(organisation));
        return organisationList;
    }

    public Organisation getOrganisationById(Long id) { return organisationRepository.findById(id).get(); }

    public void saveOrUpdateOrganisation(Organisation organisation) {organisationRepository.save(organisation); }

    public void deleteOrganisation(Long id) {organisationRepository.deleteById(id); }


}
