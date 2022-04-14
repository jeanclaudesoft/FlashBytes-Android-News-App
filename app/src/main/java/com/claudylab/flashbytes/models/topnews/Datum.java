package com.claudylab.flashbytes.models.topnews;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Datum implements Parcelable {

    @SerializedName("uuid")
    @Expose
    private String uuid;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("keywords")
    @Expose
    private String keywords;
    @SerializedName("snippet")
    @Expose
    private String snippet;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("published_at")
    @Expose
    private String publishedAt;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("categories")
    @Expose
    private List<String> categories ;
    @SerializedName("relevance_score")
    @Expose
    private Object relevanceScore;
    @SerializedName("locale")
    @Expose
    private String locale;

    public String getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getKeywords() {
        return keywords;
    }

    public String getSnippet() {
        return snippet;
    }

    public String getUrl() {
        return url;
    }

    public String getImageUrl() {
       if (imageUrl != null)
           return  imageUrl;
       else return "";
    }

    public String getLanguage() {
        return language;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getSource() {
        return source;
    }

    public List<String> getCategories() {
        return categories;
    }

    public Object getRelevanceScore() {
        return relevanceScore;
    }

    public String getLocale() {
        return locale;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.uuid);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.keywords);
        dest.writeString(this.snippet);
        dest.writeString(this.url);
        dest.writeString(this.imageUrl);
        dest.writeString(this.language);
        dest.writeString(this.publishedAt);
        dest.writeString(this.source);
        dest.writeStringList(this.categories);
        dest.writeString(this.locale);
    }

    public void readFromParcel(Parcel source) {
        this.uuid = source.readString();
        this.title = source.readString();
        this.description = source.readString();
        this.keywords = source.readString();
        this.snippet = source.readString();
        this.url = source.readString();
        this.imageUrl = source.readString();
        this.language = source.readString();
        this.publishedAt = source.readString();
        this.source = source.readString();
        this.categories = source.createStringArrayList();
        this.locale = source.readString();
    }

    public Datum() {
    }

    protected Datum(Parcel in) {
        this.uuid = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.keywords = in.readString();
        this.snippet = in.readString();
        this.url = in.readString();
        this.imageUrl = in.readString();
        this.language = in.readString();
        this.publishedAt = in.readString();
        this.source = in.readString();
        this.categories = in.createStringArrayList();
        this.locale = in.readString();
    }

    public static final Parcelable.Creator<Datum> CREATOR = new Parcelable.Creator<Datum>() {
        @Override
        public Datum createFromParcel(Parcel source) {
            return new Datum(source);
        }

        @Override
        public Datum[] newArray(int size) {
            return new Datum[size];
        }
    };
}
