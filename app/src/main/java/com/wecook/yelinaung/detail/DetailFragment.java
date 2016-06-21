package com.wecook.yelinaung.detail;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.common.base.Preconditions;
import com.squareup.otto.Bus;
import com.wecook.yelinaung.BuildConfig;
import com.wecook.yelinaung.Injection;
import com.wecook.yelinaung.MyApp;
import com.wecook.yelinaung.R;
import com.wecook.yelinaung.YoutubeThumnail;
import com.wecook.yelinaung.database.DrinksRepository;
import com.wecook.yelinaung.database.loaders.DrinkLoader;
import com.wecook.yelinaung.databinding.FragmentDetailBinding;
import com.wecook.yelinaung.databinding.ItemOccasionTextBinding;
import com.wecook.yelinaung.databinding.ItemTasteBinding;
import com.wecook.yelinaung.detail.innerAdapter.DetailInnerAdapter;
import com.wecook.yelinaung.events.onBookmarkedEvent;
import com.wecook.yelinaung.font.CustomFont;
import com.wecook.yelinaung.models.Occasion;
import com.wecook.yelinaung.models.Skill;
import com.wecook.yelinaung.models.Taste;
import com.wecook.yelinaung.util.AnalyticManager;
import com.wecook.yelinaung.widget.HomeScreenWidget;
import java.util.List;

/**
 * Created by user on 6/5/16.
 */
public class DetailFragment extends Fragment implements DetailContract.View {
  private Bus bus = com.wecook.yelinaung.events.Bus.getInstance();
  public static final String BUNDLE_EXTRA = "id";
  private FragmentDetailBinding fragmentDetailBinding;
  String id;
  private DetailPresenter detailPresenter;
  private Drawable drawable;
  public static DetailFragment INSTANCE;
  private DetailInnerAdapter ingrediantsAdapter;
  private DetailInnerAdapter toolsAdapter;
  private DetailInnerAdapter serveInAdapter;
  AdView mAdView;

  public static DetailFragment newInstance(String drinkId) {
    INSTANCE = new DetailFragment();
    Bundle bundle = new Bundle();
    bundle.putString(BUNDLE_EXTRA, drinkId);
    INSTANCE.setArguments(bundle);
    return INSTANCE;
  }

  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_detail, container, false);
    fragmentDetailBinding = DataBindingUtil.bind(view);
    id = getArguments().getString(BUNDLE_EXTRA);
    DrinksRepository drinksRepository = Injection.provideDrinkRepo(getContext());
    DrinkLoader drinkLoader = new DrinkLoader(getContext(), id, drinksRepository);
    AnalyticManager.sendScreenView(getString(R.string.detail_screen));
    detailPresenter = new DetailPresenter(drinkLoader, drinksRepository, getLoaderManager(), this);
    AdRequest adRequest = new AdRequest.Builder().build();
    mAdView = fragmentDetailBinding.adView;
    mAdView.loadAd(adRequest);
    return view;
  }

  @Override public void onPause() {
    if (mAdView != null) {
      mAdView.pause();
    }
    super.onPause();
  }

  @Override public void onResume() {
    super.onResume();
    if (mAdView != null) {
      mAdView.resume();
    }
  }

  @Override public void onDestroy() {
    if (mAdView != null) {
      mAdView.destroy();
    }
    super.onDestroy();
  }

  @Override public void setPresenter(DetailContract.Presenter Presenter) {
    Preconditions.checkNotNull(detailPresenter);
  }

  @Override public void onStart() {
    super.onStart();
    ingrediantsAdapter = new DetailInnerAdapter();
    toolsAdapter = new DetailInnerAdapter();
    serveInAdapter = new DetailInnerAdapter();
    prepareRecycler(fragmentDetailBinding.toolsRecycler, toolsAdapter);
    prepareRecycler(fragmentDetailBinding.ingrediantsRecycler, ingrediantsAdapter);
    prepareRecycler(fragmentDetailBinding.serveinRecycler, serveInAdapter);
    detailPresenter.start();
  }

  @Override public void showTitle(String titleName) {

    fragmentDetailBinding.toolbarTitle.setText(titleName);
    fragmentDetailBinding.toolbarTitle.setTypeface(CustomFont.getInstance().getFont("bold"));
    drawable = getResources().getDrawable(R.color.colorPrimary);
  }

  @Override public void prepareScroll() {

    //mUpButton.setTranslationY(Math.min(mSelectedItemUpButtonFloor - upButtonNormalBottom, 0));

  }

  public void showAnim(View view) {
    fragmentDetailBinding.likeBtn.setVisibility(View.VISIBLE);
    Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.like);
    animation.setAnimationListener(new Animation.AnimationListener() {
      @Override public void onAnimationStart(Animation animation) {
        view.setHapticFeedbackEnabled(true);
        view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
      }

      @Override public void onAnimationEnd(Animation animation) {
        fragmentDetailBinding.likeBtn.setVisibility(View.GONE);
        detailPresenter.processLike();
      }

      @Override public void onAnimationRepeat(Animation animation) {

      }
    });
    fragmentDetailBinding.likeBtn.startAnimation(animation);
  }

  @Override public void setLike(boolean like) {
    fragmentDetailBinding.like.setOnClickListener((v) -> {
      bus.post(new onBookmarkedEvent());
      Intent intent_meeting_update = new Intent(getContext(), HomeScreenWidget.class);
      intent_meeting_update.setAction(HomeScreenWidget.UPDATE);
      getContext().sendBroadcast(intent_meeting_update);
      showAnim(v);
    });
    if (like) {

      AnalyticManager.sendEvent(getString(R.string.category_item_bookmark),
          getString(R.string.detail_screen), id + "." + "1");

      fragmentDetailBinding.like.setColorFilter(getResources().getColor(R.color.color_red));
      fragmentDetailBinding.likeBtn.setColorFilter(getResources().getColor(R.color.before_like));
    } else {

      AnalyticManager.sendEvent(getString(R.string.category_item_bookmark),
          getString(R.string.detail_screen), id + "." + "0");

      fragmentDetailBinding.like.setColorFilter(getResources().getColor(R.color.text_white));
      fragmentDetailBinding.likeBtn.setColorFilter(getResources().getColor(R.color.color_red));
    }
  }

  @Override public void showRating(int rating) {
    fragmentDetailBinding.rating.setMax(10);
    fragmentDetailBinding.rating.setRating((float) rating / 10);
  }

  @Override public void showColor(String hex) {
    //fragmentDetailBinding.colorText.setTypeface(CustomFont.getInstance().getFont("medium"));
    fragmentDetailBinding.colorText.setText("Drink Color\n" + hex);
  }

  @Override public void showImage(String imageUrl) {
    String image =
        "http://assets.absolutdrinks.com/drinks/transparent-background-white/soft-shadow/150x250/"
            + imageUrl
            + ".png";

    //http://img.youtube.com/vi/bQVoAWSP7k4/0.jpg

    Glide.with(MyApp.getContext())
        .load(image)
        .crossFade()
        .fitCenter()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(fragmentDetailBinding.imageHeader);
  }

  @Override public void showAlcohol(boolean alco) {
    fragmentDetailBinding.alcoholContainer.setVisibility(alco ? View.VISIBLE : View.GONE);
  }

  @Override public void showCarbon(boolean carbon) {
    fragmentDetailBinding.carbonContainer.setVisibility(carbon ? View.VISIBLE : View.GONE);
  }

  @Override public void showHot(boolean hot) {
    fragmentDetailBinding.hotContainer.setVisibility(hot ? View.VISIBLE : View.GONE);
  }

  @BindingAdapter("app:font") public static void loadFont(TextView view, String fontStyle) {
    view.setTypeface(CustomFont.getInstance().getFont(fontStyle));
  }

  @Override public void showVideo(String id) {
    YoutubeThumnail youtubeThumnail = new YoutubeThumnail(id);

    String youtube = youtubeThumnail.getFullSize();
    fragmentDetailBinding.play.setOnClickListener((v) -> {
      AnalyticManager.sendEvent(getString(R.string.category_trailer),
          getString(R.string.detail_screen), id);
      Intent intent =
          YouTubeStandalonePlayer.createVideoIntent(getActivity(), BuildConfig.YT_KEY, id, 0, false,
              false);

      startActivity(intent);
    });
    Glide.with(MyApp.getContext())
        .load(youtube)
        .crossFade()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(fragmentDetailBinding.header);
  }

  @Override public void showDescription(String description) {

    fragmentDetailBinding.descriptionContainer.setVisibility(View.VISIBLE);
    fragmentDetailBinding.descriptionTitle.setText(getString(R.string.description));
    fragmentDetailBinding.descrtionText.setText(description);
  }

  @Override public void showNoOccasionText() {
    fragmentDetailBinding.occasionContainer.setVisibility(View.GONE);
  }

  @Override public void showOccasionText(List<Occasion> occasions) {
    if (fragmentDetailBinding.occasionContainer != null) {
      fragmentDetailBinding.occasionContainer.setVisibility(View.VISIBLE);

      fragmentDetailBinding.occasionTextContainer.removeAllViews();
      fragmentDetailBinding.occasionTitle.setText(getString(R.string.occation));
      for (Occasion oca : occasions) {
        ItemOccasionTextBinding itemOccasionTextBinding =
            ItemOccasionTextBinding.inflate(LayoutInflater.from(getContext()));
        itemOccasionTextBinding.occationText.setText(oca.getText());
        itemOccasionTextBinding.occationText.setTypeface(
            CustomFont.getInstance().getFont("regular"));
        fragmentDetailBinding.occasionTextContainer.addView(itemOccasionTextBinding.getRoot());
      }
    }
  }

  @Override public void showNoTaste() {
    fragmentDetailBinding.tasteContainer.setVisibility(View.GONE);
    fragmentDetailBinding.tasteTextContainer.setVisibility(View.GONE);
  }

  @Override public void showTastes(List<Taste> tastes) {
    fragmentDetailBinding.tasteContainer.setVisibility(View.VISIBLE);
    fragmentDetailBinding.tasteTextContainer.setVisibility(View.VISIBLE);
    fragmentDetailBinding.tasteTextContainer.removeAllViews();
    fragmentDetailBinding.tasteTitle.setText(getString(R.string.taste));
    for (Taste oca : tastes) {
      if (oca.getText().length() > 0) {
        ItemTasteBinding itemOccasionTextBinding =
            ItemTasteBinding.inflate(LayoutInflater.from(getContext()));
        itemOccasionTextBinding.tasteText.setText(oca.getText());
        itemOccasionTextBinding.tasteText.setTypeface(CustomFont.getInstance().getFont("regular"));
        fragmentDetailBinding.tasteTextContainer.addView(itemOccasionTextBinding.getRoot());
      }
    }
  }

  @Override public void showNoSkill() {
    fragmentDetailBinding.skillContainer.setVisibility(View.GONE);
  }

  @Override public void showSkill(Skill skill) {
    fragmentDetailBinding.skillContainer.setVisibility(View.VISIBLE);
    fragmentDetailBinding.skillTitle.setText(R.string.skill);
    fragmentDetailBinding.skillDescription.setText(skill.getName());
    fragmentDetailBinding.skillRating.setMax(10);
    fragmentDetailBinding.skillRating.setRating((float) skill.getValue());
  }

  @Override public void showNoTools() {
    fragmentDetailBinding.toolsRecycler.setVisibility(View.GONE);
    fragmentDetailBinding.toolbarTitle.setVisibility(View.GONE);
  }

  @Override public void showTools(List<InnerDataModel> innerDataModels) {
    fragmentDetailBinding.toolsRecycler.setVisibility(View.VISIBLE);
    fragmentDetailBinding.toolbarTitle.setVisibility(View.VISIBLE);
    toolsAdapter.replaceList(innerDataModels);
  }

  @Override public void showNoDescription() {
    fragmentDetailBinding.descriptionContainer.setVisibility(View.GONE);
  }

  public void prepareRecycler(RecyclerView recyclerView, RecyclerView.Adapter adapter) {
    LinearLayoutManager linearLayoutManager =
        new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.setNestedScrollingEnabled(true);
    recyclerView.canScrollVertically(0);
    recyclerView.setHasFixedSize(true);
    recyclerView.setAdapter(adapter);
  }

  @Override public void showNoServeIn() {
    fragmentDetailBinding.serveinRecycler.setVisibility(View.GONE);
    fragmentDetailBinding.serveinTitle.setVisibility(View.GONE);
  }

  @Override public void showServeIn(List<InnerDataModel> innerDataModels) {
    fragmentDetailBinding.serveinRecycler.setVisibility(View.VISIBLE);
    fragmentDetailBinding.serveinTitle.setVisibility(View.VISIBLE);
    serveInAdapter.replaceList(innerDataModels);
  }

  @Override public void showIngrediants(List<InnerDataModel> innerDataModels) {
    fragmentDetailBinding.ingrediantsRecycler.setVisibility(View.VISIBLE);
    fragmentDetailBinding.ingrediantsTitle.setVisibility(View.VISIBLE);
    ingrediantsAdapter.replaceList(innerDataModels);
  }

  @Override public void showNoIngrediants() {
    fragmentDetailBinding.ingrediantsRecycler.setVisibility(View.GONE);
    fragmentDetailBinding.ingrediantsTitle.setVisibility(View.GONE);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      // Respond to the action bar's Up/Home button
      case android.R.id.home:
        NavUtils.navigateUpFromSameTask(getActivity());
        return true;
    }
    return super.onOptionsItemSelected(item);
  }
}
