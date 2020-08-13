package com.erp.mastro.custom.responseBody;

import java.util.List;

public class ListResponse {

    private boolean success;
    private List<? extends Object> data;

    public ListResponse(boolean success) {
        this.success = success;
    }

    public List<? extends Object> getData() {
        return data;
    }

    public void setData(List<? extends Object> data) {
        this.data = data;
    }
}
