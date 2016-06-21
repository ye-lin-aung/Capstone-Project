package com.wecook.yelinaung.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;
import com.bumptech.glide.Glide;
import com.wecook.yelinaung.Injection;
import com.wecook.yelinaung.R;
import com.wecook.yelinaung.database.DrinkDbModel;
import com.wecook.yelinaung.database.DrinksRepository;
import com.wecook.yelinaung.detail.DetailActivity;
import com.wecook.yelinaung.util.AnalyticManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 6/19/16.
 */
public class StackWidgetService extends RemoteViewsService {

  @Override public RemoteViewsFactory onGetViewFactory(Intent intent) {
    return new StackRemoteViewsFactory(this.getApplicationContext(), intent);
  }
}

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

  private List<DrinkDbModel> mBuzzes = new ArrayList<DrinkDbModel>();
  private Context mContext;
  private int mAppWidgetId;
  DrinksRepository drinksRepository;

  public StackRemoteViewsFactory(Context context, Intent intent) {
    mContext = context;
    mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
        AppWidgetManager.INVALID_APPWIDGET_ID);
  }

  public void onCreate() {
    drinksRepository = Injection.provideDrinkRepo(mContext);
  }

  public void onDestroy() {
    mBuzzes.clear();
  }

  public int getCount() {
    return mBuzzes.size();
  }

  public RemoteViews getViewAt(int position) {

    final RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.item_widget);
    if (position <= getCount()) {

      AnalyticManager.sendScreenView(mContext.getString(R.string.widget_screen));
      DrinkDbModel buzz = mBuzzes.get(position);
      if (buzz.getId() != null) {

        String image =
            "http://assets.absolutdrinks.com/drinks/transparent-background-white/soft-shadow/150x250/"
                + buzz.getId()
                + ".png";

        try {
          Bitmap bitmap = Glide.with(mContext).load(image).asBitmap().into(100, 100).get();
          rv.setImageViewBitmap(R.id.small_like, bitmap);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }

      rv.setTextViewText(R.id.remote_title, buzz.getName());

      //rv.setImageViewResource(R.id.small_like,R.drawable.carbon);

      // rv.setFloat(R.id.remote_rating, "setRating", (float) buzz.getRating() / 10);
      //Glide.with(mContext).load()
      //if (!buzz.username.isEmpty()) {
      //  rv.setTextViewText(R.id.stackWidgetItemUsername, buzz.username);
      //}

      //rv.setTextViewText(R.id.stackWidgetItemContent, Html.fromHtml(buzz.content));
      // store the buzz ID in the extras so the main activity can use it Bundle extras = new Bundle(); extras.putString(HoneybuzzListActivity.EXTRA_ID, buzz.id);

      //Intent viewIntent = new Intent(context, HoneybuzzListActivity.class);
      //viewIntent.setAction(HoneybuzzListActivity.ACTION_VIEW);
      //viewIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
      //viewIntent.setData(Uri.parse(viewIntent.toUri(Intent.URI_INTENT_SCHEME)));
      //PendingIntent viewPendingIntent = PendingIntent.getActivity(context, 0, viewIntent, 0);
      //remoteViews.setPendingIntentTemplate(R.id.stackWidgetView, viewPendingIntent);
      // update widget
      Intent fillInIntent = new Intent();
      fillInIntent.putExtra(DetailActivity.EXTRA, buzz.getId());

      rv.setOnClickFillInIntent(R.id.item_click, fillInIntent);
    }
    return rv;
  }

  public RemoteViews getLoadingView() {
    return null;
  }

  public int getViewTypeCount() {
    return 1;
  }

  public long getItemId(int position) {
    return position;
  }

  public boolean hasStableIds() {
    return true;
  }

  public void onDataSetChanged() {
    mBuzzes = drinksRepository.getBookmarks();
  }
}
