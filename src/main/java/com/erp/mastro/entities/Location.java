package com.erp.mastro.entities;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "location")
public class Location
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "country_name")
    protected String countryName;

    @Column(name = "state_name")
    protected String stateName;

    @Column(name = "city_name")
    protected String cityName;

    @Column(name = "area_name")
    protected String areaName;

    @Column(name = "pincode")
    protected String pincode;


    public Long getId() {
        return  id;
    }

    public void setId(Long Id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

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

     public void setPincode(String pincode) {
        this.pincode=pincode;
     }





}
