package org.hseury.easybrowser.tab;

import android.content.Context;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * maintain a webview
 * Created by hseury on 8/11/17.
 */

public class Tab {

  private WebView mWebView;

  private View mContainer;

  private Context mContext;

  private WebViewClient mWebViewClient = new WebViewClient(){

  };

  Tab(Context context){
    mContext = context;
  }

  public void setContainer(View container) {
    mContainer = container;
  }

  public void setWebView(WebView webView) {
    mWebView = webView;

    if (webView != null){

    }
  }
}
