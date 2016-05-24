package com.wecook.yelinaung;

import android.app.Application;
import com.facebook.stetho.Stetho;
import com.wecook.yelinaung.component.DaggerDrinksComponent;
import com.wecook.yelinaung.component.DaggerRetrofitComponent;
import com.wecook.yelinaung.component.DrinksComponent;
import com.wecook.yelinaung.component.RetrofitComponent;
import com.wecook.yelinaung.module.RetrofitModule;

/**
 * Created by user on 5/14/16.
 */
public class MyApp extends Application {
  private RetrofitComponent mRetrofitComponent;
  private DrinksComponent drinksComponent;

  @Override public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    //new OkHttpClient.Builder().addNetworkInterceptor(new S()).build();
    mRetrofitComponent = DaggerRetrofitComponent.builder()
        .retrofitModule(new RetrofitModule("http://addb.absolutdrinks.com/"))
        .build();
    drinksComponent = DaggerDrinksComponent.builder().retrofitComponent(mRetrofitComponent).build();
  }

  public DrinksComponent getDrinksComponent() {
    return drinksComponent;
  }

  public RetrofitComponent getmRetrofitComponent() {
    return mRetrofitComponent;
  }
}
