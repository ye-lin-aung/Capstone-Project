package com.wecook.yelinaung.bookmark;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.wecook.yelinaung.R;

/**
 * Created by user on 5/31/16.
 */
public class BookmarkFragment extends Fragment {
  @Nullable @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_bookmark, container, false);

    return view;
  }
}
