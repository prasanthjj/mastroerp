package com.erp.mastro.model.request;

import com.erp.mastro.entities.SalesOrder;
import com.erp.mastro.entities.SalesOrderProduct;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class SalesOrderRequestModel {

    private Long id;
    private Long partyId;
    private List<SalesOrderRequestModel.SalesOrderProductModel> salesOrderProductModels = new ArrayList<>();
    private String specialInstructions;
    private String remarks;
    private Double grandTotal;
    private Double finalTotal;
    private Double roundOff;

    public SalesOrderRequestModel() {

    }

    public SalesOrderRequestModel(SalesOrder salesOrder) {
        if (salesOrder != null) {
            this.id = salesOrder.getId();
            this.partyId = salesOrder.getParty().getId();
            this.specialInstructions = salesOrder.getSpecialInstructions();
            this.remarks = salesOrder.getRemarks();

            salesOrder.getSalesOrderProductSet().parallelStream().forEach(x -> this.salesOrderProductModels.add(new SalesOrderRequestModel.SalesOrderProductModel(x)));
        }

    }

    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public static class SalesOrderProductModel {

        private Long id;
        private Double quantity;
        private Long productId;

        public SalesOrderProductModel() {

        }

        public SalesOrderProductModel(SalesOrderProduct salesOrderProduct) {
            if (salesOrderProduct != null) {
                this.id = salesOrderProduct.getId();
                this.quantity = salesOrderProduct.getQuantity();
                this.productId = salesOrderProduct.getId();

            }

        }

    }

}
