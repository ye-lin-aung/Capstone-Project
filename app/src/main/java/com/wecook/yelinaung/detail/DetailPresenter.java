package com.wecook.yelinaung.detail;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import com.wecook.yelinaung.database.DrinkDbModel;
import com.wecook.yelinaung.database.DrinksRepository;
import com.wecook.yelinaung.database.loaders.DrinkLoader;
import com.wecook.yelinaung.models.Ingredient;
import com.wecook.yelinaung.models.Occasion;
import com.wecook.yelinaung.models.ServedIn;
import com.wecook.yelinaung.models.Skill;
import com.wecook.yelinaung.models.Taste;
import com.wecook.yelinaung.models.Tool;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 6/12/16.
 */
public class DetailPresenter
    implements DetailContract.Presenter, LoaderManager.LoaderCallbacks<DrinkDbModel> {
  private DrinksRepository drinksRepository;
  private DrinkLoader drinkLoader;
  private LoaderManager loaderManager;
  private DetailContract.View detailView;
  private DrinkDbModel drinkDbModel;
  public static final int LOADER_ID = 4;

  public DetailPresenter(DrinkLoader drinkLoader, DrinksRepository drinksRepository,
      LoaderManager loaderManager, DetailContract.View detailView) {
    this.drinkLoader = drinkLoader;
    this.drinksRepository = drinksRepository;
    this.loaderManager = loaderManager;
    this.detailView = detailView;
  }

  @Override public void start() {
    loaderManager.initLoader(LOADER_ID, null, this);
  }

  @Override public Loader<DrinkDbModel> onCreateLoader(int id, Bundle args) {
    return drinkLoader;
  }

  @Override public void onLoadFinished(Loader<DrinkDbModel> loader, DrinkDbModel data) {
    this.drinkDbModel = data;
    detailView.showTitle(data.getName());
    detailView.showImage(data.getId());
    detailView.showVideo(data.getVideo());

    detailView.showAlcohol(data.getIsAlcohol());
    detailView.showAlcohol(data.getIsCarbon());
    detailView.showHot(data.getIsHot());
    detailView.prepareScroll();
    detailView.showRating(data.getRating());
    detailView.showColor(data.getColor());
    List<ServedIn> servedIns = new ArrayList<>();
    servedIns.add(data.getServedIns());
    loadServeIns(servedIns);
    loadTools(data.getTools());
    loadIngrediants(data.getIngredients());
    loadTaste(data.getTastes());
    loadOccasions(data.getOccasions());
    loadSkill(data.getSkills());
    loadDescription(data.getDescription());
    prepareLike(data.getBookmark());
  }

  public void loadDescription(String description) {

    if (description != null && description.length() > 0) {
      detailView.showDescription(description);
    } else {
      detailView.showNoDescription();
    }
  }

  @Override public void loadOccasions(List<Occasion> oca) {
    if (oca != null) {
      if (oca.size() == 0) {
        detailView.showNoOccasionText();
      } else {
        detailView.showOccasionText(oca);
      }
    } else {
      detailView.showNoOccasionText();
    }
  }

  public void processLike() {
    if (drinkDbModel.getBookmark() == 0) {
      drinkDbModel.setBookmark(1);
      detailView.setLike(true);
      drinksRepository.saveBookmark(drinkDbModel);
    } else {
      detailView.setLike(false);
      drinkDbModel.setBookmark(0);
      drinksRepository.saveBookmark(drinkDbModel);
    }
  }

  public void prepareLike(int like) {
    if (like == 0) {
      detailView.setLike(false);
    } else {
      detailView.setLike(true);
    }
  }

  @Override public void loadTaste(List<Taste> tastes) {
    if (tastes != null) {
      if (tastes.size() == 0) {
        detailView.showNoTaste();
      } else {
        detailView.showTastes(tastes);
      }
    } else {
      detailView.showNoTaste();
    }
  }

  @Override public void onLoaderReset(Loader<DrinkDbModel> loader) {

  }

  @Override public void loadTools(List<Tool> list) {
    if (list != null) {
      if (list.size() == 0) {
        detailView.showNoTools();
      } else {
        detailView.showTools(toolsToInner(list));
      }
    } else {
      detailView.showNoTools();
    }
  }

  private List<InnerDataModel> toolsToInner(List<Tool> list) {
    List<InnerDataModel> innerDataModels = new ArrayList<>();
    for (Tool ingredient : list) {
      innerDataModels.add(new InnerDataModel(ingredient));
    }
    return innerDataModels;
  }

  @Override public void loadIngrediants(List list) {
    if (list != null) {
      if (list.size() == 0) {
        detailView.showNoIngrediants();
      } else {
        detailView.showIngrediants(ingrediantsToInner(list));
      }
    } else {
      detailView.showNoIngrediants();
    }
  }



  @Override public void loadServeIns(List<ServedIn> list) {
    if (list != null) {
      if (list.size() == 0) {
        detailView.showNoServeIn();
      } else {
        detailView.showServeIn(serveInToInner(list));
      }
    } else {
      detailView.showNoServeIn();
    }
  }

  private List<InnerDataModel> serveInToInner(List<ServedIn> list) {
    List<InnerDataModel> innerDataModels = new ArrayList<>();
    for (ServedIn ser : list) {
      innerDataModels.add(new InnerDataModel(ser));
    }
    return innerDataModels;
  }

  private List<InnerDataModel> ingrediantsToInner(List<Ingredient> list) {
    List<InnerDataModel> innerDataModels = new ArrayList<>();
    for (Ingredient ingredient : list) {
      innerDataModels.add(new InnerDataModel(ingredient));
    }
    return innerDataModels;
  }

  @Override public void loadSkill(Skill skill) {

    if (skill != null) {

      detailView.showSkill(skill);
    } else {
      detailView.showNoSkill();
    }
  }
}
