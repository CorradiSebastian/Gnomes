package sebastiancorradi.altran.interactors;

import android.content.Context;

import sebastiancorradi.altran.RequestManager.RequestManager;
import sebastiancorradi.altran.RequestManager.iResponseListener;
import sebastiancorradi.altran.presenters.SplashPresenter;
import sebastiancorradi.altran.repository.GnomeRepository;

/**
 * Created by Gregorio on 12/2/2017.
 */

public class SplashInteractor {
    private SplashPresenter presenter;
    public SplashInteractor(SplashPresenter presenter){
        this.presenter = presenter;
    }

    public void getGnomesData(Context context){
        RequestManager.getInstance().doGnomeDetailsRequest(context, new iResponseListener() {
            @Override
            public void onResponseSuccess(String response) {
                GnomeRepository.getInstance().setData(response);
                presenter.gnomesDataLoaded();
            }

            @Override
            public void onResponseError(int errorCode, String error) {
                //presenter.gnomesDataError();
            }
        });
    }

}
