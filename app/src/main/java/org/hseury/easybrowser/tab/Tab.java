package org.hseury.easybrowser.tab;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import org.hseury.easybrowser.controller.WebViewController;
import org.hseury.easybrowser.webviewframe.IWebChromeClient;
import org.hseury.easybrowser.webviewframe.IWebView;
import org.hseury.easybrowser.webviewframe.IWebViewClient;
import org.hseury.easybrowser.webviewframe.WebViewFactory;

/**
 * maintain a webview
 * Created by hseury on 8/11/17.
 */

public class Tab {

  private IWebView mWebView;

  private boolean mLoading;
  public static String currentUrl = "http://hseury.tk";

  private WebViewCallback mCallback;

  public void setCallback(WebViewCallback callback) {
    mCallback = callback;
  }

  private WebSettings mWebSettings;

  private Context mContext;

  private WebViewController mWebViewController;

  // Main WebView wrapper
  private ViewGroup mContainer;
  // The last reported progress of the current page
  private int mPageLoadProgress;

  private static final int INITIAL_PROGRESS = 5;

  public void setContainer(ViewGroup container) {
    mContainer = container;
  }

  public ViewGroup getContainer() {
    return mContainer;
  }

  public IWebView getWebView() {
    return mWebView;
  }

  private IWebViewClient mWebViewClient = new IWebViewClient() {

    @Override public void onPageFinished(IWebView view, String url) {
      super.onPageFinished(view, url);
      mLoading = false;
      if (mCallback !=null){
        mCallback.onPageFinished(view,url);
      }

      mWebViewController.onPageFinished(Tab.this);
    }

    @Override public void onPageStarted(IWebView view, String url, Bitmap favicon) {
      super.onPageStarted(view, url, favicon);
      mLoading = true;
      mPageLoadProgress = INITIAL_PROGRESS;
      if (mCallback != null) {
        mCallback.onPageStarted(view, url, favicon);
      }

      mWebViewController.onPageStarted(Tab.this, view, favicon);

    }

    @Override public void onLoadResource(IWebView webView, String s) {
      super.onLoadResource(webView, s);
      if (mCallback!=null){
        mCallback.onLoadResource(webView,s);
      }
    }

    @Override public void doUpdateVisitedHistory(IWebView webView, String url, boolean isReload) {
      super.doUpdateVisitedHistory(webView, url, isReload);
      mWebViewController.doUpdateVisitedHistory(Tab.this, isReload);
    }
  };

  private IWebChromeClient mWebChromeClient = new IWebChromeClient() {
    @Override
    public void onProgressChanged(IWebView view, int newProgress) {
      super.onProgressChanged(view, newProgress);
      if (mCallback != null){
        mCallback.onProgressChanged(view,newProgress);
      }

      mPageLoadProgress = newProgress;
      mWebViewController.onProgressChanged(Tab.this,newProgress);
    }

    @Override
    public void onReceivedTitle(IWebView view, String title) {
      super.onReceivedTitle(view, title);
      mWebViewController.onReceivedTitle(Tab.this,title);
    }
  };

  public Tab(Context context, WebViewController controller) {
    mContext = context;

    mWebViewController = controller;
    setWebView(new WebViewFactory(context).createWebView());
  }

  public void setWebView(IWebView webView) {
    mWebView = webView;

    mWebViewController.onSetWebView(this, webView);

    if (mWebViewClient != null) {
      mWebView.setWebViewClient(mWebViewClient);
    }

    if (mWebChromeClient != null) {
      mWebView.setWebChromeClient(mWebChromeClient);
    }
  }

  public boolean canGoBack() {
    return mWebView != null && mWebView.canGoBack();
  }

  public boolean canGoForward() {
    return mWebView != null && mWebView.canGoForward();
  }

  public void goBack() {
    if (mWebView != null) {
      mWebView.goBack();
    }
  }

  public void goForward() {
    if (mWebView != null) {
      mWebView.goForward();
    }
  }

  public void loadUrl(String url) {
    if (mWebView != null) {
      mWebView.loadUrl(url);
      mPageLoadProgress = INITIAL_PROGRESS;
    }
  }

  public void requestFocus() {
    if (mWebView != null) {
      mWebView.requestFocus();
    }
  }

  public String getUrl() {
    return mWebView != null ? mWebView.getUrl() : "";
  }

  public void stopLoading() {
    if (mWebView != null) {
      mWebView.stopLoading();
    }
  }

  public void reload() {
    if (mWebView != null) {
      mWebView.reload();
    }
  }

  public boolean isLoading() {
    return mLoading;
  }

  public void saveState(Bundle state){
    if (mWebView != null)
      mWebView.saveState(state);
  }

  public void restoreState(Bundle state){
    if (mWebView != null)
      mWebView.restoreState(state);
  }

  public void onDestroy(){
    if (mWebView != null){
      mWebView.destroy();
    }
  }

  public int getProgress() {
    return mPageLoadProgress;
  }

  public interface WebViewCallback {
    void onPageStarted(IWebView view, String url, Bitmap favicon);

    void onPageFinished(IWebView view, String url);

    void onProgressChanged(IWebView view, int newProgress);

    void onLoadResource(IWebView view, String s);
  }

}
