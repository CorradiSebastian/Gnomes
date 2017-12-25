package sebastiancorradi.altran.presenters;

import android.util.Log;
import android.widget.Toast;

import sebastiancorradi.altran.Utils.Utils;
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
        Log.d("SplashPresenter", "gnomesDataLoaded");
        splashView.goToMainScreen();
    }

    @Override
    public void gnomesDataError(int errorCode, String error) {
        Log.e("SplashPresenter", "Error en la carga de Gnomes, network error: " +  errorCode);
        String message = "Network problems, please try again later";
        Utils.getInstance().showInformationDialog(splashView, message, "Error " + errorCode);
    }
}
