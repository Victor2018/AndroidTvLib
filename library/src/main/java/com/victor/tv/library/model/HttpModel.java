package com.victor.tv.library.model;

import com.victor.tv.library.presenter.OnHttpListener;

/**
 * Created by victor on 2017/9/26.
 */

public interface HttpModel <T,K> {
    void sendReuqest(T parm, OnHttpListener<K> listener);
}
