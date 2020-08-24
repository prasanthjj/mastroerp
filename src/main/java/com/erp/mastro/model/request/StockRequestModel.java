package com.erp.mastro.model.request;

import com.erp.mastro.entities.StockDetails;
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

    public StockRequestModel() {

    }

    public StockRequestModel(StockDetails stockDetails) {
        if (stockDetails != null) {
            this.id = stockDetails.getId();
            this.openingStock = stockDetails.getOpeningStock();
            this.currentStock = stockDetails.getCurrentStock();
            this.reorderLevel = stockDetails.getReorderLevel();
            this.rejectedStock = stockDetails.getRejectedStock();
            this.reserveStock = stockDetails.getReserveStock();
            this.minimumOrderQuantity = stockDetails.getMinimumOrderQuantity();
            this.minimumLeadTime = stockDetails.getMinimumLeadTime();
            this.minimumStockQuantity = stockDetails.getMinimumStockQuantity();
            this.maximumStockQuantity = stockDetails.getMaximumStockQuantity();
            this.productId = stockDetails.getProduct().getId();

        }
    }

}
