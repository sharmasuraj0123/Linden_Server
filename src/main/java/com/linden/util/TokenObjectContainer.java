package com.linden.util;

import java.io.Serializable;

public class TokenObjectContainer<T extends Serializable> {

    private String token;

    private T obj;

    public TokenObjectContainer(String token, T obj) {
        this.token = token;
        this.obj = obj;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
