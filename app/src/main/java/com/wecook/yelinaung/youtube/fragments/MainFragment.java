package com.wecook.yelinaung.youtube.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wecook.yelinaung.R;
import com.wecook.yelinaung.network.DrinksService;
import javax.inject.Inject;
import retrofit2.Retrofit;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by user on 5/10/16.
 */
public class MainFragment extends Fragment implements MainContract.View {
  @Inject Retrofit retrofit;
  @Inject DrinksService drinksService;

  private View mainView;
  private MainContract.Presenter mPresenter;

  public View getMainView() {
    return mainView;
  }

  @Override public void setLoadingIndicator(boolean active) {

  }

  @Override public void setPresenter(MainContract.Presenter presenter) {
    mPresenter = checkNotNull(presenter);
  }

  @Override public void showNoDrinks() {

  }

  @Override public void showDrinkDetail() {

  }

  @Override public void showError() {

  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    mainView = inflater.inflate(R.layout.main_fragment, container, false);

    return mainView;
  }

  @Override public void onStart() {
    super.onStart();

    //((MyApp) getActivity().getApplication()).getDrinksComponent().inject(this);
    //Call<Drinks> drinksCall = drinksService.getDrinks(ApiKeyMap.getMap());
    //
    //drinksCall.enqueue(new Callback<Drinks>() {
    //  @Override public void onResponse(Call<Drinks> call, Response<Drinks> response) {

    //  }
    //
    //  @Override public void onFailure(Call<Drinks> call, Throwable t) {
    //
    //  }
    //});
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
  }

  public interface OnFragmentInteractionListener {
    void onFragmentInteraction(Object object);
  }
}
