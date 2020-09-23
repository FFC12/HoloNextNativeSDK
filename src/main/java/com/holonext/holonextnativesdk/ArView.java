package com.holonext.holonextnativesdk;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.ar.core.exceptions.UnsupportedConfigurationException;
import com.google.ar.sceneform.ux.ArFragment;
import com.holonext.holonextnativesdk.exception.HolonextSdkInitializeException;
import com.holonext.holonextnativesdk.httphandler.RequestHandler;
import com.holonext.holonextnativesdk.renderer.ArRenderer;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 *
 * Main class of HoloNextNativeSDK.This will be using to provide our service.
 *
 * @version 1.0
 * @since 1.0
 */
public class ArView extends ArFragment {
    /**
     * ArConfig is the data class used to store the options that will be used when creating the HoloNextARView.
     */
    @NonNull
    private ArConfig arConfig;

    private ArRenderer arRenderer;

    /**
     * Default contructor.Using an API key that created for test purposes by default.
     * Other options;
     *  <p>- Using default renderer type as default
     *  <p>- Using default toolset type as basic
     */
    public ArView(){
        if (this.arConfig == null)
            this.arConfig = new ArConfig();

        this.arConfig.setHnarApiKey(ArConfig.TestAPIKey);
        this.arConfig.setHnarRendererType(RendererType.DEFAULT);
        this.arConfig.setHnarToolsetType(ToolsetType.BASIC);
    }

    public ArView(@NonNull ArConfig arConfig){
        if(this.arConfig == null)
            this.arConfig = new ArConfig();

        this.arConfig.setHnarToolsetType(arConfig.getHnarToolsetType());
        this.arConfig.setHnarRendererType(arConfig.getHnarRendererType());
        this.arConfig.setHnarApiKey(arConfig.getHnarApiKey());
    }

    /**
     *
     * @return ArConfig
     */
    public ArConfig getArConfig(){
        return this.arConfig;
    }


    //TODO: Replace init function and create build function on ArRenderer class.
    public void Init(@NonNull String sceneRelationId) throws HolonextSdkInitializeException {
        if (sceneRelationId.isEmpty()){
            throw new HolonextSdkInitializeException("You must specify a 'scene relation id' to launch model its has.");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RequestHandler.PostJsonBasedRequest(this.getActivity(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(getContext(),response.toString(),Toast.LENGTH_LONG).show();
                Log.d("On Response ",response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse errorRes = error.networkResponse;
                String stringData = "";
                if(errorRes != null && errorRes.data != null){
                    try {
                        stringData = new String(errorRes.data,"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
                Log.e("Error",stringData);
                Log.e("RequestHandler ","Something went wrong");
            }
        });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onInflate(@NonNull final Context context, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);

        TypedArray arr = getActivity().obtainStyledAttributes(attrs,R.styleable.ArView);

        CharSequence apiKey = arr.getText(R.styleable.ArView_hnar_api_key);
        RendererType rendererType = RendererType.values()[arr.getInt(R.styleable.ArView_hnar_renderer,0)];
        ToolsetType toolsetType = ToolsetType.values()[arr.getInt(R.styleable.ArView_hnar_toolset,0)];

        arr.recycle();
    }
}
