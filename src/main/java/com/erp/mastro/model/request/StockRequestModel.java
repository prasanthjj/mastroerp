package com.erp.mastro.model.request;

import com.erp.mastro.entities.Stock;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class StockRequestModel {

    private Long id;
    private Double openingStock;
    private Double currentStock;
    private Double reorderLevel;
    private Double rejectedStock;
    private Double reserveStock;
    private Double minimumOrderQuantity;
    private Double minimumLeadTime;
    private Double maximumLeadTime;
    private Double minimumStockQuantity;
    private Double maximumStockQuantity;
    private Long productId;

    public StockRequestModel() {

    }

    public StockRequestModel(Stock stockDetails) {
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
