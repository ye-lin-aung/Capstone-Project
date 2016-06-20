package com.wecook.yelinaung.database.remote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import com.wecook.yelinaung.MyApp;
import com.wecook.yelinaung.database.DrinkDbModel;
import com.wecook.yelinaung.database.DrinksContract;
import com.wecook.yelinaung.database.DrinksDatasource;
import com.wecook.yelinaung.models.Drinks;
import com.wecook.yelinaung.models.Result;
import com.wecook.yelinaung.network.ApiKeyMap;
import com.wecook.yelinaung.network.DrinksService;
import com.wecook.yelinaung.util.PrefUtil;
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
public class DrinksRemoteDataSource implements DrinksDatasource {
  @Inject DrinksService service;

  private static Map<String, DrinkDbModel> drinksServiceData = new LinkedHashMap<>();

  private DrinkDbModel drinkDbModel;
  private final String[] PROJECTIONS = {
      DrinksContract.DrinksEntry.ID, DrinksContract.DrinksEntry.NAME,
      DrinksContract.DrinksEntry.VIDEO, DrinksContract.DrinksEntry.COLOR,
      DrinksContract.DrinksEntry.RATING, DrinksContract.DrinksEntry.BOOKMARK,
      DrinksContract.DrinksEntry.DESCRIPTION
  };

  private static DrinksRemoteDataSource INSTANCE;
  private Context context;
  private static final List<DrinkDbModel> drinkList = new ArrayList<>();
  private static final List<DrinkDbModel> searchList = new ArrayList<>();

  private DrinksRemoteDataSource(@NonNull Context context) {
    this.context = context;
    checkNotNull(context);
    ((MyApp) context).getDrinksComponent().inject(this);
  }

  public static DrinksRemoteDataSource getInstance(@NonNull Context context) {
    if (INSTANCE == null) {
      INSTANCE = new DrinksRemoteDataSource(context);
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

    //Cursor cursor = context.getContentResolver()
    //   .query(DrinksContract.DrinksEntry.DRINKS_URI, PROJECTIONS, null, null, "");
    //int pageCount;
    //Map<String, String> keyPage = ApiKeyMap.getMap();
    Call<Drinks> call = service.getDrinks(PrefUtil.getUrl(context));
    try {

      Drinks drinks = call.execute().body();

      List<Result> list = drinks.getResult();
      for (int i = 0; i < list.size(); i++) {

        DrinkDbModel drinkDbModel = TransformModels(list.get(i));
        drinkList.add(drinkDbModel);
      }
      PrefUtil.setUrl(drinks.getNext(), context);
      PrefUtil.setTotalCount(drinks.getTotalResult(), context);
    } catch (IOException ioe) {

    }
    return drinkList;
  }

  @Override public List<DrinkDbModel> searchDrinks(String query) {
    searchList.clear();
    Map<String, String> keyPage = ApiKeyMap.getSearchMap();
    keyPage.put("start", "0");
    Call<Drinks> call = service.searchDrinks(query, keyPage);
    try {
      List<Result> list = call.execute().body().getResult();
      Log.d("C", call.request().toString());
      for (int i = 0; i < list.size(); i++) {
        DrinkDbModel drinkDbModel = TransformModels(list.get(i));
        Log.d("D", drinkDbModel.getName());
        searchList.add(drinkDbModel);
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }

    return searchList;
  }

  @Override public List<DrinkDbModel> getBookmarks() {
    return null;
  }

  @Override public void saveBookmark(@NonNull DrinkDbModel drinkDbModel) {

  }

  public DrinkDbModel TransformModels(Result drinks) {
    if (drinks.getVideos().size() > 0) {
      return new DrinkDbModel(drinks.getColor(), drinks.getDescriptionPlain(), drinks.getId(),
          drinks.getName(), drinks.getRating(), drinks.getVideos().get(0).getVideo(),
          drinks.getIsHot(), drinks.getIsAlcoholic(), drinks.getIsCarbonated(), drinks.getTools(),
          drinks.getIngredients(), drinks.getActions(), drinks.getTastes(), drinks.getSkill(),
          drinks.getServedIn(), drinks.getOccasions());
    } else {
      return new DrinkDbModel(drinks.getColor(), drinks.getDescriptionPlain(), drinks.getId(),
          drinks.getName(), drinks.getRating(), drinks.getVideos().get(0).getVideo(),
          drinks.getIsHot(), drinks.getIsAlcoholic(), drinks.getIsCarbonated(), drinks.getTools(),
          drinks.getIngredients(), drinks.getActions(), drinks.getTastes(), drinks.getSkill(),
          drinks.getServedIn(), drinks.getOccasions());
    }
  }

  @Override public void refreshDrinks() {

  }

  @Override public void saveDrinks(@NonNull DrinkDbModel drinkDbModel) {
    if (!drinkList.contains(drinkDbModel)) {
      drinkList.add(drinkDbModel);
    }
  }
}
