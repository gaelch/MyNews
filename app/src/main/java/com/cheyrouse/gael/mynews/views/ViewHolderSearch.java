package com.cheyrouse.gael.mynews.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.cheyrouse.gael.mynews.models.Doc;
import com.cheyrouse.gael.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;



class ViewHolderSearch extends RecyclerView.ViewHolder  {

        @BindView(R.id.fragment_Search_item_title) TextView textViewTitle;
        @BindView(R.id.fragment_Search_item_date) TextView textViewDate;
        @BindView(R.id.fragment_Search_item_image) ImageView imageView;
        @BindView(R.id.fragment_Search_item_section) TextView textViewSection;
        @BindView(R.id.relativeLayoutSearch) RelativeLayout relativeLayout;

        static final String URL = "https://static01.nyt.com/";


        ViewHolderSearch(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        //Update Items search
        void updateWithResult(final Doc article, RequestManager glide, final RecyclerViewSearchAdapter.onArticleSearchAdapterListener callback){
        this.textViewTitle.setText((article).getHeadline().getMain());
        this.textViewSection.setText(article.getSectionName());
        if(article.getPubDate() != null){
            String date = article.getPubDate().substring(0, 10);
            this.textViewDate.setText(date);
        }

            if (!article.getMultimedia().isEmpty()){
                String mUrl = URL + article.getMultimedia().get(0).getUrl();
                glide.load(mUrl).apply(RequestOptions.centerCropTransform()).into(imageView);
        }else{
            imageView.setImageResource(R.mipmap.ic_my_news);
        }
            this.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.onArticleClicked(article);
                }
            });

        }


    }

