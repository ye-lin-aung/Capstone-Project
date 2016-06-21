package com.wecook.yelinaung.search;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wecook.yelinaung.BR;
import com.wecook.yelinaung.MyApp;
import com.wecook.yelinaung.R;
import com.wecook.yelinaung.database.DrinkDbModel;
import com.wecook.yelinaung.databinding.ItemCardsSearchBinding;
import java.util.List;

/**
 * Created by user on 6/10/16.
 */
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
  private List<DrinkDbModel> list;
  private static Context context;
  private onItemEvent itemEvent;

  public SearchAdapter(List<DrinkDbModel> list) {
    this.list = list;
    notifyDataSetChanged();
  }

  public void setBookmark(DrinkDbModel drinkDbModel, int position) {

    this.list.set(position, drinkDbModel);
    notifyDataSetChanged();
  }

  @Override public int getItemCount() {
    return list.size();
  }

  public void clear() {
    this.list.clear();
    notifyDataSetChanged();
  }

  public void replaceList(List<DrinkDbModel> list) {
    this.list = list;
    notifyDataSetChanged();
  }

  public DrinkDbModel getItemAtPosition(int position) {
    return list.get(position);
  }

  @Override public void onBindViewHolder(ViewHolder holder, int position) {
    if (holder instanceof ViewHolder) {
      final DrinkDbModel drinkDbModel = list.get(position);
      if (drinkDbModel.getBookmark() == 0) {
        ((ViewHolder) holder).getItemCardsSearchBinding().smallLike.setColorFilter(
            context.getResources().getColor(R.color.before_like));
        ((ViewHolder) holder).getItemCardsSearchBinding().smallLikeText.setText(
            context.getString(R.string.like));
      } else {
        ((ViewHolder) holder).getItemCardsSearchBinding().smallLike.setColorFilter(
            context.getResources().getColor(R.color.color_red));
        ((ViewHolder) holder).getItemCardsSearchBinding().smallLikeText.setText(
            context.getString(R.string.liked));
      }

      ((ViewHolder) holder).getItemCardsSearchBinding().setVariable(BR.drink, drinkDbModel);
      ((ViewHolder) holder).getItemCardsSearchBinding().like.setVisibility(View.GONE);
      ((ViewHolder) holder).getItemCardsSearchBinding().itemView.setOnLongClickListener(
          new View.OnLongClickListener() {
            @Override public boolean onLongClick(View view) {
              ((ViewHolder) holder).getItemCardsSearchBinding().like.setVisibility(View.VISIBLE);
              Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
              Animation animation = AnimationUtils.loadAnimation(context, R.anim.like);
              animation.setAnimationListener(new Animation.AnimationListener() {
                @Override public void onAnimationStart(Animation animation) {
                  view.setHapticFeedbackEnabled(true);
                  view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                }

                @Override public void onAnimationEnd(Animation animation) {
                  ((ViewHolder) holder).getItemCardsSearchBinding().like.setVisibility(View.GONE);
                  itemEvent.onBookmark(view, position);
                }

                @Override public void onAnimationRepeat(Animation animation) {

                }
              });
              ((ViewHolder) holder).getItemCardsSearchBinding().like.startAnimation(animation);
              return true;
            }
          });
      ((ViewHolder) holder).getItemCardsSearchBinding().smallLikeContainer.setOnClickListener(
          (view) -> {
            ((ViewHolder) holder).getItemCardsSearchBinding().like.setVisibility(View.VISIBLE);
            Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.like);
            animation.setAnimationListener(new Animation.AnimationListener() {
              @Override public void onAnimationStart(Animation animation) {
                view.setHapticFeedbackEnabled(true);
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
              }

              @Override public void onAnimationEnd(Animation animation) {
                ((ViewHolder) holder).getItemCardsSearchBinding().like.setVisibility(View.GONE);
                itemEvent.onBookmark(view, position);
              }

              @Override public void onAnimationRepeat(Animation animation) {

              }
            });
            ((ViewHolder) holder).getItemCardsSearchBinding().like.startAnimation(animation);
          });
      ((ViewHolder) holder).getItemCardsSearchBinding().executePendingBindings();
    }
  }

  public void setItemEvent(onItemEvent itemEvent) {
    this.itemEvent = itemEvent;
  }

  @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    context = parent.getContext();
    LayoutInflater layoutInflater = LayoutInflater.from(context);
    return new ViewHolder(ItemCardsSearchBinding.inflate(layoutInflater, parent, false).getRoot());
  }

  @BindingAdapter("app:searchImg") public static void loadThumbnil(ImageView view, String name) {
    String image =
        "http://assets.absolutdrinks.com/drinks/transparent-background-white/soft-shadow/150x250/"
            + name
            + ".png";
    Drawable drawable = VectorDrawableCompat.create(context.getResources(), R.drawable.cocktail_svg,
        context.getTheme()).mutate();
    PorterDuff.Mode mMode = PorterDuff.Mode.SRC_ATOP;
    drawable.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), mMode);
    Glide.with(MyApp.getContext())
        .load(image)
        .placeholder(drawable)
        .crossFade()
        .fitCenter()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(view);
  }

  public class ViewHolder extends RecyclerView.ViewHolder
      implements View.OnClickListener, View.OnLongClickListener {
    private ItemCardsSearchBinding itemCardsSearchBinding;

    public ViewHolder(View itemView) {
      super(itemView);

      itemCardsSearchBinding = DataBindingUtil.bind(itemView);
      itemCardsSearchBinding.itemView.setOnLongClickListener(this);
      itemCardsSearchBinding.itemView.setOnClickListener(this);
      itemCardsSearchBinding.smallLikeContainer.setOnClickListener(this);
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

    public ItemCardsSearchBinding getItemCardsSearchBinding() {
      return itemCardsSearchBinding;
    }
  }

  public interface onItemEvent {
    void onItemClick(View v, int position);

    void onLongPressed(View v, int position);

    void onBookmark(View v, int position);
  }
}
