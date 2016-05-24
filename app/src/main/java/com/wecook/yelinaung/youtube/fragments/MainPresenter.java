package com.wecook.yelinaung.youtube.fragments;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import com.wecook.yelinaung.database.DrinkDbModel;
import com.wecook.yelinaung.database.DrinksRepository;
import com.wecook.yelinaung.database.loaders.DrinksLoader;
import java.util.List;

/**
 * Created by user on 5/24/16.
 */
public class MainPresenter
    implements MainContract.Presenter, LoaderManager.LoaderCallbacks<List<DrinkDbModel>> {

  private final LoaderManager loaderManager;
  private final DrinksRepository drinksRepository;
  private final DrinksLoader drinksLoader;
  private final MainContract.View mainView;
  private final int DRINKQUERY = 1;

  public MainPresenter(LoaderManager loaderManager, DrinksRepository drinksRepository,
      DrinksLoader drinksLoader, MainContract.View view) {
    this.loaderManager = loaderManager;
    this.drinksLoader = drinksLoader;
    this.drinksRepository = drinksRepository;
    this.mainView = view;
  }

  @Override public Loader onCreateLoader(int id, Bundle args) {
    return drinksLoader;
  }

  @Override public void onLoadFinished(Loader<List<DrinkDbModel>> loader, List<DrinkDbModel> data) {
    mainView.showDrinks(data);
  }

  @Override public void onLoaderReset(Loader loader) {

  }

  @Override public void loadDrinks(boolean force) {

  }

  @Override public void start() {
    loaderManager.initLoader(DRINKQUERY, null, this);
  }

  @Override public void result(int resultCode, int requestCode) {

  }
}
