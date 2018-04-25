package com.victor.tv.adapter;

import android.content.Context;

import com.victor.tv.R;
import com.victor.tv.library.data.ItemBean;

public class FilmPosterAdapter extends CommonRecyclerViewAdapter<ItemBean> {
    public FilmPosterAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.rv_film_poster_cell;
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, ItemBean item, int position) {
        helper.getHolder().setText(R.id.title, "从你的全世界路过 " + position);
    }
}
