package com.wecook.yelinaung.youtube.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 6/5/16.
 */
public class PagerAdapter extends FragmentPagerAdapter {
  private List<Fragment> fragments = new ArrayList<>();

  public void addFragment(Fragment fragment) {
    fragments.add(fragment);
  }

  public PagerAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override public Fragment getItem(int position) {
    return fragments.get(position);
  }

  @Override public int getCount() {
    return fragments.size();
  }
}
