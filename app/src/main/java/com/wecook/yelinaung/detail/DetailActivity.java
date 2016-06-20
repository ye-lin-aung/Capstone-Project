package com.wecook.yelinaung.detail;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowInsets;
import com.wecook.yelinaung.Injection;
import com.wecook.yelinaung.R;
import com.wecook.yelinaung.database.DrinkDbModel;
import com.wecook.yelinaung.database.DrinksRepository;
import com.wecook.yelinaung.databinding.ActivityDetailBinding;
import com.wecook.yelinaung.detail.pager.DetailPager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 6/4/16.
 */
public class DetailActivity extends AppCompatActivity {
  View rootLayout;
  ActivityDetailBinding activityDetailBinding;
  private int mSelectedItemUpButtonFloor = Integer.MAX_VALUE;
  private int mTopInset;

  private View mUpButtonContainer;
  private View mUpButton;

  private long mSelectedItemId;
  List<String> fragmentMap = new ArrayList<>();
  public static final String EXTRA = "position";

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    activityDetailBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
    String id = getIntent().getStringExtra(EXTRA);

    mUpButtonContainer = activityDetailBinding.upContainer;
    mUpButton = activityDetailBinding.actionUp;

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      mUpButtonContainer.setOnApplyWindowInsetsListener(new View.OnApplyWindowInsetsListener() {
        @Override public WindowInsets onApplyWindowInsets(View view, WindowInsets windowInsets) {
          view.onApplyWindowInsets(windowInsets);
          mTopInset = windowInsets.getSystemWindowInsetTop();
          mUpButtonContainer.setTranslationY(mTopInset);
          updateUpButtonPosition();
          return windowInsets;
        }
      });
    }
    mUpButton.setOnClickListener((view) -> {

          onSupportNavigateUp();
        }

    );

    DetailPager detailPager = new DetailPager(getSupportFragmentManager());
    DrinksRepository drinksRepository = Injection.provideDrinkRepo(this);
    activityDetailBinding.detailPager.setAdapter(detailPager);
    for (DrinkDbModel drink : drinksRepository.getDrinks())

    {
      fragmentMap.add(drink.getId());
      detailPager.add(DetailFragment.newInstance(drink.getId()), drink.getId());
    }
    // Log.d("DATA", fragmentMap.get(id) + "");
    detailPager.notifyDataSetChanged();

    mUpButton.setOnClickListener((view) -> {
      onSupportNavigateUp();
    });

    activityDetailBinding.detailPager.setPageMargin(
        (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1,

            getResources()

                .

                    getDisplayMetrics()

        ));
    activityDetailBinding.detailPager.setPageMarginDrawable(new ColorDrawable(0x22000000)

    );
    activityDetailBinding.detailPager.setCurrentItem(fragmentMap.indexOf(id), false);
    activityDetailBinding.detailPager.setOnPageChangeListener(
        new ViewPager.SimpleOnPageChangeListener()

        {
          @Override public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
            mUpButton.animate()
                .alpha((state == ViewPager.SCROLL_STATE_IDLE) ? 1f : 0f)
                .setDuration(300);
          }

          @Override public void onPageSelected(int position) {
            updateUpButtonPosition();
          }
        }

    );
  }

  private void updateUpButtonPosition() {
    int upButtonNormalBottom = mTopInset + mUpButton.getHeight();
    mUpButton.setTranslationY(Math.min(mSelectedItemUpButtonFloor - upButtonNormalBottom, 0));
  }
  //
  //private void prepareExit() {
  //  Transition transition = getWindow().getReturnTransition();
  //  transition.setStartDel
  //
  // ay(1000);
  //  transition.addListener(new Transition.TransitionListener() {
  //    @Override public void onTransitionStart(Transition transition) {
  //
  //      int cx = rootLayout.getWidth() / 2;
  //      int cy = rootLayout.getHeight() / 2;
  //
  //      float finalRadius =
  //          Math.max(rootLayout.getWidth(), rootLayout.getHeight()) + rootLayout.getHeight();
  //
  //      if (Build.VERSION.SDK_INT >= 21) {
  //        // create the animator for this view (the start radius is zero)
  //        Animator circularReveal =
  //            ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, finalRadius, 0);
  //        circularReveal.setDuration(1000);
  //
  //        // make the view visible and start the animation
  //        circularReveal.start();
  //        circularReveal.addListener(new Animator.AnimatorListener() {
  //          @Override public void onAnimationStart(Animator animator) {
  //
  //          }
  //
  //          @Override public void onAnimationEnd(Animator animator) {
  //            rootLayout.setVisibility(View.INVISIBLE);
  //          }
  //
  //          @Override public void onAnimationCancel(Animator animator) {
  //
  //          }
  //
  //          @Override public void onAnimationRepeat(Animator animator) {
  //
  //          }
  //        });
  //
  //        //finish();
  //        //exitActivityTransition.exit(this);
  //      }
  //    }
  //
  //    @Override public void onTransitionEnd(Transition transition) {
  //
  //    }
  //
  //    @Override public void onTransitionCancel(Transition transition) {
  //
  //    }
  //
  //    @Override public void onTransitionPause(Transition transition) {
  //
  //    }
  //
  //    @Override public void onTransitionResume(Transition transition) {
  //
  //    }
  //  });
  //}
  //
  //private void circularRevealActivity() {
  //  int cx = rootLayout.getWidth() / 2;
  //  int cy = rootLayout.getHeight() / 2;
  //
  //  float finalRadius = (float) Math.max(rootLayout.getWidth(), rootLayout.getHeight());
  //
  //  // create the animator for this view (the start radius is zero)
  //  Animator circularReveal =
  //      ViewAnimationUtils.createCircularReveal(rootLayout, cx, cy, 0, finalRadius);
  //  circularReveal.setDuration(1000);
  //
  //  // make the view visible and start the animation
  //  rootLayout.setVisibility(View.VISIBLE);
  //  circularReveal.start();
  //}
}
