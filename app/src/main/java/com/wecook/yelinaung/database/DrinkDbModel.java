package com.wecook.yelinaung.database;

import com.google.common.base.Objects;
import com.wecook.yelinaung.models.Action;
import com.wecook.yelinaung.models.Ingredient;
import com.wecook.yelinaung.models.Occasion;
import com.wecook.yelinaung.models.ServedIn;
import com.wecook.yelinaung.models.Skill;
import com.wecook.yelinaung.models.Taste;
import com.wecook.yelinaung.models.Tool;
import java.util.List;
import javax.annotation.Nullable;

/**
 * Created by user on 5/21/16.
 */
public class DrinkDbModel {

  private String id;
  @Nullable private String name;
  private int rating;
  private String color;
  private String video;
  private int bookmark;
  private boolean isHot;
  private boolean isAlcohol;
  private boolean isCarbon;
  private List<Tool> tools;
  private List<Ingredient> ingredients;
  private List<Action> actions;
  private List<Taste> tastes;
  private Skill skills;
  private ServedIn servedIns;
  private List<Occasion> occasions;

  public ServedIn getServedIns() {
    return servedIns;
  }

  public Skill getSkills() {
    return skills;
  }

  public List<Taste> getTastes() {
    return tastes;
  }

  public List<Tool> getTools() {
    return tools;
  }

  public List<Ingredient> getIngredients() {
    return ingredients;
  }

  public List<Action> getActions() {
    return actions;
  }

  public List<Occasion> getOccasions() {
    return occasions;
  }

  public boolean getIsCarbon() {
    return isCarbon;
  }

  public boolean getIsAlcohol() {
    return isAlcohol;
  }

  public boolean getIsHot() {
    return isHot;
  }

  @Nullable private String description;

  public int getBookmark() {
    return bookmark;
  }

  public String getColor() {
    return color;
  }

  @Nullable public String getDescription() {
    return description;
  }

  public void setBookmark(int bookmark) {
    this.bookmark = bookmark;
  }

  public String getId() {
    return id;
  }

  @Nullable public String getName() {
    return name;
  }

  public void setName(@Nullable String name) {
    this.name = name;
  }

  public void setVideo(String video) {
    this.video = video;
  }

  public int getRating() {
    return rating;
  }

  @Override public String toString() {
    return Objects.toStringHelper(DrinkDbModel.class)
        .add("id", id)
        .add("name", name)
        .add("desc", description)
        .add("color", color)
        .add("bookmark", bookmark)
        .add("video", video)
        .toString();
  }

  public DrinkDbModel() {
  }

  public DrinkDbModel(String color, String description, String id, String name, int rating,
      String video, boolean isHot, boolean isAlcohol, boolean isCarbon, List<Tool> tools, List<Ingredient> ingredients, List<Action> actions, List<Taste> tastes,
      Skill skills, ServedIn servedIns, List<Occasion> occasions) {
    this.color = color;
    this.description = description;
    this.id = id;
    this.name = name;
    this.rating = rating;
    this.video = video;
    this.isAlcohol = isAlcohol;
    this.isHot = isHot;
    this.isCarbon = isCarbon;
    this.tools = tools;
    this.ingredients = ingredients;
    this.actions = actions;
    this.tastes = tastes;
    this.occasions = occasions;
    this.servedIns = servedIns;
    this.skills = skills;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    DrinkDbModel drinkDbModel = (DrinkDbModel) o;

    return Objects.equal(id, drinkDbModel.id)
        && Objects.equal(name, drinkDbModel.name)
        && Objects.equal(rating, drinkDbModel.rating)
        && Objects.equal(color, drinkDbModel.color)
        && Objects.equal(bookmark, drinkDbModel.bookmark)
        && Objects.equal(description, drinkDbModel.description);
  }

  @Override public int hashCode() {
    return Objects.hashCode(id, name, rating, color, video, bookmark, description);
  }

  public String getVideo() {
    if (video != null && !video.equals("")) {
      return video;
    }
    return name;
  }

  public DrinkDbModel(int bookmark, String color, String description, String id, String name,
      int rating, String video, boolean isHot, boolean isAlcohol, boolean isCarbon,
      List<Tool> tools, List<Ingredient> ingredients, List<Action> actions, List<Taste> tastes,
      Skill skills,ServedIn servedIns, List<Occasion> occasions) {
    this.bookmark = bookmark;
    this.color = color;
    this.description = description;
    this.id = id;
    this.name = name;
    this.rating = rating;
    this.video = video;
    this.isAlcohol = isAlcohol;
    this.isHot = isHot;
    this.isCarbon = isCarbon;
    this.tools = tools;
    this.ingredients = ingredients;
    this.actions = actions;
    this.tastes = tastes;
    this.occasions = occasions;
    this.servedIns = servedIns;
    this.skills = skills;
  }
}
