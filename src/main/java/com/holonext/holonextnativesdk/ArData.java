package com.holonext.holonextnativesdk;

import androidx.annotation.NonNull;
/**
 *
 * This is ArData class that will be using to set or get optinal features of ArView.
 *
 * <p>Please see the {@link ArView} class for details.
 *
 */
public class ArData{
    /**
     * The HoloNext API key.
     */
    @NonNull
    private String hnarApiKey;

    /**
     * Type of renderer.
     */
    private RendererType hnarRendererType;

    /**
     * Type of toolset.
     */
    private ToolsetType hnarToolsetType;

    /**
     * An API key for development stage. It is available for only test purposes. <b>Don't use it on production stage </b>.
     */
    public final static String TestAPIKey = "#123@we?A23asdkl@#23123";


    public RendererType getHnarRendererType() {
        return hnarRendererType;
    }

    public ToolsetType getHnarToolsetType() {
        return hnarToolsetType;
    }

    public String getHnarApiKey() {
        return hnarApiKey;
    }

    public void setHnarRendererType(RendererType hnarRendererType) {
        this.hnarRendererType = hnarRendererType;
    }

    public void setHnarApiKey(@NonNull String hnarApiKey) {
        this.hnarApiKey = hnarApiKey;
    }

    public void setHnarToolsetType(ToolsetType hnarToolsetType) {
        this.hnarToolsetType = hnarToolsetType;
    }
}

