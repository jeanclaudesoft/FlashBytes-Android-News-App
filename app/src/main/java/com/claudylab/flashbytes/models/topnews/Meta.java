package com.claudylab.flashbytes.models.topnews;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Meta {

    @SerializedName("found")
    @Expose
    private Integer found;
    @SerializedName("returned")
    @Expose
    private Integer returned;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("page")
    @Expose
    private Integer page;

    public Integer getFound() {
        return found;
    }

    public Integer getReturned() {
        return returned;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getPage() {
        return page;
    }
}
