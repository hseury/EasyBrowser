package org.hseury.easybrowser.controller;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import org.hseury.easybrowser.R;
import org.hseury.easybrowser.tab.Tab;
import org.hseury.easybrowser.views.TitleBar;

/**
 * @description: Created by hseury on 10/19/17.
 */

public class BaseUi extends Ui {

	@Bind(R.id.fixed_titlebar_container) FrameLayout mFixedTitlebarContainer;
	@Bind(R.id.main_content) FrameLayout mMainContent;
	private Activity mActivity;
	protected TitleBar mTitleBar;
	private Controller mController;

	public BaseUi(Activity browserActivity, Controller controller) {
		mActivity = browserActivity;
		FrameLayout frameLayout = (FrameLayout) mActivity.getWindow()
				.getDecorView()
				.findViewById(android.R.id.content);
		View view = LayoutInflater.from(mActivity).inflate(R.layout.tab_activity, frameLayout);
		ButterKnife.bind(this, view);
		mController = controller;
		mTitleBar = new TitleBar(mActivity, mController);
		mTitleBar.setProgress(100);

		mFixedTitlebarContainer.addView(mTitleBar);
	}

	@Override public void addTab(Tab tab) {
		mMainContent.addView(tab.getContentViewParent(),
				new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
						FrameLayout.LayoutParams.MATCH_PARENT));
	}

	@Override public void updateStopOrRefresh(int resId) {
		mTitleBar.setStopOrReloadIcon(resId);
	}

	@Override public String getUrl() {
		return mTitleBar.getUrl();
	}

	@Override public void clearUrlTextFocus() {
		mTitleBar.clearUrlTextFocus();
	}

	@Override public void setProgress(int progress) {
		mTitleBar.setProgress(progress);
	}

	@Override public void setTitle(String title) {
		mTitleBar.setTitle(title);
	}

	@Override public void enableUIControl(boolean canGoBack, boolean canGoForward) {
		mTitleBar.enableUIControl(canGoBack, canGoForward);
	}
}
