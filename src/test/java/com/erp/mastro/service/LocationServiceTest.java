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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

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
    
    @Test
    public void getLocationByIdTest() {
        when(locationRepository.findById(1236L)).thenReturn(Optional.of(addLocation()));
        Assert.assertEquals(addLocation(),locationService.getLocationId(1236L));
    }

    @Test
    public void saveLocationTest() {
        locationService.saveOrUpdateLocation(addLocation());
        verify(locationRepository,times(1)).save(addLocation());
    }

    @Test
    public void deleteLocationTest() {
        locationService.deleteLocation(addLocation().getId());
        verify(locationRepository,times(1)).deleteById(addLocation().getId());
    }

    @Test
    public void getLocationIdNotNullTest() {
        when(locationRepository.findById(1234L)).thenReturn(Optional.of(addLocation()));
        Assert.assertNotNull("LocationId is not null",locationService.getLocationId(1234L));
    }

    @Test
    public void getLocationValidationSucessTest() {
        when(locationRepository.findById(addLocation().getId())).thenReturn(Optional.of(addLocation()));
        Assert.assertEquals(1236L,locationService.getLocationId(addLocation().getId()));
        Assert.assertEquals("India",locationService.getLocationId(1236L).getCountryName());
        Assert.assertEquals("Kerala",locationService.getLocationId(1236L).getStateName());
        Assert.assertEquals("Muvattupuzha",locationService.getLocationId(1236L).getCityName());
        Assert.assertEquals("Vazhakulam",locationService.getLocationId(1236L).getAreaName());
        Assert.assertEquals("686670",locationService.getLocationId(1236L).getPincode());
    }

    public List<Location> addLocations() {
        List<Location> locations = new ArrayList<Location>();
        Stream<Location> stream = Stream.of(new Location(1234L,"India","Kerala","Ernakulam","Kakkanad","682030"),
                new Location (1235L,"India","Kerala","Thrissur","Ollur","680306"));
        locations = stream.collect(Collectors.toList());
        return locations;
    }

    public Location addLocation() {
        Location location = new Location(1236L,"India"," Kerala","Muvattupuzha","Vazhakulam","686670");
        return location;
    }

}
