package com.pentagon.rcbot.api.reverse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReverseResult {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("info")
    @Expose
    private String info;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("title")
    @Expose
    private String title;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}