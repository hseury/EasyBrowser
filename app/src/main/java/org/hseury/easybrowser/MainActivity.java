package org.hseury.easybrowser;

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
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

  public static final String TAG = "browser41";
  private WebView mWebView;
  public EditText mUrlTextView;
  private ImageButton mPrevButton;
  private ImageButton mNextButton;
  private ImageButton mReloadOrStopButton;
  private LinearLayout contentContainer;
  Handler handler = new Handler();
  private ClipDrawable mProgressDrawable;

  private boolean mLoading;

  public static String currentUrl = null;

  @Override
  protected void onCreate(final Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initUIAndSettings();
    currentUrl = "http://m.2345.com";
    mWebView.loadUrl(currentUrl);
  }

  private void initializeUrlField() {
    mUrlTextView = (EditText) findViewById(R.id.url);

    mUrlTextView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
      @Override
      public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if ((actionId != EditorInfo.IME_ACTION_GO) && (event == null ||
            event.getKeyCode() != KeyEvent.KEYCODE_ENTER ||
            event.getAction() != KeyEvent.ACTION_DOWN)) {
          return false;
        }

        mWebView.loadUrl(sanitizeUrl(mUrlTextView.getText().toString()));
        mUrlTextView.clearFocus();
        setKeyboardVisibilityForUrl(false);
        mWebView.requestFocus();
        return true;
      }
    });

    mUrlTextView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
      @Override
      public void onFocusChange(View v, boolean hasFocus) {
        setKeyboardVisibilityForUrl(hasFocus);
        mNextButton.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
        mPrevButton.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
        mReloadOrStopButton.setVisibility(hasFocus ? View.GONE : View.VISIBLE);
        if (!hasFocus) {
          mUrlTextView.setText(mWebView.getUrl());
        }
      }
    });
    mUrlTextView.setOnKeyListener(new View.OnKeyListener() {
      @Override
      public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
          mWebView.requestFocus();
          return true;
        }
        return false;
      }
    });
    /***********
     *  mUrlTextView = (EditText) findViewById(R.id.url);
     mUrlTextView.setOnEditorActionListener(new OnEditorActionListener() {
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
    if ((actionId != EditorInfo.IME_ACTION_GO) && (event == null
    || event.getKeyCode() != KeyEvent.KEYCODE_ENTER
    || event.getAction() != KeyEvent.ACTION_DOWN)) {
    return false;
    }
    loadUrl(mUrlTextView.getText().toString());
    setKeyboardVisibilityForUrl(false);
    mContentViewCore.getContainerView().requestFocus();
    return true;
    }
    });
     mUrlTextView.setOnFocusChangeListener(new OnFocusChangeListener() {
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
    setKeyboardVisibilityForUrl(hasFocus);
    mNextButton.setVisibility(hasFocus ? GONE : VISIBLE);
    mPrevButton.setVisibility(hasFocus ? GONE : VISIBLE);
    mStopReloadButton.setVisibility(hasFocus ? GONE : VISIBLE);
    if (!hasFocus) {
    mUrlTextView.setText(mWebContents.getUrl());
    }
    }
    });
     mUrlTextView.setOnKeyListener(new OnKeyListener() {
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
    mContentViewCore.getContainerView().requestFocus();
    return true;
    }
    return false;
    }
    });
     */
  }

  private void initializeNavigationButtons() {
    mProgressDrawable = (ClipDrawable)(findViewById(R.id.toolbar).getBackground());
    mProgressDrawable.setLevel(0);

    mPrevButton = (ImageButton) findViewById(R.id.prev);
    mPrevButton.setEnabled(false);
    mPrevButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mWebView.canGoBack()) {
          mWebView.goBack();
        }
      }
    });

    mNextButton = (ImageButton) findViewById(R.id.next);
    mNextButton.setEnabled(false);
    mNextButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (mWebView.canGoForward()) {
          mWebView.goForward();
        }
      }
    });

    mReloadOrStopButton = (ImageButton) findViewById(R.id.stop_reload_button);
    mReloadOrStopButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        if (mLoading) {
          mWebView.stopLoading();
        } else {
          mWebView.reload();
        }
        changeStopOrRefresh();
      }
    });

  }

  @Override
  public boolean onKeyUp(int keyCode, KeyEvent event) {
    if (keyCode == KeyEvent.KEYCODE_BACK) {
      if (mWebView.canGoBack()) {
        mWebView.goBack();
        return true;
      }else{
        finish();
        System.exit(0);
      }
    }
    return super.onKeyUp(keyCode, event);
  }

  public void setKeyboardVisibilityForUrl(boolean visible){
    InputMethodManager imm = (InputMethodManager) getApplicationContext().getSystemService(
        Context.INPUT_METHOD_SERVICE);
    if(visible){
      imm.showSoftInput(mUrlTextView,InputMethodManager.SHOW_IMPLICIT);
    }else{
      imm.hideSoftInputFromWindow(mUrlTextView.getWindowToken(),0);
    }
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);

    Log.e("6666", "onConfigurationChanged");
    if(mWebView != null)
      contentContainer.removeView(mWebView);

    setContentView(R.layout.activity_main);
    initUIAndSettings();
  }

  @Override
  public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
    super.onSaveInstanceState(outState, outPersistentState);
    mWebView.saveState(outState);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    mWebView.restoreState(savedInstanceState);
  }

  public void initUIAndSettings(){
    if(mWebView == null){
      mWebView = new WebView(this);
      class JavascriptInterface{
        public void getContentHeight(String value){
          if (value != null){
            int webviewContentHeight = Integer.parseInt(value);
            Log.d("6666","result from js" +  webviewContentHeight);

          }
        }
      }
      WebSettings mWebSettings = mWebView.getSettings();
      mWebSettings.setSupportZoom(true);
      mWebSettings.setUseWideViewPort(true);
      mWebSettings.setDefaultTextEncodingName("GBK");
      mWebSettings.setLoadsImagesAutomatically(true);
      mWebSettings.setJavaScriptEnabled(true);
      mWebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
      mWebSettings.setDomStorageEnabled(true);
      mWebSettings.setSupportMultipleWindows(false);
      mWebSettings.setLoadWithOverviewMode(true);

      mWebView.setLayoutParams(new LinearLayout.LayoutParams(
          LinearLayout.LayoutParams.MATCH_PARENT,
          LinearLayout.LayoutParams.MATCH_PARENT,
          1f));

      mWebView.requestFocus();

      mWebView.setWebViewClient(new WebViewClient() {
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
          handler.proceed();
        }

        @Override public void onReceivedLoginRequest(WebView view, String realm, String account,
            String args) {
          super.onReceivedLoginRequest(view, realm, account, args);
        }

        @Override public void onPageFinished(WebView view, String url) {
          super.onPageFinished(view, url);
          mLoading = false;
          enableUIControl();
          changeStopOrRefresh();
        }

        @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
          super.onPageStarted(view, url, favicon);
          mLoading = true;
          changeStopOrRefresh();
        }
      });

      mWebView.setWebChromeClient(new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
          super.onProgressChanged(view, newProgress);
          if (!view.getUrl().equals(mUrlTextView.getText().toString())) {
            mUrlTextView.setText(view.getUrl());
            currentUrl = mUrlTextView.getText().toString();
          }

          if (newProgress == 100) {
            mProgressDrawable.setLevel(0);
          }else{
            mProgressDrawable.setLevel(newProgress * 100);
          }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
          super.onReceivedTitle(view, title);
        }

      });
    }

    contentContainer = (LinearLayout)findViewById(R.id.content_container);
    contentContainer.addView(mWebView);

    initializeUrlField();
    initializeNavigationButtons();

    mUrlTextView.setText(currentUrl);
  }

  private static String sanitizeUrl(String url) {
    if (url == null) return null;
    if (url.startsWith("www.") || url.indexOf(":") == -1) url = "http://" + url;
    return url;
  }

  /***
   * fix large font
   * @return
   */
  @Override
  public Resources getResources() {
    Resources res = super.getResources();
    Configuration config=new Configuration();
    config.setToDefaults();
    res.updateConfiguration(config,res.getDisplayMetrics() );
    return res;
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  @Override
  protected void onPause() {
    super.onPause();
    mWebView.onPause();
  }

  @Override
  protected void onStop() {
    super.onStop();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
  }

  public void enableUIControl(){
    if (mWebView.canGoBack()) {
      mPrevButton.setEnabled(true);
    } else {
      mPrevButton.setEnabled(false);
    }
    if (mWebView.canGoForward()) {
      mNextButton.setEnabled(true);
    } else {
      mNextButton.setEnabled(false);
    }
  }

  private void changeStopOrRefresh() {
    if (mLoading) {
      mReloadOrStopButton.setImageResource(android.R.drawable.ic_menu_close_clear_cancel);
    } else {
      mReloadOrStopButton.setImageResource(R.drawable.ic_refresh);
    }
  }
}
