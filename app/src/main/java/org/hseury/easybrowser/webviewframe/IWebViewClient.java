package org.hseury.easybrowser.webviewframe;

import android.graphics.Bitmap;
import com.tencent.smtt.sdk.WebView;

/**
 * Created by hseury on 9/6/17.
 */

public abstract class IWebViewClient {

  public void onPageFinished(IWebView view, String url) {
  }

  public void onPageStarted(IWebView view, String url, Bitmap favicon) {
  }

  public void onLoadResource(IWebView webView, String s) {
  }
}
