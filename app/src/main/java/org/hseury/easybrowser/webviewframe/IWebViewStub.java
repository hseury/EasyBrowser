package org.hseury.easybrowser.webviewframe;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by hseury on 9/5/17.
 */

public interface IWebViewStub {
  public View getView();

  public boolean canGoBack();

  public boolean canGoForward();

  public void goBack();

  public void goForward();

  public void loadUrl(String url);

  public void requestFocus();

  public String getUrl();

  public void stopLoading();

  public void reload();

  public void saveState(Bundle state);

  public void restoreState(Bundle state);

  public void destroy();

  public void setLayoutParams(LinearLayout.LayoutParams layoutParams);

}
