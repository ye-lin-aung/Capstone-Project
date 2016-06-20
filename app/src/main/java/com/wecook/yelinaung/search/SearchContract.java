package com.wecook.yelinaung.search;

import com.wecook.BasePresenter;
import com.wecook.yelinaung.BaseView;
import com.wecook.yelinaung.database.DrinkDbModel;
import java.util.List;

/**
 * Created by user on 6/10/16.
 */
public interface SearchContract {
  interface View extends BaseView<Presenter> {
    @Override void setPresenter(Presenter Presenter);

    void showLoadingIndicator(boolean loading);

    void showDrinks(List<DrinkDbModel> list);

    void showCantFind(boolean found);

    String getSearch();

    void setBookmark(DrinkDbModel dbModel,int position);
  }

  interface Presenter extends BasePresenter {
    @Override void start();

    void find(String query);

    void processBookmarks(DrinkDbModel drinkDbModel, int position);
  }
}
