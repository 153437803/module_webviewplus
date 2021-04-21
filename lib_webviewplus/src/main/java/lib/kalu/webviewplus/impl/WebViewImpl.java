package lib.kalu.webviewplus.impl;

import android.content.Context;
import android.webkit.WebView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

/**
 * description:
 * created by kalu on 2021-04-20
 */
public interface WebViewImpl {

    default String initJavascriptInterface() {
        return "android";
    }

    /*****/

    void initConfig(@NonNull WebView webView);

    void initBackground(@NonNull WebView webView);

    void initWebViewClient(@NonNull WebView webView);

    void initWebChromeClient(@NonNull WebView webView);

    /*****/

    void loadJavascriptAssets(@NonNull Context context, @NonNull String fliename);

    void loadJavascript(@NonNull String js);

    void onProgressChanged(@NonNull WebView view, @NonNull String targetUrl, @IntRange(from = 0, to = 100) int newProgress);
}
