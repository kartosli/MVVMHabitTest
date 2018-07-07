package com.goldze.mvvmhabit.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goldze.mvvmhabit.BR;
import com.goldze.mvvmhabit.R;
import com.goldze.mvvmhabit.databinding.FragmentFormBinding;
import com.goldze.mvvmhabit.entity.FormEntity;
import com.goldze.mvvmhabit.ui.vm.FormViewModel;

import me.goldze.mvvmhabit.base.BaseFragment;

/**
 * Created by goldze on 2017/7/17.
 * 表单提交/编辑界面
 */

public class FormFragment extends BaseFragment<FragmentFormBinding, FormViewModel> {

    private FormEntity entity = new FormEntity();
    @Override
    public void initParam() {
//        viewModel = ViewModelProviders.of(this).get(FormViewModel.class);
        //获取列表传入的实体
        Bundle mBundle = getArguments();
        if (mBundle != null) {
            entity = mBundle.getParcelable("entity");
        }
    }

    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return R.layout.fragment_form;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, initContentView(inflater, container, savedInstanceState), container, false);
        binding.setVariable(initVariableId(), viewModel = initViewModel());
        binding.setViewModel(viewModel);
        binding.executePendingBindings();
        PagerAdapter adapter = binding.pager.getAdapter();
        binding.tabs.setTabsFromPagerAdapter(adapter);
        binding.tabs.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(binding.pager));
        binding.pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabs));
/*        binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                System.out.print("");
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                System.out.print("");
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                System.out.print("");
            }
        });*/
        return binding.getRoot();

    }

    @Override
    public void initData() {
        super.initData();

    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public FormViewModel initViewModel() {
        return new FormViewModel(this, entity);
    }

    @Override
    public void initViewObservable() {
        //监听刷新界面
        viewModel.uc.refreshUIObservable.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                //调用父类的刷新方法
                PagerAdapter adapter = binding.pager.getAdapter();
                binding.tabs.setTabsFromPagerAdapter(adapter);
                refreshLayout();
            }
        });
    }
}
