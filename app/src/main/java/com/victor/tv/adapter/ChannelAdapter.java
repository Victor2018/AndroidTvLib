package com.victor.tv.adapter;

import android.content.Context;

import com.victor.data.ItemBean;
import com.victor.tv.R;

public class ChannelAdapter extends CommonRecyclerViewAdapter<ItemBean> {
    public ChannelAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.rv_channel_cell;
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, ItemBean item, int position) {
        helper.getHolder().setText(R.id.title, "CCTV-" + position);
    }
}
