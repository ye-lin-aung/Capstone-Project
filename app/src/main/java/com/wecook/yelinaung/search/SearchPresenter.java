package com.wecook.yelinaung.search;

import android.support.v4.app.FragmentManager;
import com.wecook.yelinaung.R;
import com.wecook.yelinaung.database.DrinkDbModel;
import com.wecook.yelinaung.database.DrinksRepository;
import com.wecook.yelinaung.util.AnalyticManager;
import java.util.List;

/**
 * Created by user on 6/10/16.
 */
public class SearchPresenter implements SearchContract.Presenter, SearchFragment.SearchCallbacks {
  private FragmentManager fm;
  private SearchFragment searchFragment;
  private SearchContract.View view;
  private DrinksRepository drinksRepository;
  private final String TAG = "search_tag";

  public SearchPresenter(DrinksRepository drinksRepository, FragmentManager fragmentManager,
      SearchContract.View view) {
    this.drinksRepository = drinksRepository;
    this.fm = fragmentManager;

    this.view = view;
  }

  @Override public void start() {

  }

  @Override public void find(String query) {
    searchFragment = (SearchFragment) fm.findFragmentByTag(TAG);
    if (searchFragment != null) {
      fm.beginTransaction().remove(searchFragment).commit();
    }
    if (searchFragment == null) {
      searchFragment = SearchFragment.getInstance(query);
      fm.beginTransaction().add(searchFragment, TAG).commit();
    }
  }

  @Override public void processBookmarks(DrinkDbModel drinkDbModel, int position) {
    if (drinkDbModel.getBookmark() == 0) {
      drinkDbModel.setBookmark(1);
      view.setBookmark(drinkDbModel, position);
      drinksRepository.saveBookmark(drinkDbModel);

    } else {
      drinkDbModel.setBookmark(0);
      view.setBookmark(drinkDbModel, position);
      drinksRepository.saveBookmark(drinkDbModel);
    }
  }

  @Override public void onCancelled() {

  }

  @Override public void onPreExecute() {
    view.showLoadingIndicator(true);
  }

  @Override public void onPostExecute(List<DrinkDbModel> drinks) {
    List<DrinkDbModel> list = drinks;
    fm.beginTransaction().remove(searchFragment).commit();

    if (list != null && !list.isEmpty()) {
      view.showLoadingIndicator(false);
      view.showCantFind(true);
      view.showDrinks(list);
    } else {
      view.showCantFind(false);
    }
  }
}
