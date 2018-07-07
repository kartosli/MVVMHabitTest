package com.goldze.mvvmhabit.entity;

import android.databinding.ObservableField;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import io.reactivex.internal.operators.observable.ObservableError;

/**
 * Created by goldze on 2017/7/17.
 */

public class FormEntity implements Parcelable {
    private String name ="哈哈";
    private String sex;
    private String Bir;
    private String hobby;
    private Boolean isMarry;
    private ObservableField<String> birth = new ObservableField<String>();
    private String photoUrl;
    public String getName() {
        return name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public ObservableField<String> getBirth() {
        return birth;
    }

    public void setBirth(ObservableField<String> birth) {
        this.birth = birth;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBir() {
        return Bir;
    }

    public void setBir(String bir) {
        Bir = bir;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public FormEntity() {
    }

    public Boolean getMarry() {
        return isMarry;
    }

    public void setMarry(Boolean marry) {
        isMarry = marry;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.sex);
        dest.writeString(this.Bir);
        dest.writeString(this.hobby);
        dest.writeValue(this.isMarry);
        dest.writeString(this.photoUrl);
    }

    protected FormEntity(Parcel in) {
        this.name = in.readString();
        this.sex = in.readString();
        this.Bir = in.readString();
        this.hobby = in.readString();
        this.isMarry = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.photoUrl = in.readString();
    }

    public static final Creator<FormEntity> CREATOR = new Creator<FormEntity>() {
        @Override
        public FormEntity createFromParcel(Parcel source) {
            return new FormEntity(source);
        }

        @Override
        public FormEntity[] newArray(int size) {
            return new FormEntity[size];
        }
    };
}
