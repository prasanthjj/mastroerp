package com.erp.mastro.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class ItemPartyRelationModel {

    private Long id;
    private Long productPartyRateId;
    private Double rate;
    private Double discount;
    private Double allowedPriceDevPerUpper;
    private Double allowedPriceDevPerLower;
    private Integer creditDays;
    private String remarks;
}
