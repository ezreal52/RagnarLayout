package com.ezreal52.demolayout;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.ezreal52.demolayout.utils.LogUtils;

public class NewTabStrip extends LinearLayout {


    private Context context;

    private int lastPosition;
    private int selectedPosition;
    private float selectionOffset;
    private static final int GRAVITY_BOTTOM = 0;
    private static final int GRAVITY_TOP = 1;
    private static final int GRAVITY_CENTER = 2;
    private final RectF indicatorRectF = new RectF();
    private boolean indicatorWithoutPadding;
    private boolean indicatorAlwaysInCenter;
    private boolean indicatorInFront;
    private int indicatorThickness;
    private int indicatorWidth;
    private int indicatorGravity;
    private float indicatorCornerRadius;
    private Paint indicatorPaint;
    private int tabIndicatorColor;
    private static final int AUTO_WIDTH = -1;
    private static final int DEFAULT_TOP_BORDER_THICKNESS_DIPS = 0;
    private static final byte DEFAULT_TOP_BORDER_COLOR_ALPHA = 0x26;
    private static final int DEFAULT_BOTTOM_BORDER_THICKNESS_DIPS = 2;
    private static final byte DEFAULT_BOTTOM_BORDER_COLOR_ALPHA = 0x26;
    private static final int SELECTED_INDICATOR_THICKNESS_DIPS = 8;
    private static final int DEFAULT_SELECTED_INDICATOR_COLOR = 0xFF33B5E5;
    private static final float DEFAULT_INDICATOR_CORNER_RADIUS = 0f;
    private static final int DEFAULT_DIVIDER_THICKNESS_DIPS = 1;
    private static final byte DEFAULT_DIVIDER_COLOR_ALPHA = 0x20;
    private static final float DEFAULT_DIVIDER_HEIGHT = 0.5f;
    private static final boolean DEFAULT_INDICATOR_IN_CENTER = false;
    private static final boolean DEFAULT_INDICATOR_IN_FRONT = false;
    private static final boolean DEFAULT_INDICATOR_WITHOUT_PADDING = false;
    private static final int DEFAULT_INDICATOR_GRAVITY = GRAVITY_BOTTOM;
    private static final boolean DEFAULT_DRAW_DECORATION_AFTER_TAB = false;

    public NewTabStrip(Context context) {
        this(context, null);
    }

    public NewTabStrip(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewTabStrip(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setWillNotDraw(false);

        boolean indicatorWithoutPadding = DEFAULT_INDICATOR_WITHOUT_PADDING;
        boolean indicatorInFront = DEFAULT_INDICATOR_IN_FRONT;
        boolean indicatorAlwaysInCenter = DEFAULT_INDICATOR_IN_CENTER;
        int indicatorGravity = DEFAULT_INDICATOR_GRAVITY;
        int indicatorColor = DEFAULT_SELECTED_INDICATOR_COLOR;
        int indicatorColorsId = NO_ID;
        int indicatorThickness = (int) (10);
        int indicatorWidth = AUTO_WIDTH;

        this.context = context;
        this.indicatorAlwaysInCenter = indicatorAlwaysInCenter;
        this.indicatorWithoutPadding = indicatorWithoutPadding;
        this.indicatorInFront = indicatorInFront;
        this.indicatorThickness = indicatorThickness;
        this.indicatorWidth = indicatorWidth;
        this.indicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.indicatorCornerRadius = indicatorCornerRadius;
        this.indicatorGravity = indicatorGravity;

    }

    void onViewPagerPageChanged(int position, float positionOffset) {
        selectedPosition = position;
        selectionOffset = positionOffset;

        if (positionOffset == 0f && lastPosition != selectedPosition) {
            lastPosition = selectedPosition;
        }
        invalidate();


    }



    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        LogUtils.e("dispatchDraw:");
        drawDecoration(canvas);
    }

    private void drawDecoration(Canvas canvas) {

        final int height = getHeight();
        final int width = getWidth();
        final int tabCount = getChildCount();


        if (tabCount > 0) {
            View selectedTab = getChildAt(selectedPosition);
            int selectedStart = Utils.getStart(selectedTab, indicatorWithoutPadding);
            int selectedEnd = Utils.getEnd(selectedTab, indicatorWithoutPadding);
            int left;
            int right;

            left = selectedStart;
            right = selectedEnd;
            int color = ContextCompat.getColor(context,R.color.blue);
            float thickness = indicatorThickness;
            if (selectionOffset > 0f && selectedPosition < (getChildCount() - 1)) {


                // Draw the selection partway between the tabs
                float startOffset =selectionOffset;

                float endOffset = selectionOffset;


                float thicknessOffset = 1f;

                View nextTab = getChildAt(selectedPosition + 1);
                int nextStart = Utils.getStart(nextTab, indicatorWithoutPadding);
                int nextEnd = Utils.getEnd(nextTab, indicatorWithoutPadding);

                left = (int) (startOffset * nextStart + (1.0f - startOffset) * left);
                right = (int) (endOffset * nextEnd + (1.0f - endOffset) * right);
                thickness = thickness * thicknessOffset;
            }

            drawIndicator(canvas, left, right, height, thickness, color);

        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }


    private void drawIndicator(Canvas canvas, int left, int right, int height, float thickness,
                               int color) {
        if (indicatorThickness <= 0 || indicatorWidth == 0) {
            return;
        }

        float center;
        float top;
        float bottom;

        switch (indicatorGravity) {
            case GRAVITY_TOP:
                center = indicatorThickness / 2f;
                top = center - (thickness / 2f);
                bottom = center + (thickness / 2f);
                break;
            case GRAVITY_CENTER:
                center = height / 2f;
                top = center - (thickness / 2f);
                bottom = center + (thickness / 2f);
                break;
            case GRAVITY_BOTTOM:
            default:
                center = height - (indicatorThickness / 2f);
                top = center - (thickness / 2f);
                bottom = center + (thickness / 2f);
        }



        indicatorPaint.setColor(color);
        if (indicatorWidth == AUTO_WIDTH) {
            indicatorRectF.set(left, top, right, bottom);

        } else {
            float padding = (Math.abs(left - right) - indicatorWidth) / 2f;
            indicatorRectF.set(left + padding, top, right - padding, bottom);

        }

        if (indicatorCornerRadius > 0f) {
            canvas.drawRoundRect(
                    indicatorRectF, indicatorCornerRadius,
                    indicatorCornerRadius, indicatorPaint);
        } else {
            canvas.drawRect(indicatorRectF, indicatorPaint);

        }
    }

    //设置indicator颜色
    public void setTabIndicatorColor(int indicatorColor){
          tabIndicatorColor = indicatorColor;

    }
}
