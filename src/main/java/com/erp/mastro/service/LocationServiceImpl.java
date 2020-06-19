package com.erp.mastro.service;

import com.erp.mastro.entities.Location;
import com.erp.mastro.repository.LocationRepository;
import com.erp.mastro.service.interfaces.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public List<Location> getAllLocations() {
        List<Location> locationList = new ArrayList<Location>();
        locationRepository.findAll().forEach(location -> locationList.add(location));
        return locationList;
    }

    @Override
    public Location getLocationId(Long id) {
        return locationRepository.findById(id).get();
    }

    @Override
    public void saveOrUpdateLocation(Location location) {
        locationRepository.save(location);
    }

    @Override
    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }
}
