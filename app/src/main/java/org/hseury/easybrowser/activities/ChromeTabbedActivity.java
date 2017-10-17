package org.hseury.easybrowser.activities;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.ClipDrawable;
import android.net.http.SslError;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.SslErrorHandler;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.hseury.easybrowser.R;
import org.hseury.easybrowser.tab.Tab;

public class ChromeTabbedActivity extends Activity {

  public static final String TAG = "ChromeTabbedActivity";
  private Tab mTab;

  public static final String DEFAULT_URL = "http://hseury.tk";

  @Override protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.tab_activity);
    mTab = new Tab(getApplicationContext());
    addContentView(mTab.getContentViewParent(),
        new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT));

    mTab.loadUrl(DEFAULT_URL);
  }

  @Override public boolean onKeyUp(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      if (mTab.canGoBack()) {
        mTab.goBack();
        return true;
      } else {
        //TODO: kill all activities
      }
    }
    return super.onKeyUp(keyCode, event);
  }

  @Override public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
  }

  @Override public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
    super.onSaveInstanceState(outState, outPersistentState);
    mTab.saveState(outState);
  }

  @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    mTab.restoreState(savedInstanceState);
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if(mTab != null){
      mTab.onDestroy();
    }
  }

  @Override public void onBackPressed() {
    if (mTab.canGoBack()){
      mTab.goBack();
    }
    super.onBackPressed();
  }
}