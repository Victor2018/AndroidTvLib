package com.victor.tv;

import android.animation.Animator;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.victor.tv.adapter.HomeViewPageAdapter;
import com.victor.tv.fragment.NetTvFragment;
import com.victor.tv.fragment.RecommendFragment;
import com.victor.tv.fragment.SettingFragment;
import com.victor.tv.fragment.VideoFragment;
import com.victor.tv.library.bridge.EffectNoDrawBridge;
import com.victor.tv.library.bridge.OpenEffectBridge;
import com.victor.tv.library.widget.MainUpView;
import com.victor.tv.library.widget.tablayout.TvTabLayout;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements
        ViewTreeObserver.OnGlobalFocusChangeListener,ViewPager.OnPageChangeListener {
    private String TAG = "HomeActivity";
    private TvTabLayout mTabLayout;
    private ViewPager mVpHome;
    // 移动边框.
    private MainUpView mainUpView;
    private EffectNoDrawBridge mEffectNoDrawBridge;
    private View mNewFocus;
    private View mOldView;

    private HomeViewPageAdapter homeViewPageAdapter;
    private List<Fragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_home);
        initialize();
    }
    private void initialize () {
        mTabLayout = (TvTabLayout) findViewById(R.id.tablayout);
        mTabLayout.setScaleValue(1.12f);
        mVpHome = (ViewPager) findViewById(R.id.vp_home);

        String[] titles = getResources().getStringArray(R.array.home_title);

        homeViewPageAdapter = new HomeViewPageAdapter(getSupportFragmentManager());
        fragments.add(new RecommendFragment());
        fragments.add(new NetTvFragment());
        fragments.add(new VideoFragment());
        fragments.add(new SettingFragment());
        homeViewPageAdapter.setFragTitles(titles);
        homeViewPageAdapter.setFrags(fragments);

        mVpHome.setAdapter(homeViewPageAdapter);
        mVpHome.setOnPageChangeListener(this);
        mVpHome.getViewTreeObserver().addOnGlobalFocusChangeListener(this);

        mTabLayout.setupWithViewPager(mVpHome);

        initMoveBridge();
    }

    private void initMoveBridge() {
        float density = getResources().getDisplayMetrics().density;
        mainUpView = (MainUpView) findViewById(R.id.mainUpView);
        mEffectNoDrawBridge = new EffectNoDrawBridge();
        mainUpView.setEffectBridge(mEffectNoDrawBridge);
        mEffectNoDrawBridge.setUpRectResource(R.mipmap.white_light_10); // 设置移动边框图片.
        RectF rectF = new RectF(getDimension(R.dimen.dp_5) * density, getDimension(R.dimen.dp_5) * density,
                getDimension(R.dimen.dp_5) * density, getDimension(R.dimen.dp_5) * density);
        mEffectNoDrawBridge.setDrawUpRectPadding(rectF);

    }


    private float getDimension(int id) {
        return getResources().getDimension(id);
    }

    @Override
    public void onGlobalFocusChanged(View oldFocus, View newFocus) {
      /*  Animation mAnimFocus = AnimationUtils.loadAnimation(this, R.anim.scale);
        Animation mAnimUnFocus = AnimationUtils.loadAnimation(this, R.anim.scaleback);

        mAnimFocus.setFillAfter(true);
        mAnimUnFocus.setFillAfter(true);

        if (oldFocus != null) {
            oldFocus.startAnimation(mAnimUnFocus);
        }
        if (newFocus != null) {
            newFocus.startAnimation(mAnimFocus);
            newFocus.bringToFront();
        }*/
        if (newFocus != null && !(newFocus instanceof TvTabLayout)) {
            mEffectNoDrawBridge.setVisibleWidget(false);
            mNewFocus = newFocus;
            mOldView = oldFocus;
            mainUpView.setFocusView(newFocus, oldFocus, 1.12f);
            // 让被挡住的焦点控件在前面.
            newFocus.bringToFront();
        } else { // 标题栏处理.
            mNewFocus = null;
            mOldView = null;
            mainUpView.setUnFocusView(oldFocus);
            mEffectNoDrawBridge.setVisibleWidget(true);
        }

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_IDLE: // viewpager 滚动结束.
                mainUpView.setFocusView(mNewFocus, mOldView, 1.2f);
                // 监听动画事件.
                mEffectNoDrawBridge.setOnAnimatorListener(new OpenEffectBridge.NewAnimatorListener() {
                    @Override
                    public void onAnimationStart(OpenEffectBridge bridge, View view, Animator animation) {
                    }

                    @Override
                    public void onAnimationEnd(OpenEffectBridge bridge, View view, Animator animation) {
                        // 动画结束的时候恢复原来的时间. (这里只是DEMO)
                        mEffectNoDrawBridge.setTranDurAnimTime(OpenEffectBridge.DEFAULT_TRAN_DUR_ANIM);
                    }
                });
                // 让被挡住的焦点控件在前面.
                if (mNewFocus != null)
                    mNewFocus.bringToFront();
                break;
            case ViewPager.SCROLL_STATE_DRAGGING:
                break;
            case ViewPager.SCROLL_STATE_SETTLING: // viewPager开始滚动.
                mEffectNoDrawBridge.clearAnimator(); // 清除之前的动画.
                mEffectNoDrawBridge.setTranDurAnimTime(0); // 避免边框从其它地方跑出来.
                break;
        }
    }
}
