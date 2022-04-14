package com.claudylab.flashbytes.models.sources;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum {

    @SerializedName("source_id")
    @Expose
    private String sourceId;
    @SerializedName("domain")
    @Expose
    private String domain;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("locale")
    @Expose
    private Object locale;
    @SerializedName("categories")
    @Expose
    private List<String> categories ;

    public String getSourceId() {
        return sourceId;
    }

    public String getDomain() {
        return domain;
    }

    public String getLanguage() {
        return language;
    }

    public Object getLocale() {
        return locale;
    }

    public List<String> getCategories() {
        return categories;
    }
}
