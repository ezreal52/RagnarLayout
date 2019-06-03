package come.ezreal.RagnarTabLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class RagnarTabLayout extends TabLayout {
    private final float iconWidth, iconHeight, textSize;
    private int textColorSelect;
    private int textColorUnSelect;
    private TabLayouOnViewPagerListener tlOnVpListener;
    private ViewPager mViewpager;
    private Context context;
    private String[] mTitles;
    private Fragment[] mFragments ;
    private int[] mIconUnselectIds;
    private int[] mIconSelectIds;
    public RagnarTabLayout(Context context) {
        this(context, null);
    }


    public RagnarTabLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public RagnarTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTabLayout);
        //tab字体的颜色
        textColorSelect = typedArray.getColor(R.styleable.CustomTabLayout_textSelectColor, Color.parseColor("#ffffff"));
        textColorUnSelect = typedArray.getColor(R.styleable.CustomTabLayout_textUnSelectColor, Color.parseColor("#AAffffff"));
        iconWidth = typedArray.getDimension(R.styleable.CustomTabLayout_iconWidth, dp2px(22));
        //tab图片尺寸
        iconHeight = typedArray.getDimension(R.styleable.CustomTabLayout_iconHeight, dp2px(22));
        textSize = typedArray.getDimension(R.styleable.CustomTabLayout_textSize, sp2px(12));
        typedArray.recycle();
    }

    public void init(FragmentManager fragmentManager,ViewPager viewPager,Fragment[] mfragments,String[] mTitles,int[] mIconUnselectIds,int[] mIconSelectIds) {
        this.mFragments = mfragments;
        this.mTitles = mTitles;
        this.mIconUnselectIds = mIconUnselectIds;
        this.mIconSelectIds = mIconSelectIds;
        this.mViewpager = viewPager;

        mViewpager.setAdapter(new MyPagerAdapter(fragmentManager));

        //实现viewpager和tablayout的联动
        setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayouOnViewPagerListener());
        for (int i = 0; i < getTabCount(); i++) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_tab, null);
            TextView tv = v.findViewById(R.id.tab_tv);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            ImageView img = v.findViewById(R.id.tv_img);
            img.setLayoutParams(new LinearLayout.LayoutParams((int) iconWidth, (int) iconHeight));
            img.setImageResource(mIconUnselectIds[i]);
            tv.setText(mTitles[i]);
            final Tab tab = getTabAt(i).setCustomView(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectItem(tab.getPosition());
                }
            });

        }

        selectItem(0);

    }

    public void selectItem(int index) {
        clearAllState();
        Tab tab = getTabAt(index);
        TextView tv = tab.getCustomView().findViewById(R.id.tab_tv);
        ImageView img = tab.getCustomView().findViewById(R.id.tv_img);
        tv.setTextColor(textColorSelect);
        img.setImageResource(mIconSelectIds[index]);
        tab.select();

    }

    public void clearAllState() {
        for (int i = 0; i < getTabCount(); i++) {
            TextView tv = getTabAt(i).getCustomView().findViewById(R.id.tab_tv);
            ImageView img = getTabAt(i).getCustomView().findViewById(R.id.tv_img);
            tv.setTextColor(textColorUnSelect);
            img.setImageResource(mIconUnselectIds[i]);

        }
    }


    //因为使用了自定义view的tab，所以tab状态的改变需要自己实现
    public class TabLayouOnViewPagerListener implements ViewPager.OnPageChangeListener {


        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            selectItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
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


    protected int dp2px(float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    protected int sp2px(float sp) {
        final float scale = this.context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }


}
