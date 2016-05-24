package com.wecook.yelinaung;

import android.content.Context;
import android.support.annotation.NonNull;
import com.wecook.yelinaung.database.DrinksRepository;
import com.wecook.yelinaung.database.local.DrinkLocalDataSource;
import com.wecook.yelinaung.database.remote.DrinksRemoteDataSource;

/**
 * Created by user on 5/21/16.
 */
public class Injection {
  public static DrinksRepository provideDrinkRepo(@NonNull Context context) {
    return DrinksRepository.getInstance(DrinkLocalDataSource.getInstance(context),
        DrinksRemoteDataSource.getInstance(context));
  }
}

