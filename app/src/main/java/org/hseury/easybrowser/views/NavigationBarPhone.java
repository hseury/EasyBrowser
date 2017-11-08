package org.hseury.easybrowser.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import org.hseury.easybrowser.R;
import org.hseury.easybrowser.controller.BaseUi;
import org.hseury.easybrowser.controller.UiController;

/**
 * @description: Created by hseury on 2017/10/26.
 */

public class NavigationBarPhone extends LinearLayout
		implements View.OnFocusChangeListener, View.OnClickListener, UrlInputView.UrlInputListener {

	protected UrlInputView mUrlInput;

	private ImageView mStopButton;
	private ImageView mMagnify;
	private ImageView mClearButton;
	private Drawable mStopDrawable;
	private Drawable mRefreshDrawable;
	private String mStopDescription;
	private String mRefreshDescription;
	private View mTabSwitcher;
	private View mTitleContainer;
	private View mMore;
	private Drawable mTextfieldBgDrawable;
	private PopupMenu mPopupMenu;
	private boolean mOverflowMenuShowing;
	private View mIncognitoIcon;

	protected BaseUi mBaseUi;
	protected TitleBar mTitleBar;
	protected UiController mUiController;

	public NavigationBarPhone(Context context) {
		super(context);
	}

	public NavigationBarPhone(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NavigationBarPhone(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public void setTitleBar(TitleBar titleBar) {
		mTitleBar = titleBar;
		mBaseUi = mTitleBar.getUi();
		mUiController = mTitleBar.getUiController();
		//TODO: 自动填充相关 hseury
		//mUrlInput.setController(mUiController);
	}

	@Override protected void onFinishInflate() {
		super.onFinishInflate();
		mUrlInput = (UrlInputView) findViewById(R.id.url);
		//mUrlInput.setUrlInputListener(this);
		mUrlInput.setOnFocusChangeListener(this);
		//mUrlInput.setSelectAllOnFocus(true);
		//mUrlInput.addTextChangedListener(this);

		mStopButton = (ImageView) findViewById(R.id.stop);
		//mStopButton.setOnClickListener(this);
		mClearButton = (ImageView) findViewById(R.id.clear);
		//mClearButton.setOnClickListener(this);
		mMagnify = (ImageView) findViewById(R.id.magnify);
		mTabSwitcher = findViewById(R.id.tab_switcher);
		//mTabSwitcher.setOnClickListener(this);
		mMore = findViewById(R.id.more);
		//mMore.setOnClickListener(this);
		mTitleContainer = findViewById(R.id.title_bg);
		//setFocusState(false);
		Resources res = getContext().getResources();
		mStopDrawable = res.getDrawable(R.drawable.stop);
		mRefreshDrawable = res.getDrawable(R.drawable.refresh);
		mTextfieldBgDrawable = res.getDrawable(R.drawable.textfield_active_holo_dark);
		//mUrlInput.setContainer(this);
		mUrlInput.setStateListener(this);
		mIncognitoIcon = findViewById(R.id.incognito_icon);
	}

	public void setTitle(String title){
		mUrlInput.setText(title);
	}

	public void clearUrlBarFocus(){
		mUrlInput.clearFocus();
	}

	@Override public void onFocusChange(View v, boolean hasFocus) {
		mTabSwitcher.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
		mMore.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
		mStopButton.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
	}

	@Override public void onClick(View v) {
		switch (v.getId()) {
			case R.id.stop:
				mUiController.onStopOrReloadClick();
				break;
			default:
				break;
		}
	}

	void stopEditingUrl() {
		//WebView currentTopWebView = mUiController.openTabToHomePage();
		//if (currentTopWebView != null) {
		//	currentTopWebView.requestFocus();
		//}
	}

	/***********************  input url lisetener implements ********************/
	@Override public void onStateChanged(UrlInputView.STATE state) {
		switch (state) {
			case NORMAL:
				mStopButton.setVisibility(View.GONE);
				mClearButton.setVisibility(View.GONE);
				mMagnify.setVisibility(View.GONE);
				mTabSwitcher.setVisibility(View.VISIBLE);
				mTitleContainer.setBackgroundDrawable(null);
				mMore.setVisibility(View.VISIBLE);
				break;
			case HIGHLIGHTED:
				mStopButton.setVisibility(View.VISIBLE);
				mClearButton.setVisibility(View.GONE);
				mMagnify.setVisibility(View.GONE);
				mTabSwitcher.setVisibility(View.GONE);
				mMore.setVisibility(View.GONE);
				mTitleContainer.setBackgroundDrawable(mTextfieldBgDrawable);
				break;
			case EDITED:
				mStopButton.setVisibility(View.GONE);
				mClearButton.setVisibility(View.VISIBLE);
				mMagnify.setVisibility(View.VISIBLE);
				mTabSwitcher.setVisibility(View.GONE);
				mMore.setVisibility(View.GONE);
				mTitleContainer.setBackgroundDrawable(mTextfieldBgDrawable);
				break;
			default:
				break;
		}
	}

	@Override public void onAction(String text, String extra, String source) {

	}

	@Override public void onCopySuggestion(String text) {

	}

	@Override public void onDismiss() {

	}
}
