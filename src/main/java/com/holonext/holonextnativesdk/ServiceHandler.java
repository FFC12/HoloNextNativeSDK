package com.holonext.holonextnativesdk;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * A Helper Class for Http Requests and Handle JsonObject Responses
 */
public class ServiceHandler extends Request<JSONObject> {
    /**
     * Service URI of HoloNext API.
     */
    private final static String ServiceURI = "https://holonext.azurewebsites.net/api/v1/scene/shared-scene/5de12029362138005683f7b8/Office_Chair.glb";

    private Listener<JSONObject> listener;

    private Map<String,String> params;

    public ServiceHandler(String url, Map<String, String> params, Listener<JSONObject> reponseListener, ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        this.listener = reponseListener;
        this.params = params;
    }

    public ServiceHandler(int method, String url, Map<String,String> params, Listener<JSONObject> responseListener, @Nullable ErrorListener listener) {
        super(method, url, listener);
        this.listener = responseListener;
        this.params = params;
    }

    protected Map<String,String> getParams() throws AuthFailureError{
        return params;
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonData = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            return Response.success(new JSONObject(jsonData),HttpHeaderParser.parseCacheHeaders(response));
        }catch (UnsupportedEncodingException err){
            return Response.error(new ParseError(err));
        }catch (JSONException err){
            return  Response.error(new ParseError(err));
        }
    }

    @Override
    protected void deliverResponse(JSONObject response) {
        listener.onResponse(response);
    }
}
