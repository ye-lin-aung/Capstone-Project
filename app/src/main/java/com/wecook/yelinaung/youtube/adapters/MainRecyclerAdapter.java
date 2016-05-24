package com.wecook.yelinaung.youtube.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 5/12/16.
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {
  private List list = new ArrayList();

  public void appendItems(Object object) {
    int positions = list.size();
    list.add(object);
    notifyItemInserted(positions);
  }

  public void replaceList(List list) {
    this.list = list;
    notifyDataSetChanged();
  }

  @Override
  public MainRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return null;
  }

  @Override public void onBindViewHolder(MainRecyclerAdapter.ViewHolder holder, int position) {

  }

  @Override public int getItemCount() {
    return list.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View itemView) {
      super(itemView);
    }
  }
}
