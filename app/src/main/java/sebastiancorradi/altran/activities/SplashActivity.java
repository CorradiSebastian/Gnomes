package sebastiancorradi.altran.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import sebastiancorradi.altran.R;
import sebastiancorradi.altran.presenters.SplashPresenter;

/**
 * Created by Gregorio on 12/2/2017.
 */

public class SplashActivity extends AppCompatActivity {
    private final String TAG = "SplashActivity";
    SplashPresenter presenter;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressBar = (ProgressBar) findViewById(R.id.splash_progresBar);
        presenter = new SplashPresenter(this);
        presenter.start();
    }

    public void cancelProgressBar(){
        progressBar.setVisibility(View.GONE);
    }

    public void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    public void goToMainScreen(){
        Intent intent = new Intent(this, MainActivity.class);
        this.startActivity(intent);
    }
}
