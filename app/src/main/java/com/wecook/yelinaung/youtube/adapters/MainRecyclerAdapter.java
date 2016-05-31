package com.wecook.yelinaung.youtube.adapters;

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
import com.wecook.yelinaung.R;
import com.wecook.yelinaung.YoutubeThumnail;
import com.wecook.yelinaung.database.DrinkDbModel;
import com.wecook.yelinaung.databinding.ItemCardsMainBinding;
import com.wecook.yelinaung.databinding.ProgressLayoutBinding;
import com.wecook.yelinaung.font.CustomFont;
import com.wecook.yelinaung.util.InternetUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 5/12/16.
 */
public class MainRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private List<DrinkDbModel> list = new ArrayList<DrinkDbModel>();
  private final int VIEW_ITEM = 1;
  private final int VIEW_PROG = 0;
  private onItemEvent itemEvent;

  private static Context context;

  public MainRecyclerAdapter(List<DrinkDbModel> list) {
    this.list = list;
    notifyDataSetChanged();
  }

  public void appendItems(DrinkDbModel object) {
    int positions = list.size();
    list.add(object);
    notifyItemInserted(positions - 1);
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

  public DrinkDbModel getItemAtPosition(int position) {
    return list.get(position);
  }

  public void setBookmark(DrinkDbModel drinkDbModel, int position) {
    this.list.remove(position);
    this.list.add(position, drinkDbModel);
    notifyDataSetChanged();
  }

  public void replaceList(List<DrinkDbModel> list) {
    int initPosition;

    initPosition = getItemCount();
    this.list = list;
    notifyItemRangeInserted(initPosition, list.size());
  }

  @Override public int getItemViewType(int position) {
    return position != list.size() - 1 ? VIEW_ITEM : VIEW_PROG;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    if (viewType == VIEW_ITEM) {
      ItemCardsMainBinding itemCardsMainBinding =
          ItemCardsMainBinding.inflate(inflater, parent, false);
      return new ItemViewHolder(itemCardsMainBinding.getRoot());
    } else {
      ProgressLayoutBinding progressLayoutBinding =
          ProgressLayoutBinding.inflate(inflater, parent, false);
      return new ProgressViewHolder(progressLayoutBinding.getRoot());
    }
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

  @BindingAdapter("app:imageUrl") public static void loadThumbnil(ImageView view, String video) {
    YoutubeThumnail youtubeThumnail = new YoutubeThumnail(video);
    Picasso.with(context)
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
    private ItemCardsMainBinding dataBinding;

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

    public ItemCardsMainBinding getDataBinding() {
      return dataBinding;
    }
  }

  public interface onItemEvent {
    void onItemClick(View v, int position);

    void onLongPressed(View v, int position);

    void onBookmark(View v, int position);
  }
}



