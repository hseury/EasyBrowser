package org.hseury.easybrowser.webviewframe;

import android.content.Context;
import org.hseury.easybrowser.webviewframe.systemwebview.SystemWebView;
import org.hseury.easybrowser.webviewframe.x5webview.X5WebView;

/**
 * decide use which type WebView
 * Created by hseury on 9/3/17.
 */

public class WebViewFactory {
  private Context mContext;

  private enum WEBVIEW_TYPE{
    SYSTEM_WEBVIEW,X5_WEBVIEW
  }
  public static final WEBVIEW_TYPE DEFAULT_WEBVIEW_TYPE = WEBVIEW_TYPE.SYSTEM_WEBVIEW;

  public WebViewFactory(Context context) {
    mContext = context;
  }

  public IWebView createWebView() {
    IWebView webview;
    switch (DEFAULT_WEBVIEW_TYPE){
      case SYSTEM_WEBVIEW:
        webview = createSystemWebView();
        break;
      case X5_WEBVIEW:
        webview = createX5WebView();
        break;
      default:
        webview = createSystemWebView();
        break;

    }
    return webview;
  }

  private SystemWebView createSystemWebView() {
    android.webkit.WebView insideSystemWebView = new android.webkit.WebView(mContext);
    initSystemWebViewSettings(insideSystemWebView);
    return new SystemWebView(insideSystemWebView);
  }

  private X5WebView createX5WebView() {
    com.tencent.smtt.sdk.WebView insideX5WebView = new com.tencent.smtt.sdk.WebView(mContext);
    initX5WebViewSettings(insideX5WebView);
    return new X5WebView(insideX5WebView);
  }

  public void initSystemWebViewSettings(android.webkit.WebView webview) {
    if (webview != null) {
      android.webkit.WebSettings webviewSettings = webview.getSettings();
      webviewSettings.setSupportZoom(true);
      webviewSettings.setUseWideViewPort(true);
      webviewSettings.setDefaultTextEncodingName("GBK");
      webviewSettings.setLoadsImagesAutomatically(true);
      webviewSettings.setJavaScriptEnabled(true);
      webviewSettings.setRenderPriority(android.webkit.WebSettings.RenderPriority.HIGH);
      webviewSettings.setDomStorageEnabled(true);
      webviewSettings.setSupportMultipleWindows(false);
      webviewSettings.setLoadWithOverviewMode(true);
    }
  }

  public void initX5WebViewSettings(com.tencent.smtt.sdk.WebView webview) {
    if (webview != null) {
      com.tencent.smtt.sdk.WebSettings webviewSettings = webview.getSettings();
      webviewSettings.setSupportZoom(true);
      webviewSettings.setUseWideViewPort(true);
      webviewSettings.setDefaultTextEncodingName("GBK");
      webviewSettings.setLoadsImagesAutomatically(true);
      webviewSettings.setJavaScriptEnabled(true);
      webviewSettings.setRenderPriority(com.tencent.smtt.sdk.WebSettings.RenderPriority.HIGH);
      webviewSettings.setDomStorageEnabled(true);
      webviewSettings.setSupportMultipleWindows(false);
      webviewSettings.setLoadWithOverviewMode(true);
    }
  }
}
