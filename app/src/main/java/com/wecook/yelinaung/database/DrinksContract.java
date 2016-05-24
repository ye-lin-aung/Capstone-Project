package com.wecook.yelinaung.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by user on 5/21/16.
 */
public class DrinksContract {
  public static final String DATABASE_NAME = "drinks";
  public static final int DATABASE_VERSION = 1;
  public static final String AUTHORITY = "com.wecook.yelinaung";
  public static final Uri BASE_URI = Uri.parse("content://" + AUTHORITY);

  public static abstract class DrinksEntry implements BaseColumns {
    public static final String TABLE_NAME = "drinks";
    public static final String ID = "id";
    public static final String RATING = "rating";
    public static final String NAME = "name";
    public static final String COLOR = "color";
    public static final String VIDEO = "video";
    public static final String BOOKMARK = "bookmark";
    public static final String DESCRIPTION = "description";
    public static final String PATH = "drinks";
    public static final Uri DRINKS_URI = BASE_URI.buildUpon().appendPath(PATH).build();
    public static final String ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/drink";
    public static final String DIR_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/drinks";
  }
}
