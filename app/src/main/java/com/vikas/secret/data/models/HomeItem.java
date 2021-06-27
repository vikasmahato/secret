package com.vikas.secret.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class HomeItem implements Parcelable {
    private String name;
    private int imageUrl;
    private int isOpen;
    private String videoId;

    public HomeItem(String name, String videoId, int imageUrl, boolean isOpen) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.isOpen = isOpen ? 1 : 0;
        this.videoId = videoId;
    }

    protected HomeItem(Parcel in) {
        name = in.readString();
        imageUrl = in.readInt();
        isOpen = in.readInt();
        videoId = in.readString();
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

    public boolean isOpen() {
        return isOpen == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(videoId);
        dest.writeInt(imageUrl);
        dest.writeInt(isOpen);
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }
}
