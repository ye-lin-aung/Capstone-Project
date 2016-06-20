package com.wecook.yelinaung.database;

import android.content.SearchRecentSuggestionsProvider;

/**
 * Created by user on 6/6/16.
 */
public class SearchSuggestProvider extends SearchRecentSuggestionsProvider {

  public final static int MODE = DATABASE_MODE_QUERIES;
  public static final String AUTHORITY = "com.wecook.yelinaung.search";

  public SearchSuggestProvider() {
    setupSuggestions(AUTHORITY, MODE);
  }
}
