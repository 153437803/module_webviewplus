package lib.kalu.webviewplus;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import lib.kalu.webviewplus.core.WebViewCore;

/**
 * description:
 * created by kalu on 2021-04-20
 */
@Keep
public final class WebViewPlus extends WebViewCore {

    public WebViewPlus(Context context) {
        super(context);
    }

    public WebViewPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WebViewPlus(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public WebViewPlus(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public WebViewPlus(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
    }

    @Override
    public void onPageFinished(@NonNull WebView view, @NonNull String url) {
        super.onPageFinished(view, url);

        if (null != onWebStatusChangeListener) {
            onWebStatusChangeListener.onPageFinished(view, url);
        }
    }

    @Override
    public void onPageStarted(@NonNull WebView view, @NonNull String url) {
        super.onPageStarted(view, url);

        if (null != onWebStatusChangeListener) {
            onWebStatusChangeListener.onPageStarted(view, url);
        }
    }

    @Override
    public void onProgressChanged(@NonNull WebView view, @NonNull String targetUrl, int newProgress) {
        super.onProgressChanged(view, targetUrl, newProgress);

        if (null != onWebStatusChangeListener) {
            onWebStatusChangeListener.onProgressChanged(view, targetUrl, newProgress);
        }
    }

    /****************/

    private OnWebStatusChangeListener onWebStatusChangeListener;

    public void setOnWebStatusChangeListener(@NonNull OnWebStatusChangeListener listener) {
        this.onWebStatusChangeListener = listener;
    }

    @Keep
    public interface OnWebStatusChangeListener {

        void onPageStarted(@NonNull WebView view, @NonNull String url);

        void onPageFinished(@NonNull WebView view, @NonNull String url);

        void onProgressChanged(@NonNull WebView view, @NonNull String targetUrl, int newProgress);
    }

    /****************/
}
