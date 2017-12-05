package sebastiancorradi.altran.presenters;

import android.util.Log;

import sebastiancorradi.altran.activities.SplashActivity;
import sebastiancorradi.altran.interactors.SplashInteractor;

/**
 * Created by Gregorio on 12/2/2017.
 */

public class SplashPresenter {
    SplashActivity splashView;
    SplashInteractor splashInteractor;
    public SplashPresenter(SplashActivity splashView){
        this.splashView = splashView;
        this.splashInteractor = new SplashInteractor(this);
    }

    public void start(){
        splashInteractor.getGnomesData(splashView);

    }

    public void gnomesDataLoaded(){
        Log.e("SplashPresenter", "gnomesDataLoaded");
        splashView.goToMainScreen();
    }
}
