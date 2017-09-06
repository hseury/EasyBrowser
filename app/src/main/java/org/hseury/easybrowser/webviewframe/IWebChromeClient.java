package org.hseury.easybrowser.webviewframe;

import com.tencent.smtt.sdk.WebView;

/**
 * Created by hseury on 9/6/17.
 */

public abstract class IWebChromeClient {

  public void onProgressChanged(IWebView view, int newProgress) {
  }

  public void onReceivedTitle(IWebView view, String title) {
  }
}
