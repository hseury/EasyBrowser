package org.hseury.easybrowser.controller;

import android.graphics.Bitmap;
import android.webkit.WebView;
import org.hseury.easybrowser.tab.Tab;
import org.hseury.easybrowser.webviewframe.IWebView;

/**
 * @description: Created by hseury on 2017/10/23.
 */

public interface WebViewController {

	void onPageStarted(Tab tab, IWebView view, Bitmap favicon);

	void onPageFinished(Tab tab);

	void onProgressChanged(Tab tab,int progress);

	void onReceivedTitle(Tab tab, final String title);

	void onSetWebView(Tab tab, IWebView webView);

	void doUpdateVisitedHistory(Tab tab, boolean isReload);
}
