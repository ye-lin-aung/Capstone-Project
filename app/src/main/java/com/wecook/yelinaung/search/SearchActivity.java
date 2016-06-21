package com.wecook.yelinaung.search;

import android.app.SearchManager;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import com.google.common.base.Preconditions;
import com.squareup.otto.Bus;
import com.wecook.yelinaung.Injection;
import com.wecook.yelinaung.R;
import com.wecook.yelinaung.database.DrinkDbModel;
import com.wecook.yelinaung.database.SearchSuggestProvider;
import com.wecook.yelinaung.databinding.ActivitySearchBinding;
import com.wecook.yelinaung.detail.DetailActivity;
import com.wecook.yelinaung.events.onBookmarkedEvent;
import com.wecook.yelinaung.font.CustomFont;
import com.wecook.yelinaung.util.AnalyticManager;
import java.util.ArrayList;
import java.util.List;
import jp.wasabeef.recyclerview.animators.FadeInUpAnimator;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by user on 6/6/16.
 */
public class SearchActivity extends AppCompatActivity
    implements SearchView.OnQueryTextListener, SearchContract.View, SearchAdapter.onItemEvent {
  private SearchView searchView;
  private SearchContract.Presenter searchPresenter;
  private ActivitySearchBinding activitySearchBinding;
  private SearchAdapter searchAdapter;
  private RecyclerView recyclerView;
  private Bus bus = com.wecook.yelinaung.events.Bus.getInstance();

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    activitySearchBinding = DataBindingUtil.setContentView(this, R.layout.activity_search);
    Intent intent = getIntent();
    searchView = activitySearchBinding.searchView;
    setSupportActionBar(activitySearchBinding.searchToolbar);
    getSupportActionBar().setDisplayShowTitleEnabled(false);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    AnalyticManager.sendScreenView(getString(R.string.search_screen));
    activitySearchBinding.title.setTypeface(CustomFont.getInstance().getFont("medium"));
    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
      String query = intent.getStringExtra(SearchManager.QUERY);
      SearchRecentSuggestions suggestions =
          new SearchRecentSuggestions(this, SearchSuggestProvider.AUTHORITY,
              SearchSuggestProvider.MODE);
      suggestions.saveRecentQuery(query, null);
    }

    searchPresenter =
        new SearchPresenter(Injection.provideDrinkRepo(this), getSupportFragmentManager(), this);
    searchView.setOnQueryTextListener(this);
    handleIntent(intent);
    activitySearchBinding.title.setVisibility(View.GONE);
    activitySearchBinding.errorCloud.setVisibility(View.GONE);
  }

  @Override protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);

    handleIntent(intent);
  }

  private void handleIntent(Intent intent) {

    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
      String query = intent.getStringExtra(SearchManager.QUERY);

      showResults(query);
    }
  }

  @Override public boolean onQueryTextChange(String newText) {
    return true;
  }

  @Override public boolean onQueryTextSubmit(String query) {
    searchPresenter.find(searchView.getQuery().toString());
    AnalyticManager.sendEvent(getString(R.string.category_search), getString(R.string.search),
        query);

    return true;
  }

  private void showResults(String query) {
    searchView.setQuery(query, true);
    searchView.setFocusable(true);
    searchView.setIconified(false);
    searchView.requestFocusFromTouch();
    // Query your data set and show results
    // ...
  }

  public void prepareRecyclerView() {

    recyclerView = activitySearchBinding.searchRecycler;

    recyclerView.setItemAnimator(new FadeInUpAnimator());
    recyclerView.getItemAnimator().setAddDuration(1000);
    recyclerView.getItemAnimator().setRemoveDuration(1000);
    recyclerView.getItemAnimator().setMoveDuration(1000);
    recyclerView.getItemAnimator().setChangeDuration(1000);
    searchAdapter = new SearchAdapter(new ArrayList<DrinkDbModel>(0));
    int columCount = this.getResources().getInteger(R.integer.recycler_item_count);
    GridLayoutManager gridLayoutManager = new GridLayoutManager(this, columCount);
    recyclerView.setLayoutManager(checkNotNull(gridLayoutManager));
    searchAdapter.setItemEvent(this);
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(searchAdapter);
  }

  public SearchContract.Presenter getPresenter() {
    return searchPresenter;
  }

  @Override protected void onStart() {
    super.onStart();
    searchPresenter.start();
    prepareRecyclerView();
  }

  @Override protected void onStop() {
    super.onStop();
  }

  @Override public void setPresenter(SearchContract.Presenter Presenter) {
    Preconditions.checkNotNull(searchPresenter);
  }

  @Override public void showLoadingIndicator(boolean loading) {
    if (!loading) {
      activitySearchBinding.searchRecycler.setVisibility(View.VISIBLE);
    } else {
      activitySearchBinding.searchProgress.setVisibility(View.VISIBLE);
      activitySearchBinding.searchRecycler.setVisibility(View.GONE);
    }
  }

  @Override public void onBookmark(View v, int position) {

    bus.post(new onBookmarkedEvent());
    searchPresenter.processBookmarks(searchAdapter.getItemAtPosition(position), position);
  }

  @Override public void setBookmark(DrinkDbModel dbModel, int position) {

    AnalyticManager.sendEvent(getString(R.string.category_item_bookmark),
        getString(R.string.search_screen),
        dbModel.getId() + "." + dbModel.getBookmark());

    searchAdapter.setBookmark(dbModel, position);

  }

  @Override public void onItemClick(View v, int position) {
    AnalyticManager.sendEvent(getString(R.string.category_item_click), getString(R.string.search),
        searchAdapter.getItemAtPosition(position).getId());
    Intent intent = new Intent(this, DetailActivity.class);
    intent.putExtra(DetailActivity.EXTRA, searchAdapter.getItemAtPosition(position).getId());
    startActivity(intent);
  }

  @Override public String getSearch() {
    return searchView.getQuery().toString();
  }

  @Override public void onLongPressed(View v, int position) {

  }

  @Override public void showDrinks(List<DrinkDbModel> list) {
    searchAdapter.clear();
    searchAdapter.replaceList(list);
  }

  @Override public void showCantFind(boolean found) {
    if (found) {
      activitySearchBinding.searchProgress.setVisibility(View.GONE);
      activitySearchBinding.searchRecycler.setVisibility(View.VISIBLE);
      activitySearchBinding.errorCloud.setVisibility(View.GONE);
      activitySearchBinding.title.setVisibility(View.GONE);
    } else {
      activitySearchBinding.errorCloud.setVisibility(View.VISIBLE);
      activitySearchBinding.title.setVisibility(View.VISIBLE);
      activitySearchBinding.searchRecycler.setVisibility(View.VISIBLE);
      activitySearchBinding.searchProgress.setVisibility(View.GONE);
    }

    //  getSupportFragmentManager().beginTransaction().remove(searchFragment).commit();
  }
}
