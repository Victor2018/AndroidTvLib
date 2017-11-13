package com.victor.tv.library.util;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;

import java.io.UnsupportedEncodingException;

/**
 * Created by victor on 2017/10/25.
 */

public class PostJsonRequest<T> extends JsonRequest<T> {
    private String TAG = "PostJsonRequest";
    private Response.Listener<T> mListener;
    private Class<T> mClass;
    private String url;

    public PostJsonRequest(int method, String url, String requestBody, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, requestBody, listener, errorListener);
    }

    public PostJsonRequest(String url,Object requestBody,Response.Listener<T> listener, Response.ErrorListener errorListener) {
        this(VolleyRequest.getInstance().getRequestMethod(),url,JSON.toJSONString(requestBody),listener,errorListener);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            Log.e(TAG,"HttpHeaderParser.parseCharset(response.headers) = " + HttpHeaderParser.parseCharset(response.headers));
//            String responseData = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            String responseData = new String(response.data, "utf-8");
            Log.e(TAG,"url = " + url);
            Log.e(TAG,"responseData = " + responseData);
            return (Response<T>) Response.success(JSON.parseObject(responseData, mClass),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        }
    }
}
