package com.wecook.yelinaung.database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.List;

/**
 * Created by user on 5/21/16.
 */
public interface DrinksDatasource {
  interface GetDrinkCallback {
    void onDrinkDbLoaded(DrinkDbModel drinkDbModel);

    void noDrinkAvailable();
  }

  List<DrinkDbModel> searchDrinks(String query);


  List<DrinkDbModel> getDrinks();

  List<DrinkDbModel> getBookmarks();

  void saveBookmark(@NonNull DrinkDbModel drinkDbModel);

  @Nullable DrinkDbModel getDrink(@NonNull String drinkId);

  void saveDrinks(@NonNull DrinkDbModel drinkDbModel);

  void refreshDrinks();

  void deleteAllDrinks();

  void deleteDrink(@NonNull String drinkId);
}
