package org.hseury.easybrowser.utils;

import android.util.Log;
import org.hseury.easybrowser.BuildConfig;

/**
 * Log util
 * Created by hseury on 8/13/17.
 */

public final class LogUtil {

  public static final String TAG = "HSEURY_LOG";

  public static void v(String tag, String msg) {
    if (BuildConfig.DEBUG) {
      Log.v(tag, msg);
    }
  }

  public static void i(String tag, String msg) {
    if (BuildConfig.DEBUG) {
      Log.i(tag, msg);
    }
  }

  public static void d(String tag, String msg) {
    if (BuildConfig.DEBUG) {
      Log.d(tag, msg);
    }
  }

  public static void w(String tag, String msg) {
    if (BuildConfig.DEBUG) {
      Log.w(tag, msg);
    }
  }

  public static void error(String msg) {
    if (BuildConfig.DEBUG) {
      Log.e(TAG, msg);
    }
  }

  public static void error(String tag, String msg) {
    if (BuildConfig.DEBUG) {
      Log.e(tag, msg);
    }
  }

  public static void error(String tag, Exception msg) {
    if (BuildConfig.DEBUG) {
      if (msg != null) {
        Log.e(tag, msg.toString());
      }
    }
  }
}
