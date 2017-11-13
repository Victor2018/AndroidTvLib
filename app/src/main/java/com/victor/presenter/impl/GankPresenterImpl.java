package com.victor.presenter.impl;

import com.victor.model.impl.GankModelImpl;
import com.victor.tv.library.model.HttpModel;
import com.victor.tv.library.presenter.impl.BasePresenterImpl;
import com.victor.tv.view.GankView;

/**
 * Created by Administrator on 2017/11/3.
 */

public class GankPresenterImpl<T> extends BasePresenterImpl<T> {
    private GankView gankView;
    public GankPresenterImpl(GankView gankView) {
        this.gankView = gankView;
    }

    @Override
    public void onSuccess(T data) {
        if (data == null) {
            gankView.OnGank(null,"no data response");
        } else {
            gankView.OnGank(data,"");
        }
    }

    @Override
    public void onError(String error) {
        gankView.OnGank(null,error);
    }

    @Override
    public HttpModel getHttpModelImpl() {
        return new GankModelImpl();
    }
}
