package com.ezreal52.demolayout.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ezreal52.demolayout.R;
import com.ezreal52.demolayout.utils.LogUtils;

public class HomeFragment extends BaseFragment {
    private Button btnHome;
    private TextView tvHome;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home,null);

        tvHome = v.findViewById(R.id.tv_home);
        btnHome = v.findViewById(R.id.btn_home);

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LogUtils.e("l:"+tvHome.getLeft());
                LogUtils.e("t:"+tvHome.getTop());
                LogUtils.e("r:"+tvHome.getRight());
                LogUtils.e("b:"+tvHome.getBottom());
                LogUtils.e("width:"+tvHome.getWidth());
                LogUtils.e("height:"+tvHome.getHeight());
                tvHome.scrollBy(100,100);

            }
        });


        return v;
    }
}
