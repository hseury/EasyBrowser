package org.hseury.easybrowser.tab;

import android.content.Context;
import com.tencent.smtt.sdk.WebView;

/**
 * decide use which type WebView
 * Created by hseury on 9/3/17.
 */

public class WebViewFactory {
  private Context mContext;

  WebViewFactory(Context context) {
    mContext = context;
  }

  public WebView createWebView() {
    return new CustomWebview(mContext);
  }
}
