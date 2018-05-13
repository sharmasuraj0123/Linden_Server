package com.linden.util;

import com.linden.models.content.Content;

public class ContentContainer {

    private String token;

    private Content content;

    public ContentContainer() {

    }

    public ContentContainer(String token, Content content) {
        this.token = token;
        this.content = content;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
