package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.Location;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LocationService {

    List<Location> getAllLocations();

    Location getLocationId(Long id);

    void saveOrUpdateLocation(Location location);

    void deleteLocation(Long id);

}
