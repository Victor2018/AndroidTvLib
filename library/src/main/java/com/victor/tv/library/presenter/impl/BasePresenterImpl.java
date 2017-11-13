package com.victor.tv.library.presenter.impl;

import com.victor.tv.library.model.HttpModel;
import com.victor.tv.library.presenter.HttpPresenter;
import com.victor.tv.library.presenter.OnHttpListener;

/**
 * Created by victor on 2017/9/26.
 */

public abstract class BasePresenterImpl<T> implements HttpPresenter<T>,OnHttpListener<T> {
    public abstract HttpModel getHttpModelImpl ();

    @Override
    public void sendRequest(T parm) {
        getHttpModelImpl().sendReuqest(parm,this);
    }
}
