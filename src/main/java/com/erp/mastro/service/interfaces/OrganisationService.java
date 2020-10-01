package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.Organisation;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrganisationService {

    List<Organisation> getAllOrganisation();

    Organisation getOrganisationById(Long id);

    void saveOrUpdateOrganisation(Organisation organisation);

    void deleteOrganisation(Long id);

}
