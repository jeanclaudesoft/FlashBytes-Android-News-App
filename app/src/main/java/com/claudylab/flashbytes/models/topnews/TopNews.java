package com.claudylab.flashbytes.models.topnews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TopNews {

    @SerializedName("meta")
    @Expose
    private Meta meta;
    @SerializedName("data")
    @Expose
    private List<Datum> data;

    public Meta getMeta() {
        return meta;
    }

    public List<Datum> getData() {
        return data;
    }
}