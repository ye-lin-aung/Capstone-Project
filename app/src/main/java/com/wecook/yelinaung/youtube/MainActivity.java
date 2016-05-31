package com.wecook.yelinaung.youtube;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.wecook.yelinaung.R;
import com.wecook.yelinaung.bookmark.BookmarkFragment;
import com.wecook.yelinaung.databinding.ActivityMainBinding;
import com.wecook.yelinaung.youtube.adapters.TabPagerAdapter;
import com.wecook.yelinaung.youtube.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityMainBinding activityMainBinding =
        DataBindingUtil.setContentView(this, R.layout.activity_main);
    TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
    MainFragment mainFragment = MainFragment.getInstance();
    BookmarkFragment bookmarkFragment = new BookmarkFragment();
    tabPagerAdapter.addTab("Drinks", mainFragment);
    tabPagerAdapter.addTab("Bookmarks", bookmarkFragment);
    activityMainBinding.viewPager.setAdapter(tabPagerAdapter);
    activityMainBinding.slidingTabs.setupWithViewPager(activityMainBinding.viewPager);
  }
}

