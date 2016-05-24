package com.wecook.yelinaung.component;

import com.wecook.yelinaung.module.AppModule;
import com.wecook.yelinaung.module.RetrofitModule;
import dagger.Component;
import javax.inject.Singleton;
import retrofit2.Retrofit;

/**
 * Created by user on 5/14/16.
 */
@Singleton @Component(modules = { RetrofitModule.class, AppModule.class })
public interface RetrofitComponent {
  Retrofit retrofit();
}
