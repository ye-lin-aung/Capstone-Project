package com.wecook.yelinaung.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

/**
 * Created by user on 5/21/16.
 */
public class DrinksDb extends SQLiteOpenHelper {

  private static final String TEXT_TYPE = " TEXT";
  private static final String[] COLUMNS = {
      DrinksContract.DrinksEntry.NAME, DrinksContract.DrinksEntry.VIDEO,
      DrinksContract.DrinksEntry.DESCRIPTION, DrinksContract.DrinksEntry.COLOR,
      DrinksContract.DrinksEntry.ACTIONS, DrinksContract.DrinksEntry.TOOLS,
      DrinksContract.DrinksEntry.INGREDIANTS, DrinksContract.DrinksEntry.OCCASIONS,
      DrinksContract.DrinksEntry.SKILLS, DrinksContract.DrinksEntry.TASTES,
      DrinksContract.DrinksEntry.SERVEIN
  };

  private final String INTEGER_TYPE = " INTEGER";

  public DrinksDb(Context context) {
    super(context, DrinksContract.DATABASE_NAME, null, DrinksContract.DATABASE_VERSION);
  }

  @Override public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL(BuildDrinks_SQL());
  }

  @Override public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DrinksContract.DrinksEntry.TABLE_NAME);
  }

  @NonNull public final String BuildDrinks_SQL() {
    StringBuilder stringBuilder = new StringBuilder("CREATE TABLE ");
    stringBuilder.append(DrinksContract.DrinksEntry.TABLE_NAME);
    stringBuilder.append("(");
    stringBuilder.append(DrinksContract.DrinksEntry._ID);
    stringBuilder.append(INTEGER_TYPE);
    stringBuilder.append(" NOT NULL PRIMARY KEY AUTOINCREMENT , ");
    stringBuilder.append(DrinksContract.DrinksEntry.ID);
    stringBuilder.append(TEXT_TYPE);
    stringBuilder.append(",");
    stringBuilder.append(DrinksContract.DrinksEntry.IS_HOT);
    stringBuilder.append(INTEGER_TYPE);
    stringBuilder.append(",");
    stringBuilder.append(DrinksContract.DrinksEntry.IS_CARBONATED);
    stringBuilder.append(INTEGER_TYPE);
    stringBuilder.append(",");
    stringBuilder.append(DrinksContract.DrinksEntry.ISALCOHOLIC);
    stringBuilder.append(INTEGER_TYPE);
    stringBuilder.append(",");
    stringBuilder.append(DrinksContract.DrinksEntry.RATING);
    stringBuilder.append(INTEGER_TYPE);
    stringBuilder.append(",");

    for (int i = 0; i < COLUMNS.length; i++) {
      stringBuilder.append(COLUMNS[i]);
      stringBuilder.append(", ");
    }

    stringBuilder.append(DrinksContract.DrinksEntry.BOOKMARK);
    stringBuilder.append(" INTEGER ");
    stringBuilder.append(");");
    return stringBuilder.toString();
  }
}
