package com.wecook.yelinaung.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import static com.wecook.yelinaung.database.DrinksContract.DrinksEntry;

/**
 * Created by user on 5/21/16.
 */
public class DrinksContentProvider extends ContentProvider {

  public static final int DRINK_LIST = 101;
  public static final int DRINK_ITEM = 100;
  public static final UriMatcher uriMatcher;
  public static DrinksDb database;

  static {
    uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    uriMatcher.addURI(DrinksContract.AUTHORITY, DrinksEntry.PATH, DRINK_LIST);
    uriMatcher.addURI(DrinksContract.AUTHORITY, DrinksEntry.PATH+"/#", DRINK_ITEM);
  }

  @Override public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
    SQLiteDatabase sqLiteDatabase = database.getWritableDatabase();
    int count = 0;
    switch (uriMatcher.match(uri)) {
      case DRINK_ITEM:
        throw new UnsupportedOperationException("No Such URI");

      case DRINK_LIST:
        count = sqLiteDatabase.update(DrinksEntry.TABLE_NAME, contentValues, s, strings);
        break;
    }
    getContext().getContentResolver().notifyChange(uri, null);

    return count;
  }

  @Nullable @Override
  public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
    Cursor cursor;
    SQLiteDatabase sqLiteDatabase = database.getReadableDatabase();
    switch (uriMatcher.match(uri)) {
      case DRINK_ITEM:
        cursor = sqLiteDatabase.query(DrinksEntry.TABLE_NAME, strings, s, strings1, null, null, s1);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
      case DRINK_LIST:
        cursor = sqLiteDatabase.query(DrinksEntry.TABLE_NAME, strings, s, strings1, null, null, s1);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }
    return null;
  }

  @Override public boolean onCreate() {
    database = new DrinksDb(getContext());
    return true;
  }

  @Nullable @Override public Uri insert(Uri uri, ContentValues contentValues) {
    if (uriMatcher.match(uri) != DRINK_LIST) {
      throw new UnsupportedOperationException("No Such URI");
    } else {
      ContentValues contentValues1;
      if (contentValues != null) {
        contentValues1 = contentValues;
      } else {
        contentValues1 = new ContentValues();
      }
      SQLiteDatabase db = database.getWritableDatabase();
      long count = db.insert(DrinksEntry.TABLE_NAME, null, contentValues1);
      if (count > 0) {
        Uri uri1 = ContentUris.withAppendedId(DrinksEntry.DRINKS_URI, count);
        return uri1;
      }
    }
    return null;
  }

  @Nullable @Override public String getType(Uri uri) {
    switch (uriMatcher.match(uri)) {
      case DRINK_LIST:
        return DrinksEntry.DIR_TYPE;
      case DRINK_ITEM:
        return DrinksEntry.ITEM_TYPE;
      default:
        throw new UnsupportedOperationException("No such uri type");
    }
  }

  @Override public int delete(Uri uri, String s, String[] strings) {

    SQLiteDatabase database = DrinksContentProvider.database.getWritableDatabase();
    switch (uriMatcher.match(uri)) {
      case DRINK_LIST:
        database.delete(DrinksEntry.TABLE_NAME, s, strings);
        break;
      case DRINK_ITEM:
        s = s + DrinksEntry._ID + " = " + uri.getLastPathSegment();
        break;
    }
    int rows = database.delete(DrinksEntry.TABLE_NAME, s, strings);
    getContext().getContentResolver().notifyChange(uri, null);
    return rows;
  }
}
