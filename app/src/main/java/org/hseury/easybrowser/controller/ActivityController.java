package org.hseury.easybrowser.controller;

import android.os.Bundle;
import android.view.KeyEvent;

/**
 * @description: Created by hseury on 10/20/17.
 */

public interface ActivityController {
	void start(String url);

	boolean onKeyUp(int keyCode, KeyEvent event);

	void onDestroy();

	void onBackPressed();

	void onSaveState(Bundle outState);

	void onRestoreInstanceState(Bundle savedInstanceState);
}
