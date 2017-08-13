package org.hseury.easybrowser.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import org.hseury.easybrowser.utils.LogUtil;

/**
 * Created by hseury on 8/13/17.
 */

public class LauncherActivity extends Activity{
  public static final String TAG = "LauncherActivity";

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    LogUtil.d(TAG,"onCreate-->callOnCreate");
    callOnCreate();
  }
  private void callOnCreate(){
    LogUtil.d(TAG,"callOnCreate");
    Intent intent = new Intent(this,CustomTabActivity.class);
    startActivity(intent);
  };
}
