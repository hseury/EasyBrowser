package org.hseury.easybrowser;

import android.app.Application;
import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by hseury on 8/10/17.
 */

public class BrowserApplication extends Application {
  @Override public void onCreate() {
    super.onCreate();
    QbSdk.initX5Environment(this,null);
  }
}
