package sebastiancorradi.altran.RequestManager.VolleyRequestManager;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import sebastiancorradi.altran.RequestManager.iResponseListener;

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

    private void doRequest(Context context, int method, String url, final iResponseListener responseListener){
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(method, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the first 500 characters of the response string.
                //mTextView.setText("Response is: "+ response.substring(0,500));
                responseListener.onResponseSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                responseListener.onResponseError(error.networkResponse.statusCode, error.getMessage());
            }
        });
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
