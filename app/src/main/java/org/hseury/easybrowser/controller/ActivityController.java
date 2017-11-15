package org.hseury.easybrowser.controller;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

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

	/************* popmenu ******************/
	boolean onCreateOptionsMenu(Menu menu);

	boolean onPrepareOptionsMenu(Menu menu);

	boolean onOptionsItemSelected(MenuItem item);

	void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo);

	boolean onContextItemSelected(MenuItem item);
}
