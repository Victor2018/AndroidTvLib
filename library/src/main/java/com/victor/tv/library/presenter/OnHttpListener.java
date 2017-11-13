package com.victor.tv.library.presenter;


/**
 * Created by victor on 2017/10/9.
 */

public interface OnHttpListener<T> {
    void onSuccess(T data);
    void onError(String error);
}
