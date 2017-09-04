package org.hseury.easybrowser.activities;

import android.app.Application;
import com.github.moduth.blockcanary.BlockCanary;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.smtt.sdk.QbSdk;
import org.hseury.easybrowser.utils.BrowserBlockCanaryContext;

/**
 * Created by hseury on 8/10/17.
 */

public class BrowserApplication extends Application {
  @Override public void onCreate() {
    super.onCreate();
    //init x5 WebView
    QbSdk.initX5Environment(this,null);
    //inti LeakCanary
    LeakCanary.install(this);
    //init BlockCanary
    BlockCanary.install(this, new BrowserBlockCanaryContext());
  }
}
