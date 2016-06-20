package com.wecook.yelinaung.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;
import com.wecook.yelinaung.R;
import com.wecook.yelinaung.detail.DetailActivity;

/**
 * Created by user on 6/19/16.
 */
public class HomeScreenWidget extends AppWidgetProvider {
  public static final String UPDATE = "android.appwidget.action.APPWIDGET_UPDATE";

  @Override public void onDeleted(Context context, int[] appWidgetIds) {
    super.onDeleted(context, appWidgetIds);
  }

  @Override public void onDisabled(Context context) {
    super.onDisabled(context);
  }

  @Override public void onEnabled(Context context) {
    super.onEnabled(context);
  }

  @Override public void onReceive(Context context, Intent intent) {
    super.onReceive(context, intent);
    if (intent.getAction().equals(UPDATE)) {

      AppWidgetManager mgr = AppWidgetManager.getInstance(context);
      int appWidgetIds[] = mgr.getAppWidgetIds(new ComponentName(context, HomeScreenWidget.class));

      mgr.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stackWidgetView);
    }
    super.onReceive(context, intent);
  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

    for (int i = 0; i < appWidgetIds.length; ++i) {
      RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_recycler);
      // set intent for widget service that will create the views
      Intent serviceIntent = new Intent(context, StackWidgetService.class);
      serviceIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
      serviceIntent.setData(Uri.parse(serviceIntent.toUri(Intent.URI_INTENT_SCHEME))); // embed extr
      remoteViews.setRemoteAdapter(appWidgetIds[i], R.id.stackWidgetView, serviceIntent);
      remoteViews.setEmptyView(R.id.stackWidgetView, R.id.stack_empty);

      Intent viewIntent = new Intent(context, DetailActivity.class);
      viewIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
      viewIntent.setData(Uri.parse(viewIntent.toUri(Intent.URI_INTENT_SCHEME)));
      PendingIntent viewPendingIntent = PendingIntent.getActivity(context, 0, viewIntent, 0);
      remoteViews.setPendingIntentTemplate(R.id.stackWidgetView, viewPendingIntent);

      // set intent for item click (opens main activity)
      //Intent viewIntent = new Intent(context, HoneybuzzListActivity.class);
      //viewIntent.setAction(HoneybuzzListActivity.ACTION_VIEW);
      //viewIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
      //viewIntent.setData(Uri.parse(viewIntent.toUri(Intent.URI_INTENT_SCHEME)));
      //PendingIntent viewPendingIntent = PendingIntent.getActivity(context, 0, viewIntent, 0);
      //remoteViews.setPendingIntentTemplate(R.id.stackWidgetView, viewPendingIntent);
      // update widget

      appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
    }
    super.onUpdate(context, appWidgetManager, appWidgetIds);
  }
}