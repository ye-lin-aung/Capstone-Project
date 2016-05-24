package com.wecook.yelinaung.util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by user on 5/24/16.
 */
public class ViewUtil {
  private Context context;

  public ViewUtil(Context context) {
    this.context = context;
  }

  public int dps2px(int dps) {
    final float px = context.getResources().getDisplayMetrics().density;
    return (int) (px * dps + 0.5f);
  }

  public void setViewParmas(View view, int layoutWidth, int layoutHeight) {
    ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
    layoutParams.height = layoutHeight;
    layoutParams.width = layoutWidth;
    view.setLayoutParams(layoutParams);
  }
}
