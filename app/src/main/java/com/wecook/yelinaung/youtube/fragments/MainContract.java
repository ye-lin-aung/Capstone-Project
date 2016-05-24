package com.wecook.yelinaung.youtube.fragments;

import com.wecook.BasePresenter;
import com.wecook.yelinaung.BaseView;

/**
 * Created by user on 5/22/16.
 */
public interface MainContract {
  interface View extends BaseView<Presenter> {
    @Override void setPresenter(Presenter Presenter);

    void showNoDrinks();

    void setLoadingIndicator(boolean active);

    void showDrinkDetail();

    void showError();
  }

  interface Presenter extends BasePresenter {
    @Override void start();

    void loadDrinks(boolean force);

    void result(int resultCode, int requestCode);
  }
}
