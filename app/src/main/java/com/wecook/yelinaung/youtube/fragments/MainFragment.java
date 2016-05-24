package com.wecook.yelinaung.youtube.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wecook.yelinaung.Injection;
import com.wecook.yelinaung.R;
import com.wecook.yelinaung.database.DrinkDbModel;
import com.wecook.yelinaung.database.DrinksRepository;
import com.wecook.yelinaung.database.loaders.DrinksLoader;
import com.wecook.yelinaung.youtube.adapters.MainRecyclerAdapter;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by user on 5/10/16.
 */
public class MainFragment extends Fragment
    implements MainContract.View, SwipeRefreshLayout.OnRefreshListener {
  //@BindView(R.id.recycler)
  RecyclerView recyclerView;
  SwipeRefreshLayout swipeRefreshLayout;
  private MainContract.Presenter mPresenter;
  private DrinksRepository repository;
  private MainRecyclerAdapter adapter;

  @Override public void showDrinks(List<DrinkDbModel> list) {
    adapter.replaceList(list);
  }

  @Override public void setLoadingIndicator(boolean active) {
    if (swipeRefreshLayout.isRefreshing() != active) {
      swipeRefreshLayout.setRefreshing(active);
    }
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

  @Override public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    adapter = new MainRecyclerAdapter(new ArrayList<DrinkDbModel>(0));
  }

  @Override public void onRefresh() {
    mPresenter.loadDrinks(true);
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.main_fragment, container, false);
    recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
    swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.main_swipe);
    swipeRefreshLayout.setOnRefreshListener(this);
    repository = Injection.provideDrinkRepo(getActivity().getApplicationContext());
    DrinksLoader drinksLoader = new DrinksLoader(getContext(), repository);
    mPresenter = new MainPresenter(getLoaderManager(), repository, drinksLoader, this);
    int columCount = getContext().getResources().getInteger(R.integer.recycler_item_count);
    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), columCount);
    recyclerView.setLayoutManager(checkNotNull(gridLayoutManager));
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(adapter);
    return rootView;
  }

  @Override public void onStart() {
    super.onStart();
  }

  @Override public void onResume() {
    super.onResume();
    mPresenter.start();
  }

  @Override public void onDestroy() {
    mPresenter = null;
    super.onDestroy();
  }

  public interface OnFragmentInteractionListener {
    void onFragmentInteraction(Object object);
  }
}
