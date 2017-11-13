package com.victor.tv.library.model.impl;

import android.text.TextUtils;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.victor.tv.library.model.HttpModel;
import com.victor.tv.library.presenter.OnHttpListener;
import com.victor.tv.library.util.VolleyRequest;

/**
 * Created by victor on 2017/9/26.
 */

public abstract class BaseHttpModelImpl<T,K> implements HttpModel<T,K> {
    private String TAG = "BaseHttpModelImpl";

    public abstract String getRequestUrl (T parm);
    public abstract Class getReponseCls ();

    @Override
    public void sendReuqest(T parm, final OnHttpListener<K> listener) {
        VolleyRequest.getInstance().newFastJsonRequest(getRequestUrl(parm), getReponseCls(), new Response.Listener<K>() {
            @Override
            public void onResponse(K info) {
                if (info != null) {
                    listener.onSuccess(info);
                } else {
                    listener.onError("no data response!");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String msg = error.getMessage();
                if (TextUtils.isEmpty(msg)) {
                    msg = "no data response!";
                }
                listener.onError(msg);
            }
        });
    }
}
