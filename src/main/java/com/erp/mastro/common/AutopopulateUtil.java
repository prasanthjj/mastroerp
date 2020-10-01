package com.erp.mastro.common;

import com.erp.mastro.custom.responseBody.ListResponse;
import com.erp.mastro.entities.Product;

import java.util.List;


public class AutopopulateUtil {

    public static ListResponse populateResponseProducts(List<Product> products) {
        ListResponse response = new ListResponse(true);
        response.setData(products);
        return response;
    }
}
