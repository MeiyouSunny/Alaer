package com.cyberalaer.hybrid.ui.webpage;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Build;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cyberalaer.hybrid.R;
import com.cyberalaer.hybrid.base.BaseTitleActivity;
import com.cyberalaer.hybrid.databinding.ActivityWebPageBinding;
import com.meiyou.mvp.MvpBinder;

import androidx.annotation.StringRes;

@MvpBinder()
public class WebPageActivity extends BaseTitleActivity<ActivityWebPageBinding> {

    WebView mWebView;

    public static void start(Context context, String url, @StringRes int title) {
        Intent intent = new Intent(context, WebPageActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    public static void start(Context context, String url, String titleText) {
        Intent intent = new Intent(context, WebPageActivity.class);
        intent.putExtra("titleText", titleText);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    public void onViewCreated() {
        super.onViewCreated();

        mWebView = bindRoot.webView;
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setAllowFileAccessFromFileURLs(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                handler.proceed();
            }
        });


        final int title = getIntent().getIntExtra("title", -1);
        if (title != -1) {
            setTitleText(title);
        } else {
            final String titleText = getIntent().getStringExtra("titleText");
            setTitleText(titleText);
        }
        final String url = getIntent().getStringExtra("url");
        mWebView.loadUrl(url);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_web_page;
    }

    @Override
    protected int titleResId() {
        return -1;
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
            return;
        }
        super.onBackPressed();
    }

}
