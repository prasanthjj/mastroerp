package com.erp.mastro.service;

import com.erp.mastro.repository.PartyPriceListRepository;
import com.erp.mastro.service.interfaces.PartyPriceListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class PartyPriceListRelationTest {

    @Autowired
    private PartyPriceListService partyRateService;

    @MockBean
    private PartyPriceListRepository partyPriceListRepository;

}
