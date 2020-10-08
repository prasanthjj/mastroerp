package com.erp.mastro.service.interfaces;

import com.erp.mastro.entities.Location;
import com.erp.mastro.model.request.LocationRequestModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LocationService {

    List<Location> getAllLocations();

    Location getLocationId(Long id);

    void saveOrUpdateLocation(LocationRequestModel locationRequestModel);

    void deleteLocation(Long id);

}
