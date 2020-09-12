package com.holonext.holonextnativesdk;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.ar.sceneform.ux.ArFragment;

/**
 *
 * Main class of HoloNextNativeSDK.This will be using to provide our service.
 *
 * @version 1.0
 * @since 1.0
 */
public class ArView extends ArFragment {
    /**
     * ArData is the data class used to store the options that will be used when creating the HoloNextARView.
     */
    @NonNull
    private ArData arData;

    /**
     * Default contructor.Using an API key that created for test purposes by default.
     * Other options;
     *  <p>- Using default renderer type as default
     *  <p>- Using default toolset type as basic
     */
    public ArView(){
        if (this.arData == null)
            this.arData = new ArData();

        this.arData.setHnarApiKey(ArData.TestAPIKey);
        this.arData.setHnarRendererType(RendererType.DEFAULT);
        this.arData.setHnarToolsetType(ToolsetType.BASIC);
    }

    public ArView(@NonNull ArData arData){
        if(this.arData == null)
            this.arData = new ArData();

        this.arData.setHnarToolsetType(arData.getHnarToolsetType());
        this.arData.setHnarRendererType(arData.getHnarRendererType());
        this.arData.setHnarApiKey(arData.getHnarApiKey());
    }

    /**
     *
     * @return ArData
     */
    public ArData getArData(){
        return this.arData;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);

        TypedArray arr = getActivity().obtainStyledAttributes(attrs,R.styleable.HoloNextArView);

        CharSequence apiKey = arr.getText(R.styleable.HoloNextArView_hnar_api_key);
        RendererType rendererType = RendererType.values()[arr.getInt(R.styleable.HoloNextArView_hnar_renderer,0)];
        ToolsetType toolsetType = ToolsetType.values()[arr.getInt(R.styleable.HoloNextArView_hnar_toolset,0)];

        arr.recycle();
    }
}
