package org.hseury.easybrowser.tab;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import org.hseury.easybrowser.webviewframe.IWebViewStub;
import org.hseury.easybrowser.webviewframe.WebViewFactory;

/**
 * maintain a webview
 * Created by hseury on 8/11/17.
 */

public class Tab {

  private IWebViewStub mWebView;

  private boolean mLoading;
  public static String currentUrl = "http://hseury.tk";

  private WebViewCallback mCallback;

  public void setCallback(WebViewCallback callback) {
    mCallback = callback;
  }

  private WebSettings mWebSettings;

  /** The parent view of the ContentView and the InfoBarContainer. */
  private TabContentViewParent mContentViewParent;

  private Context mContext;

  public IWebViewStub getWebView() {
    return mWebView;
  }

  public TabContentViewParent getContentViewParent() {
    return mContentViewParent;
  }

  private WebViewClient mWebViewClient = new WebViewClient() {
    @Override public void onReceivedLoginRequest(WebView view, String realm, String account,
        String args) {
      super.onReceivedLoginRequest(view, realm, account, args);
    }

    @Override public void onPageFinished(WebView view, String url) {
      super.onPageFinished(view, url);
      mLoading = false;
      if (mCallback !=null){
        mCallback.onPageFinished(view,url);
      }
    }

    @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
      super.onPageStarted(view, url, favicon);
      mLoading = true;
      if (mCallback !=null){
        mCallback.onPageStarted(view,url,favicon);
      }
    }

    @Override public void onLoadResource(WebView webView, String s) {
      super.onLoadResource(webView, s);
      if (mCallback!=null){
        mCallback.onLoadResource(webView,s);
      }
    }
  };

  private WebChromeClient mWebChromeClient = new WebChromeClient() {
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
      super.onProgressChanged(view, newProgress);
      if (mCallback != null){
        mCallback.onProgressChanged(view,newProgress);
      }
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
      super.onReceivedTitle(view, title);
    }
  };

  public Tab(Context context) {
    mContext = context;
    setWebView(new WebViewFactory(context).createWebView());
  }

  public void setWebView(IWebViewStub webView) {
    mWebView = webView;

    if (webView != null) {
      if (mContentViewParent != null) {
        mContentViewParent.removeAllViews();
      }

      mWebView.setLayoutParams(new LinearLayout.LayoutParams(
          LinearLayout.LayoutParams.MATCH_PARENT,
          LinearLayout.LayoutParams.MATCH_PARENT,
          1f));


      mContentViewParent = new TabContentViewParent(mContext, this);
      setCallback(mContentViewParent);

      mContentViewParent.getContentContainer().addView(mWebView.getView(),
          new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
              FrameLayout.LayoutParams.MATCH_PARENT));

      //if (mWebViewClient != null) {
      //  mWebView.setWebViewClient(mWebViewClient);
      //}
      //
      //if (mWebChromeClient != null) {
      //  mWebView.setWebChromeClient(mWebChromeClient);
      //}
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

  public interface WebViewCallback {
    void onPageStarted(WebView view, String url, Bitmap favicon);

    void onPageFinished(WebView view, String url);

    void onProgressChanged(WebView view, int newProgress);

    void onLoadResource(WebView view, String s);
  }

}
