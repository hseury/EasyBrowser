package org.hseury.easybrowser.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import org.hseury.easybrowser.R;

/**
 * @description: Created by hseury on 2017/10/26.
 */

public class NavigationBarPhone extends LinearLayout {

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

	public NavigationBarPhone(Context context) {
		super(context);
	}

	public NavigationBarPhone(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NavigationBarPhone(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override protected void onFinishInflate() {
		super.onFinishInflate();
		mUrlInput = (UrlInputView) findViewById(R.id.url);
		//mUrlInput.setUrlInputListener(this);
		//mUrlInput.setOnFocusChangeListener(this);
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
		//mUrlInput.setStateListener(this);
		mIncognitoIcon = findViewById(R.id.incognito_icon);
	}
}
