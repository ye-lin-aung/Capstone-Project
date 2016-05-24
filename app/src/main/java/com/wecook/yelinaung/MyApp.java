package com.wecook.yelinaung;

import android.app.Application;
import android.content.Context;
import com.facebook.stetho.Stetho;
import com.wecook.yelinaung.component.DaggerDrinksComponent;
import com.wecook.yelinaung.component.DaggerRetrofitComponent;
import com.wecook.yelinaung.component.DrinksComponent;
import com.wecook.yelinaung.component.RetrofitComponent;
import com.wecook.yelinaung.font.CustomFont;
import com.wecook.yelinaung.module.RetrofitModule;

/**
 * Created by user on 5/14/16.
 */
public class MyApp extends Application {
  private RetrofitComponent mRetrofitComponent;
  private DrinksComponent drinksComponent;
  private static Context context;

  @Override public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this);
    mRetrofitComponent = DaggerRetrofitComponent.builder()
        .retrofitModule(new RetrofitModule("http://addb.absolutdrinks.com/"))
        .build();
    drinksComponent = DaggerDrinksComponent.builder().retrofitComponent(mRetrofitComponent).build();
    context = getApplicationContext();
    CustomFont customFontFamily = CustomFont.getInstance();
    // add your custom fonts here with your own custom name.
    customFontFamily.addFont("regular", "AvenirNext-Regular.ttf");
    customFontFamily.addFont("thin", "Roboto-Light.ttf");
    customFontFamily.addFont("bold", "AvenirNext-DemiBold.ttf");
    customFontFamily.addFont("medium", "AvenirNext-Medium.ttf");

  }

  public static Context getContext() {
    return context;
  }

  @Override public void onTerminate() {
    context = null;
    super.onTerminate();
  }

  public DrinksComponent getDrinksComponent() {
    return drinksComponent;
  }

  public RetrofitComponent getmRetrofitComponent() {
    return mRetrofitComponent;
  }
}
