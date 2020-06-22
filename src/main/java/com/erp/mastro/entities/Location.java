package com.erp.mastro.entities;

import javax.persistence.*;

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

    public Location() { }

    public Location(Long id, String countryName, String stateName, String cityName, String areaName, String pincode) {
        this.id = id;
        this.countryName = countryName;
        this.stateName = stateName;
        this.cityName = cityName;
        this.areaName = areaName;
        this.pincode = pincode;
    }

    public Long getId() {
        return  id;
    }

    public void setId(Long Id) { this.id = id; }

    public String getCountryName() { return countryName; }

    public void setCountryName(String countryName) {
        this.cityName = countryName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

     public String getAreaName() {
        return areaName;
     }

     public void setAreaName(String areaName) {
         this.areaName = areaName;
     }

     public String getPincode() {
         return pincode;
     }

     public void setPincode(String pincode) { this.pincode=pincode; }

}
