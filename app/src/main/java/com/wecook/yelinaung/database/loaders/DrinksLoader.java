package com.wecook.yelinaung.database.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import com.wecook.yelinaung.database.DrinkDbModel;
import com.wecook.yelinaung.database.DrinksRepository;
import java.util.List;

/**
 * Created by user on 5/21/16.
 */
public class DrinksLoader extends AsyncTaskLoader<List<DrinkDbModel>>
    implements DrinksRepository.DrinkRepoObserver {
  private DrinksRepository drinksRepository;

  public DrinksLoader(Context context, DrinksRepository drinksRepository) {
    super(context);
    this.drinksRepository = drinksRepository;
  }

  @Override public void deliverResult(List<DrinkDbModel> data) {

    if (isReset()) {
      return;
    }
    if (isStarted()) {
      super.deliverResult(data);
    }
  }

  @Override protected void onStartLoading() {
    if (drinksRepository.cachedTasksAvailable()) {
      deliverResult(drinksRepository.getCached());
    }

    drinksRepository.registerObserver(this);

    if (takeContentChanged() || !drinksRepository.cachedTasksAvailable()) {
      // When a change has  been delivered or the repository cache isn't available, we force
      // a load.

      forceLoad();
    }
  }

  @Override protected void onStopLoading() {
    cancelLoad();
  }

  @Override protected void onReset() {
    onStopLoading();
    drinksRepository.unRegisterObserver(this);
  }

  @Override public List<DrinkDbModel> loadInBackground() {
    return drinksRepository.getDrinks();
  }

  @Override public void OnDrinksChanged() {
    if (isStarted()) {
      forceLoad();
    }
  }
}
