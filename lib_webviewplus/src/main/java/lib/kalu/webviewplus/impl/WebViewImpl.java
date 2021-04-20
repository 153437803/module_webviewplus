package lib.kalu.webviewplus.impl;

import android.webkit.WebView;

import androidx.annotation.NonNull;

/**
 * description:
 * created by kalu on 2021-04-20
 */
public interface WebViewImpl {

    void initConfig(@NonNull WebView webView);

    void initBackground(@NonNull WebView webView);

    void initWebViewClient(@NonNull WebView webView);

    void initWebChromeClient(@NonNull WebView webView);
}
