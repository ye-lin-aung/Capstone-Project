package com.wecook.yelinaung.detail;

import android.animation.Animator;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import com.wecook.yelinaung.R;
import com.wecook.yelinaung.databinding.ActivityDetailBinding;

/**
 * Created by user on 6/4/16.
 */
public class DetailActivity extends AppCompatActivity {
  public static final String EPICENTER = "epic";
  View rootLayout;
  FloatingActionButton fab;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    overridePendingTransition(R.anim.do_not_move, R.anim.do_not_move);

    ActivityDetailBinding activityDetailBinding =
        DataBindingUtil.setContentView(this, R.layout.activity_detail);
    //fab = activityDetailBinding.

    rootLayout = activityDetailBinding.appBar;
    if (savedInstanceState == null) {
      rootLayout.setVisibility(View.INVISIBLE);

      if (Build.VERSION.SDK_INT >= 21) {
        ViewTreeObserver viewTreeObserver = rootLayout.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
          viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override public void onGlobalLayout() {
              circularRevealActivity();
              rootLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
          });
        }
      }
    }
  }

  @Override public void onBackPressed() {
    circularUnRevel();
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      // Respond to the action bar's Up/Home button
      case android.R.id.home:
        circularUnRevel();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void circularUnRevel() {

    int cx = rootLayout.getWidth();
    int cy = rootLayout.getHeight();

    float finalRadius =
        Math.max(rootLayout.getWidth(), rootLayout.getHeight()) + rootLayout.getHeight();

    // create the animator for this view (the start radius is zero)
    Animator circularReveal =
        ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, finalRadius, 0);
    circularReveal.setDuration(1000);

    // make the view visible and start the animation
    rootLayout.setVisibility(View.VISIBLE);
    circularReveal.start();
    Handler handler = new Handler();
    handler.postDelayed(() -> {

      finish();
    }, 900);
  }

  private void circularRevealActivity() {

    int cx = rootLayout.getWidth();
    int cy = rootLayout.getHeight();

    float finalRadius =
        Math.max(rootLayout.getWidth(), rootLayout.getHeight()) + rootLayout.getHeight();

    // create the animator for this view (the start radius is zero)
    Animator circularReveal =
        ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, 0, finalRadius);
    circularReveal.setDuration(1000);

    // make the view visible and start the animation
    rootLayout.setVisibility(View.VISIBLE);
    circularReveal.start();
  }
}
