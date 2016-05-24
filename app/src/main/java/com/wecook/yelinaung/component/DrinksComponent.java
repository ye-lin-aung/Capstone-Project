package com.wecook.yelinaung.component;

import com.wecook.yelinaung.database.remote.DrinkRemoteDataSource;
import com.wecook.yelinaung.module.DrinksApiModule;
import com.wecook.yelinaung.scope.UserScope;
import dagger.Component;

/**
 * Created by user on 5/21/16.
 */
@UserScope @Component(modules = DrinksApiModule.class, dependencies = RetrofitComponent.class)
public interface DrinksComponent {
  void inject(DrinkRemoteDataSource drinkRemoteDataSource);
}


