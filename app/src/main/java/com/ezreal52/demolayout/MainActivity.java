package com.ezreal52.demolayout;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import come.ezreal.RagnarTabLayout.RagnarTabLayout;
import come.ezreal.RagnarTabLayout.TabEntity;

public class MainActivity extends AppCompatActivity {

    private RagnarTabLayout mTablayout;
    private ViewPager mViewpager;
    private String[] mTitles = {"首页", "消息", "联系人", "更多"};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect,
            R.mipmap.tab_contact_unselect, R.mipmap.tab_more_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_speech_select,
            R.mipmap.tab_contact_select, R.mipmap.tab_more_select};
    private ArrayList<TabEntity> mTabEntities = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTablayout = findViewById(R.id.tablayout);
        mViewpager = findViewById(R.id.viewpager);


        //初始化fragments
        for (int i=0;i<mTitles.length;i++ ) {
            mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + mTitles[i]));
        }

        mViewpager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        for (int i = 0; i <mFragments.size(); i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        mTablayout.setpages(mTabEntities,mViewpager);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            return super.getItemPosition(object);
        }
    }

}
