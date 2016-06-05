package com.wecook.yelinaung.detail;

import android.animation.Animator;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.transition.Transition;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import com.wecook.yelinaung.R;
import com.wecook.yelinaung.databinding.ActivityDetailBinding;

/**
 * Created by user on 6/4/16.
 */
public class DetailActivity extends AppCompatActivity {
  View rootLayout;
  ActivityDetailBinding activityDetailBinding;

  public static final String EXTRA = "img";

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    activityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
    setSupportActionBar(activityDetailBinding.detailToolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    //Glide.with(MyApp.getContext())
    //    .load(getIntent().getStringExtra(EXTRA))
    //    .crossFade()
    //    .fitCenter()
    //    .placeholder(R.drawable.cocktail)
    //    .diskCacheStrategy(DiskCacheStrategy.ALL)
    //    .into(activityDetailBinding.detailThumbnail);
    //prepareExit();
    //  }
    //
    //  @Override public void onTransitionCancel(Transition transition) {
    //
    //  }
    //
    //  @Override public void onTransitionPause(Transition transition) {
    //
    //  }
    //
    //  @Override public void onTransitionResume(Transition transition) {
    //
    //  }
    //});
    // prepareExit();
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      // Respond to the action bar's Up/Home button
      case android.R.id.home:
        NavUtils.navigateUpFromSameTask(this);
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void prepareExit() {
    Transition transition = getWindow().getReturnTransition();
    transition.setStartDelay(1000);
    transition.addListener(new Transition.TransitionListener() {
      @Override public void onTransitionStart(Transition transition) {

        int cx = rootLayout.getWidth() / 2;
        int cy = rootLayout.getHeight() / 2;

        float finalRadius =
            Math.max(rootLayout.getWidth(), rootLayout.getHeight()) + rootLayout.getHeight();

        if (Build.VERSION.SDK_INT >= 21) {
          // create the animator for this view (the start radius is zero)
          Animator circularReveal =
              ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, finalRadius, 0);
          circularReveal.setDuration(1000);

          // make the view visible and start the animation
          circularReveal.start();
          circularReveal.addListener(new Animator.AnimatorListener() {
            @Override public void onAnimationStart(Animator animator) {

            }

            @Override public void onAnimationEnd(Animator animator) {
              rootLayout.setVisibility(View.INVISIBLE);
            }

            @Override public void onAnimationCancel(Animator animator) {

            }

            @Override public void onAnimationRepeat(Animator animator) {

            }
          });

          //finish();
          //exitActivityTransition.exit(this);
        }
      }

      @Override public void onTransitionEnd(Transition transition) {

      }

      @Override public void onTransitionCancel(Transition transition) {

      }

      @Override public void onTransitionPause(Transition transition) {

      }

      @Override public void onTransitionResume(Transition transition) {

      }
    });
  }

  private void circularRevealActivity() {
    int cx = rootLayout.getWidth() / 2;
    int cy = rootLayout.getHeight() / 2;

    float finalRadius = (float) Math.max(rootLayout.getWidth(), rootLayout.getHeight());

    // create the animator for this view (the start radius is zero)
    Animator circularReveal =
        ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, 0, finalRadius);
    circularReveal.setDuration(1000);

    // make the view visible and start the animation
    rootLayout.setVisibility(View.VISIBLE);
    circularReveal.start();
  }
}
