package com.giroux.kevin.kevingirouxportfolio.activity.popularMovies;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.giroux.kevin.androidhttprequestlibrairy.constants.Constants;
import com.giroux.kevin.androidhttprequestlibrairy.constants.MethodDatabase;
import com.giroux.kevin.kevingirouxportfolio.R;
import com.giroux.kevin.kevingirouxportfolio.adapter.MovieAdapter;
import com.giroux.kevin.kevingirouxportfolio.database.AsyncRefreshData;
import com.giroux.kevin.kevingirouxportfolio.database.MovieContractor;
import com.giroux.kevin.kevingirouxportfolio.dto.MovieInformation;
import com.giroux.kevin.kevingirouxportfolio.interfaces.Callback;
import com.giroux.kevin.kevingirouxportfolio.network.MovieTask;
import com.giroux.kevin.kevingirouxportfolio.utils.Utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class PopularActivityFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, LoaderManager.LoaderCallbacks<Cursor> , Callback {


    private MovieAdapter movieAdapter;
    private static int PopularMovieLoader = 0;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout layout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    ArrayList<MovieInformation> listMovie;
    private Uri mUri;
    private boolean mTwoPane = false;

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

    private static String[] MOVIE_COLUMNS_INNER_JOIN = {
            MovieContractor.MovieEntry.TABLE_NAME + "." +MovieContractor.MovieEntry._ID,
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
            MovieContractor.FavoriteEntry.TABLE_NAME + "." + MovieContractor.FavoriteEntry._ID
    };

    public static int COL_MOVIE_ID =0;
    public static int COLUMN_MOVIE_ORIGINAL_TITLE =1;
    public static int COLUMN_MOVIE_TITLE =2;
    public static int COLUMN_MOVIE_RELEASE_DATE =3;
    public static int COLUMN_MOVIE_OVERVIEW =4;
    public static int COLUMN_MOVIE_USER_RATING =5;
    public static int COLUMN_MOVIE_POSTER =6;
    public static int COLUMN_MOVIE_POSTER_PATH =7;
    public static int COLUMN_MOVIE_BACKDROP_PATH =8;
    public static int COLUMN_MOVIE_SETTING =9;
    public static int COLUMN_MOVIE_POPULARITY = 10;



    public PopularActivityFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot = inflater.inflate(R.layout.fragment_popular, container, false);
        ButterKnife.bind(this, viewRoot);

        if(savedInstanceState != null && savedInstanceState.containsKey("mUri")){
            mUri = savedInstanceState.getParcelable("mUri");
            restartLoader();
            listMovie = new ArrayList<>();
        }else{
            listMovie = new ArrayList<>();
        }

        if (layout != null) {
            layout.setOnRefreshListener(this);
            layout.setColorSchemeResources(
                    android.R.color.holo_blue_bright,
                    android.R.color.holo_green_light,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light);
        }
        if (recyclerView != null) {
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2, GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);

            movieAdapter = new MovieAdapter(getActivity());
            movieAdapter.setmTwoPane(mTwoPane);
            recyclerView.setAdapter(movieAdapter);
            movieAdapter.setData(listMovie);
        }
        return viewRoot;

    }

    private void queryListFilm(){

        String preferencesString = Utility.getPreferredOrderMovie(getContext());

        if(!preferencesString.equals(getString(R.string.pref_list_exclude))){
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
            movieTask.setContext(getActivity().getApplicationContext());
            movieTask.addUIObjectToUpdate("adapter",movieAdapter);
            movieTask.execute();
        }
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

    void setmTwoPane(boolean mTwoPane){
        this.mTwoPane = mTwoPane;
        if(movieAdapter != null){
            movieAdapter.setmTwoPane(mTwoPane);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        getLoaderManager().initLoader(PopularMovieLoader, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    public void onPreferenceChange() {

        AsyncRefreshData asyncRefreshData = new AsyncRefreshData(getContext());

        String sortOrder = MovieContractor.MovieEntry.COLUMN_MOVIE_POPULARITY + " DESC";
        String preferredOrderMovieSetting = Utility.getPreferredOrderMovie(getActivity());
        if(preferredOrderMovieSetting.equals("favoriteMovies")){
            asyncRefreshData.setUri(MovieContractor.FavoriteEntry.buildFavoriteByIdMovie());
            asyncRefreshData.setProjection(MOVIE_COLUMNS_INNER_JOIN);

        }

        else{
            String whereClause = MovieContractor.MovieEntry.COLUMN_MOVIE_SETTING + " like ?";
            String value[] = new String[]{preferredOrderMovieSetting};
            asyncRefreshData.setUri(MovieContractor.MovieEntry.buildUriSettingsMovies());
            asyncRefreshData.setProjection(MOVIE_COLUMNS);
            asyncRefreshData.setWhereClause(whereClause);
            asyncRefreshData.setValue(value);


        }
        asyncRefreshData.setSortOrder(sortOrder);
        asyncRefreshData.setMethod(MethodDatabase.QUERY);
        asyncRefreshData.setObject("fragment",PopularActivityFragment.this);
        asyncRefreshData.setObject("movieAdapter",movieAdapter);
        asyncRefreshData.execute();

        getLoaderManager().restartLoader(PopularMovieLoader,null,this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String preferredOrderMovieSetting = Utility.getPreferredOrderMovie(getActivity());
        String sortOrder;
        switch (preferredOrderMovieSetting) {
            case "favoriteMovies":
                mUri = MovieContractor.FavoriteEntry.buildFavoriteByIdMovie();
                sortOrder = MovieContractor.MovieEntry.COLUMN_MOVIE_POPULARITY + " DESC";
                return new CursorLoader(getContext(), mUri, MOVIE_COLUMNS_INNER_JOIN, null, null, sortOrder);
            default:
                mUri = MovieContractor.MovieEntry.buildUriSettingsMovies();
                String whereClause = MovieContractor.MovieEntry.COLUMN_MOVIE_SETTING +" = ?";
                String value[] = new String[]{preferredOrderMovieSetting};
                sortOrder = MovieContractor.MovieEntry.COLUMN_MOVIE_POPULARITY + " DESC";
                return new CursorLoader(getContext(), mUri, MOVIE_COLUMNS, whereClause, value, sortOrder);

        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if(listMovie == null || listMovie.isEmpty()){
            listMovie = (ArrayList<MovieInformation>) MovieContractor.MovieEntry.getDataFromCursor(data);
            if(data != null && data.getCount() == 0){
                queryListFilm();
            }else{
                movieAdapter.setData(listMovie);
            }
        }



    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("mUri",mUri);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onQueryListInternet() {
        queryListFilm();
    }

    @Override
    public void restartLoader() {
        getLoaderManager().restartLoader(PopularMovieLoader,null,this);
    }
}
