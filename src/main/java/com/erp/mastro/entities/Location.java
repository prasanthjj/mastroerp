package com.erp.mastro.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "state_name")
    private String stateName;

    @Column(name = "city_name")
    private String cityName;

    @Column(name = "area_name")
    private String areaName;

    @Column(name = "pincode")
    private String pincode;

    public Location(Long id, String countryName, String stateName, String cityName, String areaName, String pincode) {
        this.id = id;
        this.countryName = countryName;
        this.stateName = stateName;
        this.cityName = cityName;
        this.areaName = areaName;
        this.pincode = pincode;
    }

}
