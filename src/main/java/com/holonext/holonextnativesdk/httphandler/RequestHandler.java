package com.holonext.holonextnativesdk.httphandler;

import android.app.Activity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import org.json.JSONObject;
import java.util.Map;

public class RequestHandler {
    static public void MakeJsonBasedRequest(Activity activity, Map<String,String> params, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener){
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        JsonBasedRequestHandler jsonRequest = new JsonBasedRequestHandler(params,listener,errorListener);
        requestQueue.add(jsonRequest);
    }
}
