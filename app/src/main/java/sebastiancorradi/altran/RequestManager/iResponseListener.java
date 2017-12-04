package sebastiancorradi.altran.RequestManager;

/**
 * Created by Gregorio on 12/1/2017.
 */

public interface iResponseListener {
    public void onResponseSuccess(String response);
    public void onResponseError(int errorCode, String error);
}
