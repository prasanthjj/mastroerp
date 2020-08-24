package com.erp.mastro.model.request;

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
