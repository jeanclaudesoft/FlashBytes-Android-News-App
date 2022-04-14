package com.claudylab.flashbytes.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.ImageView;

import com.claudylab.flashbytes.R;

public class NativeLinkActivity extends AppCompatActivity {

    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_link);

        webView = findViewById(R.id.webDescription);
        String link = getIntent().getStringExtra("url");
        webView.loadUrl(link);

        ImageView back = findViewById(R.id.back);
        back.setOnClickListener(view -> {
            onBackPressed();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}