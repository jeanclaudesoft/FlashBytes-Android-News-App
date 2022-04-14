package com.claudylab.flashbytes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.claudylab.flashbytes.R;
import com.claudylab.flashbytes.models.topnews.Datum;
import com.claudylab.flashbytes.utils.PostWebEngine;
import com.claudylab.flashbytes.utils.WebListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {


    RoundedImageView roundedImageView;
    TextView title, category, source;
    WebView webView;
    Button link;
    private PostWebEngine mPostWebEngine;
    RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        roundedImageView = findViewById(R.id.ivImage);
        title = findViewById(R.id.txtTitle);
        category = findViewById(R.id.txtCat);
        source = findViewById(R.id.txtSource);
        webView = findViewById(R.id.webDescription);
        relativeLayout = findViewById(R.id.lytCat);
        link = findViewById(R.id.url);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(view -> {
            onBackPressed();
        });

        mPostWebEngine = new PostWebEngine(webView, NewsDetailActivity.this);
        mPostWebEngine.initWebView();


        mPostWebEngine.initListeners(new WebListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onLoaded() {

            }

            @Override
            public void onProgress(int progress) {
            }

            @Override
            public void onNetworkError() {

            }

            @Override
            public void onPageTitle(String title) {
            }
        });

            Bundle data = getIntent().getExtras();
            Datum datum = data.getParcelable("data");

            Picasso.get().load(datum.getImageUrl()).into(roundedImageView);
            title.setText(datum.getTitle());
            if (datum.getCategories().size() != 0)
            category.setText(datum.getCategories().get(0));
            else category.setVisibility(View.GONE);
            source.setText("Source: " + datum.getSource());
            mPostWebEngine.loadHtml(datum.getDescription(), datum.getUrl());

            link.setOnClickListener(view -> {
                startActivity(new Intent(NewsDetailActivity.this,NativeLinkActivity.class)
                        .putExtra("url",datum.getUrl()));
            });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}