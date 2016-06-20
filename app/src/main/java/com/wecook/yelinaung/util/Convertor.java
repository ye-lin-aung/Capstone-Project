package com.wecook.yelinaung.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.repacked.antlr.v4.runtime.misc.NotNull;
import com.wecook.yelinaung.models.Action;
import com.wecook.yelinaung.models.Ingredient;
import com.wecook.yelinaung.models.Occasion;
import com.wecook.yelinaung.models.ServedIn;
import com.wecook.yelinaung.models.Skill;
import com.wecook.yelinaung.models.Taste;
import com.wecook.yelinaung.models.Tool;
import java.util.List;

/**
 * Created by user on 6/18/16.
 */
public class Convertor {
  public static List<Action> jsonToActions(String json) {
    Gson gson = new Gson();

    return gson.fromJson(json, new TypeToken<List<Action>>() {
    }.getType());
  }

  public static List<Taste> jsonToTastes(String json) {
    Gson gson = new Gson();

    return gson.fromJson(json, new TypeToken<List<Taste>>() {
    }.getType());
  }

  public static List<Tool> jsonToTools(String json) {
    Gson gson = new Gson();

    return gson.fromJson(json, new TypeToken<List<Tool>>() {
    }.getType());
  }

  public static List<Occasion> jsonToOccasions(String json) {
    Gson gson = new Gson();

    return gson.fromJson(json, new TypeToken<List<Occasion>>() {
    }.getType());
  }

  public static List<Ingredient> jsonToIngredients(String json) {
    Gson gson = new Gson();
    return gson.fromJson(json, new TypeToken<List<Ingredient>>() {
    }.getType());
  }

  public static Skill jsonToSkill(String json) {
    Gson gson = new Gson();
    return gson.fromJson(json, new TypeToken<Skill>() {
    }.getType());
  }

  public static ServedIn jsonToServeIn(String json) {
    Gson gson = new Gson();
    return gson.fromJson(json, new TypeToken<ServedIn>() {
    }.getType());
  }
  @NotNull
  public static String actionsToJson(List<Action> list) {
    Gson gson = new Gson();
    return gson.toJson(list).toString();
  }

  public static String tastesToJson(List<Taste> list) {
    Gson gson = new Gson();
    return gson.toJson(list).toString();
  }

  public static String toolsToJson(List<Tool> tools) {
    Gson gson = new Gson();
    return gson.toJson(tools).toString();
  }

  public static String occasionsToJson(List<Occasion> occasions) {
    Gson gson = new Gson();
    return gson.toJson(occasions).toString();
  }

  public static String ingrediantsToJson(List<Ingredient> ingredients) {
    Gson gson = new Gson();
    return gson.toJson(ingredients).toString();
  }

  public static String skillToJson(Skill skills) {
    Gson gson = new Gson();
    return gson.toJson(skills).toString();
  }

  public static String serveInToJson(ServedIn servedIns) {
    Gson gson = new Gson();
    return gson.toJson(servedIns).toString();
  }
}
