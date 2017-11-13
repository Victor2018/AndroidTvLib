package com.victor.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.victor.tv.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */

public class HomeViewPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> frags;
    private String[] fragTitles;

    public void setFrags(List<Fragment> frags) {
        this.frags = frags;
    }

    public void setFragTitles(String[] fragTitles) {
        this.fragTitles = fragTitles;
    }

    public List<Fragment> getFrags() {
        return frags;
    }

    public String[] getFragTitles() {
        return fragTitles;
    }

    public HomeViewPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragTitles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return frags.get(position);
    }

    @Override
    public int getCount() {
        return frags == null ? 0 :frags.size();
    }

}
