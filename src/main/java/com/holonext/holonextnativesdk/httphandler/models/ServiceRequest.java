package com.holonext.holonextnativesdk.httphandler.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceRequest {

    @SerializedName("holonextApiKey")
    @Expose
    private String holonextApiKey;
    @SerializedName("sceneRelationId")
    @Expose
    private String sceneRelationId;

    public String getHolonextApiKey() {
        return holonextApiKey;
    }

    public void setHolonextApiKey(String holonextApiKey) {
        this.holonextApiKey = holonextApiKey;
    }

    public String getSceneRelationId() {
        return sceneRelationId;
    }

    public void setSceneRelationId(String sceneRelationId) {
        this.sceneRelationId = sceneRelationId;
    }

}

