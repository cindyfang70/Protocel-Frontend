package com.example.protocel;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Protocols implements Serializable {

    private String name;
    private String protocolUrl;
    private Boolean confirmedInLab;
    private Boolean confirmedInLiterature;

    public Protocols (JSONObject protocol) {
        try {
            this.name = protocol.getString("name");
            this.protocolUrl = protocol.getString("url");
            this.confirmedInLab = protocol.getBoolean("confirmedInLab");
            this.confirmedInLiterature = protocol.getBoolean("confirmedInBooks");
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
