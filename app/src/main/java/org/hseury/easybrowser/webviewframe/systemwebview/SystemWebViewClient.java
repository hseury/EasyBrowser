package org.hseury.easybrowser.webviewframe.systemwebview;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import org.hseury.easybrowser.webviewframe.IWebViewClient;

/**
 * Created by hseury on 9/6/17.
 */

public class SystemWebViewClient extends WebViewClient {
  private IWebViewClient mClient;

  SystemWebViewClient(@NonNull IWebViewClient webViewClient) {
    mClient = webViewClient;
  }

  @Override public void onPageFinished(WebView view, String url) {
    mClient.onPageFinished(new SystemWebView(view), url);
  }

  @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
    mClient.onPageStarted(new SystemWebView(view), url, favicon);
  }

  @Override public void onLoadResource(WebView view, String url) {
    mClient.onLoadResource(new SystemWebView(view), url);
  }

  @Override public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
    mClient.doUpdateVisitedHistory(new SystemWebView(view), url, isReload);
  }
}
