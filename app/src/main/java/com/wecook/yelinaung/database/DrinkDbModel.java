package com.wecook.yelinaung.database;

import com.google.common.base.Objects;
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

  public String getId() {
    return id;
  }

  @Nullable public String getName() {
    return name;
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
      String video) {
    this.color = color;
    this.description = description;
    this.id = id;
    this.name = name;
    this.rating = rating;
    this.video = video;
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
      int rating, String video) {
    this.bookmark = bookmark;
    this.color = color;
    this.description = description;
    this.id = id;
    this.name = name;
    this.rating = rating;
    this.video = video;
  }
}
