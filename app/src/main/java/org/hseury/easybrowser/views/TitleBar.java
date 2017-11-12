package org.hseury.easybrowser.views;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.hseury.easybrowser.R;
import org.hseury.easybrowser.controller.BaseUi;
import org.hseury.easybrowser.controller.UiController;

/**
 * @description: Created by hseury on 10/20/17.
 */

public class TitleBar extends RelativeLayout {
	@Bind(R.id.magnify) ImageView mMagnify;
	@Bind(R.id.stop) ImageView mStop;
	@Bind(R.id.clear) ImageView mClear;
	@Bind(R.id.incognito_icon) ImageView mIncognitoIcon;
	@Bind(R.id.title_bg) LinearLayout mTitleBg;
	@Bind(R.id.tab_switcher) ImageButton mTabSwitcher;
	@Bind(R.id.more) ImageButton mMore;
	@Bind(R.id.taburlbar) NavigationBarPhone mNavigationBarPhone;
	private ClipDrawable mProgressDrawable;

	private BaseUi mBaseUi;
	private UiController mUiController;

	public TitleBar(Context context, UiController controller) {
		super(context);
		mUiController = controller;
		initLayout(context);
	}

	private void initLayout(Context context) {
		LayoutInflater factory = LayoutInflater.from(context);
		View view = factory.inflate(R.layout.navi_bar, this);
		ButterKnife.bind(this, view);

		setFocusable(true);
		setFocusableInTouchMode(true);

		mNavigationBarPhone.setTitleBar(this);
	}

	@OnClick({ R.id.url,R.id.stop })
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.stop:
				mUiController.onStopOrReloadClick();
				break;
			case R.id.url:
				mUiController.onUrlEditClick();
				break;
			default:
				break;
		}
	}

	public void setProgress(int progress) {
		if (progress >= 100) {
			mNavigationBarPhone.onProgressStopped();
		}else{
			mNavigationBarPhone.onProgressStarted();
		}
	}

	private void initializeUrlField() {
	}

	public String getUrl() {
		return mNavigationBarPhone.getUrlInput().getText().toString();
	}

	public void clearUrlTextFocus() {
		mNavigationBarPhone.clearUrlBarFocus();
	}

	public void setTitle(String title) {
		mNavigationBarPhone.setTitle(title);
	}

	public void enableUIControl(boolean canGoBack, boolean canGoForward) {

	}

	public void hideStopButton(){
		mStop.setVisibility(GONE);
	}

	public void setStopOrReloadIcon(int resId) {
		mStop.setImageResource(resId);
	}

	public BaseUi getUi() {
		return mBaseUi;
	}

	public UiController getUiController() {
		return mUiController;
	}

}
