package com.giroux.kevin.kevingirouxportfolio.activity.popularMovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.giroux.kevin.kevingirouxportfolio.R;

public class PopularActivity extends AppCompatActivity {

    private static boolean mTwoPane = false;
    private String DETAILFRAGMENT_TAG = "DETAILFRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FrameLayout movieDetail = (FrameLayout) findViewById(R.id.movieDetail);
        if(movieDetail != null){
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movieDetail, new DetailsActivityFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        }else{
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_popular_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            Intent t = new Intent(this,SettingsActivity.class);
            startActivity(t);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
