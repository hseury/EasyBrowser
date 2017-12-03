package org.hseury.easybrowser;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;

/**
 * @description: Created by hseury on 2017/11/30.
 */

public class BrowserWebView extends WebView {
	BrowserWebView(Context context){
		super(context);
	}

	@Override protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		Log.e("check_scroll", "l/t/oldl/oldt = " + l + " / " + t + " / " + oldl + " / " + oldt);
	}
}
