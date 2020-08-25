package com.erp.mastro.model.request;

import com.erp.mastro.entities.Indent;
import com.erp.mastro.entities.ItemStockDetails;
import com.erp.mastro.entities.Stock;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class IndentModel {

    private Long id;
    private String indentPriority;
    private Long branchId;
    private Long productId;
    private List<IndentModel.IndentItemStockDetailsModel> indentItemStockDetailsModels = new ArrayList<>();

    public IndentModel() {

    }

    public IndentModel(Indent indent) {
        if (indent != null) {
            this.id = indent.getId();

            indent.getItemStockDetailsSet().parallelStream().forEach(x -> this.indentItemStockDetailsModels.add(new IndentModel.IndentItemStockDetailsModel(x)));

        }
    }

    @Setter(AccessLevel.PUBLIC)
    @Getter(AccessLevel.PUBLIC)
    public static class IndentItemStockDetailsModel {

        private Long id;
        private Double quantityToIndent;
        private Date requiredByDate;
        private String srequiredByDate;
        private String soReferenceNo;
        private Long stockId;
        private Long uomId;
        private Stock stock;

        public IndentItemStockDetailsModel() {
        }

        public IndentItemStockDetailsModel(ItemStockDetails itemStockDetails) {
            if (itemStockDetails != null) {
                this.id = itemStockDetails.getId();
                this.stock = itemStockDetails.getStock();
            }
        }
    }

    @Override
    public String toString() {
        return "IndentModel{" +
                "id=" + id +
                ", indentPriority='" + indentPriority + '\'' +
                ", branchId=" + branchId +
                ", indentItemStockDetailsModels=" + indentItemStockDetailsModels +
                '}';
    }
}
