package com.linden.models;

import java.util.List;

public class DataResponse {
    private boolean success;
    private Integer count;
    private List data;

    public DataResponse(boolean success, int count, List data) {
        this.success = success;
        this.count = count;
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
