package com.wecook.yelinaung.youtube.scroll;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by user on 5/27/16.
 */
public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
  // The minimum amount of items to have below your current scroll position

  private int previousTotal = 0; // The total number of items in the dataset after the last load
  private boolean loading = true; // True if we are still waiting for the last set of data to load.
  private int visibleThreshold = 5;
  // The minimum amount of items to have below your current scroll position before loading more.
  int firstVisibleItem, visibleItemCount, totalItemCount;

  private GridLayoutManager gridLayoutManager;

  public EndlessRecyclerViewScrollListener(GridLayoutManager gridLayoutManager) {
    this.gridLayoutManager = gridLayoutManager;
    visibleThreshold = visibleThreshold * gridLayoutManager.getSpanCount();
  }

  @Override public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
    super.onScrolled(recyclerView, dx, dy);

    visibleItemCount = recyclerView.getChildCount();
    totalItemCount = gridLayoutManager.getItemCount();
    firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();

    if (loading) {
      if (totalItemCount > previousTotal) {
        loading = false;
        previousTotal = totalItemCount;
      }
    }
    if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
      // End has been reached

      // Do something

      onLoadMore();
      //loading = true;
    }
  }

  public void reset(int previousTotal, boolean loading) {
    this.previousTotal = previousTotal;
    this.loading = loading;
  }

  public void setLoading(boolean loading) {
    this.loading = loading;
  }

  public abstract void onLoadMore();
}