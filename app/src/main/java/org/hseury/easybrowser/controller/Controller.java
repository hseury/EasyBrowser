package org.hseury.easybrowser.controller;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.hseury.easybrowser.R;
import org.hseury.easybrowser.tab.Tab;
import org.hseury.easybrowser.webviewframe.IWebView;

import static org.hseury.easybrowser.utils.UrlUtil.sanitizeUrl;

/**
 * @description: Created by hseury on 10/19/17.
 */

public class Controller implements UiController, ActivityController,WebViewController {
	private Activity mActivity;
	private Ui mUi;
	private Tab mTab;

	public Controller(Activity browserActivity){
		mActivity = browserActivity;
	}

	public void setUi(Ui ui){
		mUi = ui;
	}

	@Override public void start(String url) {
		openTabToHomePage(url);
	}

	@Override public void openTabToHomePage(String url) {
		openTab(url);
	}

	private void openTab(String url){
		mTab = new Tab(mActivity,this);
		mUi.addTab(mTab);
		mTab.loadUrl(url);
	}

	@Override public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (mTab.canGoBack()) {
				mTab.goBack();
				return true;
			} else {
				//TODO: kill all activities
				return false;
			}
		}
		return false;
	}

	@Override public void onDestroy() {
		if (mTab != null) {
			mTab.onDestroy();
		}
	}

	@Override public void onBackPressed() {
		if (mTab.canGoBack()) {
			mTab.goBack();
		}
	}

	@Override public void onSaveState(Bundle outState) {
		mTab.saveState(outState);
	}

	@Override public void onRestoreInstanceState(Bundle savedInstanceState) {
		mTab.restoreState(savedInstanceState);
	}

	@Override public void onNextClick() {
		if (mTab.canGoForward()) {
			mTab.goForward();
		}
	}

	@Override public void onPreClick() {
		if (mTab.canGoBack()) {
			mTab.goBack();
		}
	}

	@Override public void onUrlEditClick() {

	}

	@Override public void onStopOrReloadClick() {
		if (mTab.isLoading()) {
			mTab.stopLoading();
		} else {
			mTab.reload();
		}
		updateStopOrRefresh();
	}

	private void updateStopOrRefresh() {
		mUi.updateStopOrRefresh(mTab.isLoading()?R.mipmap.stop:R.mipmap.refresh);
	}

	@Override public boolean onUrlTextViewEditorAction(TextView v, int actionId, KeyEvent event) {
		if ((actionId != EditorInfo.IME_ACTION_GO) && (event == null
				|| event.getKeyCode() != KeyEvent.KEYCODE_ENTER
				|| event.getAction() != KeyEvent.ACTION_DOWN)) {
			return false;
		}

		mTab.loadUrl(sanitizeUrl(mUi.getUrl()));
		mTab.requestFocus();
		mUi.clearUrlTextFocus();
		return true;
	}

	@Override public void onPageFinished(Tab tab) {
		enableUIControl();
		hideStopButton();
	}

	private void hideStopButton(){
		mUi.hideStopButton();
	}

	@Override public void onPageStarted(Tab tab, IWebView view, Bitmap favicon) {
		updateStopOrRefresh();
		mUi.setTitle(view.getUrl());
	}

	@Override public void onProgressChanged(Tab tab,int newProgress) {
		if (newProgress > 80) {
			mUi.setProgress(0);
		} else {
			mUi.setProgress(newProgress);
		}
	}

	@Override public void onReceivedTitle(Tab tab, String title) {
		mUi.setTitle(title);
	}

	public void enableUIControl() {
		mUi.enableUIControl(mTab.canGoBack(),mTab.canGoForward());
	}

	@Override public void onSetWebView(Tab tab, IWebView webView) {
		mUi.onSetWebView(tab,webView);
	}

	@Override public void stopLoading() {
		//mLoadStopped = true;
		//Tab tab = mTabControl.getCurrentTab();
		//WebView w = getCurrentTopWebView();
		//if (w != null) {
		//	w.stopLoading();
		//	mUi.onPageStopped(tab);
		//}
		mTab.stopLoading();
		mUi.onPageStopped(mTab);
	}
}

