package com.holonext.holonextnativesdk.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceResponse {

    @SerializedName("body")
    @Expose
    private ServiceResponseBody body;

    public ServiceResponseBody getBody() {
        return body;
    }

    public void setBody(ServiceResponseBody body) {
        this.body = body;
    }

}