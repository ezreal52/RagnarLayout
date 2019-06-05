package com.ezreal52.demolayout;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ezreal52.demolayout.utils.LogUtils;

public class  NewTabLayout extends HorizontalScrollView {

    private static final int TAB_VIEW_TEXT_COLOR = 0xFC000000;
    private static final String TAG = "NewTabLayout";
    private final Context context;
    private  ColorStateList tabViewTextColors;
    private  Paint mPaint;
    private NewTabStrip tabStrip;
    private ViewPager viewPager;
    private InternalTabClickListener internalTabClickListener ;


    public NewTabLayout(Context context) {
        this(context, null);
    }

    public NewTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.NewTabLayout,defStyleAttr,0);

        this.context =context;
        tabStrip = new NewTabStrip(context);


        //tab字体颜色
        ColorStateList textColors = a.getColorStateList(R.styleable.NewTabLayout_newTabTextColor);





        this.tabViewTextColors = (textColors != null) ? textColors : ColorStateList.valueOf(TAB_VIEW_TEXT_COLOR);

        addView(tabStrip, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        // Make sure that the Tab Strips fills this View
        setFillViewport(true);
        this.internalTabClickListener =  new InternalTabClickListener() ;


    }

    public void setViewPager(ViewPager viewPager) {
        tabStrip.removeAllViews();
        this.viewPager = viewPager;
        if (viewPager != null && viewPager.getAdapter() != null) {
            populateTabStrip();
            viewPager.addOnPageChangeListener(new InternalViewPagerListener());
        }
    }



    private void populateTabStrip() {

        final PagerAdapter adapter = viewPager.getAdapter();

        for (int i = 0; i < adapter.getCount(); i++) {

            //自定义tabview还是用默认的
            View tabView  =LayoutInflater.from(context).inflate(R.layout.item_tab, tabStrip,false);
            TextView tv = tabView.findViewById(R.id.tab_tv);
            tv.setTextColor(tabViewTextColors);
            tv.setText("体育新闻"+i);

            if (tabView == null) {
                throw new IllegalStateException("tabView is null.");
            }
            LogUtils.e("count:L"+adapter.getCount());
            //设置每个tab等宽
            if (true) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tabView.getLayoutParams();

                lp.width = 0;
                lp.weight = 1;
            }



            if (internalTabClickListener != null) {
                tabView.setOnClickListener(internalTabClickListener);
            }

            tabStrip.addView(tabView);

            if (i == viewPager.getCurrentItem()) {
                tabView.setEnabled(true);
            }


        }

    }

    private class InternalTabClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < tabStrip.getChildCount(); i++) {
                if (v == tabStrip.getChildAt(i)) {
                    viewPager.setCurrentItem(i);
                    return;
                }
            }
        }
    }

    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float v, int i1) {

        }

        @Override
        public void onPageSelected(int position) {

            for (int i = 0, size = tabStrip.getChildCount(); i < size; i++) {
                tabStrip.getChildAt(i).setSelected(position == i);
            }

        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure:");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);



    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        Log.d(TAG, "-------------:");
        Log.d(TAG, "l:" + l);
        Log.d(TAG, "t:" + t);
        Log.d(TAG, "r:" + r);
        Log.d(TAG, "b:" + b);
        Log.d(TAG, "-------------:");
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        Log.d(TAG, "onDraw:");


    }

    public void setTabTextColor(int selectedColor,int unselectedColor) {

        tabViewTextColors = new ColorStateList(
                new int[][]{new int[]{android.R.attr.state_selected},new int[]{}},
                new int[]{selectedColor,unselectedColor});


    }

}
