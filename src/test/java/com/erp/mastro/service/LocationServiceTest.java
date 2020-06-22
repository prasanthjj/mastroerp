package com.erp.mastro.service;

import com.erp.mastro.repository.LocationRepository;
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
        Assert.assertEquals(addLocation().getId(),locationService.getLocationId(addLocation().getId()).getId());
    }

    @Test
    public void saveLocationTest() {
        Location location = new Location(1236L,"India"," Kerala","Muvattupuzha","Vazhakulam","686670");
        locationService.saveOrUpdateLocation(location);
        verify(locationRepository,times(1)).save(location);
    }

    @Test
    public void deleteLocationTest() {
        locationService.deleteLocation(addLocation().getId());
        verify(locationRepository,times(1)).deleteById(addLocation().getId());
    }

    @Test
    public void getLocationValidationSucessTest() {
        when(locationRepository.findById(addLocation().getId())).thenReturn(Optional.of(addLocation()));
        //Assert.assertEquals(Optional.of(1236L),locationService.getLocationId(addLocation().getId()).getId());
        Assert.assertEquals("India",locationService.getLocationId(addLocation().getId()).getCountryName());
        Assert.assertEquals("Kerala",locationService.getLocationId(addLocation().getId()).getStateName());
        Assert.assertEquals("Muvattupuzha",locationService.getLocationId(addLocation().getId()).getCityName());
        Assert.assertEquals("Vazhakulam",locationService.getLocationId(addLocation().getId()).getAreaName());
        Assert.assertEquals("686670",locationService.getLocationId(addLocation().getId()).getPincode());
    }

    public List<Location> addLocations() {
        List<Location> locations = new ArrayList<Location>();
        Stream<Location> stream = Stream.of(new Location(1234L,"India","Kerala","Ernakulam","Kakkanad","682030"),
                new Location (1235L,"India","Kerala","Thrissur","Ollur","680306"));
        locations = stream.collect(Collectors.toList());
        return locations;
    }

    public Location addLocation () {
        Location location = new Location(1236L,"India","Kerala","Muvattupuzha","Vazhakulam","686670");
        return location;
    }

}
