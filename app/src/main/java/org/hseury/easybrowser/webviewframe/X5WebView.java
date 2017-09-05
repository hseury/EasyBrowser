package org.hseury.easybrowser.webviewframe;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import com.tencent.smtt.sdk.WebView;

/**
 * x5 WebView wrapper
 * Created by hseury on 9/5/17.
 */

public class X5WebView implements IWebViewStub {
  private WebView mWebView;

  public X5WebView(WebView webView) {
    mWebView = webView;
  }

  @Override public View getView() {
    return mWebView;
  }

  public WebView getInsideWebView() {
    return mWebView;
  }

  @Override public boolean canGoBack() {
    return mWebView.canGoBack();
  }

  @Override public boolean canGoForward() {
    return mWebView.canGoForward();
  }

  @Override public void goBack() {
    mWebView.goBack();
  }

  @Override public void goForward() {
    mWebView.goForward();
  }

  @Override public void loadUrl(String url) {
    mWebView.loadUrl(url);
  }

  @Override public void requestFocus() {
    mWebView.requestFocus();
  }

  @Override public String getUrl() {
    return mWebView.getUrl();
  }

  @Override public void stopLoading() {
    mWebView.stopLoading();
  }

  @Override public void reload() {
    mWebView.reload();
  }

  @Override public void saveState(Bundle state) {
    mWebView.saveState(state);
  }

  @Override public void restoreState(Bundle state) {
    mWebView.restoreState(state);
  }

  @Override public void destroy() {
    mWebView.destroy();
  }

  @Override public void setLayoutParams(LinearLayout.LayoutParams layoutParams) {
    mWebView.setLayoutParams(layoutParams);
  }
}
