package com.holonext.holonextnativesdk;

import androidx.annotation.NonNull;

import com.holonext.holonextnativesdk.models.types.RendererType;
import com.holonext.holonextnativesdk.models.types.ToolsetType;

/**
 *
 * This is HoloNextArConfig class that will be using to set or get optinal features of HoloNextArViewer.
 *
 * <p>Please see the {@link HoloNextArViewer} class for details.
 *
 */
public class HoloNextArConfig {
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
     * HoloNextArViewer context reference
     */
    private HoloNextArViewer hnarArViewer;

    /**
     * An API key for development stage. It is available for only test purposes.
     * <b>Don't use it on production stage and use API key provided by our-service only for you.</b>.
     */
    public final static String TestAPIKey = "XRoZUSFPSH6qBqrz62sERFZbsLwfzuPLLE2wcAC9LMIZ7cs0iDhSW8jrLROHFLKB";


    /**
     * Get the type of renderer already selected.
     * @return RendererType
     */
    public RendererType getHnarRendererType() {
        return hnarRendererType;
    }

    /**
     * Get the HoloNextArViewer instance.
     * @return HoloNextArViewer
     */
    public HoloNextArViewer getHnarArViewer() { return hnarArViewer; }

    /**
     * Get the type of toolset already selected.
     * @return ToolsetType
     */
    public ToolsetType getHnarToolsetType() {
        return hnarToolsetType;
    }

    /**
     * Get the HoloNext API key already used.
     * @return String
     */
    public String getHnarApiKey() {
        return hnarApiKey;
    }

    /**
     * Set the HoloNext renderer type.
     * @param hnarRendererType
     */
    public void setHnarRendererType(RendererType hnarRendererType) {
        this.hnarRendererType = hnarRendererType;
    }

    /**
     * Set the HoloNextArViewer instance.
     * @param holoNextArViewer
     */
    public void setHnarArViewer(HoloNextArViewer holoNextArViewer){
        this.hnarArViewer = holoNextArViewer;
    }

    /**
     * Set the HoloNext API key.
     * @param hnarApiKey
     */
    public void setHnarApiKey(@NonNull String hnarApiKey) {
        this.hnarApiKey = hnarApiKey;
    }

    /**
     * Set the HoloNext toolset type.
     * @param hnarToolsetType
     */
    public void setHnarToolsetType(ToolsetType hnarToolsetType) {
        this.hnarToolsetType = hnarToolsetType;
    }
}

