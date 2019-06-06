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
                tabView.setSelected(true);
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
        public void onPageScrolled(int position, float positionOffset, int i1) {
            tabStrip.onViewPagerPageChanged(position,positionOffset);
            scrollToTab(position,positionOffset);
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



    //移动sccollview

    /**
     * @param tabIndex
     * @param positionOffset
     */
    //offset是0-1的数，tabWidth*offset
    private void scrollToTab(int tabIndex, float positionOffset) {
        final int tabStripChildCount = tabStrip.getChildCount();
        if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
            return;
        }

        View selectedTab = tabStrip.getChildAt(tabIndex);
        int extraOffset ;
        int x =0;
        View nextTab = tabStrip.getChildAt(tabIndex + 1);
        int selectHalfWidth = Utils.getWidth(selectedTab) / 2 + Utils.getMarginEnd(selectedTab);
        int nextHalfWidth = Utils.getWidth(nextTab) / 2 + Utils.getMarginStart(nextTab);
        extraOffset = Math.round(positionOffset * (selectHalfWidth + nextHalfWidth));
        int start = Utils.getStart(selectedTab);
        x += start + extraOffset;
        scrollTo(x, 0);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);



    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        // Ensure first scroll
        if (changed && viewPager != null) {
            scrollToTab(viewPager.getCurrentItem(), 0);
        }
    }


    //设置tab文字颜色
    public void setTabTextColor(int selectedColorId,int unselectedColorId) {
        ContextCompat.getColor(context,selectedColorId);

        tabViewTextColors = new ColorStateList(
                new int[][]{new int[]{android.R.attr.state_selected},new int[]{}},
                new int[]{  ContextCompat.getColor(context,selectedColorId),  ContextCompat.getColor(context,unselectedColorId)});


    }
    //设置indicator颜色
    public void setTabIndicatorColor(int indicatorColorId){
        tabStrip.setTabIndicatorColor(ContextCompat.getColor(context,indicatorColorId));

    }

}
