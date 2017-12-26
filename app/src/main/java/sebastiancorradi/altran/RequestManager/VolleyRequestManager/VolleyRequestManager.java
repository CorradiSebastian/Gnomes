package sebastiancorradi.altran.RequestManager.VolleyRequestManager;

import android.app.Activity;
import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import sebastiancorradi.altran.RequestManager.iResponseListener;
import sebastiancorradi.altran.Utils.Utils;

/**
 * Created by Gregorio on 12/1/2017.
 */

public class VolleyRequestManager {

    private static VolleyRequestManager instance;
    private VolleyRequestManager(){

    }

    public static VolleyRequestManager getInstance(){
        if (instance == null){
            instance = new VolleyRequestManager();
        }
        return instance;
    }

    private void doRequest(final Context context, int method, String url, final iResponseListener responseListener){
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                responseListener.onResponseSuccess(response);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseListener.onResponseError(error.networkResponse == null ? 0 : error.networkResponse.statusCode, error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("User-Token", Utils.getInstance().getToken() );

                return params;
            }
        };;
// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    public void doGetRequest(Context context, String url, iResponseListener responseListener){
        doRequest(context, Request.Method.GET, url, responseListener);
    }

    public void doPostRequest(Context context, String url, iResponseListener responseListener){
        doRequest(context, Request.Method.POST, url, responseListener);
    }
}
