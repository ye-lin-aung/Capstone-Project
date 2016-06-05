package com.wecook.yelinaung.detail;

import com.wecook.BasePresenter;
import com.wecook.yelinaung.BaseView;

/**
 * Created by user on 6/4/16.
 */
public interface DetailContract {
  public interface View extends BaseView<Presenter> {
    @Override void setPresenter(Presenter Presenter);
  }

  public interface Presenter extends BasePresenter {
    @Override void start();
  }
}
