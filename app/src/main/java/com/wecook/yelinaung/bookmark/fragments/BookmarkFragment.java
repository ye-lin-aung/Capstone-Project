package com.wecook.yelinaung.bookmark.fragments;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.wecook.yelinaung.Injection;
import com.wecook.yelinaung.R;
import com.wecook.yelinaung.bookmark.BookmarkContract;
import com.wecook.yelinaung.bookmark.BookmarkPresenter;
import com.wecook.yelinaung.bookmark.adapter.BookmarkRecyclerAdapter;
import com.wecook.yelinaung.database.DrinkDbModel;
import com.wecook.yelinaung.database.DrinksRepository;
import com.wecook.yelinaung.database.loaders.BookmarkLoader;
import com.wecook.yelinaung.databinding.FragmentBookmarkBinding;
import com.wecook.yelinaung.events.onBookmarkedEvent;
import com.wecook.yelinaung.font.CustomFont;
import java.util.ArrayList;
import java.util.List;
import jp.wasabeef.recyclerview.adapters.AnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by user on 5/31/16.
 */
public class BookmarkFragment extends Fragment
    implements BookmarkContract.View, SwipeRefreshLayout.OnRefreshListener,
    BookmarkRecyclerAdapter.onItemEvent {
  private FragmentBookmarkBinding fragmentBookmarkBinding;
  private LinearLayout linearLayout;
  private TextView title;
  private BookmarkContract.Presenter mPresenter;
  private SwipeRefreshLayout swipeRefreshLayout;
  private static BookmarkFragment INSTANCE;
  private RecyclerView recyclerView;
  private DrinksRepository repository;
  private BookmarkRecyclerAdapter adapter;
  private Bus bus = com.wecook.yelinaung.events.Bus.getInstance();

  public static BookmarkFragment getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new BookmarkFragment();
    }
    return INSTANCE;
  }

  @Subscribe public void onBookmarkChange(onBookmarkedEvent obe) {
    mPresenter.loadDrinks(true);
  }

  @Override public void showDrinks(List<DrinkDbModel> list) {
    recyclerView.setVisibility(View.VISIBLE);
    linearLayout.setVisibility(View.GONE);
    title.setVisibility(View.GONE);
    adapter.noAnimationAddList(list);
  }

  @Override public void onBookmark(View v, int position) {

    mPresenter.processBookmarks(adapter.getItemAtPosition(position), position);
    adapter.unBookmarkItems(position);
  }

  @Override public void setLoadingIndicator(boolean active) {
    if (swipeRefreshLayout.isRefreshing() != active) {

      swipeRefreshLayout.setRefreshing(active);
    }
  }

  @Override public void onItemClick(View v, int position) {

  }

  @Override public void onLongPressed(View v, int position) {

  }

  @Override public void setPresenter(BookmarkContract.Presenter presenter) {
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
    adapter = new BookmarkRecyclerAdapter(new ArrayList<DrinkDbModel>(0));
  }

  @Override public void onRefresh() {
    mPresenter.loadDrinks(true);
  }

  public void prepareSwipe(View rootView) {
    swipeRefreshLayout = fragmentBookmarkBinding.mainSwipe;
    swipeRefreshLayout.setOnRefreshListener(this);
    swipeRefreshLayout.setColorSchemeResources(R.color.blue_light, R.color.color_red,
        R.color.colorAccent);
  }

  @Override public void showNoDrinksView() {
    recyclerView.setVisibility(View.GONE);
    linearLayout.setVisibility(View.VISIBLE);
    title.setVisibility(View.VISIBLE);
    fragmentBookmarkBinding.errorCloud.setVisibility(View.VISIBLE);
  }

  public void prepareRecycler(View rootView) {
    recyclerView = fragmentBookmarkBinding.recycler;

    recyclerView.setItemAnimator(new FadeInUpAnimator());
    recyclerView.getItemAnimator().setAddDuration(500);
    recyclerView.getItemAnimator().setRemoveDuration(500);
    recyclerView.getItemAnimator().setMoveDuration(500);
    recyclerView.getItemAnimator().setChangeDuration(500);

    int columCount = getContext().getResources().getInteger(R.integer.recycler_item_count);
    GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), columCount);
    recyclerView.setLayoutManager(checkNotNull(gridLayoutManager));
    adapter.setItemEvent(this);
    AnimationAdapter adapter2 = new SlideInBottomAnimationAdapter(adapter);
    adapter2.setFirstOnly(true);
    adapter2.setDuration(500);
    adapter2.setInterpolator(new OvershootInterpolator(0.5f));
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(adapter);
  }

  private void showMessage(String message) {
    Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_bookmark, container, false);
    fragmentBookmarkBinding = DataBindingUtil.bind(rootView);
    linearLayout = fragmentBookmarkBinding.errorContainer;
    title = fragmentBookmarkBinding.title;

    ViewCompat.setElevation(fragmentBookmarkBinding.errorCloud,
        getResources().getDimension(R.dimen.error_cloud_elevation));
    title.setTypeface(CustomFont.getInstance().getFont("medium"));
    prepareSwipe(rootView);
    prepareRecycler(rootView);
    repository = Injection.provideDrinkRepo(getActivity().getApplicationContext());
    BookmarkLoader drinksLoader = new BookmarkLoader(getContext(), repository);
    mPresenter = new BookmarkPresenter(getLoaderManager(), repository, drinksLoader, this);
    Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
    fragmentBookmarkBinding.errorCloud.startAnimation(animation);
    swipeRefreshLayout.measure(rootView.getMeasuredWidth(), rootView.getMeasuredHeight());
    linearLayout.setOnClickListener((V) -> {
      if (!swipeRefreshLayout.isRefreshing()) {
        mPresenter.loadDrinks(true);
      }
    });
    return rootView;
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
  }

  @Override public void onStart() {
    super.onStart();
  }

  @Override public void onResume() {
    super.onResume();
    bus.register(this);
    mPresenter.start();
  }

  @Override public void onPause() {
    bus.unregister(this);
    super.onPause();
  }

  @Override public void onDestroy() {
    mPresenter = null;
    super.onDestroy();
  }

  public interface OnFragmentInteractionListener {
    void onFragmentInteraction(Object object);
  }
}
