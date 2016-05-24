package com.wecook.yelinaung.youtube.adapters;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.wecook.yelinaung.BuildConfig;
import com.wecook.yelinaung.R;
import com.wecook.yelinaung.database.DrinkDbModel;
import com.wecook.yelinaung.databinding.ItemCardsMainBinding;
import com.wecook.yelinaung.listener.YoutubeThumnailListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 5/12/16.
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {

  private List<DrinkDbModel> list = new ArrayList<DrinkDbModel>();

  private static Context context;

  public MainRecyclerAdapter(List<DrinkDbModel> list) {
    this.list = list;
    notifyDataSetChanged();
  }

  public void appendItems(DrinkDbModel object) {
    int positions = list.size();
    list.add(object);
    notifyItemInserted(positions);
  }

  public void replaceList(List<DrinkDbModel> list) {
    this.list = list;
    notifyDataSetChanged();
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(context);
    ItemCardsMainBinding itemCardsMainBinding =
        DataBindingUtil.inflate(inflater, R.layout.item_cards_main, parent, false);
    return new ViewHolder(itemCardsMainBinding.getRoot());
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    holder.getDataBinding().setDrink(list.get(position));
    holder.getDataBinding().executePendingBindings();
  }

  @BindingAdapter("app:imageUrl")
  public static void loadThumbnil(YouTubeThumbnailView view, String video) {
    YoutubeThumnailListener youtubeThumnailListener = new YoutubeThumnailListener(context, video);
    view.initialize(BuildConfig.YT_KEY, youtubeThumnailListener);
  }

  @Override public int getItemCount() {
    return list.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    private ItemCardsMainBinding dataBinding;

    public ViewHolder(View itemView) {
      super(itemView);
      dataBinding = DataBindingUtil.bind(itemView);
    }

    public ItemCardsMainBinding getDataBinding() {
      return dataBinding;
    }
  }
}


