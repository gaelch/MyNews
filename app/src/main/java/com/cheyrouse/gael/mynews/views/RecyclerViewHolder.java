package com.cheyrouse.gael.mynews.views;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.cheyrouse.gael.mynews.models.Result;
import com.cheyrouse.gael.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;

class RecyclerViewHolder extends RecyclerView.ViewHolder  {

    @BindView(R.id.fragment_main_item_title) TextView textViewTitle;
    @BindView(R.id.fragment_main_item_date) TextView textViewDate;
    @BindView(R.id.fragment_main_item_image) ImageView imageView;
    @BindView(R.id.fragment_main_item_section) TextView textViewSection;
    @BindView(R.id.relativeLayout) RelativeLayout relativeLayout;

    private static final String SUBSECTION = " > ";


    RecyclerViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    //Update Items
    void updateWithResult(final Result article, RequestManager glide, final RecyclerViewAdapter.onArticleAdapterListener callback){
        this.textViewTitle.setText(article.getTitle());
        if(article.getSubsection() == null){
            this.textViewSection.setText(String.format("%s%s%s", article.getSection(), SUBSECTION, "Most Viewed"));
        }else {
            this.textViewSection.setText(String.format("%s%s%s", article.getSection(), SUBSECTION, article.getSubsection()));
        }
        if(article.getUpdatedDate() != null){
            this.textViewDate.setText(article.getUpdatedDate().substring(0, 10));
        }


        if(article.getMultimedia()!=null && article.getMultimedia().size()>=1){
            glide.load(article.getMultimedia().get(0).getUrl()).apply(RequestOptions.centerInsideTransform()).into(imageView);
        }
        else if(article.getMedia()!=null && article.getMedia().size()>=1){
            glide.load(article.getMedia().get(0).getMediaMetadata().get(0).getUrl()).apply(RequestOptions.centerInsideTransform()).into(imageView);
        }else{
            imageView.getResources().getDrawable(R.mipmap.ic_my_news);
        }
        this.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onArticleClicked(article);
            }
        });

    }
}
