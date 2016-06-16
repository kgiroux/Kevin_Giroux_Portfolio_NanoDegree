package com.giroux.kevin.kevingirouxportfolio.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.giroux.kevin.kevingirouxportfolio.R;

/**
 * Created by kevin on 15/06/2016. KevinGirouxPortfolio
 */

public class ViewHolderReview extends RecyclerView.ViewHolder  {

    private TextView nameTv;
    private TextView contentTv;

    public TextView getNameTv() {
        return nameTv;
    }

    public TextView getContentTv() {
        return contentTv;
    }

    public ViewHolderReview(View itemView) {
        super(itemView);
        nameTv = (TextView) itemView.findViewById(R.id.reviewName);
        contentTv = (TextView) itemView.findViewById(R.id.reviewText);
    }
}
