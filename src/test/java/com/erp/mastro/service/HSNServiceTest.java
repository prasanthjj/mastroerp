package com.erp.mastro.service;


import com.erp.mastro.repository.HSNRepository;
import com.erp.mastro.service.interfaces.HSNService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class HSNServiceTest {

    @Autowired
    private HSNService hsnService;

    @MockBean
    private HSNRepository hsnRepository;

}

