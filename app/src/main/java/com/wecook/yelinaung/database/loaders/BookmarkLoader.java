package com.wecook.yelinaung.database.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import com.wecook.yelinaung.database.DrinkDbModel;
import java.util.List;

/**
 * Created by user on 5/31/16.
 */
public class BookmarkLoader extends AsyncTaskLoader<List<DrinkDbModel>> {
  public BookmarkLoader(Context context) {
    super(context);
  }

  @Override protected void onReset() {
    onStopLoading();
    
  }

  @Override public List<DrinkDbModel> loadInBackground() {
    return null;
  }
}
