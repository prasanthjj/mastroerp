package com.erp.mastro.service;

import com.erp.mastro.entities.Location;
import com.erp.mastro.model.request.LocationRequestModel;
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


    public List<Location> getAllLocations() {
        List<Location> locationList = new ArrayList<Location>();
        locationRepository.findAll().forEach(location -> locationList.add(location));
        return locationList;
    }

    public Location getLocationId(Long id) {
        return locationRepository.findById(id).get();
    }

    public void saveOrUpdateLocation(LocationRequestModel locationRequestModel) {
        if (locationRequestModel.getId() == null) {
            Location location = new Location();
            setLocationData(locationRequestModel, location);
        } else {
            Location location = locationRepository.findById(locationRequestModel.getId()).get();
            setLocationData(locationRequestModel, location);
        }
    }

    private void setLocationData(LocationRequestModel locationRequestModel, Location location) {
        location.setCountryName(locationRequestModel.getCountryName());
        location.setStateName(locationRequestModel.getStateName());
        location.setCityName(locationRequestModel.getCityName());
        location.setAreaName(locationRequestModel.getAreaName());
        location.setPincode(locationRequestModel.getPincode());
        locationRepository.save(location);
    }

    public void deleteLocation(Long id) {
        locationRepository.deleteById(id);
    }
}
