package com.erp.mastro.controller;

import com.erp.mastro.custom.responseBody.GenericResponse;
import com.erp.mastro.entities.Location;
import com.erp.mastro.model.request.LocationRequestModel;
import com.erp.mastro.service.interfaces.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping("/master/getLocation")
    public String getLocation(Model model) {

        try {
            List<Location> locationList = locationService.getAllLocations();

            return "views/locationMaster";
        } catch (Exception e) {
            throw e;
        }

    }

    @PostMapping("master/saveLocation")
    public String saveLocation(@ModelAttribute("locationForm") @Valid LocationRequestModel locationRequestModel, HttpServletRequest request, Model model) {

        try {
            locationService.saveOrUpdateLocation(locationRequestModel);
            return "redirect:/master/getLocation";
        } catch (Exception e) {
            throw e;
        }
    }

    @GetMapping("/master/getLocationForEdit")
    @ResponseBody
    public GenericResponse getLocationForEdit(Model model, HttpServletRequest request, @RequestParam("locationId") Long locationId) {

        try {

            Location locationDetails = locationService.getLocationId(locationId);
            return new GenericResponse(true, "get location details")
                    .setProperty("locationId", locationDetails.getId())
                    .setProperty("countryName", locationDetails.getCountryName())
                    .setProperty("stateName", locationDetails.getStateName())
                    .setProperty("cityName", locationDetails.getCityName())
                    .setProperty("areaName", locationDetails.getAreaName())
                    .setProperty("pincode", locationDetails.getPincode());

        } catch (Exception e) {

            return new GenericResponse(false, e.getMessage());

        }

    }


}
