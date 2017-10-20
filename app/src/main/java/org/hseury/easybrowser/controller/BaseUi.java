package org.hseury.easybrowser.controller;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import org.hseury.easybrowser.R;
import org.hseury.easybrowser.tab.Tab;

/**
 * @description: Created by hseury on 10/19/17.
 */

public class BaseUi extends Ui {

	@Bind(R.id.fixed_titlebar_container) FrameLayout mFixedTitlebarContainer;
	@Bind(R.id.main_content) FrameLayout mMainContent;
	private Activity mActivity;

	public BaseUi(Activity browserActivity) {
		mActivity = browserActivity;
		FrameLayout frameLayout = (FrameLayout) mActivity.getWindow()
				.getDecorView()
				.findViewById(android.R.id.content);
		View view = LayoutInflater.from(mActivity).inflate(R.layout.tab_activity, frameLayout);
		ButterKnife.bind(this, view);
	}

	@Override public void addTab(Tab tab) {
		mMainContent.addView(tab.getContentViewParent(),
				new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
						FrameLayout.LayoutParams.MATCH_PARENT));
	}
}
