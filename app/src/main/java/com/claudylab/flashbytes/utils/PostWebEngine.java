package com.claudylab.flashbytes.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.fragment.app.Fragment;

public class PostWebEngine {

    private WebView webView;
    private Activity mActivity;
    private Context mContext;
    private Fragment mFragment;

    public static final int KEY_FILE_PICKER = 554;
    private static final String GOOGLE_DOCS_VIEWER = "https://docs.google.com/viewerng/viewer?url=";


    private WebListener mWebListener;
    private String mDownloadUrl;
    //private VideoViewer mVideoViewer;
    private WebChromeClient.CustomViewCallback mVideoViewCallback;

    public PostWebEngine(WebView webView, Activity activity) {
        this.webView = webView;
        this.mActivity = activity;
        this.mContext = mActivity.getApplicationContext();
      //  mVideoViewer = VideoViewer.getInstance();
    }

    public void initWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setAppCacheMaxSize(1024);
        webView.getSettings().setAppCachePath(mContext.getCacheDir().getAbsolutePath());
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDefaultTextEncodingName("utf-8");
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        if (!isNetworkAvailable(mContext)) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }


    }

    public void initListeners(final WebListener webListener) {

        this.mWebListener = webListener;

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                webListener.onProgress(newProgress);
            }


            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                webListener.onPageTitle(webView.getTitle());
            }


            @Override
            public void onHideCustomView() {
                super.onHideCustomView();
                //mVideoViewer.dismiss();
                mVideoViewCallback.onCustomViewHidden();
            }


        });

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String webUrl) {
                //loadPage(webUrl);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webUrl));
                mActivity.startActivity(intent);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                webListener.onStart();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                webListener.onLoaded();
            }

        });


    }

    public void loadPage(String webUrl) {
        if (isNetworkAvailable(mContext)) {

            if (webUrl.startsWith("tel:") || webUrl.startsWith("sms:") || webUrl.startsWith("smsto:")
                    || webUrl.startsWith("mms:") || webUrl.startsWith("mmsto:")
                    || webUrl.startsWith("mailto:") /*|| webUrl.contains("youtube.com")*/
                    || webUrl.contains("geo:")) {
                invokeNativeApp(webUrl);
            } else if (webUrl.contains("?target=blank")) {
                invokeNativeApp(webUrl.replace("?target=blank", ""));
            } else if (webUrl.endsWith(".doc") || webUrl.endsWith(".docx") || webUrl.endsWith(".xls")
                    || webUrl.endsWith(".xlsx") || webUrl.endsWith(".pptx") || webUrl.endsWith(".pdf")) {
                webView.loadUrl(GOOGLE_DOCS_VIEWER + webUrl);
                webView.getSettings().setBuiltInZoomControls(true);
            } else {
                webView.loadUrl(webUrl);
            }

        } else {
            mWebListener.onNetworkError();
        }
    }

    public void loadHtml(String htmlString,String url) {
        if (htmlString.startsWith("tel:") || htmlString.startsWith("sms:") || htmlString.startsWith("smsto:")
                || htmlString.startsWith("mms:") || htmlString.startsWith("mmsto:")
                || htmlString.startsWith("mailto:") /*|| htmlString.contains("youtube.com")*/
                || htmlString.contains("geo:")) {
            invokeNativeApp(htmlString);
        } else if (htmlString.contains("?target=blank")) {
            invokeNativeApp(htmlString.replace("?target=blank", ""));
        } else if (htmlString.endsWith(".doc") || htmlString.endsWith(".docx") || htmlString.endsWith(".xls")
                || htmlString.endsWith(".xlsx") || htmlString.endsWith(".pptx") || htmlString.endsWith(".pdf")) {
            webView.loadUrl(GOOGLE_DOCS_VIEWER + htmlString);
            webView.getSettings().setBuiltInZoomControls(true);
        } else {
            // load data in LTR mode
            webView.loadDataWithBaseURL(url, htmlString, "text/html; charset=utf-8", "UTF-8", null);

            // load data in RTL mode
            // webView.loadDataWithBaseURL(null, "<html dir=\"rtl\" lang=\"\"><body>" + htmlString + "</body></html>", "text/html; charset=utf-8", "UTF-8", null);
        }
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private void invokeNativeApp(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        mActivity.startActivity(intent);
    }

    public void invokeImagePickerActivity() {
    }




}

