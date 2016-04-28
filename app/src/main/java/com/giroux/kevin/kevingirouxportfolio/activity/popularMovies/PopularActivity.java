package com.giroux.kevin.kevingirouxportfolio.activity.popularMovies;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.giroux.kevin.androidhttprequestlibrairy.constants.Constants;
import com.giroux.kevin.kevingirouxportfolio.R;
import com.giroux.kevin.kevingirouxportfolio.adapter.MovieAdapter;
import com.giroux.kevin.kevingirouxportfolio.dto.MovieInformation;
import com.giroux.kevin.kevingirouxportfolio.network.MovieImageTask;
import com.giroux.kevin.kevingirouxportfolio.network.MovieTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PopularActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();
    }

    private MovieAdapter movieAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);

        /*
            URL IMAGE : http://image.tmdb.org/t/p/w500/8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg
            https://api.themoviedb.org/3/movie/55?api_key=c31a455f61c0ef7088b1177843ce8372
            https://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=c31a455f61c0ef7088b1177843ce8372
         */

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        if(recyclerView != null){
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2);
            GridLayoutManager.LayoutParams layoutParams = new GridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutManager.generateLayoutParams(layoutParams);
            recyclerView.setLayoutManager(layoutManager);

             movieAdapter = new MovieAdapter(getApplicationContext(),new ArrayList<MovieInformation>());
            recyclerView.setAdapter(movieAdapter);
        }


        Map<String,String> urlParam = new HashMap<>();
        urlParam.put("api_key","c31a455f61c0ef7088b1177843ce8372");
        urlParam.put("sort_by","popularity.desc");
        urlParam.put("","movie");

        MovieTask movieTask = new MovieTask("https://api.themoviedb.org/3/discover/", Constants.METHOD_GET,urlParam);
        movieTask.setJSON(false);
        movieTask.addUIObjectToUpdate("adapter",movieAdapter);
        movieTask.execute();
        //MovieImageTask movieImageTask = new MovieImageTask();
    }

    private void queryListFilm(){

    }
}
