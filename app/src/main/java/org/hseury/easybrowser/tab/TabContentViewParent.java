package org.hseury.easybrowser.tab;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ClipDrawable;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tencent.smtt.sdk.WebView;
import org.hseury.easybrowser.R;

/**
 * parent container for WebView， including navigation bar， go back / forward button , stop or
 * reload button, progressbar, handling with ui interface.
 * Created by hseury on 8/16/17.
 */

public class TabContentViewParent extends FrameLayout implements Tab.WebViewCallback {
  private LinearLayout mInfobarWrapper;

  private Tab mTab;
  private Context mContext;

  public EditText mUrlTextView;
  private ImageButton mPrevButton;
  private ImageButton mNextButton;
  private ImageButton mReloadOrStopButton;
  private LinearLayout mContentContainer;
  Handler handler = new Handler();
  private ClipDrawable mProgressDrawable;
  private String mCurrentUrl;
  private boolean mIsLoadResouce = false;

  TabContentViewParent(Context context, Tab tab) {
    super(context);
    mTab = tab;
    initUi(context);
  }

  public LinearLayout getContentContainer() {
    return mContentContainer;
  }

  public void initUi(Context context) {
    LayoutInflater.from(context).inflate(R.layout.tab_shell, this);
    mInfobarWrapper = (LinearLayout) findViewById(R.id.testshell_activity);
    mInfobarWrapper.setFocusable(true);
    mInfobarWrapper.setFocusableInTouchMode(true);

    removeAllViews();
    addView(mInfobarWrapper,
        new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

    mContentContainer = (LinearLayout) findViewById(R.id.content_container);

    initializeUrlField();
    initializeNavigationButtons();

    mUrlTextView.setText(mTab.getUrl());
  }

  private void initializeUrlField() {
    mUrlTextView = (EditText) findViewById(R.id.url);

    mUrlTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if ((actionId != EditorInfo.IME_ACTION_GO) && (event == null
            || event.getKeyCode() != KeyEvent.KEYCODE_ENTER
            || event.getAction() != KeyEvent.ACTION_DOWN)) {
          return false;
        }

        mTab.loadUrl(sanitizeUrl(mUrlTextView.getText().toString()));
        mUrlTextView.clearFocus();
        setKeyboardVisibilityForUrl(false);
        mTab.requestFocus();
        return true;
      }
    });

    mUrlTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override public void onFocusChange(View v, boolean hasFocus) {
        setKeyboardVisibilityForUrl(hasFocus);
        mNextButton.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
        mPrevButton.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
        mReloadOrStopButton.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
        if (!hasFocus) {
          mUrlTextView.setText(mTab.getUrl());
        }
      }
    });
    mUrlTextView.setOnKeyListener(new View.OnKeyListener() {
      @Override public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
          mTab.requestFocus();
          return true;
        }
        return false;
      }
    });
  }

  private void initializeNavigationButtons() {
    mProgressDrawable = (ClipDrawable) (findViewById(R.id.toolbar).getBackground());
    mProgressDrawable.setLevel(0);

    mPrevButton = (ImageButton) findViewById(R.id.prev);
    mPrevButton.setEnabled(false);
    mPrevButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (mTab.canGoBack()) {
          mTab.goBack();
        }
      }
    });

    mNextButton = (ImageButton) findViewById(R.id.next);
    mNextButton.setEnabled(false);
    mNextButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (mTab.canGoForward()) {
          mTab.goForward();
        }
      }
    });

    mReloadOrStopButton = (ImageButton) findViewById(R.id.stop_reload_button);
    mReloadOrStopButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (mTab.isLoading()) {
          mTab.stopLoading();
        } else {
          mTab.reload();
        }
        changeStopOrRefresh();
      }
    });
  }

  public void setKeyboardVisibilityForUrl(boolean visible) {
    InputMethodManager imm =
        (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
    if (visible) {
      imm.showSoftInput(mUrlTextView, InputMethodManager.SHOW_IMPLICIT);
    } else {
      imm.hideSoftInputFromWindow(mUrlTextView.getWindowToken(), 0);
    }
  }

  private static String sanitizeUrl(String url) {
    if (url == null) return null;
    if (url.startsWith("www.") || url.indexOf(":") == -1) url = "http://" + url;
    return url;
  }

  public void enableUIControl() {
    if (mTab.canGoBack()) {
      mPrevButton.setEnabled(true);
    } else {
      mPrevButton.setEnabled(false);
    }
    if (mTab.canGoForward()) {
      mNextButton.setEnabled(true);
    } else {
      mNextButton.setEnabled(false);
    }
  }

  private void changeStopOrRefresh() {
    if (mTab.isLoading()) {
      mReloadOrStopButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
    } else {
      mReloadOrStopButton.setImageResource(R.drawable.ic_refresh);
    }
  }

  @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
    changeStopOrRefresh();
  }

  @Override public void onPageFinished(WebView view, String url) {
    enableUIControl();
    changeStopOrRefresh();
  }

  @Override public void onProgressChanged(WebView view, int newProgress) {
    if (!view.getUrl().equals(mUrlTextView.getText().toString())) {
      mUrlTextView.setText(view.getUrl());
      mCurrentUrl = mUrlTextView.getText().toString();
    }

    if (newProgress > 80) {
      mProgressDrawable.setLevel(0);
    } else {
      mProgressDrawable.setLevel(newProgress * 100);
    }
  }

  @Override public void onLoadResource(WebView view, String s) {
    mIsLoadResouce = true;
  }
}
