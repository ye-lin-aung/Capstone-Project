package com.wecook.yelinaung.bookmark.adapter;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.wecook.yelinaung.BR;
import com.wecook.yelinaung.MyApp;
import com.wecook.yelinaung.R;
import com.wecook.yelinaung.YoutubeThumnail;
import com.wecook.yelinaung.database.DrinkDbModel;
import com.wecook.yelinaung.databinding.ItemCardsBookmarkBinding;
import com.wecook.yelinaung.databinding.ProgressLayoutBinding;
import com.wecook.yelinaung.font.CustomFont;
import com.wecook.yelinaung.util.InternetUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 5/31/16.
 */
public class BookmarkRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private List<DrinkDbModel> list = new ArrayList<DrinkDbModel>();

  private onItemEvent itemEvent;

  private static Context context;

  public BookmarkRecyclerAdapter(List<DrinkDbModel> list) {
    this.list = list;
    notifyDataSetChanged();
  }

  public void noAnimationAddList(List<DrinkDbModel> list) {
    if (this.list.size() <= 0) {
      this.list = list;
      notifyItemRangeInserted(this.list.size(), list.size());
    } else {
      this.list = list;
      notifyDataSetChanged();
    }
  }

  public void unBookmarkItems(int position) {
    //  if (position == list.size()) {
    //  list.remove(position);
    // notifyDataSetChanged();
    //} else {
    list.remove(position);
    notifyItemRemoved(position);
    //}
  }

  public DrinkDbModel getItemAtPosition(int position) {
    return list.get(position);
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    ItemCardsBookmarkBinding itemCardsMainBinding =
        ItemCardsBookmarkBinding.inflate(inflater, parent, false);
    return new ItemViewHolder(itemCardsMainBinding.getRoot());
  }

  public void setItemEvent(onItemEvent itemEvent) {
    this.itemEvent = itemEvent;
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    final DrinkDbModel drinkDbModel = list.get(position);
    if (holder instanceof ItemViewHolder) {
      if (drinkDbModel.getBookmark() == 0) {
        ((ItemViewHolder) holder).getDataBinding().smallLike.setColorFilter(
            context.getResources().getColor(R.color.before_like));
        ((ItemViewHolder) holder).getDataBinding().smallLikeText.setText(
            context.getString(R.string.like));
      } else {
        ((ItemViewHolder) holder).getDataBinding().smallLike.setColorFilter(
            context.getResources().getColor(R.color.colorPrimary));
        ((ItemViewHolder) holder).getDataBinding().smallLikeText.setText(
            context.getString(R.string.liked));
      }
      ((ItemViewHolder) holder).getDataBinding().setVariable(BR.drink, drinkDbModel);
      ((ItemViewHolder) holder).getDataBinding().like.setVisibility(View.GONE);
      ((ItemViewHolder) holder).getDataBinding().itemView.setOnLongClickListener(
          new View.OnLongClickListener() {
            @Override public boolean onLongClick(View view) {
              ((ItemViewHolder) holder).getDataBinding().like.setVisibility(View.VISIBLE);
              Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
              Animation animation = AnimationUtils.loadAnimation(context, R.anim.like);
              animation.setAnimationListener(new Animation.AnimationListener() {
                @Override public void onAnimationStart(Animation animation) {
                  view.setHapticFeedbackEnabled(true);
                  view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                }

                @Override public void onAnimationEnd(Animation animation) {
                  ((ItemViewHolder) holder).getDataBinding().like.setVisibility(View.GONE);
                  itemEvent.onBookmark(view, position);
                }

                @Override public void onAnimationRepeat(Animation animation) {

                }
              });
              ((ItemViewHolder) holder).getDataBinding().like.startAnimation(animation);
              return true;
            }
          });
      ((ItemViewHolder) holder).getDataBinding().smallLikeContainer.setOnClickListener((view) -> {
        ((ItemViewHolder) holder).getDataBinding().like.setVisibility(View.VISIBLE);
        Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.like);
        animation.setAnimationListener(new Animation.AnimationListener() {
          @Override public void onAnimationStart(Animation animation) {
            view.setHapticFeedbackEnabled(true);
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
          }

          @Override public void onAnimationEnd(Animation animation) {
            ((ItemViewHolder) holder).getDataBinding().like.setVisibility(View.GONE);
            itemEvent.onBookmark(view, position);
          }

          @Override public void onAnimationRepeat(Animation animation) {

          }
        });
        ((ItemViewHolder) holder).getDataBinding().like.startAnimation(animation);
      });
      ((ItemViewHolder) holder).getDataBinding().executePendingBindings();
    } else {
      if (InternetUtil.isOnline(context)) {
        ((ProgressViewHolder) holder).progressLayoutBinding.moreProgress.setVisibility(
            View.VISIBLE);
        ((ProgressViewHolder) holder).progressLayoutBinding.errorCloud.setVisibility(View.GONE);
        ((ProgressViewHolder) holder).progressLayoutBinding.moreProgress.setIndeterminate(true);
        ((ProgressViewHolder) holder).progressLayoutBinding.progressText.setText(
            context.getResources().getString(R.string.load_more));
      } else {
        ((ProgressViewHolder) holder).progressLayoutBinding.moreProgress.setVisibility(View.GONE);
        ((ProgressViewHolder) holder).progressLayoutBinding.errorCloud.setVisibility(View.VISIBLE);
        ((ProgressViewHolder) holder).progressLayoutBinding.progressText.setText(
            context.getString(R.string.cant_connect));
      }
    }
  }

  @BindingAdapter("app:font") public static void loadFont(TextView textView, String fontName) {
    textView.setTypeface(CustomFont.getInstance().getFont(fontName));
  }

  @BindingAdapter("app:videoUrl") public static void loadThumbnil(ImageView view, String video) {
    YoutubeThumnail youtubeThumnail = new YoutubeThumnail(video);
    Picasso.with(MyApp.getContext())
        .load(youtubeThumnail.getFullSize())
        .noPlaceholder()
        .noFade()
        .fit()
        .into(view);
  }

  @Override public int getItemCount() {
    return list != null ? list.size() : 0;
  }

  public class ProgressViewHolder extends RecyclerView.ViewHolder {
    private ProgressLayoutBinding progressLayoutBinding;

    public ProgressViewHolder(View itemView) {
      super(itemView);
      progressLayoutBinding = DataBindingUtil.bind(itemView);
    }
  }

  public class ItemViewHolder extends RecyclerView.ViewHolder
      implements View.OnClickListener, View.OnLongClickListener {
    private ItemCardsBookmarkBinding dataBinding;

    public ItemViewHolder(View itemView) {
      super(itemView);
      dataBinding = DataBindingUtil.bind(itemView);
      dataBinding.itemView.setOnLongClickListener(this);
      dataBinding.itemView.setOnClickListener(this);
      dataBinding.smallLikeContainer.setOnClickListener(this);
    }

    @Override public void onClick(View view) {
      if (view.getId() != R.id.small_like_container) {
        if (itemEvent != null) {
          itemEvent.onItemClick(view, getAdapterPosition());
        } else {
          throw new NullPointerException("Please implement item click");
        }
      } else {
        itemEvent.onBookmark(view, getAdapterPosition());
      }
    }

    @Override public boolean onLongClick(View view) {
      itemEvent.onLongPressed(view, getAdapterPosition());
      return true;
    }

    public ItemCardsBookmarkBinding getDataBinding() {
      return dataBinding;
    }
  }

  public interface onItemEvent {
    void onItemClick(View v, int position);

    void onLongPressed(View v, int position);

    void onBookmark(View v, int position);
  }
}




