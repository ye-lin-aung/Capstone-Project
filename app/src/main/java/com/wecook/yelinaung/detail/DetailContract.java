package com.wecook.yelinaung.detail;

import com.wecook.BasePresenter;
import com.wecook.yelinaung.BaseView;
import com.wecook.yelinaung.models.Ingredient;
import com.wecook.yelinaung.models.Occasion;
import com.wecook.yelinaung.models.ServedIn;
import com.wecook.yelinaung.models.Skill;
import com.wecook.yelinaung.models.Taste;
import com.wecook.yelinaung.models.Tool;
import java.util.List;

/**
 * Created by user on 6/4/16.
 */
public interface DetailContract {
  interface View extends BaseView<Presenter> {
    @Override void setPresenter(Presenter Presenter);

    void showTitle(String titleName);

    void showImage(String imageUrl);

    void prepareScroll();

    void showRating(int rating);

    void setLike(boolean like);

    void showColor(String hex);

    void showCarbon(boolean carbon);

    void showAlcohol(boolean alco);

    void showDescription(String description);

    void showOccasionText(List<Occasion> occasions);

    void showNoOccasionText();

    void showNoDescription();

    void showHot(boolean hot);

    void showVideo(String id);

    void showTastes(List<Taste> tastes);

    void showNoTaste();

    void showSkill(Skill skill);

    void showNoSkill();

    void showIngrediants(List<InnerDataModel> innerDataModels);

    void showNoIngrediants();

    void showTools(List<InnerDataModel> innerDataModels);

    void showNoTools();

    void showServeIn(List<InnerDataModel> innerDataModels);

    void showNoServeIn();
  }

  interface Presenter extends BasePresenter {
    @Override void start();

    void loadOccasions(List<Occasion> list);

    void loadTaste(List<Taste> tastes);

    void loadSkill(Skill skill);

    void loadIngrediants(List<Ingredient> ingredients);

    void loadTools(List<Tool> tools);

    void loadServeIns(List<ServedIn> servedIns);
  }
}
