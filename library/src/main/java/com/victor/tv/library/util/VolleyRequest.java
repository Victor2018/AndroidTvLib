package com.victor.tv.library.util;import android.content.Context;import com.android.volley.Request;import com.android.volley.RequestQueue;import com.android.volley.Response;import com.android.volley.toolbox.Volley;import java.util.List;/** * Created by victor on 2017/2/8. */public class VolleyRequest {    private String TAG = "VolleyRequest";    private static RequestQueue mRequestQueue;    private static VolleyRequest mVolleyRequest;    private int requestMethod = Request.Method.GET;//请求方式 默认POST    private VolleyRequest() {    }    /**     * @param context ApplicationContext     */    public static void buildRequestQueue(Context context) {        mRequestQueue = Volley.newRequestQueue(context);        //... do something    }    public static VolleyRequest getInstance() {        if (mRequestQueue == null) {            throw new NullPointerException("Call buildRequestQueue method first.");        }        if (mVolleyRequest == null) {            synchronized (VolleyRequest.class) {                if (mVolleyRequest == null) {                    mVolleyRequest = new VolleyRequest();                }            }        }        return mVolleyRequest;    }    /**     * 设置请求方式     */    public void setRequestMethod (int method) {        requestMethod = method;    }    /**     * 获取请求方式     * @return     */    public int getRequestMethod () {        return requestMethod;    }    public <T> FastJsonRequest<T> newFastJsonRequest (String url, Class<T> clazz, Response.Listener<T> listener,                                                      Response.ErrorListener errorListener) {        FastJsonRequest<T> request = new FastJsonRequest<T>(url, clazz, listener, errorListener);        mRequestQueue.add(request);        return request;    }    public <T> FastJsonArrayRequest<T> newFastJsonArrayRequest (String url, Class<T> clazz, Response.Listener<List<T>> listener,                                                                Response.ErrorListener errorListener) {        FastJsonArrayRequest<T> request = new FastJsonArrayRequest<T>(url, clazz, listener, errorListener);        mRequestQueue.add(request);        return request;    }    public <T> PostJsonRequest<T> newPostJsonRequest (String url, Object requestBody,  Response.Listener<T> listener,                                                                Response.ErrorListener errorListener) {        PostJsonRequest<T> request = new PostJsonRequest<T>(url, requestBody, listener, errorListener);        mRequestQueue.add(request);        return request;    }}