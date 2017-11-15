package org.hseury.easybrowser.controller;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * @description:
 * views control model
 * Created by hseury on 10/19/17.
 */

public interface UiController {
	void openTabToHomePage(String url);

	void onPreClick();

	void onNextClick();

	void onStopOrReloadClick();

	void onUrlBarHighted();

	boolean onUrlTextViewEditorAction(TextView v, int actionId, KeyEvent event);

	void stopLoading();

	Activity getActivity();

	boolean onOptionsItemSelected(MenuItem item);


}
