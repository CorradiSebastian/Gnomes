package sebastiancorradi.altran.RequestManager;

import android.content.Context;

import sebastiancorradi.altran.R;
import sebastiancorradi.altran.VolleyRequestManager.VolleyRequestManager;

/**
 * Created by Gregorio on 12/1/2017.
 */

public class RequestManager {

    private static RequestManager instance;
    private RequestManager(){

    }

    public static RequestManager getInstance(){
        if (instance == null){
            instance = new RequestManager();
        }
        return instance;
    }

    public void doRequest(){

    }

    private void doGetRequest(Context context, String url, iResponseListener responseListener){
        VolleyRequestManager.getInstance().doGetRequest(context, url, responseListener);
    }


    public void doPostRequest(){}

    public void doGnomeDetailsRequest(Context context, iResponseListener responseListener){
        String url = context.getString(R.string.gnomes_details_url);
        doGetRequest(context, url, responseListener);
    }
}
