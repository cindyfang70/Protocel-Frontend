package com.example.protocel;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Protocols implements Serializable, Searchable {

    private String name;
    private String protocolUrl;
    private Boolean confirmedInLab;
    private Boolean confirmedInLiterature;

    public Protocols (JSONObject protocol) {
        this.confirmedInLab = false;
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

    public boolean getLabConfirmation(){return this.confirmedInLab;}
    public boolean getLiteratureConfirmation(){return this.confirmedInLiterature;}

    public String getProtocolUrl() {
        return protocolUrl;
    }
}
