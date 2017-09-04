package org.hseury.easybrowser.utils;

import com.github.moduth.blockcanary.BlockCanaryContext;
import org.hseury.easybrowser.BuildConfig;

/**
 * Created by hseury on 9/4/17.
 */

public class BrowserBlockCanaryContext extends BlockCanaryContext {
  @Override
  public int provideBlockThreshold() {
    // 1000 ms pause
    return 1000;
  }
}
