package org.hseury.easybrowser.controller;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.hseury.easybrowser.R;
import org.hseury.easybrowser.model.DataController;
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

	@Override public void onUrlBarHighted() {
		mUi.setTitle(mTab.getUrl());
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
		updateStopOrRefresh();
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
		mUi.onProgressChanged(tab);
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

	public Tab getTab() {
		return mTab;
	}

	@Override public Activity getActivity() {
		return mActivity;
	}

	@Override public boolean onOptionsItemSelected(MenuItem item) {
		if (null == mTab.getWebView()) {
			return false;
		}
		switch (item.getItemId()) {
			case R.id.stop_reload_menu_id:
				if (mTab.isLoading()) {
					stopLoading();
				} else {
					mTab.reload();
				}
				break;

			case R.id.back_menu_id:
				mTab.goBack();
				break;

			case R.id.forward_menu_id:
				mTab.goForward();
				break;

			default:
				return false;
		}
		return true;
	}

	@Override public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = mActivity.getMenuInflater();
		inflater.inflate(R.menu.browser, menu);
		return true;
	}

	@Override public boolean onContextItemSelected(MenuItem item) {
		return false;
	}

	@Override public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenu.ContextMenuInfo menuInfo) {

	}

	@Override public boolean onPrepareOptionsMenu(Menu menu) {
		menu.setGroupVisible(R.id.MAIN_MENU, true);
		menu.setGroupEnabled(R.id.MAIN_MENU, true);
		menu.setGroupEnabled(R.id.MAIN_SHORTCUT_MENU, true);
		updateMenuState(mTab, menu);
		return mUi.onPrepareOptionsMenu(menu);
	}

	public void updateMenuState(Tab tab, Menu menu) {
		boolean canGoBack = false;
		boolean canGoForward = false;
		boolean isHome = false;
		boolean isDesktopUa = false;
		boolean isLive = false;
		if (tab != null) {
			canGoBack = tab.canGoBack();
			canGoForward = tab.canGoForward();
			//isHome = mSettings.getHomePage().equals(tab.getUrl());
			//isDesktopUa = mSettings.hasDesktopUseragent(tab.getWebView());
			//isLive = !tab.isSnapshot();
		}
		final MenuItem back = menu.findItem(R.id.back_menu_id);
		back.setEnabled(canGoBack);

		final MenuItem home = menu.findItem(R.id.homepage_menu_id);
		home.setEnabled(!isHome);

		final MenuItem forward = menu.findItem(R.id.forward_menu_id);
		forward.setEnabled(canGoForward);

		final MenuItem source = menu.findItem(mTab.isLoading() ? R.id.stop_menu_id
				: R.id.reload_menu_id);
		final MenuItem dest = menu.findItem(R.id.stop_reload_menu_id);
		if (source != null && dest != null) {
			dest.setTitle(source.getTitle());
			dest.setIcon(source.getIcon());
		}
		menu.setGroupVisible(R.id.NAV_MENU, isLive);

		// decide whether to show the share link option
		PackageManager pm = mActivity.getPackageManager();
		Intent send = new Intent(Intent.ACTION_SEND);
		send.setType("text/plain");
		ResolveInfo ri = pm.resolveActivity(send,
				PackageManager.MATCH_DEFAULT_ONLY);
		menu.findItem(R.id.share_page_menu_id).setVisible(ri != null);

		//boolean isNavDump = mSettings.enableNavDump();
		//final MenuItem nav = menu.findItem(R.id.dump_nav_menu_id);
		//nav.setVisible(isNavDump);
		//nav.setEnabled(isNavDump);

		//boolean showDebugSettings = mSettings.isDebugEnabled();
		final MenuItem uaSwitcher = menu.findItem(R.id.ua_desktop_menu_id);
		uaSwitcher.setChecked(isDesktopUa);

		menu.setGroupVisible(R.id.LIVE_MENU, isLive);
		menu.setGroupVisible(R.id.SNAPSHOT_MENU, !isLive);
		menu.setGroupVisible(R.id.COMBO_MENU, false);

		mUi.updateMenuState(tab, menu);
	}

	/**************** url bar suggestion ********************/
	@Override public void doUpdateVisitedHistory(Tab tab, boolean isReload) {
		DataController.getInstance(mActivity).updateVisitedHistory(tab.getUrl());
	}
}

