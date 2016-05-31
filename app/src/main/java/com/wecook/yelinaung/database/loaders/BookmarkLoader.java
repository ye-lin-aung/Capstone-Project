package com.wecook.yelinaung.database.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import com.wecook.yelinaung.database.DrinkDbModel;
import com.wecook.yelinaung.database.DrinksRepository;
import java.util.List;

/**
 * Created by user on 5/31/16.
 */
public class BookmarkLoader extends AsyncTaskLoader<List<DrinkDbModel>>
    implements DrinksRepository.BookmarkObserver {
  private DrinksRepository drinksRepository;

  public BookmarkLoader(Context context, DrinksRepository drinksRepository) {
    super(context);
    this.drinksRepository = drinksRepository;
  }

  @Override protected void onReset() {
    onStopLoading();
    drinksRepository.removeBookmarkObserver(this);
  }

  @Override protected void onStartLoading() {
    deliverResult(drinksRepository.getBookmarks());
    drinksRepository.addBookmarkObserver(this);

    // When a change has  been delivered or the repository cache isn't available, we force
    // a load.

    forceLoad();
  }

  @Override protected void onStopLoading() {
    cancelLoad();
  }

  @Override public void deliverResult(List<DrinkDbModel> data) {
    if (isReset()) {
      return;
    }
    if (isStarted()) {
      super.deliverResult(data);
    }
  }

  @Override public void OnBookmarkChannged() {
    if (isStarted()) {
      forceLoad();
    }
  }

  @Override public List<DrinkDbModel> loadInBackground() {
    return drinksRepository.getBookmarks();
  }
}
