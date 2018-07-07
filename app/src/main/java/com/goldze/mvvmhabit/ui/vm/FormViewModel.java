package com.goldze.mvvmhabit.ui.vm;

import android.app.DatePickerDialog;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;

import com.goldze.mvvmhabit.BR;
import com.goldze.mvvmhabit.R;
import com.goldze.mvvmhabit.entity.FormEntity;
import com.goldze.mvvmhabit.entity.SpinnerItemData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import me.goldze.mvvmhabit.base.BaseViewModel;
import me.goldze.mvvmhabit.base.ServerUrl;
import me.goldze.mvvmhabit.binding.command.BindingAction;
import me.goldze.mvvmhabit.binding.command.BindingCommand;
import me.goldze.mvvmhabit.binding.command.BindingConsumer;
import me.goldze.mvvmhabit.binding.viewadapter.spinner.IKeyAndValue;
import me.goldze.mvvmhabit.utils.MaterialDialogUtils;
import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;

/**
 * Created by goldze on 2017/7/17.
 */

public class FormViewModel extends BaseViewModel {
    public FormEntity entity;
    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();
    public List<IKeyAndValue> sexItemDatas;

    public ObservableField<String> userUame = new ObservableField<>("");

    public final ItemBinding<ItemViewModel> singleItem = ItemBinding.of(BR.item, R.layout.item);
    public final ObservableList<ItemViewModel> items = new ObservableArrayList<>();
    public final BindingViewPagerAdapter.PageTitles<ItemViewModel> pageTitles = new BindingViewPagerAdapter.PageTitles<ItemViewModel>() {
        @Override
        public CharSequence getPageTitle(int position, ItemViewModel item) {
            return "Itemhaozi " + (item.getIndex() + 1);
        }
    };

    public ObservableList itemList = new ObservableArrayList();
    public class UIChangeObservable {
        //刷新界面的观察者
        public ObservableBoolean refreshUIObservable = new ObservableBoolean(false);
    }
    /**
     * Custom adapter that logs calls.
     */
    public FormViewModel(){
        super();// 关键是这句话
    }
    public FormViewModel(Fragment fragment, FormEntity entity) {
        super(fragment);
        for (int i = 0; i < 3; i++) {
            items.add(new ItemViewModel(i, true));
        }
        this.entity = entity;

        //sexItemDatas 一般可以从本地Sqlite数据库中取出数据字典对象集合，让该对象实现IKeyAndValue接口
        sexItemDatas = new ArrayList<>();
        sexItemDatas.add(new SpinnerItemData("男", "1"));
        sexItemDatas.add(new SpinnerItemData("女", "2"));
    }

    @Override
    public void onCreate() {
        //sexItemDatas 一般可以从本地Sqlite数据库中取出数据字典对象集合，让该对象实现IKeyAndValue接口
        sexItemDatas = new ArrayList<>();
        sexItemDatas.add(new SpinnerItemData("男", "1"));
        sexItemDatas.add(new SpinnerItemData("女", "2"));
    }

    //性别选择的监听
    public BindingCommand<IKeyAndValue> onSexSelectorCommand = new BindingCommand<>(new BindingConsumer<IKeyAndValue>() {
        @Override
        public void call(IKeyAndValue iKeyAndValue) {
            entity.setSex(iKeyAndValue.getValue());
        }
    });
    //生日选择的监听
    public BindingCommand onBirClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

            for (int i = 0; i < 3; i++) {
                items.add(new ItemViewModel((i+1)*4, true));
            }

            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    //设置数据到实体中
                    entity.getBirth().set(year + "年" + (month + 1) + "月" + dayOfMonth + "日");
                    //回调到Fragment中刷新界面
//                    uc.refreshUIObservable.set(!uc.refreshUIObservable.get());
                }
            }, year, month, day);
            datePickerDialog.setMessage("生日选择");
            datePickerDialog.show();
        }
    });
    //是否已婚Switch点状态改变回调
    public BindingCommand<Boolean> onMarryCheckedChangeCommand = new BindingCommand<>(new BindingConsumer<Boolean>() {
        @Override
        public void call(Boolean isChecked) {
            entity.setMarry(isChecked);
        }
    });
    //提交按钮点击事件
    public BindingCommand onCmtClickCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
//               entity.setName("1111");
            userUame.set("33333");
            String submitJson = new Gson().toJson(entity);
//            MaterialDialogUtils.showBasicDialog(context, "提交的json实体数据：\r\n" + submitJson).show();
        }
    });

    @Override
    public void onDestroy() {
        super.onDestroy();
        entity = null;
        uc = null;
        sexItemDatas.clear();
        sexItemDatas = null;
    }
}
