package com.cheyrouse.gael.mynews.Views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.cheyrouse.gael.mynews.Models.Doc;
import com.cheyrouse.gael.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ViewHolderSearch extends RecyclerView.ViewHolder  {

        @BindView(R.id.fragment_Search_item_title) TextView textViewTitle;
        @BindView(R.id.fragment_Search_item_date) TextView textViewDate;
        @BindView(R.id.fragment_Search_item_image) ImageView imageView;
        @BindView(R.id.fragment_Search_item_section) TextView textViewSection;
        @BindView(R.id.relativeLayoutSearch) RelativeLayout relativeLayout;


        public ViewHolderSearch(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void updateWithResult(final Doc article, RequestManager glide, final RecyclerViewSearchAdapter.onArticleSearchAdapterListener callback){
        this.textViewTitle.setText((article).getHeadline().getMain());
        this.textViewSection.setText(article.getSectionName());
        if(article.getPubDate() != null){
            this.textViewDate.setText(article.getPubDate());
        }

        if(article.getMultimedia()!=null && article.getMultimedia().size()>=1){
            glide.load(article.getMultimedia().get(0).getUrl()).apply(RequestOptions.centerInsideTransform()).into(imageView);
        }
            this.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onArticleClicked(article);
                }
            });

        }


    }

