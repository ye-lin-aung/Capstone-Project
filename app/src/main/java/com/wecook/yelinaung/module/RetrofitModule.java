package com.wecook.yelinaung.module;

import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by user on 5/14/16.
 */
@Module public class RetrofitModule {
  String baseUrl;

  public RetrofitModule(String baseUrl) {
    this.baseUrl = baseUrl;
  }

  @Provides @Singleton Retrofit provideRetrofit() {
    return new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build();
  }
}
