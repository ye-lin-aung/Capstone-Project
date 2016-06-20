package com.wecook.yelinaung.network;

import com.wecook.yelinaung.models.Drinks;
import com.wecook.yelinaung.models.Result;
import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by user on 5/13/16.
 */
public interface DrinksService {
  //http://addb.absolutdrinks.com/drinks?apikey=f0052aa8b4ae4973963560c42ad4cce2&apisecret=fb28ccfbec32401180c177bda7731689
  @GET("/drinks") Call<Drinks> getDrinks(@QueryMap(encoded = true) Map<String, String> keys);

  @GET Call<Drinks> getDrinks(@Url String url);

  //http://addb.absolutdrinks.com/drinks/pennsylvania?apikey=f0052aa8b4ae4973963560c42ad4cce2&apisecret=fb28ccfbec32401180c177bda7731689
  @GET("/drinks/{drink_id}") Call<Result> getDrinkById(@Path("drink_id") String id,
      @QueryMap(encoded = true) Map<String, String> keys);




  @GET("/quickSearch/drinks/{search}") Call<Drinks> searchDrinks(@Path("search") String query,
      @QueryMap(encoded = true) Map<String, String> keys);
}
