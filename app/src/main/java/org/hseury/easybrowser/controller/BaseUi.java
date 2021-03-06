package org.hseury.easybrowser.controller;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import butterknife.Bind;
import butterknife.ButterKnife;
import org.hseury.easybrowser.R;
import org.hseury.easybrowser.tab.Tab;
import org.hseury.easybrowser.views.TitleBar;
import org.hseury.easybrowser.webviewframe.IWebView;

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
		mMainContent.addView(tab.getWebView().getView(),
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

	/**
	 * clean tab container
	 * @param tab
	 * @param webView
	 */
	@Override public void onSetWebView(Tab tab, IWebView webView) {
		tab.setContainer(mMainContent);
		if (webView != null) {
			if (mMainContent != null) {
				mMainContent.removeAllViews();
			}
		}
	}

	@Override public void onPageStopped(Tab tab) {

	}

	@Override public void hideStopButton() {
		mTitleBar.hideStopButton();
	}

	@Override public void onProgressChanged(Tab tab) {
		mTitleBar.setProgress(tab.getProgress());
	}

	@Override public boolean onPrepareOptionsMenu(Menu menu) {
		updateMenuState(null,menu);
		return true;
	}

	@Override public void updateMenuState(Tab tab, Menu menu) {
		MenuItem bm = menu.findItem(R.id.bookmarks_menu_id);
		if (bm != null) {
			bm.setVisible(true);
		}
		MenuItem abm = menu.findItem(R.id.add_bookmark_menu_id);
		if (abm != null) {
			abm.setVisible(true /*(tab != null)&& !tab.isSnapshot() && !showingNavScreen()*/);
		}
		MenuItem info = menu.findItem(R.id.page_info_menu_id);
		if (info != null) {
			info.setVisible(false);
		}
		MenuItem newTab = menu.findItem(R.id.new_tab_menu_id);
		if (newTab != null /*&& !mUseQuickControls*/) {
			newTab.setVisible(false);
		}
		MenuItem newIncognitoTab = menu.findItem(R.id.new_incognito_tab_menu_id);
		if (newIncognitoTab != null/* && !mUseQuickControls*/) {
			newIncognitoTab.setVisible(false);
		}
		MenuItem closeOthers = menu.findItem(R.id.close_other_tabs_id);
		//if (closeOthers != null) {
		//	boolean isLastTab = true;
		//	if (tab != null) {
		//		isLastTab = (mTabControl.getTabCount() <= 1);
		//	}
		//	closeOthers.setEnabled(!isLastTab);
		//}
		if (/*showingNavScreen()*/true) {
			menu.setGroupVisible(R.id.LIVE_MENU, false);
			menu.setGroupVisible(R.id.SNAPSHOT_MENU, false);
			menu.setGroupVisible(R.id.NAV_MENU, true);
			menu.setGroupVisible(R.id.COMBO_MENU, true);
		}
	}


}
