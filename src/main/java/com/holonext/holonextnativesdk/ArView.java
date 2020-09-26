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

import com.google.ar.sceneform.ux.ArFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.holonext.holonextnativesdk.exception.HolonextSdkInitializeException;
import com.holonext.holonextnativesdk.httphandler.ApiClient;
import com.holonext.holonextnativesdk.httphandler.ApiInterface;
import com.holonext.holonextnativesdk.models.ServiceFailedResponse;
import com.holonext.holonextnativesdk.models.ServiceRequest;
import com.holonext.holonextnativesdk.models.ServiceResponse;
import com.holonext.holonextnativesdk.models.ServiceResponseBody;
import com.holonext.holonextnativesdk.renderer.ArRenderer;

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

                    Toast.makeText(arConfig.getHnarArView().getActivity(), "Here is the url : " + modelUrl, Toast.LENGTH_LONG).show();

                    if (arRenderer == null) {
                        arRenderer = new ArRenderer(arConfig.getHnarArView(), arConfig.getHnarArView().getContext(),modelUrl);
                        arRenderer.setDebugMode(true);
                    } else {
                        //use arRenderer instance which already exists.
                    }
                }else{
                    Gson failed = new GsonBuilder().create();
                    try {
                        ServiceFailedResponse failedResponse = failed.fromJson(failed.toJson(response.body()), ServiceFailedResponse.class);
                        Toast.makeText(arConfig.getHnarArView().getActivity(), failedResponse.getError().getMessage(), Toast.LENGTH_LONG).show();
                    }catch (NullPointerException e) {
                        Log.e("Failed to deserialization of response ", response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<ServiceResponse> call, Throwable t) {
                Log.e("ArView Error ","Something went wrong while requesting json over http");
            }
        });
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
