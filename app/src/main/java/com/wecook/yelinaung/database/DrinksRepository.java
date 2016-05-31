package com.wecook.yelinaung.database;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.wecook.yelinaung.database.local.DrinkLocalDataSource;
import com.wecook.yelinaung.database.remote.DrinksRemoteDataSource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by user on 5/21/16.
 */
public class DrinksRepository implements DrinksDatasource {

  private static DrinksRepository INSTANCE = null;
  private DrinkLocalDataSource mDrinkLocalDataSource;
  private DrinksRemoteDataSource mDrinkRemoteDataSource;
  private List<DrinkRepoObserver> drinkOb = new ArrayList<DrinkRepoObserver>();
  Map<String, DrinkDbModel> mCachedTasks = null;
  Map<String, DrinkDbModel> mCachedBookmarks = null;
  boolean mBookmarksIsDirty;
  private List<BookmarkObserver> obList = new ArrayList<BookmarkObserver>();
  boolean mCachedIsDirty;

  public static DrinksRepository getInstance(DrinkLocalDataSource drinkLocalDataSource,
      DrinksRemoteDataSource drinkRemoteDataSource) {
    if (INSTANCE == null) {
      INSTANCE = new DrinksRepository(drinkLocalDataSource, drinkRemoteDataSource);
    }
    return INSTANCE;
  }

  private DrinksRepository(@NonNull DrinkLocalDataSource tasksRemoteDataSource,
      @NonNull DrinksRemoteDataSource tasksLocalDataSource) {
    mDrinkLocalDataSource = checkNotNull(tasksRemoteDataSource);
    mDrinkRemoteDataSource = checkNotNull(tasksLocalDataSource);
  }

  public void registerObserver(DrinkRepoObserver drinkRepoObserver) {
    if (!drinkOb.contains(drinkRepoObserver)) {
      drinkOb.add(drinkRepoObserver);
    }
  }

  public void destroyInstance() {
    if (INSTANCE != null) {
      INSTANCE = null;
    }
  }

  public void notifyObservers() {
    for (DrinkRepoObserver respo : drinkOb) {
      respo.OnDrinksChanged();
    }
  }

  public void unRegisterObserver(DrinkRepoObserver drinkRepoObserver) {
    if (drinkOb.contains(drinkRepoObserver)) {
      drinkOb.remove(drinkRepoObserver);
    }
  }

  @Override public void deleteAllDrinks() {
    mDrinkLocalDataSource.deleteAllDrinks();
    mDrinkRemoteDataSource.deleteAllDrinks();
  }

  @Override public List<DrinkDbModel> getBookmarks() {

    List<DrinkDbModel> drinkDbModels = null;
    // if (!mBookmarksIsDirty) {

    drinkDbModels = checkNotNull(mDrinkLocalDataSource.getBookmarks());
    // }
    //if (drinkDbModels == null || drinkDbModels.isEmpty()) {
    //  // Grab remote data if cache is dirty or local data not available.
    //  drinkDbModels = checkNotNull(mDrinkLocalDataSource.getBookmarks());
    //
    //  // We copy the data to the device so we don't need to query the network next time
    //
    //  saveDrinksInLocalDataSource(drinkDbModels);
    //}
    //processLoadedBookmarks(drinkDbModels);

    return drinkDbModels;
  }

  @Override public List<DrinkDbModel> getDrinks() {

    List<DrinkDbModel> drinkDbModels = null;
    if (!mCachedIsDirty) {
      if (mCachedTasks != null) {
        drinkDbModels = getCached();
        return drinkDbModels;
      } else {
        drinkDbModels = mDrinkLocalDataSource.getDrinks();
      }
    }
    if (drinkDbModels == null || drinkDbModels.isEmpty()) {
      // Grab remote data if cache is dirty or local data not available.
      drinkDbModels = mDrinkRemoteDataSource.getDrinks();

      // We copy the data to the device so we don't need to query the network next time

      saveDrinksInLocalDataSource(drinkDbModels);
    }

    processLoadedDrinks(drinkDbModels);
    return getCached();
  }

  private void processLoadedDrinks(List<DrinkDbModel> drinkDbModels) {
    if (drinkDbModels == null) {
      mCachedTasks = null;
      mCachedIsDirty = false;
      return;
    }
    if (mCachedTasks == null) {
      mCachedTasks = new LinkedHashMap<>();
    }
    for (DrinkDbModel dk : drinkDbModels) {
      mCachedTasks.put(dk.getId(), dk);
    }
    mCachedIsDirty = false;
  }

  private void processLoadedBookmarks(List<DrinkDbModel> drinkDbModels) {
    if (drinkDbModels == null) {
      mCachedBookmarks = null;
      mBookmarksIsDirty = false;
      return;
    }
    if (mCachedBookmarks == null) {
      mCachedBookmarks = new LinkedHashMap<>();
    }
    for (DrinkDbModel dk : drinkDbModels) {
      mCachedBookmarks.put(dk.getId(), dk);
    }
    mBookmarksIsDirty = false;
  }

  private void saveDrinksInLocalDataSource(List<DrinkDbModel> list) {
    if (list != null) {
      for (DrinkDbModel drinkDbModel : list) {
        mDrinkLocalDataSource.saveDrinks(drinkDbModel);
      }
    }
  }

  public List<DrinkDbModel> getCachedBookmark() {
    Log.d("DATA", mCachedBookmarks + "SS");
    return mCachedBookmarks == null ? null : new ArrayList<>(mCachedBookmarks.values());
  }

  public List<DrinkDbModel> getCached() {

    return mCachedTasks == null ? null : new ArrayList<>(mCachedTasks.values());
  }

  @Nullable @Override public DrinkDbModel getDrink(@NonNull String drinkId) {
    DrinkDbModel drinkDbModel = getDrinkWithId(drinkId);
    if (drinkDbModel != null) {
      return drinkDbModel;
    }
    DrinkDbModel data = mDrinkLocalDataSource.getDrink(drinkId);
    if (data == null) {
      data = mDrinkRemoteDataSource.getDrink(drinkId);
    }
    return data;
  }

  @Nullable private DrinkDbModel getDrinkWithId(@NonNull String id) {
    checkNotNull(id);
    if (mCachedTasks == null || mCachedTasks.isEmpty()) {
      return null;
    } else {
      return mCachedTasks.get(id);
    }
  }

  //This was never called
  @Override public void saveDrinks(@NonNull DrinkDbModel drinkDbModel) {
    checkNotNull(drinkDbModel);
    mDrinkRemoteDataSource.saveDrinks(drinkDbModel);
    mDrinkLocalDataSource.saveDrinks(drinkDbModel);

    // Do in memory cache update to keep the app UI up to date
    if (mCachedTasks == null) {
      mCachedTasks = new LinkedHashMap<>();
    }
    mCachedTasks.put(drinkDbModel.getId(), drinkDbModel);

    // Update the UI
    notifyObservers();
  }

  @Override public void saveBookmark(@NonNull DrinkDbModel drinkDbModel) {
    mDrinkLocalDataSource.saveBookmark(drinkDbModel);
    if (mCachedTasks == null) {
      mCachedTasks = new LinkedHashMap<>();
    }
    mCachedTasks.put(drinkDbModel.getId(), drinkDbModel);
    notifyObservers();
    notifyAllBookmarkObservers();
  }

  public boolean cachedBookmarkAvailable() {
    return mCachedBookmarks != null && !mBookmarksIsDirty;
  }

  public boolean cachedTasksAvailable() {
    return mCachedTasks != null && !mCachedIsDirty;
  }

  @Override public void refreshDrinks() {
    mCachedIsDirty = true;
    notifyObservers();
  }

  public void refreshBookmark() {
    mBookmarksIsDirty = true;
    notifyAllBookmarkObservers();
  }

  public void notifyAllBookmarkObservers() {
    for (BookmarkObserver bo : obList) {
      bo.OnBookmarkChannged();
    }
  }

  public void addBookmarkObserver(BookmarkObserver bookmarkObserver) {
    if (!obList.contains(bookmarkObserver)) {
      obList.add(bookmarkObserver);
    }
  }

  public void removeBookmarkObserver(BookmarkObserver bookmarkObserver) {
    if (obList.contains(bookmarkObserver)) {
      obList.remove(bookmarkObserver);
    }
  }

  @Override public void deleteDrink(@NonNull String drinkId) {
    mDrinkRemoteDataSource.deleteDrink(checkNotNull(drinkId));
    mDrinkLocalDataSource.deleteDrink(checkNotNull(drinkId));
    mCachedTasks.remove(drinkId);
    notifyObservers();
  }

  public interface DrinkRepoObserver {
    void OnDrinksChanged();
  }

  public interface BookmarkObserver {
    void OnBookmarkChannged();
  }
}
