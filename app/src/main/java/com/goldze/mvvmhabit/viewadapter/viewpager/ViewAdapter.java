package com.goldze.mvvmhabit.viewadapter.viewpager;


import android.databinding.BindingAdapter;
import android.support.v4.view.*;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * Created by goldze on 2017/6/18.
 */
public final class ViewAdapter {
    @BindingAdapter(value = {"items"}, requireAll = false)
    public static void initView(android.support.v4.view.ViewPager viewPager, List items) {
        System.out.print("1");
    }
}

