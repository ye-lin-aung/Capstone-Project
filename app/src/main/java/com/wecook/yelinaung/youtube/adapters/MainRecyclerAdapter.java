package com.wecook.yelinaung.youtube.adapters;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.wecook.yelinaung.BR;
import com.wecook.yelinaung.R;
import com.wecook.yelinaung.YoutubeThumnail;
import com.wecook.yelinaung.database.DrinkDbModel;
import com.wecook.yelinaung.databinding.ItemCardsMainBinding;
import com.wecook.yelinaung.font.CustomFont;
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
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    ItemCardsMainBinding itemCardsMainBinding =
        ItemCardsMainBinding.inflate(inflater, parent, false);
    return new ViewHolder(itemCardsMainBinding.getRoot());
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    final DrinkDbModel drinkDbModel = list.get(position);
    holder.getDataBinding().setVariable(BR.drink, drinkDbModel);
    holder.getDataBinding().executePendingBindings();
  }

  @BindingAdapter("app:font") public static void loadFont(TextView textView, String fontName) {
    textView.setTypeface(CustomFont.getInstance().getFont(fontName));
  }

  @BindingAdapter("app:imageUrl") public static void loadThumbnil(ImageView view, String video) {
    YoutubeThumnail youtubeThumnail = new YoutubeThumnail(video);
    Picasso.with(context)
        .load(youtubeThumnail.getFullSize())
        .placeholder(R.drawable.loading)
        .noFade()
        .fit()
        .into(view);
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



