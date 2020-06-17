package com.erp.mastro.service;

import com.erp.mastro.dao.LocationRepository;
import com.erp.mastro.entities.Location;
import com.erp.mastro.service.interfaces.LocationService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.when;

@SpringBootTest
public class LocationServiceTest {

    @Autowired
    private LocationService locationService;

    @MockBean
    private LocationRepository locationRepository;

    @Test
    public void getLocationsSizeEqualTest() {
        when(locationRepository.findAll()).thenReturn(addLocations());
        Assert.assertEquals(2,locationService.getAllLocations().size());
    }

    @Test
    public void getLocationsSizeNotEqualTest() {
        when(locationRepository.findAll()).thenReturn(addLocations());
        Assert.assertNotEquals(1,locationService.getAllLocations().size());
    }

    public List<Location> addLocations() {

        List<Location> locations = new ArrayList<Location>();
        Stream<Location> stream = Stream.of(new Location(1234L,"India","Kerala","Ernakulam","Kakkanad","682030"),
                new Location (1235L,"India","Kerala","Thrissur","Ollur","680306"));
        locations = stream.collect(Collectors.toList());
        return locations;

    }
}
