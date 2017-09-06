package org.hseury.easybrowser.webviewframe.x5webview;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import org.hseury.easybrowser.webviewframe.IWebViewClient;

/**
 * Created by hseury on 9/6/17.
 */

public class X5WebViewClient extends WebViewClient {
  private IWebViewClient mClient;

  X5WebViewClient(@NonNull IWebViewClient webViewClient) {
    mClient = webViewClient;
  }

  @Override public void onPageFinished(WebView view, String url) {
    mClient.onPageFinished(new X5WebView(view), url);
  }

  @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
    mClient.onPageStarted(new X5WebView(view), url, favicon);
  }

  @Override public void onLoadResource(WebView view, String url) {
    mClient.onLoadResource(new X5WebView(view), url);
  }
}
