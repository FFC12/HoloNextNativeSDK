package com.holonext.holonextnativesdk;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.holonext.holonextnativesdk.exception.HolonextSdkInitializeException;
import com.holonext.holonextnativesdk.httphandler.ApiClient;
import com.holonext.holonextnativesdk.httphandler.ApiInterface;
import com.holonext.holonextnativesdk.httphandler.RequestHandler;
import com.holonext.holonextnativesdk.httphandler.models.ServiceFailedResponse;
import com.holonext.holonextnativesdk.httphandler.models.ServiceRequest;
import com.holonext.holonextnativesdk.httphandler.models.ServiceResponse;
import com.holonext.holonextnativesdk.httphandler.models.ServiceResponseBody;
import com.holonext.holonextnativesdk.renderer.ArRenderer;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

    private ApiInterface apiService;

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
        this.arConfig.setHnarArView(this);
    }

    public ArView(@NonNull ArConfig arConfig){
        if(this.arConfig == null)
            this.arConfig = new ArConfig();

        this.arConfig.setHnarToolsetType(arConfig.getHnarToolsetType());
        this.arConfig.setHnarRendererType(arConfig.getHnarRendererType());
        this.arConfig.setHnarApiKey(arConfig.getHnarApiKey());
        this.arConfig.setHnarArView(this);
    }

    /**
     *
     * @return ArConfig
     */
    public ArConfig getArConfig(){
        return this.arConfig;
    }

    public void init(@NonNull String sceneRelationId) throws HolonextSdkInitializeException {
        if (sceneRelationId.isEmpty()){
            throw new HolonextSdkInitializeException("You must specify a 'scene relation id' to launch model its has.");
        }

        if(apiService == null)
            apiService = ApiClient.getClient().create(ApiInterface.class);

        ServiceRequest serviceRequest = new ServiceRequest();
        serviceRequest.setHolonextApiKey(this.arConfig.getHnarApiKey());
        serviceRequest.setSceneRelationId(sceneRelationId);

        Call<ServiceResponse> serviceResponse = apiService.postRequest(serviceRequest);
        serviceResponse.enqueue(new Callback<ServiceResponse>() {
            @Override
            public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                if(response.isSuccessful()) {
                    ServiceResponse responseBody = response.body();
                    ServiceResponseBody body = responseBody.getBody();
                    String modelUrl = body.getGlbModel();

                    Toast.makeText(arConfig.getHnarArView().getActivity(), "Here is the url " + modelUrl, Toast.LENGTH_LONG).show();

                    if (arRenderer == null) {
                        arRenderer = new ArRenderer(arConfig.getHnarArView(), arConfig.getHnarArView().getContext(), modelUrl);
                    } else {
                        //use arRenderer instance which already exists.
                    }
                }else{
                    Log.e("----Failed Request----", response.message());
//                    Gson failed = new GsonBuilder().create();
//                    ServiceFailedResponse failedResponse = failed.fromJson(failed.toJson(response.body()),ServiceFailedResponse.class);
//                    Toast.makeText(arConfig.getHnarArView().getActivity(),failedResponse.getError().getMessage(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse> call, Throwable t) {
                Log.e("ArView Error ","Something went wrong while requesting json over http");
            }
        });

//        RequestHandler.PostJsonBasedRequest(this.getActivity(), data, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                //Debugging
//                Toast.makeText(getContext(),response.toString(),Toast.LENGTH_LONG).show();
//                Log.e("On Response ",response.toString());
//
//                //Work in action
//                /*
//                    "error": {
//                         "message": "Invalid api key.",
//                         "code": "INVALID_API_KEY"
//                    }
//                    -----------------------------------
//                    {
//                    "error": {
//                         "message": "Invalid scene key.",
//                         "code": "INVALID_SCENE_KEY"
//                    }
//                }
//                */
//                if(response.has("error")) {
//                    try {
//                        JSONObject error = response.getJSONObject("error");
//                        String code = error.getString("code");
//                        String message = error.getString("message");
//
//                        if ( code == "INVALID_API_KEY")
//                            Log.e("Authorization failed ","An invalid API key");
//                        else if (code == "INVALID_SCENE_KEY")
//                            Log.e("Scene relation id failure ","The scene relation id is not valid.");
//                        else
//                            Log.e("An unknown failure ","Might be an unhandled response retrieved.");
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        Log.e("JSONException ",e.getMessage());
//                    }
//                }else {
//                    if(response.has("body")) {
//                        try {
//                            JSONObject body = response.getJSONObject("body");
//                            String modelUrl = body.getString("glbModel");
//
//                            Toast.makeText(arConfig.getHnarArView().getContext(),body.toString(),Toast.LENGTH_LONG).show();
//                            if (arRenderer == null) {
//                                arRenderer = new ArRenderer(arConfig.getHnarArView(),arConfig.getHnarArView().getContext(),modelUrl);
//                            }else{
//                                //use arRenderer instance which already exists.
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                            Log.e("JSONException ",e.getMessage());
//                        }
//                    }else {
//                        try {
//                            throw new HolonextSdkInitializeException("An error occured while retrieving data from service.");
//                        } catch (HolonextSdkInitializeException e) {
//                            e.printStackTrace();
//                            Log.e("HolonextSdkInitializeException ",e.getMessage());
//                        }
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                NetworkResponse errorRes = error.networkResponse;
//                String stringData = "";
//                if(errorRes != null && errorRes.data != null){
//                    try {
//                        stringData = new String(errorRes.data,"UTF-8");
//                    } catch (UnsupportedEncodingException e) {
//                        e.printStackTrace();
//                    }
//                }
//                Log.e("Request Handler Error ",stringData);
//            }
//        });
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
    public void onInflate(@NonNull final Context context, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);

        TypedArray arr = getActivity().obtainStyledAttributes(attrs,R.styleable.ArView);

        CharSequence apiKey = arr.getText(R.styleable.ArView_hnar_api_key);
        RendererType rendererType = RendererType.values()[arr.getInt(R.styleable.ArView_hnar_renderer,0)];
        ToolsetType toolsetType = ToolsetType.values()[arr.getInt(R.styleable.ArView_hnar_toolset,0)];

        arr.recycle();
    }
}
