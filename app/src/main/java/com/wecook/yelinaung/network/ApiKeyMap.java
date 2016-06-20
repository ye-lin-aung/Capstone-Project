package com.wecook.yelinaung.network;

import com.wecook.yelinaung.BuildConfig;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 5/13/16.
 */
public class ApiKeyMap {
  private static Map<String, String> map = new HashMap<>();
  private static final String PAGE_SIZE = "30";
  private static final String SEARCH_SIZE = "1000";

  public static void addToMap(String key, String value) {
    map.put(key, value);
  }

  public static Map<String, String> getMap() {
    map.put("apiKey", BuildConfig.API_KEY);
    map.put("apiSecret", BuildConfig.API_SECRET);
    map.put("pageSize", PAGE_SIZE);
    return map;
  }

  public static Map<String, String> getSearchMap() {
    map.put("apiKey", BuildConfig.API_KEY);
    map.put("apiSecret", BuildConfig.API_SECRET);
    map.put("pageSize", SEARCH_SIZE);
    return map;
  }

}
