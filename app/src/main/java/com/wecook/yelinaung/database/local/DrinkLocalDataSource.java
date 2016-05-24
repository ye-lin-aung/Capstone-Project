package com.wecook.yelinaung.database.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.wecook.yelinaung.database.DrinkDbModel;
import com.wecook.yelinaung.database.DrinksContract;
import com.wecook.yelinaung.database.DrinksDatasource;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.wecook.yelinaung.database.DrinksContract.DrinksEntry;

/**
 * Created by user on 5/21/16.
 */
public class DrinkLocalDataSource implements DrinksDatasource {
  private static DrinkLocalDataSource INSTANCE;
  private Context context;
  private final String[] PROJECTIONS = {
      DrinksEntry.ID, DrinksEntry.NAME, DrinksEntry.VIDEO, DrinksEntry.COLOR, DrinksEntry.RATING,
      DrinksEntry.BOOKMARK, DrinksEntry.DESCRIPTION
  };

  private DrinkLocalDataSource(@NonNull Context context) {
    checkNotNull(context);

    this.context = context;
  }

  public static DrinkLocalDataSource getInstance(@NonNull Context context) {
    if (INSTANCE == null) {
      INSTANCE = new DrinkLocalDataSource(context);
    }
    return INSTANCE;
  }

  @Override public void deleteAllDrinks() {
    context.getContentResolver().delete(DrinksContract.DrinksEntry.DRINKS_URI, null, null);
  }

  @Override public void deleteDrink(@NonNull String drinkId) {
    context.getContentResolver()
        .delete(DrinksEntry.DRINKS_URI, DrinksEntry.ID + " == " + drinkId, PROJECTIONS);
  }

  @Nullable @Override public DrinkDbModel getDrink(@NonNull String drinkId) {

    Cursor cursor = context.getContentResolver()
        .query(DrinksEntry.DRINKS_URI, PROJECTIONS, drinkId + " == " + DrinksEntry.ID,
            new String[] {}, "asc");

    DrinkDbModel drinkDbModel = null;
    if (cursor != null && cursor.getCount() > 0) {
      cursor.moveToFirst();
      String id = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.ID));
      String name = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.NAME));
      String description = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.DESCRIPTION));
      String video = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.VIDEO));
      int rating = cursor.getInt(cursor.getColumnIndexOrThrow(DrinksEntry.RATING));
      int bookmark = cursor.getInt(cursor.getColumnIndexOrThrow(DrinksEntry.BOOKMARK));
      String color = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.COLOR));
      drinkDbModel = new DrinkDbModel(bookmark, color, description, id, name, rating, video);
    }
    if (cursor != null) {
      cursor.close();
    }

    return drinkDbModel;
  }

  @Override public List<DrinkDbModel> getDrinks() {
    List<DrinkDbModel> drinks = new ArrayList<DrinkDbModel>();
    Cursor cursor =
        context.getContentResolver().query(DrinksEntry.DRINKS_URI, PROJECTIONS, null, null, "");
    if (cursor != null && cursor.getCount() > 0) {
      while (cursor.moveToNext()) {
        DrinkDbModel drinkDbModel = null;
        String id = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.NAME));
        String description =
            cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.DESCRIPTION));
        String video = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.VIDEO));
        int rating = cursor.getInt(cursor.getColumnIndexOrThrow(DrinksEntry.RATING));
        int bookmark = cursor.getInt(cursor.getColumnIndexOrThrow(DrinksEntry.BOOKMARK));
        String color = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.COLOR));
        drinkDbModel = new DrinkDbModel(bookmark, color, description, id, name, rating, video);
        drinks.add(drinkDbModel);
      }
      if (cursor != null) {
        cursor.close();
      }
    }
    return drinks;
  }

  @Override public void refreshDrinks() {

  }

  @Override public void saveDrinks(@NonNull DrinkDbModel drinkDbModel) {
    checkNotNull(drinkDbModel);
    ContentValues contentValues = new ContentValues();
    if (drinkDbModel != null) {
      contentValues.put(DrinksContract.DrinksEntry.ID, drinkDbModel.getId());
      contentValues.put(DrinksContract.DrinksEntry.COLOR, drinkDbModel.getColor());
      contentValues.put(DrinksContract.DrinksEntry.NAME, drinkDbModel.getName());
      contentValues.put(DrinksContract.DrinksEntry.DESCRIPTION, drinkDbModel.getDescription());
      contentValues.put(DrinksContract.DrinksEntry.VIDEO, drinkDbModel.getVideo());
      contentValues.put(DrinksContract.DrinksEntry.RATING, drinkDbModel.getRating());
      contentValues.put(DrinksEntry.BOOKMARK, 0);
      if (context.getContentResolver()
          .update(DrinksEntry.DRINKS_URI, contentValues,
              DrinksEntry.ID + " = '" + drinkDbModel.getId()+"'", null) <= 0) {
        context.getContentResolver().insert(DrinksContract.DrinksEntry.DRINKS_URI, contentValues);
      }
    }
    //} else {
    //context.getContentResolver()
    //    .insert(DrinksEntry.DRINKS_URI, contentValues, DrinksEntry.ID + "=" + drinkDbModel.getId(),
    //        null);
    // }
  }
}
