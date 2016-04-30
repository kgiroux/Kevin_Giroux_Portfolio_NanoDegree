package com.giroux.kevin.kevingirouxportfolio.activity.popularMovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.giroux.kevin.androidhttprequestlibrairy.constants.Constants;
import com.giroux.kevin.kevingirouxportfolio.R;
import com.giroux.kevin.kevingirouxportfolio.adapter.MovieAdapter;
import com.giroux.kevin.kevingirouxportfolio.dto.MovieInformation;
import com.giroux.kevin.kevingirouxportfolio.network.MovieTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PopularActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    @Override
    protected void onStart() {
        super.onStart();
        queryListFilm();
    }

    private MovieAdapter movieAdapter;
    @BindView(R.id.swipe_container) SwipeRefreshLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);
        ButterKnife.bind(this);
        if (layout != null) {
            layout.setOnRefreshListener(this);
            layout.setColorSchemeResources(
                    android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        if(recyclerView != null){
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),2,GridLayoutManager.VERTICAL,false);
            recyclerView.setLayoutManager(layoutManager);
            movieAdapter = new MovieAdapter(getApplicationContext(),new ArrayList<MovieInformation>());
            recyclerView.setAdapter(movieAdapter);
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

    private void queryListFilm(){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String preferencesString = preferences.getString(getString(R.string.pref_list_key),getString(R.string.pref_list_default));
        Log.e(Constants.TAG_POPULAR_MOVIE,preferencesString);
        Map<String,String> urlParam = new HashMap<>();
        urlParam.put("api_key",getString(R.string.apiKey));
        urlParam.put("",preferencesString);
        /*
            I create my own http Manager that can allow user to change the UI on the postMethod. We have just to create a class and extends it to AndroidHttpRequest.
            After done this, just implement the logic in the onPostResult

            This logic is for the moment only a prototype, and i would to have your mind on it
         */
        /*
        https://api.themoviedb.org/3/movie/popular?api_key=c31a455f61c0ef7088b1177843ce8372
         */
        MovieTask movieTask = new MovieTask("https://api.themoviedb.org/3/movie/", Constants.METHOD_GET,urlParam);
        movieTask.setJSON(false);
        movieTask.addUIObjectToUpdate("adapter",movieAdapter);
        movieTask.execute();
    }

    @Override
    public void onRefresh() {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                layout.setRefreshing(false);
                queryListFilm();
            }
        });
    }
}
