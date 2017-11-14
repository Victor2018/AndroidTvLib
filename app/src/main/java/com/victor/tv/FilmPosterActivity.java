package com.victor.tv;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;

import com.victor.data.ItemBean;
import com.victor.tv.adapter.FilmPosterAdapter;
import com.victor.tv.library.util.Loger;
import com.victor.tv.library.util.ToastUtils;
import com.victor.widget.recycleview.TvRecyclerView;
import com.victor.widget.focus.FocusBorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FilmPosterActivity extends BaseActivity implements TvRecyclerView.OnItemListener,
        TvRecyclerView.OnInBorderKeyEventListener,TvRecyclerView.OnLoadMoreListener,View.OnFocusChangeListener {
    private String TAG = "FilmPosterActivity";
    private static final int LOAD_MORE_DATA = 0x1001;

    private TvRecyclerView mTrvFilmPoster;
    private FilmPosterAdapter filmPosterAdapter;
    protected FocusBorder mFocusBorder;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case LOAD_MORE_DATA:
                    filmPosterAdapter.appendDatas(getMovietDatas(20)); //加载数据
                    mTrvFilmPoster.setLoadingMore(false); //加载数据完毕
                    mTrvFilmPoster.setHasMoreData(true);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film_poster);
        initialize();
        setListener();
    }

    private void initialize () {
        mTrvFilmPoster = (TvRecyclerView) findViewById(R.id.trv_film_poster);
        // 设置布局的横纵间距
        mTrvFilmPoster.setSpacingWithMargins(0, 10);

        filmPosterAdapter = new FilmPosterAdapter(this);
        filmPosterAdapter.setDatas(getMovietDatas(20));
        mTrvFilmPoster.setAdapter(filmPosterAdapter);
        mTrvFilmPoster.setSelectedItemAtCentered(true);

        mFocusBorder = new FocusBorder.Builder()
                .asColor()
                .borderColor(getResources().getColor(R.color.white))
                .borderWidth(TypedValue.COMPLEX_UNIT_DIP, 2)
                .shadowColor(getResources().getColor(R.color.item_activated_color))
                .shadowWidth(TypedValue.COMPLEX_UNIT_DIP, 8)
                .build(this);
    }

    public static List<ItemBean> getMovietDatas(int count) {
        List<ItemBean> mItems = new ArrayList<>();
        ItemBean itemBean;
        for(int i=0; i<count; i++) {
            itemBean = new ItemBean(i, getMovieUrl());
            mItems.add(itemBean);
        }
        return mItems;
    }

    private void setListener() {
        mTrvFilmPoster.setOnItemListener(this);
        //边界监听
        mTrvFilmPoster.setOnInBorderKeyEventListener(this);
        mTrvFilmPoster.setOnLoadMoreListener(this);
    }

    protected void onMoveFocusBorder(View focusedView, float scale, float roundRadius) {
        if(null != mFocusBorder) {
            mFocusBorder.onFocus(focusedView, FocusBorder.OptionsFactory.get(scale, scale, roundRadius));
        }
    }

    private static String[] movieImgs = new String[]{
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=4116929131,507038119&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=2778645995,2482914601&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=471731589,4180970475&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=3838971077,4151306981&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=524852470,2918555158&fm=26&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=1954849729,4021317348&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1603461606,3565424705&fm=11&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3946902641,1203417546&fm=26&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3940629958,3916474893&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=177212210,3066778892&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=838139577,1861058471&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3800968064,203324487&fm=26&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=955228025,2469569596&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3483986471,1443536788&fm=26&gp=0.jpg",
            "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1889706933,2434657849&fm=26&gp=0.jpg",
            "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3346934314,259707941&fm=26&gp=0.jpg",
            "https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1069765571,2669917239&fm=26&gp=0.jpg",
            "https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=3179642771,1751426616&fm=26&gp=0.jpg"
    };

    public static String getMovieUrl() {
        Random random = new Random();
        return movieImgs[random.nextInt(movieImgs.length)];
    }

    @Override
    public void onItemPreSelected(TvRecyclerView parent, View itemView, int position) {
    }

    @Override
    public void onItemSelected(TvRecyclerView parent, View itemView, int position) {
        onMoveFocusBorder(itemView, 1.1f, 0);
    }

    @Override
    public void onItemClick(TvRecyclerView parent, View itemView, int position) {
        onMoveFocusBorder(itemView, 1.1f, 0);
        ToastUtils.showShort(getApplicationContext(),"onItemClick::"+position);
    }

    @Override
    public boolean onInBorderKeyEvent(int direction, int keyCode, KeyEvent event) {
        return false;//需要拦截返回true,否则返回false
    }

    @Override
    public boolean onLoadMore() {
        Loger.e(TAG, "onLoadMore()..................");
        mTrvFilmPoster.setLoadingMore(true); //正在加载数据
        mHandler.sendEmptyMessage(LOAD_MORE_DATA);
        return false; //是否还有更多数据
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        mFocusBorder.setVisible(hasFocus);
    }
}
