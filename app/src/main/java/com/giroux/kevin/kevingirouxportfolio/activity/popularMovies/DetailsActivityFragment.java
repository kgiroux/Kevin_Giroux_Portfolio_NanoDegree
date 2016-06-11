package com.giroux.kevin.kevingirouxportfolio.activity.popularMovies;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.giroux.kevin.androidhttprequestlibrairy.constants.Constants;
import com.giroux.kevin.androidhttprequestlibrairy.constants.TypeMine;
import com.giroux.kevin.kevingirouxportfolio.R;
import com.giroux.kevin.kevingirouxportfolio.database.MovieContractor;
import com.giroux.kevin.kevingirouxportfolio.dto.MovieInformation;
import com.giroux.kevin.kevingirouxportfolio.network.MovieImageDetailsTask;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private final int DETAIL_LOADER = 0;
    public static String DETAIL_URI = "Detail Activity Movie";
    private Uri mUri;
    private MovieInformation movieInformation;
    @BindView(R.id.movieDetail_imageView)
    GifImageView gifImageView;
    @BindView(R.id.movieDetail_title) TextView titleTv;
    @BindView(R.id.yearTextView) TextView yearTv;
    @BindView(R.id.durationTextView) TextView durationTv;
    @BindView(R.id.ratingTextView) TextView ratingTv;


    private static String[] MOVIE_COLUMNS = {
            MovieContractor.MovieEntry._ID,
            MovieContractor.MovieEntry.COLUMN_MOVIE_ORIGINAL_TITLE,
            MovieContractor.MovieEntry.COLUMN_MOVIE_TITLE,
            MovieContractor.MovieEntry.COLUMN_MOVIE_RELEASE_DATE,
            MovieContractor.MovieEntry.COLUMN_MOVIE_OVERVIEW,
            MovieContractor.MovieEntry.COLUMN_MOVIE_USER_RATING,
            MovieContractor.MovieEntry.COLUMN_MOVIE_POSTER,
            MovieContractor.MovieEntry.COLUMN_MOVIE_POSTER_PATH,
            MovieContractor.MovieEntry.COLUMN_MOVIE_BACKDROP_PATH,
            MovieContractor.MovieEntry.COLUMN_MOVIE_SETTING,
            MovieContractor.MovieEntry.COLUMN_MOVIE_DATE_QUERY_MOVIEDB,
            MovieContractor.MovieEntry.COLUMN_MOVIE_POPULARITY
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    public DetailsActivityFragment() {
        setHasOptionsMenu(true);
    }

    /*@Override
    public void onStart() {
        super.onStart();
        LoadImage();
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(DETAIL_URI);
        }

        View root = inflater.inflate(R.layout.activity_movie_detail, container, false);
        ButterKnife.bind(this, root);
        return root;
    }


    private String formatTextInformation() {
        return "Title : " + movieInformation.getTitle()
                + "\n" + "Original Title : " + movieInformation.getOriginalTitle()
                + "\n" + "Realease Date : " + movieInformation.getReleaseDate()
                + "\n" + "User Rating : " + String.valueOf(movieInformation.getUserRating())
                + "\n" + "Synopsis : " + movieInformation.getOverView();
    }

    private void LoadImage() {
        if(movieInformation != null){
            /* Task that can load the picture */
            Map<String, String> urlParams = new HashMap<>();
            urlParams.put("", movieInformation.getBackdropPath());
            MovieImageDetailsTask movieImageDetailsTask = new MovieImageDetailsTask("http://image.tmdb.org/t/p/w500/", Constants.METHOD_GET, urlParams);
            movieImageDetailsTask.setJSON(false);
            movieImageDetailsTask.setContext(getActivity().getApplicationContext());
            movieImageDetailsTask.setTypeMine(TypeMine.IMAGE_WEBP);
            movieImageDetailsTask.addUIObjectToUpdate("gifImageView", gifImageView);
            movieImageDetailsTask.execute();
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if ( null != mUri ) {
            // Now create and return a CursorLoader that will take care of
            // creating a Cursor for the data being displayed.
            return new CursorLoader(
                    getActivity(),
                    mUri,
                    MOVIE_COLUMNS,
                    null,
                    null,
                    null
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.i(Constants.TAG_DETAILS_ACTIVITY, "In onLoadFinished");
         /* We bind the ImageView for update the picture when we download the picture */
        if (gifImageView != null)
            gifImageView.setImageResource(R.drawable.loadingspinner);
        movieInformation = (MovieContractor.MovieEntry.getDataFromCursor(data)).get(0);
        LoadImage();
        /* We format informations for adding them to the activity */
        if (titleTv != null)
            titleTv.setText(movieInformation.getTitle());

        if (yearTv != null)
            yearTv.setText(movieInformation.getReleaseDate());

        /*if (durationTv != null)
            durationTv.setText(movieInformation);

        if (titleTv != null)
            titleTv.setText(movieInformation.getTitle());*/

        /* We change the title for the activity by the name of the movie */
        getActivity().setTitle(movieInformation.getTitle());
        // UI
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }
}
