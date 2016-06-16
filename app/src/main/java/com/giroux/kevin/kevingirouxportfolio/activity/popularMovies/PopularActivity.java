package com.giroux.kevin.kevingirouxportfolio.activity.popularMovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.giroux.kevin.kevingirouxportfolio.R;
import com.giroux.kevin.kevingirouxportfolio.interfaces.OnCustomItemClickListener;

public class PopularActivity extends AppCompatActivity implements OnCustomItemClickListener{

    private static boolean mTwoPane = false;
    private String DETAILFRAGMENT_TAG = "DETAILFRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(getString(R.string.popularMovie));
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
            if(getSupportActionBar() != null)
                getSupportActionBar().setElevation(0f);
        }
        PopularActivityFragment ff = (PopularActivityFragment)getSupportFragmentManager().findFragmentById(R.id.idMovieFragment);
        ff.setmTwoPane(!mTwoPane);

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


    @Override
    public void onItemSelected(Uri uri) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle args = new Bundle();
            args.putParcelable(DetailsActivityFragment.DETAIL_URI, uri);

            DetailsActivityFragment fragment = new DetailsActivityFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movieDetail, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, DetailsActivity.class).setData(uri);
            startActivity(intent);
        }
    }
}
