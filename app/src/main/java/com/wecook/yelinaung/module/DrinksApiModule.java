package com.wecook.yelinaung.module;

import com.wecook.yelinaung.network.DrinksService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by user on 5/14/16.
 */

@Module public class DrinksApiModule {

  @Provides public DrinksService provideDrinksService(Retrofit retrofit) {
    return retrofit.create(DrinksService.class);
  }
}
