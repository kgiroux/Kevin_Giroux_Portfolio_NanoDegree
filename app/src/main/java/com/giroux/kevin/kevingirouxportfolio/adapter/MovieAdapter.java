package com.giroux.kevin.kevingirouxportfolio.adapter;

import android.content.Context;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.giroux.kevin.androidhttprequestlibrairy.constants.Constants;
import com.giroux.kevin.androidhttprequestlibrairy.constants.TypeMine;
import com.giroux.kevin.kevingirouxportfolio.R;
import com.giroux.kevin.kevingirouxportfolio.ViewHolder.ViewHolderMovie;
import com.giroux.kevin.kevingirouxportfolio.dto.MovieInformation;
import com.giroux.kevin.kevingirouxportfolio.network.MovieImageTask;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kevin on 28/04/2016. Kevin Giroux Portfolio
 */
public class MovieAdapter extends RecyclerView.Adapter {

    private final int VIEW_ITEM = 0;
    private final String PATTERN_URL_IMAGE = "http://image.tmdb.org/t/p/w500/8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg";
    private List<MovieInformation> listObjects;
    private Context context;
    public MovieAdapter(Context context, List<MovieInformation> listObjects){
        this.context = context;
        this.listObjects = listObjects;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderMovie(LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_movie_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolderMovie){
            if (listObjects.get(position).getPosterBitmap() != null)
                ((ViewHolderMovie) holder).getImageMovie().setImageBitmap(listObjects.get(position).getPosterBitmap());
            else{
                ((ViewHolderMovie) holder).getImageMovie().setImageResource(R.drawable.loadingspinner);
                Map<String,String> urlParams= new HashMap<>();
                urlParams.put("",listObjects.get(position).getPosterPath());
                MovieImageTask movieImageTask = new MovieImageTask("http://image.tmdb.org/t/p/w500/", Constants.METHOD_GET,urlParams);
                movieImageTask.setJSON(false);
                movieImageTask.setContext(this.context);
                movieImageTask.setTypeMine(TypeMine.IMAGE_WEBP);
                movieImageTask.addUIObjectToUpdate("ViewHolder",holder);
                movieImageTask.execute();
            }

            ((ViewHolderMovie)holder).getTitleMovie().setText(listObjects.get(position).getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return listObjects.size();
    }

    public void setDate(List<MovieInformation> list){
        if(list != null){
            this.listObjects = list;
            notifyDataSetChanged();
        }
    }

}
