package com.victor.model.impl;

import com.victor.model.data.GankData;
import com.victor.tv.library.model.impl.BaseHttpModelImpl;

/**
 * Created by Administrator on 2017/11/3.
 */

public class GankModelImpl extends BaseHttpModelImpl {
    @Override
    public String getRequestUrl(Object parm) {
        return "http://gank.io/api/data/Android/10/1";
    }

    @Override
    public Class getReponseCls() {
        return GankData.class;
    }
}
