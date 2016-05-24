package com.wecook.yelinaung.youtube;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import com.wecook.yelinaung.Injection;
import com.wecook.yelinaung.R;
import com.wecook.yelinaung.database.DrinkDbModel;
import com.wecook.yelinaung.database.DrinksRepository;
import com.wecook.yelinaung.database.loaders.DrinksLoader;
import java.util.List;

public class MainActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks<List<DrinkDbModel>> {

  private DrinksLoader drinksLoader;
  private DrinksRepository repository;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  @Override protected void onStart() {
    super.onStart();

    repository = Injection.provideDrinkRepo(getApplicationContext());

    drinksLoader = new DrinksLoader(getApplicationContext(), repository);
    getSupportLoaderManager().initLoader(1, null, this);
  }

  @Override public Loader<List<DrinkDbModel>> onCreateLoader(int id, Bundle args) {
    System.out.println("SDASD");
    return drinksLoader;
  }

  @Override public void onLoadFinished(Loader<List<DrinkDbModel>> loader, List<DrinkDbModel> data) {
    for (DrinkDbModel d : data) {
      Log.d("DATA", d.getName());
    }
  }

  public void add(View view) {
    repository.refreshDrinks();
  }

  @Override public void onLoaderReset(Loader<List<DrinkDbModel>> loader) {

    System.out.println("RESET");
  }
}

