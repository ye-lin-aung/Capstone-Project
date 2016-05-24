package com.wecook.yelinaung.module;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by user on 5/14/16.
 */
@Module public class AppModule {
  Application application;

  public AppModule(Application application) {
    this.application = application;
  }

  @Provides @Singleton Application provideApplication() {
    return application;
  }
}
