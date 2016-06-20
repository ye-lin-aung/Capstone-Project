package com.wecook.yelinaung.search;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.wecook.yelinaung.Injection;
import com.wecook.yelinaung.database.DrinkDbModel;
import com.wecook.yelinaung.database.DrinksRepository;
import java.util.List;

/**
 * Created by user on 6/9/16.
 */
public class SearchFragment extends Fragment {
  private DrinksRepository drinksRepository;
  public static final String BUNDLE_KEY = "query_key";
  public static SearchFragment searchFragment;

  interface SearchCallbacks {
    void onPreExecute();

    void onCancelled();

    void onPostExecute(List<DrinkDbModel> drinks);
  }

  private SearchCallbacks searchCallbacks;
  private SearchTask searchTask;

  @Override public void onAttach(Context activity) {
    super.onAttach(activity);
    searchCallbacks = (SearchCallbacks) ((SearchActivity) activity).getPresenter();
  }

  public static SearchFragment getInstance(String query) {

    searchFragment = new SearchFragment();
    Bundle bundle = new Bundle();
    bundle.putString(BUNDLE_KEY, query);
    searchFragment.setArguments(bundle);

    return searchFragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Retain this fragment across configuration changes.
    setRetainInstance(true);

    // Create and execute the background task.
    Bundle bundle = getArguments();
    String query = bundle.getString(BUNDLE_KEY);
    drinksRepository = Injection.provideDrinkRepo(getActivity());
    searchTask = new SearchTask();
    searchTask.onCancelled();
    searchTask.execute(query);
  }

  @Override public void onDetach() {
    super.onDetach();
    searchCallbacks = null;
  }

  public class SearchTask extends AsyncTask<String, Void, List<DrinkDbModel>> {
    @Override protected void onPreExecute() {
      if (searchCallbacks != null) {
        searchCallbacks.onPreExecute();
      }
    }

    @Override protected void onPostExecute(List<DrinkDbModel> drinkDbModels) {
      if (!isCancelled()) {
        if (searchCallbacks != null) {
          searchCallbacks.onPostExecute(drinkDbModels);
          
        }
      }
    }

    @Override protected void onCancelled() {
      if (searchCallbacks != null) {
        searchCallbacks.onCancelled();
      }
    }

    @Override protected List<DrinkDbModel> doInBackground(String... strings) {
      return drinksRepository.searchDrinks(strings[0]);
    }
  }
}
