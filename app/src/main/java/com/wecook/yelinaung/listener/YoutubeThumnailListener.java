package com.wecook.yelinaung.listener;

import android.content.Context;
import android.view.ViewGroup;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.wecook.yelinaung.R;
import com.wecook.yelinaung.util.ViewUtil;

/**
 * Created by user on 5/24/16.
 */
public class YoutubeThumnailListener implements YouTubeThumbnailView.OnInitializedListener,
    YouTubeThumbnailLoader.OnThumbnailLoadedListener {
  private String video;
  private Context context;

  public YoutubeThumnailListener(Context context, String video) {
    this.context = context;
    this.video = video;
  }

  @Override public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView,
      YouTubeInitializationResult youTubeInitializationResult) {
    youTubeThumbnailView.setImageResource(R.drawable.no_thumbnail);
  }

  @Override public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView,
      YouTubeThumbnailLoader youTubeThumbnailLoader) {
    youTubeThumbnailLoader.setVideo(video);
    youTubeThumbnailLoader.setOnThumbnailLoadedListener(this);
    ViewUtil viewUtil = new ViewUtil(context);
    viewUtil.setViewParmas(youTubeThumbnailView, ViewGroup.LayoutParams.MATCH_PARENT,
        viewUtil.dps2px(200));
  }

  @Override public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {

  }

  @Override public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView,
      YouTubeThumbnailLoader.ErrorReason errorReason) {
    youTubeThumbnailView.setImageResource(R.drawable.no_thumbnail);
  }
}
