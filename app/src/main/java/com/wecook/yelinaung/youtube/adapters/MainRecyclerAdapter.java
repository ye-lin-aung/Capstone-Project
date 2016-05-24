package com.wecook.yelinaung.youtube.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.wecook.yelinaung.BuildConfig;
import com.wecook.yelinaung.R;
import com.wecook.yelinaung.database.DrinkDbModel;
import com.wecook.yelinaung.listener.YoutubeThumnailListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 5/12/16.
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<MainRecyclerAdapter.ViewHolder> {

  private List<DrinkDbModel> list = new ArrayList<DrinkDbModel>();

  private Context context;

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
    return new ViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item, parent, false));
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    YoutubeThumnailListener youtubeThumnailListener =
        new YoutubeThumnailListener(context, list.get(position).getVideo());
    holder.thumbnail.initialize(BuildConfig.YT_KEY, youtubeThumnailListener);
    holder.title.setText(list.get(position).getName());
  }

  @Override public int getItemCount() {
    return list.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.thumbnail) YouTubeThumbnailView thumbnail;
    @BindView(R.id.title) TextView title;

    public ViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(itemView);
    }
  }
}
