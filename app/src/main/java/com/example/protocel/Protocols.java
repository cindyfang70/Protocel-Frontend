package com.example.protocel;


public class Protocols {

    private final String name;
    private final String protocolUrl;

    public Protocols (String name, String url) {
        this.name = name;
        this.protocolUrl = url;
    }

    public String getName() {
        return name;
    }

    public String getProtocolUrl() {
        return protocolUrl;
    }
}
