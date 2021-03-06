package lib.kalu.webviewplus.core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.Map;

import lib.kalu.webviewplus.R;
import lib.kalu.webviewplus.client.WebChromeClientPlus;
import lib.kalu.webviewplus.client.WebViewClientPlus;
import lib.kalu.webviewplus.impl.WebViewImpl;
import lib.kalu.webviewplus.util.FileUtil;
import lib.kalu.webviewplus.util.JavascriptUtil;
import lib.kalu.webviewplus.util.LogUtil;

/**
 * description:
 * created by kalu on 2021-04-20
 */
public class WebViewCore extends WebView implements WebViewImpl, Handler.Callback {

    public WebViewCore(Context context) {
        super(context);

        initConfig(this);
        initBackground(this);

        initWebViewClient(this);
        initWebChromeClient(this);
    }

    public WebViewCore(Context context, AttributeSet attrs) {
        super(context, attrs);

        initConfig(this);
        initBackground(this);

        initWebViewClient(this);
        initWebChromeClient(this);
    }

    public WebViewCore(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initConfig(this);
        initBackground(this);

        initWebViewClient(this);
        initWebChromeClient(this);
    }

    public WebViewCore(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        initConfig(this);
        initBackground(this);

        initWebViewClient(this);
        initWebChromeClient(this);
    }

    public WebViewCore(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);

        initConfig(this);
        initBackground(this);

        initWebViewClient(this);
        initWebChromeClient(this);
    }

    /**********/

    private final Handler mHandler = new Handler(WebViewCore.this);

    @Override
    public boolean handleMessage(@NonNull Message msg) {

        LogUtil.log("WebViewCore", "reload => run");

        if (msg.what != 1001 || msg.arg1 != 2002 || msg.arg2 != 3003) {
            LogUtil.log("WebViewCore", "reload => false");
            return false;
        }

        Object tag = getTag(R.id.id_webplus_assets_url_reload);
        if (null == tag) {
            LogUtil.log("WebViewCore", "reload => false");
            return false;
        }

        String url = tag.toString();
        if (!url.startsWith("http")) {
            LogUtil.log("WebViewCore", "reload => false");
            return false;
        }

        loadUrl(url);
        // setTag(R.id.id_webviewplus_targeturl, null);
        LogUtil.log("WebViewCore", "reload => succ");

        return false;
    }

    @JavascriptInterface
    @Override
    public void reload() {
        LogUtil.log("WebViewCore", "reload => start");
        Message message = Message.obtain(mHandler, 1001, 2002, 3003, null);
        message.sendToTarget();
    }

    @Override
    public boolean canGoForward() {
        return super.canGoForward();
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseTimers();
    }

    @Override
    public void onResume() {
        super.onResume();
        resumeTimers();
    }

    @Override
    public void loadUrl(String url) {
        if (null == url || url.length() == 0)
            return;
        super.loadUrl(url);
    }

    @Override
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        if (null == url || url.length() == 0)
            return;
        super.loadUrl(url, additionalHttpHeaders);
    }

    @Override
    public void loadData(String data, @Nullable String mimeType, @Nullable String encoding) {
        if (null == data || data.length() == 0)
            return;
        super.loadData(data, mimeType, encoding);
    }

    @Override
    public void loadDataWithBaseURL(@Nullable String baseUrl, String data, @Nullable String mimeType, @Nullable String encoding, @Nullable String historyUrl) {
        if (null == data || data.length() == 0)
            return;
        super.loadDataWithBaseURL(baseUrl, data, mimeType, encoding, historyUrl);
    }

    /**********/

    @SuppressLint("JavascriptInterface")
    @Override
    public void initConfig(@NonNull WebView webView) {

        if (null != webView) {

            webView.setLongClickable(false);
            webView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    return true;
                }
            });

            webView.removeJavascriptInterface(initJavascriptInterface());
            webView.addJavascriptInterface(WebViewCore.this, initJavascriptInterface());

            // fix h5??????????????????????????????
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);

            // ?????????????????????????????????
            webView.setHorizontalScrollBarEnabled(false);
            // ??????????????????????????????????????????
            webView.setVerticalScrollbarOverlay(false);
            // ????????????????????????
            webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

            WebSettings settings = webView.getSettings();
            if (null == settings)
                return;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
            } else {
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
            }
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);

            // ??????????????????????????????
            settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
            // ?????????H5?????????????????????
            settings.setGeolocationEnabled(false);

            // ??????????????????
            settings.setSavePassword(false);
            // ????????????????????????
            settings.setSaveFormData(false);
            // ?????? DOM storage API ??????
            settings.setDomStorageEnabled(true);
            // ?????? database storage API ??????
            settings.setDatabaseEnabled(false);
            // ?????? Application Caches ??????
            settings.setAppCacheEnabled(false);
            // ??????webview?????????
            // String path = getContext().getFilesDir().getAbsolutePath() + "/caweb"; // ?????????
            // settings.setAppCachePath(path); // ?????????
            settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
            // ????????????????????????
            settings.setAllowFileAccess(true);
            // ??????????????????
            settings.setDefaultTextEncodingName("utf-8");
            // ????????????
            settings.setSupportZoom(false);
            // ??????WebView???????????????????????????????????????????????????????????????????????????true?????????????????????
            settings.setDisplayZoomControls(false);
            settings.setAllowContentAccess(true);
            settings.setAllowFileAccess(true);
            settings.setAllowFileAccessFromFileURLs(true);
            settings.setAllowUniversalAccessFromFileURLs(true);
            // ??????WebView??????????????????????????????????????????????????????????????????????????????????????????false??????????????????????????????
            settings.setBuiltInZoomControls(false);
            // ??????WebView??????????????????????????????????????????????????????????????????????????????????????????false??????????????????????????????
            settings.setAllowContentAccess(false);
            // ??????WebView????????????????????????????????????, ????????????????????????
            settings.setLoadWithOverviewMode(true);
            // ??????WebView????????????????????????????????????, ??????100, ?????????
            settings.setTextZoom(100);
            // ????????????????????????webview?????????
            settings.setUseWideViewPort(true);
            // ??????WebView????????????????????????????????????????????????true?????????????????????
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                settings.setMediaPlaybackRequiresUserGesture(false);
            }
            // ??????Javascript
            settings.setJavaScriptEnabled(true);
            //??????webview????????????
            settings.setPluginState(WebSettings.PluginState.ON);
            settings.setSupportMultipleWindows(true);// ??????
            // ????????????JS???????????????
            settings.setJavaScriptCanOpenWindowsAutomatically(true);
            // ??????WebView?????????http???https????????????????????????????????????????????????false
            settings.setBlockNetworkImage(false);
            // ?????????????????????, ??????????????????????????????????????????
            settings.setLoadsImagesAutomatically(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT);
            // ??????http ??? https ?????????????????????
            // settings.setMixedContentMode(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE : WebSettings.LOAD_DEFAULT);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                settings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
            }
        }
    }

    @Override
    public void initBackground(@NonNull WebView webView) {

        if (null != webView) {
            setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
            setBackgroundResource(android.R.color.transparent);
        }
    }

    @Override
    public void initWebViewClient(@NonNull WebView webView) {

        if (null != webView) {

            webView.setWebViewClient(new WebViewClientPlus() {

            });
        }
    }

    @Override
    public void initWebChromeClient(@NonNull WebView webView) {

        if (null != webView) {

            webView.setWebChromeClient(new WebChromeClientPlus() {

            });
        }
    }

    @Override
    public void onPageStarted(@NonNull WebView view, @NonNull String url) {
        LogUtil.log("WebViewCore", "onPageStarted => url = " + url);
    }

    @Override
    public void onPageFinished(@NonNull WebView view, @NonNull String url) {
        LogUtil.log("WebViewCore", "onPageFinished => url = " + url);
    }

    @Override
    public boolean onJsTimeout() {
        LogUtil.log("WebViewCore", "onJsTimeout =>");
        return true;
    }

    @Override
    public boolean onJsBeforeUnload(@NonNull WebView view, @NonNull String url, @NonNull String message, @NonNull JsResult result) {
        LogUtil.log("WebViewCore", "onJsBeforeUnload => url = " + url);
        return true;
    }

    @Override
    public boolean onJsAlert(@NonNull WebView view, @NonNull String url, @NonNull String message, @NonNull JsResult result) {
        LogUtil.log("WebViewCore", "onJsAlert => url = " + url);
        return true;
    }

    @Override
    public boolean onJsConfirm(@NonNull WebView view, @NonNull String url, @NonNull String message, @NonNull JsResult result) {
        LogUtil.log("WebViewCore", "onJsConfirm => url = " + url);
        return true;
    }

    @Override
    public boolean onJsPrompt(@NonNull WebView view, @NonNull String url, @NonNull String message, @NonNull String defaultValue, @NonNull JsPromptResult result) {
        LogUtil.log("WebViewCore", "onJsPrompt => url = " + url);
        return true;
    }

    @Override
    public void onBackPressed(@NonNull Activity activity, boolean tryAgin) {

        if (null == activity)
            return;

        // ??????initAssetDefaultInitResourceName
        boolean canGoBack = canGoBack();
        String initUrl = null;
        if (null != getTag(R.id.id_webplus_assets_url_init)) {
            initUrl = getTag(R.id.id_webplus_assets_url_init).toString();
        }
        String targetUrl = getUrl();
        String resourceName = initAssetDefaultInitResourceName();
        LogUtil.log("WebViewCore", "onBackPressed => tryAgin = " + tryAgin + ", canGoBack = " + canGoBack + ", targetUrl = " + targetUrl + ", initUrl = " + initUrl + ", resourceName = " + resourceName);

        if (tryAgin && null != initUrl && initUrl.length() > 0 && initUrl.startsWith("file:///android_asset/") && initUrl.endsWith(resourceName)) {
            activity.finish();
        }
        // ???????????????
        else if (canGoBack()) {
            goBack();
            onBackPressed(activity, true);
        }
        // ??????
        else if (!tryAgin) {
            activity.finish();
        }
    }

    @Override
    public void loadJavascriptAssets(@NonNull String fliename) {
        Context context = getContext().getApplicationContext();
        String jstring = FileUtil.readAssets(context, fliename);
        loadJavascriptString(jstring);
    }

    @Override
    public void loadJavascriptString(@NonNull String jstring) {
        String javascript = JavascriptUtil.encode(jstring);
        loadDataWithBaseURL(null, javascript, "application/javascript", "utf-8", null);
        // evaluateJavascript(javascript, callbacck);
    }

    @Override
    public void onProgressChanged(@NonNull WebView view, @NonNull String targetUrl, int newProgress) {
    }
}
