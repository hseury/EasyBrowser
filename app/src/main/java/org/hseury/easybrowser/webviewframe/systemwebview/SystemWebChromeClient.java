package org.hseury.easybrowser.webviewframe.systemwebview;

import android.support.annotation.NonNull;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import org.hseury.easybrowser.webviewframe.IWebChromeClient;

/**
 * Created by hseury on 9/6/17.
 */

public class SystemWebChromeClient extends WebChromeClient {
  private IWebChromeClient mClient;

  SystemWebChromeClient(@NonNull IWebChromeClient webChromeClient){
    mClient = webChromeClient;
  }

  @Override public void onProgressChanged(WebView view, int newProgress) {
    mClient.onProgressChanged(new SystemWebView(view),newProgress);
  }

  @Override public void onReceivedTitle(WebView view, String title) {
    mClient.onReceivedTitle(new SystemWebView(view),title);
  }
}
