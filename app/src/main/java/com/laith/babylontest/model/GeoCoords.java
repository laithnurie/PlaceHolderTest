package com.laith.babylontest.model;

import android.os.Parcel;
import android.os.Parcelable;

public class GeoCoords implements Parcelable {
    private String lat;
    private String lng;

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.lat);
        dest.writeString(this.lng);
    }

    public GeoCoords() {
    }

    protected GeoCoords(Parcel in) {
        this.lat = in.readString();
        this.lng = in.readString();
    }

    public static final Parcelable.Creator<GeoCoords> CREATOR = new Parcelable.Creator<GeoCoords>() {
        @Override
        public GeoCoords createFromParcel(Parcel source) {
            return new GeoCoords(source);
        }

        @Override
        public GeoCoords[] newArray(int size) {
            return new GeoCoords[size];
        }
    };
}
