package com.furkanzayimoglu.btchallange.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class UniModel implements Parcelable {
    private String name;
    private String type;
    private String shortDetail;
    private String url;
    private double lat;
    private double lng;
    private int id ;
    private String longDetail;
    private List<ImageDetail> imageDetails;
    public  List<UniDepartmentDetail> departmentDetailList;

    public UniModel() {
    }

    public UniModel(Parcel in) {
        name = in.readString();
        type = in.readString();
        shortDetail = in.readString();
        url = in.readString();
        lat = in.readDouble();
        lng = in.readDouble();
        id = in.readInt();
        longDetail = in.readString();
    }

    public static final Creator<UniModel> CREATOR = new Creator<UniModel>() {
        @Override
        public UniModel createFromParcel(Parcel in) {
            return new UniModel(in);
        }

        @Override
        public UniModel[] newArray(int size) {
            return new UniModel[size];
        }
    };

    public List<ImageDetail> getImageDetails() {
        return imageDetails;
    }

    public void setImageDetails(List<ImageDetail> imageDetails) {
        this.imageDetails = imageDetails;
    }

    public List<UniDepartmentDetail> getDepartmentDetailList() {
        return departmentDetailList;
    }

    public void setDepartmentDetailList(List<UniDepartmentDetail> departmentDetailList) {
        this.departmentDetailList = departmentDetailList;
    }

    public List<ImageDetail> getImageDetail() {
        return imageDetails;
    }

    public void setImageDetail(List<ImageDetail> imageDetail) {
        this.imageDetails = imageDetail;
    }

    public String getLongDetail() {
        return longDetail;
    }

    public void setLongDetail(String longDetail) {
        this.longDetail = longDetail;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShortDetail() {
        return shortDetail;
    }

    public void setShortDetail(String shortDetail) {
        this.shortDetail = shortDetail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(type);
        parcel.writeString(shortDetail);
        parcel.writeString(url);
        parcel.writeDouble(lat);
        parcel.writeDouble(lng);
        parcel.writeInt(id);
        parcel.writeString(longDetail);
    }
}
