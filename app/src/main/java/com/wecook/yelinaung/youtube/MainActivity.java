package com.wecook.yelinaung.youtube;

import android.content.Intent;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import com.wecook.yelinaung.R;
import com.wecook.yelinaung.bookmark.fragments.BookmarkFragment;
import com.wecook.yelinaung.databinding.ActivityMainBinding;
import com.wecook.yelinaung.font.CustomFont;
import com.wecook.yelinaung.widget.HomeScreenWidget;
import com.wecook.yelinaung.youtube.adapters.TabPagerAdapter;
import com.wecook.yelinaung.youtube.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //if (Build.VERSION.SDK_INT >= 21) {
    //
    //  overridePendingTransition(Explode.MODE_IN, Explode.MODE_OUT);
    //}
    ActivityMainBinding activityMainBinding =
        DataBindingUtil.setContentView(this, R.layout.activity_main);

    setSupportActionBar(activityMainBinding.toolbar);
    getSupportActionBar().setDisplayShowHomeEnabled(false);
    getSupportActionBar().setDisplayShowTitleEnabled(false);

    TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
    MainFragment mainFragment = new MainFragment();
    BookmarkFragment bookmarkFragment = new BookmarkFragment();
    tabPagerAdapter.addTab(getString(R.string.drinks_tab), mainFragment);
    tabPagerAdapter.addTab(getString(R.string.fav_tab), bookmarkFragment);
    activityMainBinding.viewPager.setAdapter(tabPagerAdapter);

    activityMainBinding.slidingTabs.setupWithViewPager(activityMainBinding.viewPager);
    activityMainBinding.slidingTabs.getTabAt(0).setIcon(R.drawable.cocktail_svg);

    Drawable drawable = getResources().getDrawable(R.drawable.ic_favorite_black_24dp);
    PorterDuff.Mode mMode = PorterDuff.Mode.SRC_ATOP;
    drawable.setColorFilter(Color.WHITE, mMode);
    activityMainBinding.slidingTabs.getTabAt(1).setIcon(drawable);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.search) {
      onSearchRequested();
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override protected void onPause() {

    Intent intent_meeting_update = new Intent(this, HomeScreenWidget.class);
    intent_meeting_update.setAction(HomeScreenWidget.UPDATE);
    sendBroadcast(intent_meeting_update);
    super.onPause();
  }

  @Override protected void onResume() {

    super.onResume();
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
    //SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
    //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
    //searchView.setIconifiedByDefault(false);

    return true;
  }

  @BindingAdapter("app:font") public static void loadFont(TextView textView, String fontName) {
    textView.setTypeface(CustomFont.getInstance().getFont(fontName));
  }
}

