package com.ezreal52.demolayout.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;

public class MyPagerAdapter extends FragmentPagerAdapter {
    private  Fragment[] mFragments;

    public MyPagerAdapter(FragmentManager fm, Fragment[] mFragments) {
        super(fm);
        this.mFragments = mFragments;

    }

    @Override
    public int getCount() {
        return mFragments.length;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];

    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return super.getItemPosition(object);
    }
}
