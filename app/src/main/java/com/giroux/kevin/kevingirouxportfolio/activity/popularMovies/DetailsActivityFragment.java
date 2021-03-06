package com.giroux.kevin.kevingirouxportfolio.activity.popularMovies;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.giroux.kevin.androidhttprequestlibrairy.AsyncCursor;
import com.giroux.kevin.androidhttprequestlibrairy.constants.Constants;
import com.giroux.kevin.androidhttprequestlibrairy.constants.MethodDatabase;
import com.giroux.kevin.androidhttprequestlibrairy.constants.TypeMine;
import com.giroux.kevin.kevingirouxportfolio.R;
import com.giroux.kevin.kevingirouxportfolio.adapter.ReviewAdapter;
import com.giroux.kevin.kevingirouxportfolio.adapter.TrailerAdapter;
import com.giroux.kevin.kevingirouxportfolio.database.AsyncQueryReview;
import com.giroux.kevin.kevingirouxportfolio.database.AsyncQueryTrailer;
import com.giroux.kevin.kevingirouxportfolio.database.MovieContractor;
import com.giroux.kevin.kevingirouxportfolio.dto.MovieInformation;
import com.giroux.kevin.kevingirouxportfolio.dto.Review;
import com.giroux.kevin.kevingirouxportfolio.dto.Trailer;
import com.giroux.kevin.kevingirouxportfolio.network.MovieFullTask;
import com.giroux.kevin.kevingirouxportfolio.network.MovieImageDetailsTask;
import com.giroux.kevin.kevingirouxportfolio.network.MovieReviewsTask;
import com.giroux.kevin.kevingirouxportfolio.network.MovieTrailerTask;
import com.giroux.kevin.kevingirouxportfolio.utils.Utility;

import java.util.ArrayList;
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

    static String DETAIL_URI = "Detail Activity Movie";
    private int DETAIL_LOADER = 1;
    @BindView(R.id.movieDetail)
    @Nullable
    CoordinatorLayout movieDetail;
    @BindView(R.id.listTrailer)
    RecyclerView listTrailer;
    @BindView(R.id.listReview)
    RecyclerView listReview;
    public static final int COL_MOVIE_ID = 0;
    public static final int COLUMN_MOVIE_ORIGINAL_TITLE = 1;
    public static final int COLUMN_MOVIE_TITLE = 2;
    private Uri mUri;
    private MovieInformation movieInformation;
    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;
    private GifImageView gifImageView;
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
    public static final int COLUMN_MOVIE_RELEASE_DATE = 3;
    public static final int COLUMN_MOVIE_OVERVIEW = 4;
    public static final int COLUMN_MOVIE_USER_RATING = 5;
    public static final int COLUMN_MOVIE_POSTER = 6;
    public static final int COLUMN_MOVIE_POSTER_PATH = 7;
    public static final int COLUMN_MOVIE_BACKDROP_PATH = 8;
    public static final int COLUMN_MOVIE_SETTING = 9;
    public static final int COLUMN_MOVIE_DATE_QUERY_MOVIEDB = 10;
    public static final int COLUMN_MOVIE_POPULARITY = 11;
    public static final int COLUMN_MOVIE_MARK_AS_FAVORITE = 12;
    public static final int COLUMN_MOVIE_DURATION = 13;
    public static final int COLUMN_MOVIE_TRAILER_LOADED = 14;
    public static final int COLUMN_MOVIE_REVIEW_LOADED = 15;
    public static final int COLUMN_MOVIE_BACKDROP = 16;
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
            MovieContractor.MovieEntry.COLUMN_MOVIE_POPULARITY,
            MovieContractor.MovieEntry.COLUMN_MOVIE_MARK_AS_FAVORITE,
            MovieContractor.MovieEntry.COLUMN_MOVIE_DURATION,
            MovieContractor.MovieEntry.COLUMN_MOVIE_TRAILER_LOADED,
            MovieContractor.MovieEntry.COLUMN_MOVIE_REVIEW_LOADED,
            MovieContractor.MovieEntry.COLUMN_MOVIE_BACKGROUND
    };
    @BindView(R.id.synopsisContent) TextView synopsisContent;

    private static String[] REVIEW_ENTRY = {
            MovieContractor.ReviewEntry._ID,
            MovieContractor.ReviewEntry.COLUMN_REVIEWS_CONTENT,
            MovieContractor.ReviewEntry.COLUMN_REVIEWS_NAME,
            MovieContractor.ReviewEntry.COLUMN_REVIEWS_ID_MOVIE
    };

    public static int COL_REVIEW_ID = 0;
    public static int COL_REVIEW_CONTENT = 1;
    public static int COL_REVIEW_NAME = 2;
    public static int COL_REVIEW_ID_MOVIE = 3;
    private CollapsingToolbarLayout toolbarLayout;
    private static String[] TRAILER_ENTRY = {
            MovieContractor.TrailerEntry._ID,
            MovieContractor.TrailerEntry.COLUMN_TRAILER_NAME,
            MovieContractor.TrailerEntry.COLUMN_TRAILER_KEY_YOUTUBE,
            MovieContractor.TrailerEntry.COLUMN_TRAILER_IS_YOUTUBE,
            MovieContractor.TrailerEntry.COLUMN_TRAILER_TYPE,
            MovieContractor.TrailerEntry.COLUMN_TRAILER_MOVIE_ID
    };

    public static int COL_TRAILER_ID = 0;
    public static int COLUMN_TRAILER_NAME = 1;
    public static int COLUMN_TRAILER_KEY_YOUTUBE = 2;
    public static int COLUMN_TRAILER_IS_YOUTUBE = 3;
    public static int COLUMN_TRAILER_TYPE = 4;
    public static int COLUMN_TRAILER_MOVIE_ID = 5;


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


        if(savedInstanceState != null && savedInstanceState.containsKey("mUri")){
            mUri = savedInstanceState.getParcelable("mUri");
            restartLoader();
        }

        if (arguments != null) {
            mUri = arguments.getParcelable(DETAIL_URI);
        }


        View root = inflater.inflate(R.layout.content_movie_detail, container, false);
        ButterKnife.bind(this, root);
        gifImageView = (GifImageView) container.findViewById(R.id.movieDetail_imageView);
        if (gifImageView == null)
            gifImageView = (GifImageView) root.findViewById(R.id.movieDetail_imageView);
        if (movieDetail != null)
            movieDetail.setVisibility(View.VISIBLE);

        toolbarLayout = (CollapsingToolbarLayout) root.findViewById(R.id.toolbar_layout);
        if (toolbarLayout == null) {
            toolbarLayout = (CollapsingToolbarLayout) container.findViewById(R.id.toolbar_layout);
        }

        Toolbar toolbar = (Toolbar) container.findViewById(R.id.toolbar);
        if(toolbar != null)
            toolbar.setNavigationIcon(R.drawable.keyboard_backspace);


        markAsFavorite.setOnClickListener(this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listTrailer.setLayoutManager(layoutManager);


        trailerAdapter = new TrailerAdapter(getContext(), new ArrayList<Trailer>());

        listTrailer.setAdapter(trailerAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listReview.setLayoutManager(linearLayoutManager);

        reviewAdapter = new ReviewAdapter(getContext(), new ArrayList<Review>());
        listReview.setAdapter(reviewAdapter);


        return root;
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
            movieImageDetailsTask.addUIObjectToUpdate("idMovie", movieInformation.getId());
            movieImageDetailsTask.execute();
        }

    }

    private void LoadTrailer(int idMovie) {
        Map<String, String> urlParam = new HashMap<>();
        urlParam.put("api_key", getString(R.string.apiKey));
        urlParam.put("/", "videos");
        urlParam.put("", String.valueOf(idMovie));
        MovieTrailerTask movieTrailerTask = new MovieTrailerTask("https://api.themoviedb.org/3/movie/", Constants.METHOD_GET, urlParam);
        movieTrailerTask.setJSON(false);
        movieTrailerTask.setContext(getActivity().getApplicationContext());
        Map<String, Object> listObject = new HashMap<>();
        listObject.put("idMovie", movieInformation.getId());
        listObject.put("trailerAdapter", trailerAdapter);
        listObject.put("movieInformation", movieInformation);
        movieTrailerTask.setListObject(listObject);
        movieTrailerTask.execute();
    }

    private void LoadReviews(int idMovie) {
        Map<String, String> urlParam = new HashMap<>();
        urlParam.put("api_key", getString(R.string.apiKey));
        urlParam.put("/", "reviews");
        urlParam.put("", String.valueOf(idMovie));
        MovieReviewsTask movieReviewsTask = new MovieReviewsTask("https://api.themoviedb.org/3/movie/", Constants.METHOD_GET, urlParam);
        movieReviewsTask.setJSON(false);
        movieReviewsTask.setContext(getActivity().getApplicationContext());
        Map<String, Object> listObject = new HashMap<>();
        listObject.put("idMovie", movieInformation.getId());
        listObject.put("reviewAdapter", reviewAdapter);
        listObject.put("movieInformation", movieInformation);
        movieReviewsTask.setListObject(listObject);
        movieReviewsTask.execute();
    }

    private void LoadMovieDetail(int idMovie) {

        Map<String, String> urlParam = new HashMap<>();
        urlParam.put("api_key", getString(R.string.apiKey));
        urlParam.put("", String.valueOf(idMovie));
        MovieFullTask movieFullTask = new MovieFullTask("https://api.themoviedb.org/3/movie/", Constants.METHOD_GET, urlParam);
        movieFullTask.setJSON(false);
        movieFullTask.setContext(getActivity().getApplicationContext());
        Map<String, Object> listObject = new HashMap<>();
        listObject.put("movieObject", movieInformation);
        listObject.put("durationTv", durationTv);
        movieFullTask.setListObject(listObject);
        movieFullTask.execute();

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
        if (gifImageView != null && movieInformation == null)
            gifImageView.setImageResource(R.drawable.loadingspinner);

        if(movieInformation == null){
            List<MovieInformation> informationList = MovieContractor.MovieEntry.getAllDataFromCursor(data);
            if (!informationList.isEmpty()) {
                movieInformation = informationList.get(0);



                if (movieInformation.isReviewLoaded()) {
                    Log.e("LOG","LOG_UPDATE AsyncQueryReview");
                    String whereClause = MovieContractor.ReviewEntry.COLUMN_REVIEWS_ID_MOVIE + " = ?";
                    AsyncQueryReview asyncQueryReview = new AsyncQueryReview(getContext());
                    asyncQueryReview.setValue( new String[]{String.valueOf(movieInformation.getId())});
                    asyncQueryReview.setUri(MovieContractor.ReviewEntry.CONTENT_URI);
                    asyncQueryReview.setProjection(REVIEW_ENTRY);
                    asyncQueryReview.setWhereClause(whereClause);
                    asyncQueryReview.setMethod(MethodDatabase.QUERY);
                    asyncQueryReview.setObject("reviewAdapter",reviewAdapter);
                    asyncQueryReview.execute();
                } else {
                    LoadReviews(movieInformation.getId());
                }

                if (movieInformation.isTrailerLoaded()) {
                    Log.e("LOG","LOG_UPDATE asyncQueryTrailer");
                    String whereClause = MovieContractor.TrailerEntry.COLUMN_TRAILER_MOVIE_ID + "= ?";
                    AsyncQueryTrailer asyncQueryTrailer = new AsyncQueryTrailer(getContext());
                    asyncQueryTrailer.setValue( new String[]{String.valueOf(movieInformation.getId())});
                    asyncQueryTrailer.setUri(MovieContractor.TrailerEntry.CONTENT_URI);
                    asyncQueryTrailer.setProjection(TRAILER_ENTRY);
                    asyncQueryTrailer.setWhereClause(whereClause);
                    asyncQueryTrailer.setMethod(MethodDatabase.QUERY);
                    asyncQueryTrailer.setObject("trailerAdapter",trailerAdapter);
                    asyncQueryTrailer.execute();


                } else {
                    LoadTrailer(movieInformation.getId());
                }



            }
        }
        updateUI();


    }

    public void updateUI(){
        if (movieInformation.getBackdropPathBitmap() != null) {
            gifImageView.setImageBitmap(new BitmapDrawable(getActivity().getApplicationContext().getResources(), BitmapFactory.decodeByteArray(movieInformation.getBackdropPathBitmap(), 0, movieInformation.getBackdropPathBitmap().length)).getBitmap());
        } else {
            LoadImage();
        }

        if (movieInformation.getDuration() == 0)
            LoadMovieDetail(movieInformation.getId());
        else
            durationTv.setText(Utility.formatDuration(movieInformation.getDuration()));
            /* We format informations for adding them to the activity */
        if (titleTv != null)
            titleTv.setText(movieInformation.getTitle());

        if (yearTv != null)
            yearTv.setText(Utility.formatDate(movieInformation.getReleaseDate()));

        if (gifPoster != null && movieInformation.getPosterBitmap() != null) {
            gifPoster.setImageBitmap(new BitmapDrawable(getActivity().getApplicationContext().getResources(), BitmapFactory.decodeByteArray(movieInformation.getPosterBitmap(), 0, movieInformation.getPosterBitmap().length)).getBitmap());
        }

        if (ratingTv != null)
            ratingTv.setText(Utility.formatUserRating(movieInformation.getUserRating()));

        if (markAsFavorite != null && movieInformation.isMarkAsFavorite()) {
            markAsFavorite.setText(getString(R.string.favoriteMovie));
        }

        if (synopsisContent != null && movieInformation.getOverView() != null)
            synopsisContent.setText(movieInformation.getOverView());

        toolbarLayout.setTitle(movieInformation.getTitle());
        getActivity().setTitle(movieInformation.getTitle());
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
            case R.id.toolbar :
                Intent intent = new Intent(getActivity(),PopularActivity.class);
                if(Build.VERSION.SDK_INT> 21 )
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT);
                else
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
        }
    }

    private void markasFavorite() {
        if (movieInformation == null)
            Toast.makeText(getActivity().getApplicationContext(), getString(R.string.errorOccured), Toast.LENGTH_LONG).show();
        else {
            ContentValues contentValues;
            if (movieInformation.isMarkAsFavorite()) {
                // Movie already in the favorie, we delete it;

                String value[] = new String[]{Integer.toString(movieInformation.getId())};
                String whereClause = MovieContractor.MovieEntry._ID + "=?";

                getContext().getContentResolver().delete(MovieContractor.FavoriteEntry.CONTENT_URI, whereClause, value);
                markAsFavorite.setText(getString(R.string.markasfavorite));
            } else {
                // if Movie not in favorite option we had it
                contentValues = MovieContractor.FavoriteEntry.buildContentValue(movieInformation.getId());
                getContext().getContentResolver().insert(MovieContractor.FavoriteEntry.CONTENT_URI, contentValues);
                markAsFavorite.setText(getString(R.string.favoriteMovie));
            }

            movieInformation.setMarkAsFavorite(!movieInformation.isMarkAsFavorite());

            contentValues = new ContentValues();
            contentValues.put(MovieContractor.MovieEntry.COLUMN_MOVIE_MARK_AS_FAVORITE, movieInformation.isMarkAsFavorite());
            String value[] = new String[]{Integer.toString(movieInformation.getId())};
            String whereClause = MovieContractor.MovieEntry._ID + "=?";
            Log.e("LOG","LOG_UPDATE FAVORITE MARK AS FAVORITE");
            AsyncCursor asyncCursor = new AsyncCursor(getContext());
            asyncCursor.setContentValues(contentValues);
            asyncCursor.setValue(value);
            asyncCursor.setWhereClause(whereClause);
            asyncCursor.setUri(MovieContractor.MovieEntry.buildMovieUri(movieInformation.getId()));
            asyncCursor.setMethod(MethodDatabase.UPDATE);
            asyncCursor.execute();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        if(mUri != null)
            outState.putParcelable("mUri",mUri);
        super.onSaveInstanceState(outState);
    }

    public void setPreferenceChange() {
        if(getLoaderManager() != null)
            getLoaderManager().restartLoader(DETAIL_LOADER, null, this);

    }

    public void restartLoader() {
        getLoaderManager().restartLoader(DETAIL_LOADER,null,this);
    }


}
