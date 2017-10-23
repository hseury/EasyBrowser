package org.hseury.easybrowser.views;

import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import org.hseury.easybrowser.R;
import org.hseury.easybrowser.controller.BaseUi;
import org.hseury.easybrowser.controller.UiController;

import static org.hseury.easybrowser.utils.UrlUtil.sanitizeUrl;

/**
 * @description: Created by hseury on 10/20/17.
 */

public class TitleBar extends RelativeLayout {

	@Bind(R.id.stop_reload_button) ImageButton mReloadOrStopButton;
	@Bind(R.id.url) EditText mUrlTextView;
	@Bind(R.id.prev) ImageButton mPrevButton;
	@Bind(R.id.next) ImageButton mNextButton;
	@Bind(R.id.toolbar) LinearLayout mNavBar;
	private ClipDrawable mProgressDrawable;


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

		mProgressDrawable = (ClipDrawable) (mNavBar.getBackground());
		mProgressDrawable.setLevel(0);

		mPrevButton.setEnabled(false);
		mNextButton.setEnabled(false);

		mUrlTextView.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override public void onFocusChange(View v, boolean hasFocus) {
				//setKeyboardVisibilityForUrl(hasFocus);
				mNextButton.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
				mPrevButton.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
				mReloadOrStopButton.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
			}
		});

		mUrlTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				return mUiController.onUrlTextViewEditorAction(v, actionId, event);
			}
		});

		setFocusable(true);
		setFocusableInTouchMode(true);
	}

	@OnClick({ R.id.stop_reload_button, R.id.url, R.id.prev, R.id.next })
	public void onViewClicked(View view) {
		switch (view.getId()) {
			case R.id.stop_reload_button:
				mUiController.onStopOrReloadClick();
				break;
			case R.id.url:
				mUiController.onUrlEditClick();
				break;
			case R.id.prev:
				mUiController.onPreClick();
				break;
			case R.id.next:
				mUiController.onNextClick();
				break;
			default:
				break;
		}
	}

	public void setProgress(int progress){
		mProgressDrawable.setLevel(progress * 100);
	}

	private void initializeUrlField() {
		mUrlTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			@Override public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				return mUiController.onUrlTextViewEditorAction(v, actionId, event);
			}
		});
	}

	public String getUrl(){
		return mUrlTextView.getText().toString();
	}

	public void clearUrlTextFocus() {
		mUrlTextView.clearFocus();
	}

	public void setTitle(String title){
		mUrlTextView.setText(title);
	}

	public void enableUIControl(boolean canGoBack, boolean canGoForward){
		if (canGoBack) {
			mPrevButton.setEnabled(true);
		} else {
			mPrevButton.setEnabled(false);
		}
		if (canGoForward) {
			mNextButton.setEnabled(true);
		} else {
			mNextButton.setEnabled(false);
		}
	}

	public void setStopOrReloadIcon(int resId){
		mReloadOrStopButton.setImageResource(resId);
	}

}
