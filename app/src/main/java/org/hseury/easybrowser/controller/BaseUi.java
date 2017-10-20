package org.hseury.easybrowser.controller;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import org.hseury.easybrowser.tab.Tab;

/**
 * @description: Created by hseury on 10/19/17.
 */

public class BaseUi extends Ui {
	private ViewGroup mContentContainer;
	public BaseUi(ViewGroup container){
		mContentContainer = container;
	}
	@Override public void addTab(Tab tab) {
		mContentContainer.addView(tab.getContentViewParent(),
				new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
						FrameLayout.LayoutParams.MATCH_PARENT));
	}
}
