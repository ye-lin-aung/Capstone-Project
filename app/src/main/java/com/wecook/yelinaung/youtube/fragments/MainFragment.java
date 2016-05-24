package com.wecook.yelinaung.youtube.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.common.base.Preconditions;
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
public class MainFragment extends Fragment implements MainContract.View {
  //@BindView(R.id.recycler)
  RecyclerView recyclerView;
  private MainContract.Presenter mPresenter;
  private DrinksRepository repository;
  private MainRecyclerAdapter adapter;

  @Override public void showDrinks(List<DrinkDbModel> list) {
    adapter.replaceList(list);
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

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    adapter = new MainRecyclerAdapter(new ArrayList<DrinkDbModel>(0));
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.main_fragment, container, false);
    recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
    repository = Injection.provideDrinkRepo(getActivity().getApplicationContext());
    DrinksLoader drinksLoader = new DrinksLoader(getContext(), repository);
    mPresenter = new MainPresenter(getLoaderManager(), repository, drinksLoader, this);
    int columCount = getContext().getResources().getInteger(R.integer.recycler_item_count);
    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), columCount);
    recyclerView.setLayoutManager(Preconditions.checkNotNull(gridLayoutManager));
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(adapter);
    return rootView;
  }

  @Override public void onStart() {
    super.onStart();
    mPresenter.start();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
  }

  public interface OnFragmentInteractionListener {
    void onFragmentInteraction(Object object);
  }
}
