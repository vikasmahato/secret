package com.vikas.secret.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class HomeItem implements Parcelable {
    private String name;
    private String detail;
    private int imageUrl;

    public HomeItem(String name, String detail, int imageUrl) {
        this.name = name;
        this.detail = detail;
        this.imageUrl = imageUrl;
    }

    protected HomeItem(Parcel in) {
        name = in.readString();
        detail = in.readString();
        imageUrl = in.readInt();
    }

    public static final Creator<HomeItem> CREATOR = new Creator<HomeItem>() {
        @Override
        public HomeItem createFromParcel(Parcel in) {
            return new HomeItem(in);
        }

        @Override
        public HomeItem[] newArray(int size) {
            return new HomeItem[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(int imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(detail);
        dest.writeInt(imageUrl);
    }
}
