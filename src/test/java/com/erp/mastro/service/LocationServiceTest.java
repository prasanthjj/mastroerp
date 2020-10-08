package com.erp.mastro.service;

import com.erp.mastro.repository.LocationRepository;
import com.erp.mastro.service.interfaces.LocationService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class LocationServiceTest {

    @Autowired
    private LocationService locationService;

    @MockBean
    private LocationRepository locationRepository;

    @Test(expected = NullPointerException.class)
    public void getData() {

    }
}
