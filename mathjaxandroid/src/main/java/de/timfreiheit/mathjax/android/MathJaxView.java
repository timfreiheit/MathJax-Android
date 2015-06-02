package de.timfreiheit.mathjax.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Handler;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

/**
 * Containerview for an WebView which renders LaTex using MathJax
 * https://github.com/mathjax/MathJax
 * <p/>
 * Created by timfreiheit on 26.05.15.
 */
public class MathJaxView extends FrameLayout {

    private static final String HTML_LOCATION = "file:///android_asset/MathJaxAndroid/latex.html";

    private String laTex = null;
    private WebView mWebView;
    private Handler handler = new Handler();
    protected MathJaxJavaScriptBridge mBridge;

    /**
     * laTex can only be rendered when WebView is already loaded
     */
    private boolean webViewLoaded = false;

    public MathJaxView(Context context) {
        super(context);
        init(context, null);
    }

    public MathJaxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public MathJaxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void init(Context context, AttributeSet attrSet) {
        mWebView = new WebView(context);

        int gravity = Gravity.CENTER;
        boolean verticalScrollbarsEnabled = false;
        boolean horizontalScrollbarsEnabled = false;

        if (attrSet != null) {
            TypedArray attrs = context.obtainStyledAttributes(attrSet, R.styleable.MathJaxView);
            gravity = attrs.getInteger(R.styleable.MathJaxView_android_gravity, Gravity.CENTER);
            verticalScrollbarsEnabled = attrs.getBoolean(R.styleable.MathJaxView_verticalScrollbarsEnabled, false);
            horizontalScrollbarsEnabled = attrs.getBoolean(R.styleable.MathJaxView_horizontalScrollbarsEnabled, false);
            attrs.recycle();
        }

        addView(
                mWebView,
                new LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        gravity
                )
        );

        // callback when WebView is loading completed
        webViewLoaded = false;
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (webViewLoaded) {
                    // WebView was already finished
                    // do not load content again
                    return;
                }
                webViewLoaded = true;
                if (!TextUtils.isEmpty(laTex)) {
                    setLaTex(laTex);
                }
            }
        });

        mBridge = new MathJaxJavaScriptBridge(this);
        mWebView.addJavascriptInterface(mBridge, "Bridge");

        // be careful, we do not need internet access
        mWebView.getSettings().setBlockNetworkLoads(true);

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.loadUrl(HTML_LOCATION);
        mWebView.setVerticalScrollBarEnabled(verticalScrollbarsEnabled);
        mWebView.setHorizontalScrollBarEnabled(horizontalScrollbarsEnabled);
        mWebView.setBackgroundColor(0);
    }

    /**
     * called when webView is ready with rendering LaTex
     */
    protected void laTexRendered() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mWebView.setVisibility(View.VISIBLE);
            }
        },100);
    }

    /**
     * change the displayed LaTex
     *
     * @param laTex laTex-String
     */
    public void setLaTex(String laTex) {
        this.laTex = laTex;

        //wait for WebView to finish loading
        if (!webViewLoaded) {
            return;
        }

        String laTexString;
        if (laTex != null) {
            laTexString = doubleEscapeTeX(laTex);
        } else {
            laTexString = "";
        }

        String text = "\\\\["
                + laTexString
                + "\\\\]";

        mWebView.setVisibility(View.INVISIBLE);
        String javascriptCommand = "javascript:changeLatexText(\"" + text + "\")";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.evaluateJavascript(javascriptCommand, null);
        } else {
            mWebView.loadUrl(javascriptCommand);
        }

    }

    /**
     * @return the current laTex-String
     * null if not set
     */
    public String getLaTex() {
        return laTex;
    }

    private String doubleEscapeTeX(String s) {
        String t = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '\'') t += '\\';
            if (s.charAt(i) != '\n') t += s.charAt(i);
            if (s.charAt(i) == '\\') t += "\\";
        }
        return t;
    }
}
