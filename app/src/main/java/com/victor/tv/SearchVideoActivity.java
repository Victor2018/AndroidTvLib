package com.victor.tv;

import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.victor.model.data.ItemBean;
import com.victor.tv.adapter.FilmPosterAdapter;
import com.victor.tv.library.util.Loger;
import com.victor.tv.library.util.ToastUtils;
import com.victor.widget.focus.FocusBorder;
import com.victor.widget.keyboard.SkbContainer;
import com.victor.widget.keyboard.SoftKey;
import com.victor.widget.keyboard.SoftKeyBoardListener;
import com.victor.widget.recycleview.TvRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SearchVideoActivity extends BaseActivity implements TvRecyclerView.OnItemListener,
        TvRecyclerView.OnInBorderKeyEventListener,TvRecyclerView.OnLoadMoreListener,View.OnFocusChangeListener {
    private String TAG = "SearchVideoActivity";
    private static final int LOAD_MORE_DATA = 0x1001;

    TextView input_tv;
    SkbContainer skbContainer;

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
        setContentView(R.layout.activity_search_video);
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


        input_tv = (TextView) findViewById(R.id.input_tv);
        skbContainer = (SkbContainer) findViewById(R.id.skbContainer);
        skbContainer.setSkbLayout(R.xml.sbd_qwerty);
        skbContainer.setFocusable(true);
        skbContainer.setFocusableInTouchMode(true);
        // 设置属性(默认是不移动的选中边框)
        setSkbContainerMove();
        //
        skbContainer.setSelectSofkKeyFront(true); // 设置选中边框最前面.
        // 监听键盘事件.
        skbContainer.setOnSoftKeyBoardListener(new SoftKeyBoardListener() {
            @Override
            public void onCommitText(SoftKey softKey) {
                if ((skbContainer.getSkbLayoutId() == R.xml.skb_t9_keys)) {
                    onCommitT9Text(softKey);
                } else {
                    int keyCode = softKey.getKeyCode();
                    String keyLabel = softKey.getKeyLabel();
                    if (!TextUtils.isEmpty(keyLabel)) { // 输入文字.
                        input_tv.setText(input_tv.getText() + softKey.getKeyLabel());
                    } else { // 自定义按键，这些都是你自己在XML中设置的keycode.
                        keyCode = softKey.getKeyCode();
                        if (keyCode == KeyEvent.KEYCODE_DEL) {
                            String text = input_tv.getText().toString();
                            if (TextUtils.isEmpty(text)) {
                                Toast.makeText(getApplicationContext(), "文本已空", Toast.LENGTH_LONG).show();
                            } else {
                                input_tv.setText(text.substring(0, text.length() - 1));
                            }
                        } else if (keyCode == KeyEvent.KEYCODE_BACK) {
                            finish();
                        } else if (keyCode == 66) {
                            Toast.makeText(getApplicationContext(), "回车", Toast.LENGTH_LONG).show();
                        } else if (keyCode == 250) { //切换键盘
                            // 这里只是测试，你可以写自己其它的数字键盘或者其它键盘
                            setSkbContainerOther();
                            skbContainer.setSkbLayout(R.xml.sbd_number);
                        }
                    }
                }
            }

            @Override
            public void onBack(SoftKey key) {
                finish();
            }

            @Override
            public void onDelete(SoftKey key) {
                String text = input_tv.getText().toString();
                input_tv.setText(text.substring(0, text.length() - 1));
            }

        });
        // DEMO（测试键盘失去焦点和获取焦点)
        skbContainer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Loger.d(TAG,"hasFocus:"+hasFocus);
                if (hasFocus) {
                    if (mOldSoftKey != null)
                        skbContainer.setKeySelected(mOldSoftKey);
                    else
                        skbContainer.setDefualtSelectKey(0, 0);
                } else {
                    mOldSoftKey = skbContainer.getSelectKey();
                    skbContainer.setKeySelected(null);
                }
            }
        });
      /*  // 英文键盘切换测试.
        findViewById(R.id.en_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSkbContainerMove();
                skbContainer.setSkbLayout(R.xml.sbd_qwerty);
            }
        });
        // 数字键盘切换测试.
        findViewById(R.id.num_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSkbContainerOther();
                skbContainer.setSkbLayout(R.xml.sbd_number);
                skbContainer.setKeyScale(1.1f); // 设置按键放大.
            }
        });
        // 全键盘切换测试.
        findViewById(R.id.all_key_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSkbContainerOther();
                skbContainer.setSkbLayout(R.xml.skb_all_key);
                skbContainer.setKeyScale(1.0f); // 设置按键放大.
            }
        });
        findViewById(R.id.t9_key_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSkbContainerMove();
                skbContainer.setSkbLayout(R.xml.skb_t9_keys);
            }
        });*/
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

    private void setSkbContainerMove() {
        mOldSoftKey = null;
        skbContainer.setMoveSoftKey(true); // 设置是否移动按键边框.
        RectF rectf = new RectF((int)getResources().getDimension(R.dimen.
                dp_8), (int)getResources().getDimension(R.dimen.
                dp_8), (int)getResources().getDimension(R.dimen.
                dp_8), (int)getResources().getDimension(R.dimen.
                dp_8));
        skbContainer.setSoftKeySelectPadding(rectf); // 设置移动边框相差的间距.
        skbContainer.setMoveDuration(200); // 设置移动边框的时间(默认:300)
        skbContainer.setSelectSofkKeyFront(true); // 设置选中边框在最前面.
    }

    /**
     * 切换布局测试.
     * 因为布局不相同，所以属性不一样，
     * 需要重新设置(不用参考我的,只是DEMO)
     */
    private void setSkbContainerOther() {
        mOldSoftKey = null;
        skbContainer.setMoveSoftKey(false);
        skbContainer.setSoftKeySelectPadding(0);
        skbContainer.setSelectSofkKeyFront(false);
    }

    SoftKey mOldSoftKey;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (skbContainer.onSoftKeyDown(keyCode, event))
            return true;
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (skbContainer.onSoftKeyUp(keyCode, event))
            return true;
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 处理T9键盘的按键.
     * @param softKey
     */
    private void onCommitT9Text(SoftKey softKey) {
        Toast.makeText(getApplicationContext(), "keycode:" + softKey.getKeyCode(), Toast.LENGTH_SHORT).show();
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
