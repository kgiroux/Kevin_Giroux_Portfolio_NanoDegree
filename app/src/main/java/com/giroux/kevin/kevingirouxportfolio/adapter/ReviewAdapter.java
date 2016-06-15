package com.giroux.kevin.kevingirouxportfolio.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.giroux.kevin.kevingirouxportfolio.R;
import com.giroux.kevin.kevingirouxportfolio.ViewHolder.ViewHolderReview;
import com.giroux.kevin.kevingirouxportfolio.ViewHolder.ViewHolderTrailer;
import com.giroux.kevin.kevingirouxportfolio.dto.Review;
import com.giroux.kevin.kevingirouxportfolio.dto.Trailer;

import java.util.List;

/**
 * Created by kevin on 15/06/2016. KevinGirouxPortfolio
 */

public class ReviewAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Review> listReview;

    public ReviewAdapter(Context context, List<Review> listReview) {
        this.listReview = listReview;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolderTrailer(LayoutInflater.from(context).inflate(R.layout.recycler_item_review, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolderReview) {
            ((ViewHolderReview) holder).getNameTv().setText(listReview.get(position).getNameReviewer());
            ((ViewHolderReview) holder).getContentTv().setText(listReview.get(position).getContent());
        }
    }

    @Override
    public int getItemCount() {
        return listReview.size();
    }

    public void setData(List<Review> reviewList){
        this.listReview = reviewList;
        notifyDataSetChanged();
    }
}