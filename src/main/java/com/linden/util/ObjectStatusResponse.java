package com.linden.util;

import java.io.Serializable;

public class ObjectStatusResponse<T extends Serializable> extends StatusResponse {
    T obj;

    public ObjectStatusResponse(T obj, String status) {
        super(status);
        this.obj = obj;
    }

    public ObjectStatusResponse(T obj, String status, String message) {
        super(status, message);
        this.obj = obj;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
