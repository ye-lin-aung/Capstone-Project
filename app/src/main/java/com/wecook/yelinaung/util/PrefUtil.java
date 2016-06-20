package com.wecook.yelinaung.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by user on 6/11/16.
 */
public class PrefUtil {
  public static final String URL = "URL";
  public static final String COUNT = "COUNT";

  public static void setTotalCount(int count, Context context) {

    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putInt(COUNT, count);
    editor.commit();
  }

  public static int getCount(Context context) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    return sharedPreferences.getInt(COUNT, 5000);
  }

  public static void setUrl(String url, Context context) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putString(URL, url);
    editor.commit();
  }

  public static String getUrl(Context context) {
    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    return sharedPreferences.getString(URL,
        "http://addb.absolutdrinks.com/drinks?apikey=f0052aa8b4ae4973963560c42ad4cce2&apisecret=fb28ccfbec32401180c177bda7731689");
  }
}
