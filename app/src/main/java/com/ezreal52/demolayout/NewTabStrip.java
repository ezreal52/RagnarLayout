package com.ezreal52.demolayout;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class NewTabStrip extends LinearLayout {
    public NewTabStrip(Context context) {
        this(context,null);
    }

    public NewTabStrip(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NewTabStrip(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }


}
