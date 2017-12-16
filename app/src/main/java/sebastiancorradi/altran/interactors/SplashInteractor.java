package sebastiancorradi.altran.interactors;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import sebastiancorradi.altran.RequestManager.RequestManager;
import sebastiancorradi.altran.RequestManager.iResponseListener;
import sebastiancorradi.altran.SQL.AltranDBHelper;
import sebastiancorradi.altran.model.Gnome;
import sebastiancorradi.altran.presenters.SplashPresenter;
import sebastiancorradi.altran.presenters.listeners.iGnomesLoadedListener;
import sebastiancorradi.altran.repository.GnomeRepository;

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
                GnomeRepository.getInstance().setData(response);
                final DBInteractor dbInteractor = DBInteractor.getInstance(context);
                int gnomesCount = dbInteractor.gnomeCount();
                if (gnomesCount == 0 ) {
                    //dbInteractor.insertAll(GnomeRepository.getInstance().getGnomeList());

                    new Thread(new Runnable() {
                        public void run(){
                            dbInteractor.setDataBaseNOTReady();
                            dbInteractor.insertAll(GnomeRepository.getInstance().getGnomeList());
                            dbInteractor.setDataBaseReady();
                        }
                    }).start();
                } else {
                    dbInteractor.setDataBaseReady();
                }

                //ArrayList<Gnome> hairColor = dbInteractor.getGnomesByHairColor("Red");
                listener.gnomesDataLoaded();
            }

            @Override
            public void onResponseError(int errorCode, String error) {
                //listener.gnomesDataError();
            }
        });
    }

}
