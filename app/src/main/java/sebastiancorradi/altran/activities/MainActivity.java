package sebastiancorradi.altran.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import sebastiancorradi.altran.R;
import sebastiancorradi.altran.model.Gnome;
import sebastiancorradi.altran.presenters.MainPresenter;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    public static final String  GNOMES_DATA_KEY = "GNOMES_DATA";

    private MainPresenter presenter;
    private ArrayList<Gnome> gnomesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainPresenter(this);
    }

}
