package com.giroux.kevin.kevingirouxportfolio.activity.popularMovies;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.giroux.kevin.androidhttprequestlibrairy.constants.Constants;
import com.giroux.kevin.androidhttprequestlibrairy.constants.TypeMine;
import com.giroux.kevin.kevingirouxportfolio.R;
import com.giroux.kevin.kevingirouxportfolio.database.MovieContractor;
import com.giroux.kevin.kevingirouxportfolio.dto.MovieInformation;
import com.giroux.kevin.kevingirouxportfolio.network.MovieImageDetailsTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailsActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, View.OnClickListener {

    private final int DETAIL_LOADER = 1;
    public static String DETAIL_URI = "Detail Activity Movie";
    @BindView(R.id.movieDetail)
    CoordinatorLayout movieDetail;
    private Uri mUri;
    private MovieInformation movieInformation;
    @BindView(R.id.movieDetail_imageView)
    GifImageView gifImageView;
    @BindView(R.id.movie_item_picture)
    GifImageView gifPoster;
    @BindView(R.id.movieDetail_title)
    TextView titleTv;
    @BindView(R.id.yearTextView)
    TextView yearTv;
    @BindView(R.id.durationTextView)
    TextView durationTv;
    @BindView(R.id.ratingTextView)
    TextView ratingTv;
    @BindView(R.id.markAsFavorite)
    Button markAsFavorite;


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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle arguments = getArguments();
        if (arguments != null) {
            mUri = arguments.getParcelable(DETAIL_URI);
        }

        View root = inflater.inflate(R.layout.activity_movie_detail, container, false);
        ButterKnife.bind(this, root);
        movieDetail.setVisibility(View.VISIBLE);
        markAsFavorite.setOnClickListener(this);

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
        if (movieInformation != null) {
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

    private void LoadTrailer() {

    }

    private void LoadReviews() {

    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (null != mUri) {
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
        List<MovieInformation> informationList = MovieContractor.MovieEntry.getDataFromCursor(data);
        if (!informationList.isEmpty()) {
            movieInformation = informationList.get(0);
            LoadImage();
            /* We format informations for adding them to the activity */
            if (titleTv != null)
                titleTv.setText(movieInformation.getTitle());

            if (yearTv != null)
                yearTv.setText(movieInformation.getReleaseDate());

            if (gifPoster != null && movieInformation.getPosterBitmap() != null) {
                gifPoster.setImageBitmap(new BitmapDrawable(getActivity().getApplicationContext().getResources(), BitmapFactory.decodeByteArray(movieInformation.getPosterBitmap(), 0, movieInformation.getPosterBitmap().length)).getBitmap());
            }

        /*if (durationTv != null)
            durationTv.setText(movieInformation);

        if (titleTv != null)
            titleTv.setText(movieInformation.getTitle());*/

        /* We change the title for the activity by the name of the movie */

            // UI
            getActivity().setTitle(movieInformation.getTitle());
        }

    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.markAsFavorite:
                markasFavorite();
                break;
        }
    }

    private void markasFavorite() {
        if (movieInformation == null)
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.errorOccured), Toast.LENGTH_LONG).show();
        else {
            //Uri uri = MovieContractor.FavoriteEntry.buildFavoriteByIdMovie(movieInformation.getId());
            ContentValues contentValues = MovieContractor.FavoriteEntry.buildContentValue(movieInformation.getId());
            getContext().getContentResolver().insert(MovieContractor.FavoriteEntry.CONTENT_URI, contentValues);
        }
    }
}
