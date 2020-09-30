package com.holonext.holonextnativesdk;

import android.content.Context;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import com.holonext.holonextnativesdk.models.types.RendererType;
import com.holonext.holonextnativesdk.models.types.ToolsetType;
import com.holonext.holonextnativesdk.renderer.HoloNextArRenderer;

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
public class HoloNextArViewer extends ArFragment {
    /**
     * HoloNextArConfig is a data class used to store the options that will be used when creating the HoloNextARView.
     */
    @NonNull
    private HoloNextArConfig holoNextArConfig;

    /**
     * HoloNextArRenderer is a class that wrapping the renderer of AR Viewer.
     */
    @NonNull
    private HoloNextArRenderer holoNextArRenderer;

    /**
     * ApiInterface is providing Http communication of the HoloNextNativeSDK.
     */
    @NonNull
    private ApiInterface apiService;

    /**
     * Default contructor.Already using an API key that created for test purposes by default constructor.
     * Other options;
     *  <p>- Using default renderer type as default
     *  <p>- Using default toolset type as basic
     */
    public HoloNextArViewer(){
        if (this.holoNextArConfig == null)
            this.holoNextArConfig = new HoloNextArConfig();

        this.holoNextArConfig.setHnarApiKey(HoloNextArConfig.TestAPIKey);
        this.holoNextArConfig.setHnarRendererType(RendererType.DEFAULT);
        this.holoNextArConfig.setHnarToolsetType(ToolsetType.BASIC);
        this.holoNextArConfig.setHnarArViewer(this);
    }

    /**
     * This constructor has a only parameter as <b>HoloNextArConfig</b> type.
     * With that you can instance HoloNextArConfig object and set the data
     * desire you requirements to use of HoloNextNativeSDK.
     * @param holoNextArConfig
     */
    public HoloNextArViewer(@NonNull HoloNextArConfig holoNextArConfig){
        if(this.holoNextArConfig == null)
            this.holoNextArConfig = new HoloNextArConfig();

        this.holoNextArConfig.setHnarToolsetType(holoNextArConfig.getHnarToolsetType());
        this.holoNextArConfig.setHnarRendererType(holoNextArConfig.getHnarRendererType());
        this.holoNextArConfig.setHnarApiKey(holoNextArConfig.getHnarApiKey());
        this.holoNextArConfig.setHnarArViewer(this);
    }

    /**
     * Get the HoloNextArConfig instance
     * @return HoloNextArConfig
     */
    public HoloNextArConfig getHoloNextArConfig(){
        return this.holoNextArConfig;
    }

    /**
     * Initialize HoloNextArRenderer by using sceneRelationId and then open camera.
     * @param sceneRelationId
     * @throws HolonextSdkInitializeException
     */
    public void init(@NonNull String sceneRelationId) throws HolonextSdkInitializeException {
        if (sceneRelationId.isEmpty()){
            throw new HolonextSdkInitializeException("You must specify a 'scene relation id' to launch model its has.");
        }

        if(apiService == null)
            apiService = ApiClient.getClient().create(ApiInterface.class);

        ServiceRequest serviceRequest = new ServiceRequest();
        serviceRequest.setHolonextApiKey(this.holoNextArConfig.getHnarApiKey());
        serviceRequest.setSceneRelationId(sceneRelationId);

        final ConnectivityManager conMgr = (ConnectivityManager) this.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetwork = conMgr.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnectedOrConnecting()) {
            Call<ServiceResponse> serviceResponse = apiService.postRequest(serviceRequest);
            serviceResponse.enqueue(new Callback<ServiceResponse>() {
                @Override
                public void onResponse(Call<ServiceResponse> call, Response<ServiceResponse> response) {
                    if (response.isSuccessful()) {
                        ServiceResponse responseBody = response.body();
                        ServiceResponseBody body = responseBody.getBody();
                        String modelUrl = body.getGlbModel();

                        Log.d("HoloNextArViewer ",modelUrl);

                        if (holoNextArRenderer == null) {
                            holoNextArRenderer = new HoloNextArRenderer(holoNextArConfig.getHnarArViewer(), holoNextArConfig.getHnarArViewer().getContext(), modelUrl);
                            holoNextArRenderer.setDebugMode(false);
                        } else {
                            //use holoNextArRenderer instance which already exists.
                        }
                    } else {
                        Gson failed = new GsonBuilder().create();
                        try {
                            ServiceFailedResponse failedResponse = failed.fromJson(failed.toJson(response.body()), ServiceFailedResponse.class);
                            Toast.makeText(holoNextArConfig.getHnarArViewer().getActivity(), failedResponse.getError().getMessage(), Toast.LENGTH_LONG).show();
                        } catch (NullPointerException e) {
                            Log.e("Failed on deserialization of response ", response.message());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ServiceResponse> call, Throwable t) {
                    Log.e("HoloNextArViewer Error ", "Something went wrong while request over http");
                }
            });
        }else{
            Toast.makeText(holoNextArConfig.getHnarArViewer().getActivity(), "You must have an internet connection to retrieve data", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onInflate(@NonNull final Context context, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);

        TypedArray arr = getActivity().obtainStyledAttributes(attrs,R.styleable.HoloNextArViewer);

        CharSequence apiKey = arr.getText(R.styleable.HoloNextArViewer_hnar_api_key);
        RendererType rendererType = RendererType.values()[arr.getInt(R.styleable.HoloNextArViewer_hnar_renderer,0)];
        ToolsetType toolsetType = ToolsetType.values()[arr.getInt(R.styleable.HoloNextArViewer_hnar_toolset,0)];

        arr.recycle();
    }
}
