package com.erp.mastro.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class StockRequestModel {

    private Long id;
    private Long openingStock;
    private Long currentStock;
    private int reorderLevel;
    private int rejectedStock;
    private int reserveStock;
    private int minimumOrderQuantity;
    private int minimumLeadTime;
    private Long maximumLeadTime;
    private int minimumStockQuantity;
    private Long maximumStockQuantity;
    private Long productId;

}
