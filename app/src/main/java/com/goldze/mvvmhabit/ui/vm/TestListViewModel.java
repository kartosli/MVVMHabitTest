package com.goldze.mvvmhabit.ui.vm;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.view.LayoutInflater;

import com.goldze.mvvmhabit.R;
import com.goldze.mvvmhabit.databinding.ItemTestlistBinding;
import com.goldze.mvvmhabit.entity.DemoEntity;
import com.goldze.mvvmhabit.service.DemoApiService;
import com.goldze.mvvmhabit.utils.RetrofitClient;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.http.BaseResponse;
import me.goldze.mvvmhabit.utils.RxUtils;
import me.goldze.mvvmhabit.utils.ToastUtils;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * Created by User on 2018/7/1.
 */

public class TestListViewModel extends BaseViewModel {
    @Override
    public void onCreate() {
        super.onCreate();
//        requestNetWork();
    }

    public TestListViewModel(Context context) {
        super(context);
    }

    public UIChangeObservable uc = new UIChangeObservable();
    public class UIChangeObservable{
        public ObservableBoolean isFinishAddRefreshing = new ObservableBoolean(true);
        public ObservableBoolean isFinishRefreshing = new ObservableBoolean(true);

    }

    public BindingCommand onRefreshCommand =  new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("下拉刷新");
            requestNetWork();
        }
    });
    //给RecyclerView添加ItemView
//    public ItemBinding<NetWorkItemViewModel> itemBinding = ItemBinding.of(com.goldze.mvvmhabit.BR.viewModel, R.layout.item_network);
    public ItemBinding<TestListItemViewModel> itemBinding =  ItemBinding.of(me.goldze.mvvmhabit.BR.viewModel, R.layout.item_testlist);
    //给RecyclerView添加ObservableList
    public ObservableList<TestListItemViewModel> observableList = new ObservableArrayList<>();
    public ObservableList<ItemTestlistBinding> observableAddList = new ObservableArrayList<>();
    private void requestNetWork() {
        RetrofitClient.getInstance().create(DemoApiService.class)
                .demoGet().
                compose(RxUtils.bindToLifecycle(context))
                .compose(RxUtils.schedulersTransformer())
                .compose(RxUtils.exceptionTransformer())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        uc.isFinishRefreshing.set(!uc.isFinishRefreshing.get());
                    }
                }).subscribe(new Consumer<BaseResponse<DemoEntity>>() {
            @Override
            public void accept(BaseResponse<DemoEntity> response) throws Exception {

                for(int i=0;i<3;i++){
                    ItemTestlistBinding itemAddBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.item_testlist,null,false);

                    DemoEntity.ItemsEntity entity = new DemoEntity.ItemsEntity();

                    TestListItemViewModel itemViewModel = new TestListItemViewModel(context,entity);
                    itemAddBinding.setViewModel(itemViewModel);
                    entity.setName("test1111111111111111111111111111111111111"+i);
                    observableAddList.add(itemAddBinding);
                }




                //                        itemIndex = 0;
                //关闭对话框
                dismissDialog();
                //清除列表
                observableList.clear();
                //刷新完成收回
                uc.isFinishAddRefreshing.set(!uc.isFinishAddRefreshing.get());
                uc.isFinishRefreshing.set(!uc.isFinishRefreshing.get());
                //请求成功
                if (response.getCode() == 1) {
                    //将实体赋给全局变量，双向绑定动态添加Item
                    for (DemoEntity.ItemsEntity entity : response.getResult().getItems()) {
                        observableList.add(new TestListItemViewModel(context, entity));
                    }
                } else {
                    //code错误时也可以定义Observable回调到View层去处理
                    ToastUtils.showShort("数据错误");
                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });

    }
}
