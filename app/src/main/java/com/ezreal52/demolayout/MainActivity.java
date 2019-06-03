package com.ezreal52.demolayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import come.ezreal.RagnarTabLayout.RagnarTabLayout;

public class MainActivity extends AppCompatActivity {

    private RagnarTabLayout mTablayout;
    private ViewPager mViewpager;
    private String[] mTitles = {"首页", "消息", "联系人", "我的"};
    private Fragment[] mFragments = {new HomeFragment(), new MsgFragment(), new ContactFragment(), new MyFragment()};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect,
            R.mipmap.tab_contact_unselect, R.mipmap.tab_more_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_speech_select,
            R.mipmap.tab_contact_select, R.mipmap.tab_more_select};




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTablayout = findViewById(R.id.tablayout);
        mViewpager = findViewById(R.id.viewpager);

        mTablayout.init(getSupportFragmentManager(),mViewpager,mFragments,mTitles, mIconUnselectIds,mIconSelectIds );
    }


}
