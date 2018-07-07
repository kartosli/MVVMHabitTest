package com.goldze.mvvmhabit.ui.fragment;

import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.goldze.mvvmhabit.BR;
import com.goldze.mvvmhabit.R;
import com.goldze.mvvmhabit.databinding.FragmentNetworkBinding;
import com.goldze.mvvmhabit.databinding.FragmentTestlistBinding;
import com.goldze.mvvmhabit.databinding.ItemTestlistBinding;
import com.goldze.mvvmhabit.entity.DemoEntity;
import com.goldze.mvvmhabit.ui.vm.NetWorkViewModel;
import com.goldze.mvvmhabit.ui.vm.TestListItemViewModel;
import com.goldze.mvvmhabit.ui.vm.TestListViewModel;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * Created by goldze on 2017/7/17.
 * 网络请求列表界面
 */

public class TestListFragment extends BaseFragment<FragmentTestlistBinding, TestListViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return R.layout.fragment_testlist;

    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public TestListViewModel initViewModel() {
        return new TestListViewModel(this.getContext());
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.refreshLayout();
        ProgressLayout header = new ProgressLayout(this.getActivity());
//        refreshLayout.setHeaderView(header);

        System.out.print(binding.twinklingRefreshLayout);
        binding.twinklingRefreshLayout.setHeaderView(header);
        binding.twinklingRefreshLayout.startRefresh();
        binding.twinklingRefreshLayout.setFloatRefresh(true);
    }

    @Override
    public void initViewObservable() {
        viewModel.uc.isFinishAddRefreshing.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                LinearLayout linearLayout=new LinearLayout(binding.getRoot().getContext());
                RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                linearLayout.setLayoutParams(params);
                binding.layoutTestadd.removeAllViews();
                ObservableList oList = viewModel.observableAddList;
                for (int y=0;y<oList.size();y++){
                    ItemTestlistBinding itemAddBinding  = (ItemTestlistBinding)oList.get(y);
                    binding.layoutTestadd.addView(itemAddBinding.getRoot());
                }
//                binding.layoutTestadd.addView(linearLayout);
            }
        });
        viewModel.uc.isFinishRefreshing.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                ObservableBoolean bb=(ObservableBoolean)observable;
                boolean is = bb.get();
                if(!is){
                }else{
                    binding.twinklingRefreshLayout.finishRefreshing();
                }

            }
        });
        //监听下拉刷新完成
/*        viewModel.uc.isFinishRefreshing.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                //结束刷新
                binding.twinklingRefreshLayout.finishRefreshing();
            }
        });
        //监听上拉加载完成
        viewModel.uc.isFinishLoadmore.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable observable, int i) {
                //结束刷新
                binding.twinklingRefreshLayout.finishLoadmore();
            }
        });*/
    }
}
