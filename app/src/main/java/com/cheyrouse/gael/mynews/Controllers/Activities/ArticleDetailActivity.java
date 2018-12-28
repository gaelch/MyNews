package com.cheyrouse.gael.mynews.Controllers.Activities;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.cheyrouse.gael.mynews.Models.Result;
import com.cheyrouse.gael.mynews.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;

public class ArticleDetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webview)
    WebView webView;


    private Disposable disposable;
    private String url;


    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        ButterKnife.bind(this);

        url = getIntent().getStringExtra(Result.TOPSTORIES_EXTRA);

        updateUI();

        ConfigureToolbar();

        onConfigureWebView();

        onPageFinished();

    }

    // --------------------
    // TOOLBAR
    // --------------------

    private void ConfigureToolbar(){
        // Set the Toolbar
        setSupportActionBar(toolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void onConfigureWebView() {
        // Configure la webview pour l'utilisation du javascript
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Permet l'ouverture des fenêtres
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        // Autorise le stockage DOM (Document Object Model)
        webSettings.setDomStorageEnabled(true);
    }


    // Fonction qui permet l'affichage de la page lorsque tout est chargé (événement onPageFinished)
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

    private void updateUI(){
        // Charge l'url
        webView.loadUrl(url);
    }

}
