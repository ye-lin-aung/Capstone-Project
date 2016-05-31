package com.wecook.yelinaung.youtube.fragments;

import com.wecook.BasePresenter;
import com.wecook.yelinaung.BaseView;
import com.wecook.yelinaung.database.DrinkDbModel;
import java.util.List;

/**
 * Created by user on 5/22/16.
 */
public interface MainContract {
  interface View extends BaseView<Presenter> {
    @Override void setPresenter(Presenter Presenter);

    void showNoDrinks();

    void showNoDrinksView();

    void setLoadingIndicator(boolean active);

    void showDrinkDetail();

    void showDrinks(List<DrinkDbModel> list);

    void showError();

    void setLiked(DrinkDbModel drinkDbModel, int position, boolean like);
  }

  interface Presenter extends BasePresenter {
    @Override void start();

    void processBookmarks(DrinkDbModel drinkDbModel, int position);

    void loadDrinks(boolean force);

    void paginateDrinks();

    void result(int resultCode, int requestCode);
  }
}
