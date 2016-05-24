package com.wecook.yelinaung.database.remote;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.wecook.yelinaung.MyApp;
import com.wecook.yelinaung.database.DrinkDbModel;
import com.wecook.yelinaung.database.DrinksContract;
import com.wecook.yelinaung.database.DrinksDatasource;
import com.wecook.yelinaung.models.Drinks;
import com.wecook.yelinaung.models.Result;
import com.wecook.yelinaung.network.ApiKeyMap;
import com.wecook.yelinaung.network.DrinksService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by user on 5/21/16.
 */
public class DrinkRemoteDataSource implements DrinksDatasource {
  @Inject DrinksService service;

  private static Map<String, DrinkDbModel> drinksServiceData = new LinkedHashMap<>();

  private DrinkDbModel drinkDbModel;
  private final String[] PROJECTIONS = {
      DrinksContract.DrinksEntry.ID, DrinksContract.DrinksEntry.NAME,
      DrinksContract.DrinksEntry.VIDEO, DrinksContract.DrinksEntry.COLOR,
      DrinksContract.DrinksEntry.RATING, DrinksContract.DrinksEntry.BOOKMARK,
      DrinksContract.DrinksEntry.DESCRIPTION
  };

  private static DrinkRemoteDataSource INSTANCE;
  private Context context;
  private static final List<DrinkDbModel> drinkList = new ArrayList<>();

  private DrinkRemoteDataSource(@NonNull Context context) {
    this.context = context;
    checkNotNull(context);
  }

  @Override public List<DrinkDbModel> refreshCache() {
    return null;
  }

  public static DrinkRemoteDataSource getInstance(@NonNull Context context) {
    if (INSTANCE == null) {
      INSTANCE = new DrinkRemoteDataSource(context);
    }
    return INSTANCE;
  }

  @Override public void deleteAllDrinks() {
    drinkList.clear();
  }

  @Override public void deleteDrink(@NonNull String drinkId) {
    drinkList.remove(drinkId);
  }

  @Nullable @Override public DrinkDbModel getDrink(@NonNull String drinkId) {

    Call<Result> resultCall = service.getDrinkById(drinkId, ApiKeyMap.getMap());
    resultCall.enqueue(new Callback<Result>() {
      @Override public void onResponse(Call<Result> call, Response<Result> response) {
        Result result = response.body();
        drinkDbModel = TransformModels(result);
      }

      @Override public void onFailure(Call<Result> call, Throwable t) {

      }
    });
    return drinkDbModel;
  }

  @Override public List<DrinkDbModel> getDrinks() {
    ((MyApp) context).getDrinksComponent().inject(this);

    Cursor cursor = context.getContentResolver()
        .query(DrinksContract.DrinksEntry.DRINKS_URI, PROJECTIONS, null, null, "");
    int pageCount;
    Map<String, String> keyPage = ApiKeyMap.getMap();
    if (cursor != null && cursor.getCount() > 0) {
      pageCount = cursor.getCount();
    } else {
      pageCount = 0;
    }
    try {

      keyPage.put("start", Integer.toString(pageCount));
      Call<Drinks> call = service.getDrinks(keyPage);
      Drinks drinks = call.execute().body();

      List<Result> list = drinks.getResult();
      for (int i = 0; i < list.size(); i++) {
        DrinkDbModel drinkDbModel = TransformModels(list.get(i));
        drinkList.add(drinkDbModel);
      }
    } catch (IOException ioe) {

    }

    return drinkList;
  }

  public DrinkDbModel TransformModels(Result drinks) {
    return new DrinkDbModel(drinks.getColor(), drinks.getDescription(), drinks.getId(),
        drinks.getName(), drinks.getRating(), drinks.getVideos().get(0).getVideo());
  }

  @Override public void refreshDrinks() {

  }

  @Override public void saveDrinks(@NonNull DrinkDbModel drinkDbModel) {
    if (!drinkList.contains(drinkDbModel)) {
      drinkList.add(drinkDbModel);
    }
  }
}
