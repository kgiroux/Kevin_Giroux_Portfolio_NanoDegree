package com.giroux.kevin.kevingirouxportfolio.activity.popularMovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.giroux.kevin.kevingirouxportfolio.R;
import com.giroux.kevin.kevingirouxportfolio.network.MovieImageTask;
import com.giroux.kevin.kevingirouxportfolio.network.MovieTask;

public class PopularActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);


        MovieTask movieTask = new MovieTask();
        MovieImageTask movieImageTask = new MovieImageTask();
    }
}
