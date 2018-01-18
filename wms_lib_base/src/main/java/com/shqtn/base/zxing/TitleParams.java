package com.shqtn.base.zxing;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017-7-6.
 */
public class TitleParams implements Parcelable {


    private String title;
    private int titleColor;
    private int backgroundColor;
    private int titleSize;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getTitleSize() {
        return titleSize;
    }

    public void setTitleSize(int titleSize) {
        this.titleSize = titleSize;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeInt(this.titleColor);
        dest.writeInt(this.backgroundColor);
        dest.writeInt(this.titleSize);
    }

    public TitleParams() {
    }

    protected TitleParams(Parcel in) {
        this.title = in.readString();
        this.titleColor = in.readInt();
        this.backgroundColor = in.readInt();
        this.titleSize = in.readInt();
    }

    public static final Creator<TitleParams> CREATOR = new Creator<TitleParams>() {
        @Override
        public TitleParams createFromParcel(Parcel source) {
            return new TitleParams(source);
        }

        @Override
        public TitleParams[] newArray(int size) {
            return new TitleParams[size];
        }
    };
}
