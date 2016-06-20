package com.wecook.yelinaung.util;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.wecook.yelinaung.R;

/**
 * Created by user on 6/16/16.
 */
public class AnalyticManager {

  private static Context sAppContext = null;
  private static Tracker mTracker;

  private static boolean canSend() {
    // We can only send Google Analytics when ALL the following conditions are true:
    //    1. This module has been initialized.
    //    2. The user has accepted the ToS.
    //    3. Analytics is enabled in Settings.
    return sAppContext != null && mTracker != null;
  }

  public static void sendScreenView(String screenName) {
    if (canSend()) {
      mTracker.setScreenName(screenName);
      mTracker.send(new HitBuilders.AppViewBuilder().build());

    } else {

    }
  }

  public static void sendEvent(String category, String action, String label, long value) {
    if (canSend()) {
      mTracker.send(new HitBuilders.EventBuilder().setCategory(category)
          .setAction(action)
          .setLabel(label)
          .setValue(value)
          .build());


    } else {

    }
  }

  public static void sendEvent(String category, String action, String label) {
    sendEvent(category, action, label, 0);
  }

  public static synchronized void initializeAnalyticsTracker(Context context) {
    sAppContext = context;
    if (mTracker == null) {
      int useProfile;
      useProfile = R.xml.global_tracker;
      mTracker = GoogleAnalytics.getInstance(context).newTracker(useProfile);

      mTracker.enableExceptionReporting(false);
      mTracker.enableAdvertisingIdCollection(true);
      mTracker.enableAutoActivityTracking(true);
    }
  }

  public Tracker getTracker() {
    return mTracker;
  }

  public static synchronized void setTracker(Tracker tracker) {
    mTracker = tracker;
  }
}
