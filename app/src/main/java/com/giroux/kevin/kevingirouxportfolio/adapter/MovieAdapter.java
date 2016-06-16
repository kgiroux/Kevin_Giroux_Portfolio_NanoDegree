package com.giroux.kevin.kevingirouxportfolio.adapter;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.giroux.kevin.androidhttprequestlibrairy.constants.Constants;
import com.giroux.kevin.androidhttprequestlibrairy.constants.TypeMine;
import com.giroux.kevin.kevingirouxportfolio.R;
import com.giroux.kevin.kevingirouxportfolio.ViewHolder.ViewHolderMovie;
import com.giroux.kevin.kevingirouxportfolio.dto.MovieInformation;
import com.giroux.kevin.kevingirouxportfolio.network.MovieImageTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kevin on 28/04/2016. Kevin Giroux Portfolio
 */

/**
 * This class is a an Custom Adapter for the RecyclerView I implement on my activity PopularActivity. We can bind view  and fetch data (picture) from server
 */

public class MovieAdapter extends RecyclerView.Adapter {

    private List<MovieInformation> listObjects;
    private Activity mActivtiy;
    private boolean mTwoPane;

    public void setmTwoPane(boolean mTwoPane) {
        this.mTwoPane = mTwoPane;
    }

    public MovieAdapter(Activity activtiy, List<MovieInformation> listObjects){
        this.mActivtiy = activtiy;
        this.listObjects = listObjects;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderMovie( LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolderMovie) {
            ImageView imageView = ((ViewHolderMovie) holder).getImageMovie();
            if (listObjects.get(position).getPosterBitmap() != null){
                imageView.setImageBitmap(new BitmapDrawable(mActivtiy.getApplicationContext().getResources(), BitmapFactory.decodeByteArray(listObjects.get(position).getPosterBitmap(),0,listObjects.get(position).getPosterBitmap().length)).getBitmap());
            }else{
                /* We have load the information with the MovieTask, It is time to load the picture Poster for the rendering */
                imageView.setImageResource(R.drawable.loadingspinner);
                Map<String,String> urlParams= new HashMap<>();
                urlParams.put("",listObjects.get(position).getPosterPath());
                MovieImageTask movieImageTask = new MovieImageTask("http://image.tmdb.org/t/p/w500/", Constants.METHOD_GET,urlParams);
                movieImageTask.setJSON(false);
                movieImageTask.setContext(this.mActivtiy.getApplicationContext());
                movieImageTask.setTypeMine(TypeMine.IMAGE_WEBP);
                movieImageTask.addUIObjectToUpdate("ViewHolder",holder);
                movieImageTask.addUIObjectToUpdate("listMovie",listObjects);
                movieImageTask.execute();
            }
            ((ViewHolderMovie)holder).setMPosition(position);
            ((ViewHolderMovie)holder).setmTwoPane(this.mTwoPane);
            ((ViewHolderMovie)holder).setActivity(this.mActivtiy);
            ((ViewHolderMovie)holder).setMovieInformation(listObjects.get(position));
            ((ViewHolderMovie)holder).getImageMovie().setContentDescription(listObjects.get(position).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return listObjects.size();
    }

    public void setData(List<MovieInformation> list){
        if(list != null){
            this.listObjects = list;
            notifyDataSetChanged();
        }
    }

    private void displayListObject(){
        for(MovieInformation m: listObjects){
            Log.e(Constants.MOVIE_ADAPTER, m.toString());
        }
    }

}
