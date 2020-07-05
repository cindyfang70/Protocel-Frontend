package com.example.protocel;


import org.json.JSONException;
import org.json.JSONObject;

public class Protocols {

    private String name;
    private String protocolUrl;
    private Boolean confirmedInLab;
    private Boolean confirmedInLiterature;

    public Protocols (JSONObject protocol) {
        this.confirmedInLab = true;
        this.confirmedInLiterature = true;
        try {
            this.name = protocol.getString("name");
            this.protocolUrl = protocol.getString("url");
        }
        catch (JSONException e){
            e.printStackTrace();
        }

    }


    public String getName() {
        return name;
    }

    public String getProtocolUrl() {
        return protocolUrl;
    }
}
