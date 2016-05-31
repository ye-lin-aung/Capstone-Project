package com.wecook.yelinaung.youtube.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 5/31/16.
 */
public class TabPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
  private List<String> titles = new ArrayList<String>();
  private List<Fragment> fragments = new ArrayList<Fragment>();

  @Override public CharSequence getPageTitle(int position) {
    return titles.get(position);
  }

  public TabPagerAdapter(FragmentManager fm) {
    super(fm);
  }

  public void addTab(String title, Fragment fragment) {
    titles.add(title);
    fragments.add(fragment);
  }

  @Override public Fragment getItem(int position) {
    return fragments.get(position);
  }

  @Override public int getCount() {
    return fragments.size();
  }
}
