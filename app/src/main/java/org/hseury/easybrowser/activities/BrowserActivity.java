package org.hseury.easybrowser.activities;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import org.hseury.easybrowser.R;
import org.hseury.easybrowser.controller.BaseUi;
import org.hseury.easybrowser.controller.Controller;
import org.hseury.easybrowser.tab.Tab;

public class BrowserActivity extends Activity {

	public static final String TAG = "BrowserActivity";
	@Bind(R.id.tab_container) LinearLayout tabContainer;
	private Controller mController;

	public static final String DEFAULT_URL = "http://hseury.tk";

	@Override protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_activity);
		ButterKnife.bind(this);

		mController = createController();
		mController.start(DEFAULT_URL);
	}

	private Controller createController() {
		Controller controller = new Controller(this);
		controller.setUi(new BaseUi(tabContainer));
		return controller;
	}

	@Override public boolean onKeyUp(int keyCode, KeyEvent event) {
		return mController.onKeyUp(keyCode, event) || super.onKeyUp(keyCode, event);
	}

	@Override public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
		super.onSaveInstanceState(outState, outPersistentState);
		mController.onSaveState(outState);
	}

	@Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		mController.onRestoreInstanceState(savedInstanceState);
	}

	@Override protected void onDestroy() {
		super.onDestroy();
		mController.onDestroy();
	}

	@Override public void onBackPressed() {
		super.onBackPressed();
		mController.onBackPressed();
	}
}