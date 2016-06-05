package com.wecook.yelinaung.database.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import com.wecook.yelinaung.database.DrinkDbModel;
import com.wecook.yelinaung.database.DrinksRepository;

/**
 * Created by user on 6/4/16.
 */
public class DrinkLoader extends AsyncTaskLoader<DrinkDbModel>
    implements DrinksRepository.DrinkRepoObserver {
  private DrinksRepository drinksRepository;
  private String drinkId;

  public DrinkLoader(Context context, String drinkId, DrinksRepository drinksRepository) {
    super(context);
    this.drinkId = drinkId;
    this.drinksRepository = drinksRepository;
  }

  @Override public DrinkDbModel loadInBackground() {
    return drinksRepository.getDrink(drinkId);
  }

  @Override public void deliverResult(DrinkDbModel data) {
    if (isReset()) {
      return;
    }
    if (isStarted()) {
      super.deliverResult(data);
    }
  }

  @Override protected void onStartLoading() {

    // Deliver any previously loaded data immediately if available.
    if (drinksRepository.cachedTasksAvailable()) {
      deliverResult(drinksRepository.getCachedDrink(drinkId));
    }

    // Begin monitoring the underlying data source.
    drinksRepository.registerObserver(this);

    if (takeContentChanged() || !drinksRepository.cachedTasksAvailable()) {
      // When a change has  been delivered or the repository cache isn't available, we force
      // a load.
      forceLoad();
    }
  }

  @Override protected void onReset() {
    onStopLoading();
    drinksRepository.unRegisterObserver(this);
    super.onReset();
  }

  @Override public void OnDrinksChanged() {
    if (isStarted()) {
      forceLoad();
    }
  }
}
