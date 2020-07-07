package com.erp.mastro.service;

import com.erp.mastro.repository.PartyRepository;
import com.erp.mastro.service.interfaces.PartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class PartyServiceTest {

    @Autowired
    private PartyService partyService;

    @MockBean
    private PartyRepository partyRepository;

}
