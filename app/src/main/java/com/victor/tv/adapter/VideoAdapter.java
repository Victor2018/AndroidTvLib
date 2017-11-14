
package com.victor.tv.adapter;

import android.content.Context;

import com.victor.data.ItemBean;
import com.victor.tv.R;

public class VideoAdapter extends CommonRecyclerViewAdapter<ItemBean> {
    public VideoAdapter(Context context) {
        super(context);
    }

    @Override
    public int getItemLayoutId(int viewType) {
        return R.layout.rv_video_cell;
    }

    @Override
    public void onBindItemHolder(CommonRecyclerViewHolder helper, ItemBean item, int position) {
        helper.getHolder()
                .setText(R.id.title, String.valueOf(position))
                .showImage(R.id.image, item.imgUrl);
    }
}
