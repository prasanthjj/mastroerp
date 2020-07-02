package com.erp.mastro.service;

import com.erp.mastro.repository.AssetRepository;
import com.erp.mastro.service.interfaces.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class AssetServiceTest {

    @Autowired
    private AssetService assetService;

    @MockBean
    private AssetRepository assetRepository;


}
