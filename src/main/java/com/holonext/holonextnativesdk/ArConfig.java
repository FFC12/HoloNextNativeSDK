package com.holonext.holonextnativesdk;

import androidx.annotation.NonNull;
/**
 *
 * This is ArConfig class that will be using to set or get optinal features of ArView.
 *
 * <p>Please see the {@link ArView} class for details.
 *
 */
public class ArConfig {
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
     * An API key for development stage. It is available for only test purposes.
     * <b>Don't use it on production stage and use API key provided by our-service only for you.</b>.
     */
    public final static String TestAPIKey = "XRoZUSFPSH6qBqrz62sERFZbsLwfzuPLLE2wcAC9LMIZ7cs0iDhSW8jrLROHFLKB";


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

