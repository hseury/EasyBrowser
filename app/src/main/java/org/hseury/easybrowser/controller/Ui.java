package org.hseury.easybrowser.controller;

import org.hseury.easybrowser.tab.Tab;
import org.hseury.easybrowser.webviewframe.IWebView;

/**
 * @description: Created by hseury on 10/19/17.
 */

public abstract class Ui {
	public abstract void addTab(Tab tab);

	public abstract void updateStopOrRefresh(int resId);

	public abstract String getUrl();

	public abstract void clearUrlTextFocus();

	public abstract void setProgress(int progress);

	public abstract void setTitle(String title);

	public abstract void enableUIControl(boolean canGoBack, boolean canGoForward);

	public abstract void onSetWebView(Tab tab, IWebView webView);
}
