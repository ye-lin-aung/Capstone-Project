package com.wecook.yelinaung.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by user on 5/28/16.
 */
public class InternetUtil {
  public static boolean isOnline(Context c) {
    NetworkInfo netInfo = null;
    try {
      ConnectivityManager cm =
          (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);
      netInfo = cm.getActiveNetworkInfo();
    } catch (SecurityException e) {
      e.printStackTrace();
    }
    return netInfo != null && netInfo.isConnectedOrConnecting();
  }

}
