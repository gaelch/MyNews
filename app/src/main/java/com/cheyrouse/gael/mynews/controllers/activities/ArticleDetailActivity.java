package com.cheyrouse.gael.mynews.controllers.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.cheyrouse.gael.mynews.models.Result;
import com.cheyrouse.gael.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

public class ArticleDetailActivity extends AppCompatActivity  {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webview)
    WebView webView;


    private Disposable disposable;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ButterKnife.bind(this);

        String url = getIntent().getStringExtra(Result.TOPSTORIES_EXTRA);

        updateUI(url);

        ConfigureToolbar();

        onConfigureWebView();

        onPageFinished();

    }

    // --------------------
    // TOOLBAR
    // --------------------

    //Set the toolbar
    private void ConfigureToolbar(){
        // Set the Toolbar
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
    }

    //To configure WebView
    @SuppressLint("SetJavaScriptEnabled")
    private void onConfigureWebView() {
        //Configure the webview for the use of javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // Allows opening of windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //Allow storage DOM (Document Object Model)
        webSettings.setDomStorageEnabled(true);
    }

    // Function that allows the display of the page when everything is loaded
    public void onPageFinished() {
        webView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    // -------------------
    // UPDATE UI
    // -------------------

    //Show WebView
    protected void updateUI(String url){
        webView.loadUrl(url);
    }
}
