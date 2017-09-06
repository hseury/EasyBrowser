package org.hseury.easybrowser.webviewframe.x5webview;

import android.support.annotation.NonNull;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import org.hseury.easybrowser.webviewframe.IWebChromeClient;

/**
 * Created by hseury on 9/6/17.
 */

public class X5WebChromeClient extends WebChromeClient {
  private IWebChromeClient mClient;

  X5WebChromeClient(@NonNull IWebChromeClient webChromeClient){
    mClient = webChromeClient;
  }

  @Override public void onProgressChanged(WebView view, int newProgress) {
    mClient.onProgressChanged(new X5WebView(view),newProgress);
  }

  @Override public void onReceivedTitle(WebView view, String title) {
    mClient.onReceivedTitle(new X5WebView(view),title);
  }
}
