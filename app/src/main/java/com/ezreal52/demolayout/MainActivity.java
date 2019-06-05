package com.ezreal52.demolayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ezreal52.demolayout.adapter.MyPagerAdapter;
import com.ezreal52.demolayout.fragment.ContactFragment;
import com.ezreal52.demolayout.fragment.HomeFragment;
import com.ezreal52.demolayout.fragment.MsgFragment;
import com.ezreal52.demolayout.fragment.MyFragment;

import come.ezreal.RagnarTabLayout.RagnarTabLayout;


public class MainActivity extends AppCompatActivity {

    private RagnarTabLayout mTablayout;
    private ViewPager mViewpager;
    private String[] mTitles = {"首页", "消息", "联系人", "我的"};
    private Fragment[] mFragments = {new HomeFragment(), new MsgFragment(), new ContactFragment(), new MyFragment(),new HomeFragment(), new MsgFragment(), new ContactFragment(), new MyFragment()};
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect,
            R.mipmap.tab_contact_unselect, R.mipmap.tab_more_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_speech_select,
            R.mipmap.tab_contact_select, R.mipmap.tab_more_select};
    private NewTabLayout newTab;
    private Button btn;
    private LinearLayout ln;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mViewpager = findViewById(R.id.viewpager);
        newTab = findViewById(R.id.newTablayout);

        mViewpager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(),mFragments));
        newTab.setTabTextColor(ContextCompat.getColor(this,R.color.white),ContextCompat.getColor(this,R.color.black) );
        newTab.setViewPager(mViewpager);



    }


}
