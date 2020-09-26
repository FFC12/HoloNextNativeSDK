package com.holonext.holonextnativesdk.httphandler;

import com.holonext.holonextnativesdk.models.ServiceRequest;
import com.holonext.holonextnativesdk.models.ServiceResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {
    @POST("sdkCheckModel")
    Call<ServiceResponse> postRequest(@Body ServiceRequest body);
}
