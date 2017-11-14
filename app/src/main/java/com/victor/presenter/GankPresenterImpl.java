package com.victor.presenter;

import com.android.volley.Request;
import com.victor.http.annotation.HttpParms;
import com.victor.http.inject.HttpInject;
import com.victor.http.presenter.impl.BasePresenterImpl;
import com.victor.data.GankData;
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

    @HttpParms(method = Request.Method.GET,url = "http://gank.io/api/data/Android/10/1",responseCls = GankData.class)
    @Override
    public void sendRequest(T parm) {
        HttpInject.inject(this);
        super.sendRequest(parm);
    }

}
