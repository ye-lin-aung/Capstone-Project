package com.wecook.yelinaung.bookmark;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import com.wecook.yelinaung.database.DrinkDbModel;
import com.wecook.yelinaung.database.DrinksRepository;
import com.wecook.yelinaung.database.loaders.BookmarkLoader;
import java.util.List;

/**
 * Created by user on 5/31/16.
 */
public class BookmarkPresenter
    implements BookmarkContract.Presenter, LoaderManager.LoaderCallbacks<List<DrinkDbModel>> {
  private final android.support.v4.app.LoaderManager loaderManager;
  private final DrinksRepository drinksRepository;
  private final BookmarkLoader drinksLoader;
  private final BookmarkContract.View mainView;
  private final int DRINKQUERY = 2;

  public BookmarkPresenter(android.support.v4.app.LoaderManager loaderManager,
      DrinksRepository drinksRepository, BookmarkLoader drinksLoader, BookmarkContract.View view) {
    this.loaderManager = loaderManager;
    this.drinksLoader = drinksLoader;
    this.drinksRepository = drinksRepository;
    this.mainView = view;
  }

  @Override public Loader onCreateLoader(int id, Bundle args) {
    mainView.setLoadingIndicator(true);
    return drinksLoader;
  }

  @Override public void processBookmarks(DrinkDbModel drinkDbModel, int position) {
    drinkDbModel.setBookmark(0);
    drinksRepository.saveBookmark(drinkDbModel);

  }

  @Override public void onLoadFinished(Loader<List<DrinkDbModel>> loader, List<DrinkDbModel> data) {
    if (data!=null && data.size() > 0) {
      mainView.setLoadingIndicator(false);
      mainView.showDrinks(data);
    } else {
      mainView.setLoadingIndicator(false);
      mainView.showNoDrinksView();
    }
  }

  @Override public void onLoaderReset(Loader loader) {

  }

  @Override public void paginateDrinks() {
  }

  @Override public void loadDrinks(boolean force) {
    mainView.setLoadingIndicator(true);
    drinksRepository.refreshBookmark();
  }

  @Override public void start() {
    loaderManager.initLoader(DRINKQUERY, null, this);
  }

  @Override public void result(int resultCode, int requestCode) {

  }
}
