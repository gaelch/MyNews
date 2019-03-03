package com.cheyrouse.gael.mynews.views;

import com.bumptech.glide.RequestManager;
import com.cheyrouse.gael.mynews.Controllers.Fragments.ArticlesFragment;
import com.cheyrouse.gael.mynews.Models.Result;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RecyclerViewAdapterUnitTest  {

    private List<Result> list = new ArrayList<>();
    private RequestManager glide;
    private ArticlesFragment frag;

    private RecyclerViewAdapter mRecyclerView = new RecyclerViewAdapter(list, glide, frag);


    @Test
    public void shouldReturnGoodCount(){
        assertEquals(0, mRecyclerView.getItemCount());
    }
}