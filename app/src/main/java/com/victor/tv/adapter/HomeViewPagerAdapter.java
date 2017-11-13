package com.victor.tv.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2017/11/8.
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
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

    public HomeViewPagerAdapter(FragmentManager fm) {
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
