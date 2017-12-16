package sebastiancorradi.altran.presenters;

import android.util.Log;

import sebastiancorradi.altran.activities.SplashActivity;
import sebastiancorradi.altran.interactors.SplashInteractor;
import sebastiancorradi.altran.presenters.listeners.iGnomesLoadedListener;

/**
 * Created by Gregorio on 12/2/2017.
 */

public class SplashPresenter implements iGnomesLoadedListener {
    SplashActivity splashView;
    SplashInteractor splashInteractor;
    public SplashPresenter(SplashActivity splashView){
        this.splashView = splashView;
        this.splashInteractor = new SplashInteractor();
    }

    public void start(){
        splashInteractor.getGnomesData(splashView, this);

    }

    public void gnomesDataLoaded(){
        Log.e("SplashPresenter", "gnomesDataLoaded");
        splashView.goToMainScreen();
    }

    @Override
    public void gnomesDataError() {

    }
}
