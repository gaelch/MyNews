package com.cheyrouse.gael.mynews.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchArticle implements Parcelable {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("copyright")
    @Expose
    private String copyright;
    @SerializedName("response")
    @Expose
    private Response response;

    protected SearchArticle(Parcel in) {
        status = in.readString();
        copyright = in.readString();
    }

    public static final Creator<SearchArticle> CREATOR = new Creator<SearchArticle>() {
        @Override
        public SearchArticle createFromParcel(Parcel in) {
            return new SearchArticle(in);
        }

        @Override
        public SearchArticle[] newArray(int size) {
            return new SearchArticle[size];
        }
    };

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(copyright);
    }
}
