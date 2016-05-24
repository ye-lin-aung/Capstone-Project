package com.wecook.yelinaung.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 5/13/16.
 */
public class RetrofitInstance {
  private static Retrofit mRetrofit;
  public static final String BASE_URL = "http://addb.absolutdrinks.com/";

  public static Retrofit getInstance() {
    if (mRetrofit == null) {
      Retrofit.Builder builder = new Retrofit.Builder();
      builder.addConverterFactory(GsonConverterFactory.create());
      builder.baseUrl(BASE_URL);
      mRetrofit = builder.build();
    }
    return mRetrofit;
  }
}
