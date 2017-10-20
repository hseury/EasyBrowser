package org.hseury.easybrowser.controller;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import org.hseury.easybrowser.tab.Tab;

/**
 * @description: Created by hseury on 10/19/17.
 */

public class Controller implements UiController, ActivityController {
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
		mTab = new Tab(mActivity);
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
}

