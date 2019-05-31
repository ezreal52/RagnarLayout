package come.ezreal.RagnarTabLayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
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


public class RagnarTabLayout extends TabLayout{
    private final float iconWidth,iconHeight,textSize;
    private int textColorSelect;
    private int textColorUnSelect;

    private ViewPager mViewPager;
    private TabLayouOnViewPagerListener tlOnVpListener;
    private List<TabEntity> entities;
    private Context context;

    public RagnarTabLayout(Context context) {
        this(context,null);
    }


    public RagnarTabLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }


    public RagnarTabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTabLayout);
        textColorSelect = typedArray.getColor(R.styleable.CustomTabLayout_textSelectColor, Color.parseColor("#ffffff"));
        textColorUnSelect = typedArray.getColor(R.styleable.CustomTabLayout_textUnSelectColor, Color.parseColor("#AAffffff"));
        iconWidth = typedArray.getDimension(R.styleable.CustomTabLayout_iconWidth,dp2px(22));
        iconHeight = typedArray.getDimension(R.styleable.CustomTabLayout_iconHeight, dp2px(22));
        textSize  = typedArray.getDimension(R.styleable.CustomTabLayout_textSize,sp2px(12));
        typedArray.recycle();
    }

    public void setpages(ArrayList<TabEntity> entities, ViewPager viewPager){
         this.entities = entities;
         this.mViewPager = viewPager;
         setupWithViewPager(viewPager);
         viewPager.addOnPageChangeListener(new TabLayouOnViewPagerListener());
         for(int i=0;i<getTabCount();i++){
            View v = LayoutInflater.from(context).inflate(R.layout.item_tab,null);
            TextView tv = v.findViewById(R.id.tab_tv);

             tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
            ImageView img = v.findViewById(R.id.tv_img);

             img.setLayoutParams(new LinearLayout.LayoutParams((int) iconWidth,(int) iconHeight));
            img.setImageResource(entities.get(i).getTabUnselectedIcon());
            tv.setText(entities.get(i).getTabTitle());
            final Tab  tab  = getTabAt(i).setCustomView(v);
             v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectItem(tab.getPosition());
                }
            });

         }

         selectItem(0);

    }

    public void selectItem(int index){
        clearAllState();
        Tab tab = getTabAt(index);
        TextView tv =  tab.getCustomView().findViewById(R.id.tab_tv);
        ImageView img =tab.getCustomView().findViewById(R.id.tv_img);
        tv.setTextColor(textColorSelect);
        img.setImageResource(entities.get(index).getTabSelectedIcon());
        tab.select();

    }
     public void clearAllState(){
         for(int i=0;i<getTabCount();i++){
             TextView tv = getTabAt(i).getCustomView().findViewById(R.id.tab_tv);
             ImageView img = getTabAt(i).getCustomView().findViewById(R.id.tv_img);
             tv.setTextColor(textColorUnSelect);
             img.setImageResource(entities.get(i).getTabUnselectedIcon());

         }
    }



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

    protected int dp2px(float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
    protected int sp2px(float sp) {
        final float scale = this.context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * scale + 0.5f);
    }


}
