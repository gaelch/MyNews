package com.cheyrouse.gael.mynews.Views;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.cheyrouse.gael.mynews.Models.Doc;
import com.cheyrouse.gael.mynews.R;

import java.util.List;

public class RecyclerViewSearchAdapter extends RecyclerView.Adapter<ViewHolderSearch>{

    private final onArticleSearchAdapterListener mListener;

    // FOR COMMUNICATION


    // FOR DATA
    private List<Doc> search;
    private RequestManager glide;

    // CONSTRUCTOR
    public RecyclerViewSearchAdapter(List<Doc> search, RequestManager glide, onArticleSearchAdapterListener articleAdapterSearchListener) {
        this.mListener = articleAdapterSearchListener;
        this.search = search;
        this.glide = glide;

    }

    @NonNull
    @Override
    public ViewHolderSearch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_search_item, parent, false);

        return new ViewHolderSearch(view);
    }

    // UPDATE VIEW HOLDER WITH A TOPSTORIES
    @Override
    public void onBindViewHolder(@NonNull ViewHolderSearch viewHolder, int position) {
        viewHolder.updateWithResult(this.search.get(position), this.glide, mListener);
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.search.size();
    }


    public Doc getArticle (int position) {
        return this.search.get(position);
    }

    public interface onArticleSearchAdapterListener {
        void onArticleClicked(Doc resultTopStories);
    }


}
