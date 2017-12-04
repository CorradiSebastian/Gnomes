package sebastiancorradi.altran.presenters;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import sebastiancorradi.altran.RequestManager.RequestManager;
import sebastiancorradi.altran.activities.MainActivity;
import sebastiancorradi.altran.activities.SplashActivity;
import sebastiancorradi.altran.interactors.SplashInteractor;
import sebastiancorradi.altran.model.Brastlewark;
import sebastiancorradi.altran.model.Gnome;
import sebastiancorradi.altran.parsers.GnomesParser;

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
