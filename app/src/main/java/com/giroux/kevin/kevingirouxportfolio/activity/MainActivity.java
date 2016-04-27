package com.giroux.kevin.kevingirouxportfolio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.giroux.kevin.kevingirouxportfolio.R;
import com.giroux.kevin.kevingirouxportfolio.activity.popularMovies.PopularActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //We get All the button

        Button popularMoviesBtn = (Button) findViewById(R.id.mainPopularMovies);
        if (null != popularMoviesBtn)
            popularMoviesBtn.setOnClickListener(this);
        Button stockHawkBtn = (Button) findViewById(R.id.mainStockHawk);
        if (null != stockHawkBtn)
            stockHawkBtn.setOnClickListener(this);
        Button buildItBiggerBtn = (Button) findViewById(R.id.mainBuildItBigger);
        if (null != buildItBiggerBtn)
            buildItBiggerBtn.setOnClickListener(this);
        Button makeYourAppMaterialBtn = (Button) findViewById(R.id.mainMakeYourAppMaterial);
        if (null != makeYourAppMaterialBtn)
            makeYourAppMaterialBtn.setOnClickListener(this);
        Button goUbiquitousBtn = (Button) findViewById(R.id.mainGoUbiquitous);
        if (null != goUbiquitousBtn)
            goUbiquitousBtn.setOnClickListener(this);
        Button capstoneBtn = (Button) findViewById(R.id.mainCapstone);
        if (null != capstoneBtn)
            capstoneBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        String textToDisplay;
        Intent t = null;
        switch (v.getId()) {
            case R.id.mainPopularMovies:
                t = new Intent(this, PopularActivity.class);
                textToDisplay = "This button will launch my Popular Movies App";
                break;
            case R.id.mainStockHawk:
                textToDisplay = "This button will launch my Stock Hawk App";
                break;
            case R.id.mainBuildItBigger:
                textToDisplay = "This button will launch my Build It Bigger App";
                break;
            case R.id.mainMakeYourAppMaterial:
                textToDisplay = "This button will launch my Make Your App Material App";
                break;
            case R.id.mainGoUbiquitous:
                textToDisplay = "This button will launch my Go Ubiquitous App";
                break;
            case R.id.mainCapstone:
                textToDisplay = "This button will launch my Capstone App";
                break;
            default:
                textToDisplay = "";
                t = null;
                break;
        }
        if (!textToDisplay.equals("")) {
            Toast.makeText(getApplicationContext(), textToDisplay, Toast.LENGTH_LONG).show();
        }
        if(t != null)
            startActivity(t);
    }
}
