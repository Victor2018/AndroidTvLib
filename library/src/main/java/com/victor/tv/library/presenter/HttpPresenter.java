package com.victor.tv.library.presenter;

/**
 * Created by victor on 2017/9/26.
 */

public interface HttpPresenter<T> {
    void sendRequest(T parm);
}
