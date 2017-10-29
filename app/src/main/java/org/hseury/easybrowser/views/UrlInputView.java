package org.hseury.easybrowser.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.support.v7.widget.AppCompatAutoCompleteTextView;

/**
 * @description: Created by hseury on 2017/10/29.
 */

public class UrlInputView extends AppCompatAutoCompleteTextView {
	enum STATE {NORMAL, HIGHLIGHTED, EDITED}

	public UrlInputView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public UrlInputView(Context context, AttributeSet attrs) {
		// SWE_TODO : Needs Fix
		//this(context, attrs, R.attr.autoCompleteTextViewStyle);
		this(context, attrs, 0);
	}

	public UrlInputView(Context context) {
		this(context, null);
	}

	private void init(Context ctx) {
		//mInputManager = (InputMethodManager) ctx.getSystemService(Context.INPUT_METHOD_SERVICE);
		//setOnEditorActionListener(this);
		//mAdapter = new SuggestionsAdapter(ctx, this);
		//setAdapter(mAdapter);
		//setSelectAllOnFocus(true);
		//onConfigurationChanged(ctx.getResources().getConfiguration());
		//setThreshold(1);
		//setOnItemClickListener(this);
		//mNeedsUpdate = false;
		//addTextChangedListener(this);
		//setDropDownAnchor(com.android.stockbrowser.R.id.taburlbar);
		//mState = StateListener.STATE_NORMAL;
		//mContext = ctx;
	}

}
