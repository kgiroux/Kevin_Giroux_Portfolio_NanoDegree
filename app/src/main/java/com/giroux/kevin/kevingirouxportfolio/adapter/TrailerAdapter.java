package com.giroux.kevin.kevingirouxportfolio.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.giroux.kevin.kevingirouxportfolio.R;
import com.giroux.kevin.kevingirouxportfolio.ViewHolder.ViewHolderTrailer;
import com.giroux.kevin.kevingirouxportfolio.dto.Trailer;

import java.util.List;

/**
 * Created by kevin on 15/06/2016. KevinGirouxPortfolio
 */

public class TrailerAdapter extends RecyclerView.Adapter{

    private Context context;
    private List<Trailer> trailerList;

    public TrailerAdapter(Context context,List<Trailer> trailerList){
        this.trailerList = trailerList;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderTrailer(LayoutInflater.from(context).inflate(R.layout.recycler_item_trailer,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolderTrailer){
            ((ViewHolderTrailer)holder).setTrailer(trailerList.get(position));
            ((ViewHolderTrailer)holder).setContext(context);
            ((ViewHolderTrailer)holder).getTextView().setText(trailerList.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public void setData(List<Trailer> trailerList){
        this.trailerList = trailerList;
        notifyDataSetChanged();
    }
}
