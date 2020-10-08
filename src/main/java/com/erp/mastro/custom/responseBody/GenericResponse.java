package com.erp.mastro.custom.responseBody;

import java.util.HashMap;
import java.util.Map;

public class GenericResponse {

    private boolean success;
    private String message;
    private Map<String, Object> data = new HashMap<>(1);

    public GenericResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public GenericResponse setProperty(String property, Object value) {
        if (property != null)
            this.data.put(property, value);
        return this;
    }
}
