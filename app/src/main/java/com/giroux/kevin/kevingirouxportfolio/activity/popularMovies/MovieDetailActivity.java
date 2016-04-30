package com.giroux.kevin.kevingirouxportfolio.activity.popularMovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.giroux.kevin.androidhttprequestlibrairy.constants.Constants;
import com.giroux.kevin.androidhttprequestlibrairy.constants.TypeMine;
import com.giroux.kevin.kevingirouxportfolio.R;
import com.giroux.kevin.kevingirouxportfolio.dto.MovieInformation;
import com.giroux.kevin.kevingirouxportfolio.network.MovieImageDetailsTask;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onStart() {
        super.onStart();

        LoadImage();
    }
    private MovieInformation movieInformation;
    @BindView(R.id.movieDetail_imageView) GifImageView gifImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /* We get the MovieInformation from the previous activity */
        movieInformation = getIntent().getParcelableExtra("movieInformation");

        /* We bind the ImageView for update the picture when we download the picture */
        if(gifImageView != null)
            gifImageView.setImageResource(R.drawable.loadingspinner);


        /* We format informations for adding them to the activity */
        TextView textView =(TextView) findViewById(R.id.movieDetail_text);
        if(textView != null)
            textView.setText(formatTextInformation());


        /* We change the title for the activity by the name of the movie */
        setTitle(movieInformation.getTitle());

    }

    private String formatTextInformation(){
        return  "Title : " + movieInformation.getTitle()
                +"\n" + "Original Title : " + movieInformation.getOriginalTitle()
                +"\n" + "Realease Date : " + movieInformation.getReleaseDate()
                +"\n" + "User Rating : " + String.valueOf(movieInformation.getUserRating())
                +"\n" + "Synopsis : "+ movieInformation.getOverView();
    }

    private void LoadImage(){
        /* Task that can load the picture */
        Map<String,String> urlParams= new HashMap<>();
        urlParams.put("",movieInformation.getBackdropPath());
        MovieImageDetailsTask movieImageDetailsTask = new MovieImageDetailsTask("http://image.tmdb.org/t/p/w500/", Constants.METHOD_GET,urlParams);
        movieImageDetailsTask.setJSON(false);
        movieImageDetailsTask.setContext(getApplicationContext());
        movieImageDetailsTask.setTypeMine(TypeMine.IMAGE_WEBP);
        movieImageDetailsTask.addUIObjectToUpdate("gifImageView",gifImageView);
        movieImageDetailsTask.execute();
    }
}
