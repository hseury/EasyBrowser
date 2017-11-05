package org.hseury.easybrowser.views;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
//import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import org.hseury.easybrowser.utils.ReflectHelper;

/**
 * @description: Created by hseury on 2017/10/29.
 */

public class UrlInputView extends AutoCompleteTextView implements
		TextView.OnEditorActionListener {
	enum STATE {NORMAL, HIGHLIGHTED, EDITED}

	private InputMethodManager mInputManager;
	private UrlInputListener   mListener;
	private STATE mState;

	static final int POST_DELAY = 100;

	public UrlInputView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public UrlInputView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public UrlInputView(Context context) {
		this(context, null);
	}

	private void init(Context ctx) {
		mInputManager = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
		setOnEditorActionListener(this);
		//mAdapter = new SuggestionsAdapter(ctx, this);
		//setAdapter(mAdapter);
		setSelectAllOnFocus(true);
		onConfigurationChanged(ctx.getResources().getConfiguration());
		setThreshold(1);
		//setOnItemClickListener(this);
		//mNeedsUpdate = false;
		//addTextChangedListener(this);
		//setDropDownAnchor(com.android.stockbrowser.R.id.taburlbar);
		mState = STATE.NORMAL;
		//mContext = ctx;
	}

	void hideIME() {
		mInputManager.hideSoftInputFromWindow(getWindowToken(), 0);
	}

	void showIME() {
		//mInputManager.focusIn(this);
		Object[] params  = {this};
		Class[] type = new Class[] {View.class};
		ReflectHelper.invokeMethod(mInputManager, "focusIn", type, params);
		mInputManager.showSoftInput(this, 0);
	}


	@Override public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		finishInput(getText().toString(), null, null);
		return true;
	}

	private void finishInput(String url, String extra, String source) {
		//mNeedsUpdate = true;
		dismissDropDown();
		mInputManager.hideSoftInputFromWindow(getWindowToken(), 0);
		if (TextUtils.isEmpty(url)) {
			mListener.onDismiss();
		} else {
			//if (mIncognitoMode && isSearch(url)) {
			//	// To prevent logging, intercept this request
			//	// TODO: This is a quick hack, refactor this
			//	SearchEngine searchEngine = BrowserSettings.getInstance()
			//			.getSearchEngine();
			//	if (searchEngine == null) return;
			//	SearchEngineInfo engineInfo = SearchEngines
			//			.getSearchEngineInfo(mContext, searchEngine.getName());
			//	if (engineInfo == null) return;
			//	url = engineInfo.getSearchUriForQuery(url);
			//	// mLister.onAction can take it from here without logging
			//}
			//mListener.onAction(url, extra, source);
		}
	}

	@Override public boolean onTouchEvent(MotionEvent evt) {
		boolean hasSelection = hasSelection();
		boolean res = super.onTouchEvent(evt);
		if ((MotionEvent.ACTION_DOWN == evt.getActionMasked())
				&& hasSelection) {
			postDelayed(new Runnable() {
				public void run() {
					changeState(STATE.EDITED);
				}}, POST_DELAY);
		}
		return res;
	}

	@Override
	protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
		super.onFocusChanged(focused, direction, previouslyFocusedRect);
		STATE state = STATE.NORMAL;
		if (focused) {
			if (hasSelection()) {
				state = STATE.HIGHLIGHTED;
			} else {
				state = STATE.EDITED;
			}
		} else {
			// reset the selection state
			state = STATE.NORMAL;
		}
		final STATE s = state;
		post(new Runnable() {
			public void run() {
				changeState(s);
			}
		});
	}

	private void changeState(STATE newState) {
		mState = newState;
		if (mListener != null) {
			mListener.onStateChanged(mState);
		}
	}
	interface UrlInputListener {

		public void onDismiss();

		public void onAction(String text, String extra, String source);

		public void onCopySuggestion(String text);

		public void onStateChanged(STATE state);
	}
}
