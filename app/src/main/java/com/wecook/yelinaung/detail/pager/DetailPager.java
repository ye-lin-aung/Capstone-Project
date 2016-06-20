package com.wecook.yelinaung.detail.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;
import com.wecook.yelinaung.detail.DetailFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 6/11/16.
 */
public class DetailPager extends FragmentStatePagerAdapter {
  public List<Fragment> list = new ArrayList<>();

  public void add(Fragment fragment, String id) {
    list.add(fragment);
  }


  public DetailPager(FragmentManager fm) {
    super(fm);
  }

  @Override public Fragment getItem(int position) {
    return list.get(position);
  }

  @Override public int getCount() {
    return list.size();
  }
}
