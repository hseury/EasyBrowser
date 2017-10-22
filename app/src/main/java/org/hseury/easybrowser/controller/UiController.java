package org.hseury.easybrowser.controller;

import android.view.KeyEvent;
import android.widget.TextView;

/**
 * @description: Created by hseury on 10/19/17.
 */

public interface UiController {
	void openTabToHomePage(String url);

	void onPreClick();

	void onNextClick();

	void onStopOrReloadClick();

	void onUrlEditClick();

	boolean onUrlTextViewEditorAction(TextView v, int actionId, KeyEvent event);
}
