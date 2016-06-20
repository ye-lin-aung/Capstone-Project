package com.wecook.yelinaung.detail.innerAdapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wecook.yelinaung.BR;
import com.wecook.yelinaung.databinding.ItemCardsItemBinding;
import com.wecook.yelinaung.detail.InnerDataModel;
import java.util.List;

/**
 * Created by user on 6/19/16.
 */
public class DetailInnerAdapter extends RecyclerView.Adapter<DetailInnerAdapter.ViewHolder> {
  static Context context;
  List<InnerDataModel> innerDataModels;

  public DetailInnerAdapter() {
  }

  public DetailInnerAdapter(List<InnerDataModel> innerDataModels) {
    this.innerDataModels = innerDataModels;
    notifyDataSetChanged();
  }

  public void replaceList(List<InnerDataModel> innerDataModels) {
    this.innerDataModels = innerDataModels;
    notifyDataSetChanged();
  }

  @Override public int getItemCount() {
    return innerDataModels.size();
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();
    return new ViewHolder(
        ItemCardsItemBinding.inflate(LayoutInflater.from(context), parent, false).getRoot());
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.getItemCardsItemBinding().setVariable(BR.di, innerDataModels.get(position));
    holder.getItemCardsItemBinding().executePendingBindings();
  }

  @BindingAdapter("app:itemUrl")
  public static void loadImage(ImageView imageView, String imageUrl) {
    Glide.with(context)
        .load(imageUrl)
        .crossFade()
        .fitCenter()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(imageView);
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    ItemCardsItemBinding itemCardsItemBinding;

    public ViewHolder(View itemView) {
      super(itemView);
      itemCardsItemBinding = DataBindingUtil.bind(itemView);
    }

    public ItemCardsItemBinding getItemCardsItemBinding() {
      return itemCardsItemBinding;
    }
  }
}
