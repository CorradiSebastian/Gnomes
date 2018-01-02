package sebastiancorradi.altran.interactors;

import android.content.Context;
import java.util.ArrayList;
import sebastiancorradi.altran.RequestManager.RequestManager;
import sebastiancorradi.altran.RequestManager.iResponseListener;
import sebastiancorradi.altran.model.Gnome;
import sebastiancorradi.altran.model.InjectableGnomeCreator;
import sebastiancorradi.altran.presenters.listeners.iGnomesLoadedListener;


/**
 * Created by Gregorio on 12/2/2017.
 */

public class SplashInteractor {
    //private SplashPresenter presenter;
    public SplashInteractor(){

    }

    public void getGnomesData(final Context context, final iGnomesLoadedListener listener){
        RequestManager.getInstance().doGnomeDetailsRequest(context, new iResponseListener() {
            @Override
            public void onResponseSuccess(String response) {
                final DBInteractor dbInteractor = DBInteractor.getInstance(context, new InjectableGnomeCreator());
                dbInteractor.insertAll(response);
                listener.gnomesDataLoaded();
            }

            @Override
            public void onResponseError(int errorCode, String error) {
                DBInteractor dbInteractor = DBInteractor.getInstance(context, new InjectableGnomeCreator());
                if (dbInteractor.isDataBaseReady()){
                    dbInteractor.prepareData();
                    listener.gnomesDataLoaded();
                } else {
                    listener.gnomesDataError(errorCode, error);
                }
            }
        });
    }

    public boolean gnomesDataAvailable(Context context){
        return DBInteractor.getInstance(context, new InjectableGnomeCreator()).isDataBaseReady();
    }

    public ArrayList<Gnome> getGnomesStoredData(Context context){
        DBInteractor dbInteractor = DBInteractor.getInstance(context, new InjectableGnomeCreator());
        if (!dbInteractor.isDataBaseReady()){
            return null;
        } else {
            return dbInteractor.getAll();
        }
    }
}
