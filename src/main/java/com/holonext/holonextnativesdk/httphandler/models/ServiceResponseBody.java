package com.holonext.holonextnativesdk.httphandler.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceResponseBody {

    @SerializedName("modelViewer")
    @Expose
    private String modelViewer;
    @SerializedName("glbModel")
    @Expose
    private String glbModel;
    @SerializedName("usdzModel")
    @Expose
    private String usdzModel;

    public String getModelViewer() {
        return modelViewer;
    }

    public void setModelViewer(String modelViewer) {
        this.modelViewer = modelViewer;
    }

    public String getGlbModel() {
        return glbModel;
    }

    public void setGlbModel(String glbModel) {
        this.glbModel = glbModel;
    }

    public String getUsdzModel() {
        return usdzModel;
    }

    public void setUsdzModel(String usdzModel) {
        this.usdzModel = usdzModel;
    }

}