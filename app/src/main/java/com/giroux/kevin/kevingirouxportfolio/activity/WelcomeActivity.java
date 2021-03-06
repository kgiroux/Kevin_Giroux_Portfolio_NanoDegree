package com.giroux.kevin.kevingirouxportfolio.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.giroux.kevin.androidhttprequestlibrairy.constants.Constants;
import com.giroux.kevin.kevingirouxportfolio.R;


public class WelcomeActivity extends AppCompatActivity {
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        long SPLASH_LENGTH = 3000;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        /* Récupération du numéro de version de l'application */
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            // Récupération du TextView permettant l'affichage de la version
            TextView versionNumber = (TextView) findViewById(R.id.versionNumber);
            if (versionNumber != null)
                versionNumber.setText(version);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(Constants.TAG_WELCOME_ACTIVITY, "PackageManager not found exception",e);
        }
        /* Affichage du SplashScreen pendant la durée SPLASH_LENGTH */
        handler.postDelayed(new Runnable() {

            public void run() {
                // On Lance l'activité MainActivity lorsqu'on a atteint le temps SPLASH_LENGTH
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_LENGTH);


    }
}
