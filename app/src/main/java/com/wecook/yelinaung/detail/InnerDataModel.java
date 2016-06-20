package com.wecook.yelinaung.detail;

import com.wecook.yelinaung.models.Action;
import com.wecook.yelinaung.models.Ingredient;
import com.wecook.yelinaung.models.Occasion;
import com.wecook.yelinaung.models.ServedIn;
import com.wecook.yelinaung.models.Taste;
import com.wecook.yelinaung.models.Tool;

/**
 * Created by user on 6/19/16.
 */
public class InnerDataModel {
  private String id;
  private String title;
  private String imageUrl;

  public static final String INGREDIANTS_IN_IMAGE = "http://assets.absolutdrinks.com/ingredients/";
  public static final String TOOLS_IN_IMAGE = "http://assets.absolutdrinks.com/tools/";
  public static final String SERVE_IN_IMAGE = "http://assets.absolutdrinks.com/glasses/";

  public static final String JPG = ".jpg";

  public static final String PNG = ".png";

  public InnerDataModel() {
  }

  public InnerDataModel(Occasion occasion) {
    this.id = occasion.getId();
    this.title = occasion.getText();
    //TODO ADD IMAGE URL

  }

  public InnerDataModel(ServedIn servedIn) {
    this.id = servedIn.getId();
    this.title = servedIn.getText();
    this.imageUrl = SERVE_IN_IMAGE + servedIn.getId() + JPG;
  }

  public InnerDataModel(Action action) {
    this.id = action.getId();
    this.title = action.getText();
    //TODO ADD IMAGE URL
  }

  public InnerDataModel(Tool tool) {
    this.id = tool.getId();
    this.title = tool.getText();
    this.imageUrl = TOOLS_IN_IMAGE + tool.getId() + JPG;
  }

  public InnerDataModel(Ingredient ingredient) {
    this.id = ingredient.getId();
    this.title = ingredient.getTextPlain();
    this.imageUrl = INGREDIANTS_IN_IMAGE + ingredient.getId() + JPG;
    //TODO ADD IMAGE URL

  }

  public InnerDataModel(Taste taste) {
    this.id = taste.getId();
    this.title = taste.getText();
    //TODO ADD IMAGE URL

  }

  public String getId() {
    return id;
  }

  public InnerDataModel(String id, String title, String imageUrl) {
    this.id = id;
    this.title = title;
    this.imageUrl = imageUrl;
  }

  public String getTitle() {
    return title;
  }

  public String getImageUrl() {
    return imageUrl;
  }
}
