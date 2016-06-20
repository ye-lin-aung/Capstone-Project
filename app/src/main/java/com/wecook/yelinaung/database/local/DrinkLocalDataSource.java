package com.wecook.yelinaung.database.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.wecook.yelinaung.database.DrinkDbModel;
import com.wecook.yelinaung.database.DrinksContract;
import com.wecook.yelinaung.database.DrinksDatasource;
import com.wecook.yelinaung.models.Action;
import com.wecook.yelinaung.models.Ingredient;
import com.wecook.yelinaung.models.Occasion;
import com.wecook.yelinaung.models.ServedIn;
import com.wecook.yelinaung.models.Skill;
import com.wecook.yelinaung.models.Taste;
import com.wecook.yelinaung.models.Tool;
import com.wecook.yelinaung.util.Convertor;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.wecook.yelinaung.database.DrinksContract.DrinksEntry;

/**
 * Created by user on 5/21/16.
 */
public class DrinkLocalDataSource implements DrinksDatasource {
  private static DrinkLocalDataSource INSTANCE;
  private Context context;
  private final String[] PROJECTIONS = {
      DrinksEntry.ID, DrinksEntry.NAME, DrinksEntry.VIDEO, DrinksEntry.COLOR, DrinksEntry.RATING,
      DrinksEntry.BOOKMARK, DrinksEntry.DESCRIPTION, DrinksEntry.IS_HOT, DrinksEntry.IS_CARBONATED,
      DrinksEntry.ISALCOHOLIC, DrinksEntry.TOOLS, DrinksEntry.INGREDIANTS, DrinksEntry.ACTIONS,
      DrinksEntry.TASTES, DrinksEntry.SKILLS, DrinksEntry.SERVEIN, DrinksEntry.OCCASIONS
  };

  private DrinkLocalDataSource(@NonNull Context context) {
    checkNotNull(context);

    this.context = context;
  }

  public int convertBoolean(boolean condition) {
    if (condition) {
      return 1;
    } else {
      return 0;
    }
  }

  public boolean convertInt(int i) {
    if (i == 1) {
      return true;
    } else {
      return false;
    }
  }

  @Override public void saveBookmark(@NonNull DrinkDbModel drinkDbModel) {
    checkNotNull(drinkDbModel);
    ContentValues contentValues = new ContentValues();
    if (drinkDbModel != null) {
      contentValues.put(DrinksContract.DrinksEntry.ID, drinkDbModel.getId());
      contentValues.put(DrinksContract.DrinksEntry.COLOR, drinkDbModel.getColor());
      contentValues.put(DrinksContract.DrinksEntry.NAME, drinkDbModel.getName());
      contentValues.put(DrinksContract.DrinksEntry.DESCRIPTION, drinkDbModel.getDescription());
      contentValues.put(DrinksContract.DrinksEntry.VIDEO, drinkDbModel.getVideo());
      contentValues.put(DrinksContract.DrinksEntry.RATING, drinkDbModel.getRating());
      contentValues.put(DrinksEntry.IS_HOT, convertBoolean(drinkDbModel.getIsHot()));
      contentValues.put(DrinksEntry.ISALCOHOLIC, convertBoolean(drinkDbModel.getIsAlcohol()));
      contentValues.put(DrinksEntry.IS_CARBONATED, convertBoolean(drinkDbModel.getIsCarbon()));

      contentValues.put(DrinksEntry.BOOKMARK, drinkDbModel.getBookmark());
      context.getContentResolver()
          .update(DrinksEntry.DRINKS_URI, contentValues,
              DrinksEntry.ID + " = '" + drinkDbModel.getId() + "'", null);
    }
    //} else {
    //context.getContentResolver()
    //    .insert(DrinksEntry.DRINKS_URI, contentValues, DrinksEntry.ID + "=" + drinkDbModel.getId(),
    //        null);
    // }

  }

  public static DrinkLocalDataSource getInstance(@NonNull Context context) {
    if (INSTANCE == null) {
      INSTANCE = new DrinkLocalDataSource(context);
    }
    return INSTANCE;
  }

  @Override public void deleteAllDrinks() {
    context.getContentResolver().delete(DrinksContract.DrinksEntry.DRINKS_URI, null, null);
  }

  @Override public void deleteDrink(@NonNull String drinkId) {
    context.getContentResolver()
        .delete(DrinksEntry.DRINKS_URI, DrinksEntry.ID + " == " + drinkId, PROJECTIONS);
  }

  @Override public List<DrinkDbModel> getBookmarks() {
    List<DrinkDbModel> drinks = new ArrayList<DrinkDbModel>();
    Cursor cursor = context.getContentResolver()
        .query(DrinksEntry.DRINKS_URI, PROJECTIONS, DrinksEntry.BOOKMARK + " == 1", null, "");
    if (cursor != null && cursor.getCount() > 0) {
      while (cursor.moveToNext()) {
        DrinkDbModel drinkDbModel = null;
        String id = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.NAME));
        String description =
            cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.DESCRIPTION));
        String video = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.VIDEO));
        int rating = cursor.getInt(cursor.getColumnIndexOrThrow(DrinksEntry.RATING));
        int bookmark = cursor.getInt(cursor.getColumnIndexOrThrow(DrinksEntry.BOOKMARK));
        String color = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.COLOR));
        boolean hot = convertInt(cursor.getInt(cursor.getColumnIndexOrThrow(DrinksEntry.IS_HOT)));
        boolean alcohol =
            convertInt(cursor.getInt(cursor.getColumnIndexOrThrow(DrinksEntry.ISALCOHOLIC)));
        boolean carbon =
            convertInt(cursor.getInt(cursor.getColumnIndexOrThrow(DrinksEntry.IS_CARBONATED)));
        String tools = cursor.getString(cursor.getColumnIndex(DrinksEntry.TOOLS));
        List<Tool> list = Convertor.jsonToTools(tools);

        String ingrediants = cursor.getString(cursor.getColumnIndex(DrinksEntry.INGREDIANTS));
        List<Ingredient> ingredientList = Convertor.jsonToIngredients(ingrediants);

        String actions = cursor.getString(cursor.getColumnIndex(DrinksEntry.ACTIONS));
        List<Action> actionList = Convertor.jsonToActions(actions);

        String tastes = cursor.getString(cursor.getColumnIndex(DrinksEntry.TASTES));
        List<Taste> tasteList = Convertor.jsonToTastes(tastes);

        String skills = cursor.getString(cursor.getColumnIndex(DrinksEntry.SKILLS));
        Skill skill = Convertor.jsonToSkill(skills);

        String serveIn = cursor.getString(cursor.getColumnIndex(DrinksEntry.SERVEIN));
        ServedIn servedIn = Convertor.jsonToServeIn(serveIn);

        String occasions = cursor.getString(cursor.getColumnIndex(DrinksEntry.OCCASIONS));
        List<Occasion> occasionList = Convertor.jsonToOccasions(occasions);
        drinkDbModel =
            new DrinkDbModel(bookmark, color, description, id, name, rating, video, hot, alcohol,
                carbon, list, ingredientList, actionList, tasteList, skill, servedIn, occasionList);
        drinks.add(drinkDbModel);
      }
      if (cursor != null) {
        cursor.close();
      }
    }
    return drinks;
  }

  @Nullable @Override public DrinkDbModel getDrink(@NonNull String drinkId) {

    Cursor cursor = context.getContentResolver()
        .query(DrinksEntry.DRINKS_URI, PROJECTIONS, drinkId + " == " + DrinksEntry.ID,
            new String[] {}, "asc");

    DrinkDbModel drinkDbModel = null;
    if (cursor != null && cursor.getCount() > 0) {
      cursor.moveToFirst();
      String id = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.ID));
      String name = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.NAME));
      String description = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.DESCRIPTION));
      String video = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.VIDEO));
      int rating = cursor.getInt(cursor.getColumnIndexOrThrow(DrinksEntry.RATING));
      int bookmark = cursor.getInt(cursor.getColumnIndexOrThrow(DrinksEntry.BOOKMARK));
      String color = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.COLOR));
      boolean hot = convertInt(cursor.getInt(cursor.getColumnIndexOrThrow(DrinksEntry.IS_HOT)));
      boolean alcohol =
          convertInt(cursor.getInt(cursor.getColumnIndexOrThrow(DrinksEntry.ISALCOHOLIC)));
      boolean carbon =
          convertInt(cursor.getInt(cursor.getColumnIndexOrThrow(DrinksEntry.IS_CARBONATED)));
      String tools = cursor.getString(cursor.getColumnIndex(DrinksEntry.TOOLS));
      List<Tool> list = Convertor.jsonToTools(tools);

      String ingrediants = cursor.getString(cursor.getColumnIndex(DrinksEntry.INGREDIANTS));
      List<Ingredient> ingredientList = Convertor.jsonToIngredients(ingrediants);

      String actions = cursor.getString(cursor.getColumnIndex(DrinksEntry.ACTIONS));
      List<Action> actionList = Convertor.jsonToActions(actions);

      String tastes = cursor.getString(cursor.getColumnIndex(DrinksEntry.TASTES));
      List<Taste> tasteList = Convertor.jsonToTastes(tastes);

      String skills = cursor.getString(cursor.getColumnIndex(DrinksEntry.SKILLS));
      Skill skill = Convertor.jsonToSkill(skills);

      String serveIn = cursor.getString(cursor.getColumnIndex(DrinksEntry.SERVEIN));
      ServedIn servedIn = Convertor.jsonToServeIn(serveIn);

      String occasions = cursor.getString(cursor.getColumnIndex(DrinksEntry.OCCASIONS));
      List<Occasion> occasionList = Convertor.jsonToOccasions(occasions);
      drinkDbModel =
          new DrinkDbModel(bookmark, color, description, id, name, rating, video, hot, alcohol,
              carbon, list, ingredientList, actionList, tasteList, skill, servedIn, occasionList);
    }
    if (cursor != null) {
      cursor.close();
    }

    return drinkDbModel;
  }

  @Override public List<DrinkDbModel> getDrinks() {
    List<DrinkDbModel> drinks = new ArrayList<DrinkDbModel>();
    Cursor cursor =
        context.getContentResolver().query(DrinksEntry.DRINKS_URI, PROJECTIONS, null, null, "");
    if (cursor != null && cursor.getCount() > 0) {
      while (cursor.moveToNext()) {
        DrinkDbModel drinkDbModel = null;
        String id = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.NAME));
        String description =
            cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.DESCRIPTION));
        String video = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.VIDEO));
        int rating = cursor.getInt(cursor.getColumnIndexOrThrow(DrinksEntry.RATING));
        int bookmark = cursor.getInt(cursor.getColumnIndexOrThrow(DrinksEntry.BOOKMARK));
        String color = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.COLOR));
        boolean hot = convertInt(cursor.getInt(cursor.getColumnIndexOrThrow(DrinksEntry.IS_HOT)));
        boolean alcohol =
            convertInt(cursor.getInt(cursor.getColumnIndexOrThrow(DrinksEntry.ISALCOHOLIC)));
        boolean carbon =
            convertInt(cursor.getInt(cursor.getColumnIndexOrThrow(DrinksEntry.IS_CARBONATED)));
        String tools = cursor.getString(cursor.getColumnIndex(DrinksEntry.TOOLS));
        List<Tool> list = Convertor.jsonToTools(tools);

        String ingrediants = cursor.getString(cursor.getColumnIndex(DrinksEntry.INGREDIANTS));
        List<Ingredient> ingredientList = Convertor.jsonToIngredients(ingrediants);

        String actions = cursor.getString(cursor.getColumnIndex(DrinksEntry.ACTIONS));
        List<Action> actionList = Convertor.jsonToActions(actions);

        String tastes = cursor.getString(cursor.getColumnIndex(DrinksEntry.TASTES));
        List<Taste> tasteList = Convertor.jsonToTastes(tastes);

        String skills = cursor.getString(cursor.getColumnIndex(DrinksEntry.SKILLS));
        Skill skill = Convertor.jsonToSkill(skills);

        String serveIn = cursor.getString(cursor.getColumnIndex(DrinksEntry.SERVEIN));
        ServedIn servedIn = Convertor.jsonToServeIn(serveIn);

        String occasions = cursor.getString(cursor.getColumnIndex(DrinksEntry.OCCASIONS));
        List<Occasion> occasionList = Convertor.jsonToOccasions(occasions);
        drinkDbModel =
            new DrinkDbModel(bookmark, color, description, id, name, rating, video, hot, alcohol,
                carbon, list, ingredientList, actionList, tasteList, skill, servedIn, occasionList);

        drinks.add(drinkDbModel);
      }
      if (cursor != null) {
        cursor.close();
      }
    }
    return drinks;
  }

  @Override public void refreshDrinks() {

  }

  @Override public List<DrinkDbModel> searchDrinks(String query) {
    List<DrinkDbModel> drinks = new ArrayList<DrinkDbModel>();
    Cursor cursor = context.getContentResolver()
        .query(DrinksEntry.DRINKS_URI, PROJECTIONS, DrinksEntry.NAME + " like '%" + query + "%'",
            new String[] {}, "");

    if (cursor != null && cursor.getCount() > 0) {
      while (cursor.moveToNext()) {
        DrinkDbModel drinkDbModel = null;
        String id = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.NAME));
        String description =
            cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.DESCRIPTION));
        String video = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.VIDEO));
        int rating = cursor.getInt(cursor.getColumnIndexOrThrow(DrinksEntry.RATING));
        int bookmark = cursor.getInt(cursor.getColumnIndexOrThrow(DrinksEntry.BOOKMARK));
        String color = cursor.getString(cursor.getColumnIndexOrThrow(DrinksEntry.COLOR));
        boolean hot = convertInt(cursor.getInt(cursor.getColumnIndexOrThrow(DrinksEntry.IS_HOT)));
        boolean alcohol =
            convertInt(cursor.getInt(cursor.getColumnIndexOrThrow(DrinksEntry.ISALCOHOLIC)));
        boolean carbon =
            convertInt(cursor.getInt(cursor.getColumnIndexOrThrow(DrinksEntry.IS_CARBONATED)));
        String tools = cursor.getString(cursor.getColumnIndex(DrinksEntry.TOOLS));
        List<Tool> list = Convertor.jsonToTools(tools);

        String ingrediants = cursor.getString(cursor.getColumnIndex(DrinksEntry.INGREDIANTS));
        List<Ingredient> ingredientList = Convertor.jsonToIngredients(ingrediants);

        String actions = cursor.getString(cursor.getColumnIndex(DrinksEntry.ACTIONS));
        List<Action> actionList = Convertor.jsonToActions(actions);

        String tastes = cursor.getString(cursor.getColumnIndex(DrinksEntry.TASTES));
        List<Taste> tasteList = Convertor.jsonToTastes(tastes);

        String skills = cursor.getString(cursor.getColumnIndex(DrinksEntry.SKILLS));
        Skill skill = Convertor.jsonToSkill(skills);

        String serveIn = cursor.getString(cursor.getColumnIndex(DrinksEntry.SERVEIN));
        ServedIn servedIn = Convertor.jsonToServeIn(serveIn);

        String occasions = cursor.getString(cursor.getColumnIndex(DrinksEntry.OCCASIONS));
        List<Occasion> occasionList = Convertor.jsonToOccasions(occasions);
        drinkDbModel =
            new DrinkDbModel(bookmark, color, description, id, name, rating, video, hot, alcohol,
                carbon, list, ingredientList, actionList, tasteList, skill, servedIn, occasionList);

        drinks.add(drinkDbModel);
      }
      if (cursor != null) {
        cursor.close();
      }
    }
    return drinks;
  }
  //public static final String TOOLS = "tools";
  //public static final String INGREDIANTS = "ingrediants";
  //public static final String ACTIONS = "actions";
  //public static final String TASTES = "tastes";
  //public static final String SKILLS = "skills";
  //public static final String SERVEIN = "serve_in";
  //public static final String OCCASIONS = "occasions";

  @Override public void saveDrinks(@NonNull DrinkDbModel drinkDbModel) {
    checkNotNull(drinkDbModel);
    ContentValues contentValues = new ContentValues();
    if (drinkDbModel != null) {
      contentValues.put(DrinksContract.DrinksEntry.ID, drinkDbModel.getId());
      contentValues.put(DrinksContract.DrinksEntry.COLOR, drinkDbModel.getColor());
      contentValues.put(DrinksContract.DrinksEntry.NAME, drinkDbModel.getName());
      contentValues.put(DrinksContract.DrinksEntry.DESCRIPTION, drinkDbModel.getDescription());
      contentValues.put(DrinksContract.DrinksEntry.VIDEO, drinkDbModel.getVideo());
      contentValues.put(DrinksContract.DrinksEntry.RATING, drinkDbModel.getRating());
      contentValues.put(DrinksEntry.IS_HOT, convertBoolean(drinkDbModel.getIsHot()));
      contentValues.put(DrinksEntry.ISALCOHOLIC, convertBoolean(drinkDbModel.getIsAlcohol()));
      contentValues.put(DrinksEntry.IS_CARBONATED, convertBoolean(drinkDbModel.getIsCarbon()));

      String tools = Convertor.toolsToJson(drinkDbModel.getTools());
      String ingrediants = Convertor.ingrediantsToJson(drinkDbModel.getIngredients());
      String actions = Convertor.actionsToJson(drinkDbModel.getActions());
      String tastes = Convertor.tastesToJson(drinkDbModel.getTastes());
      String skills = Convertor.skillToJson(drinkDbModel.getSkills());
      String serveIn = Convertor.serveInToJson(drinkDbModel.getServedIns());
      String occasions = Convertor.occasionsToJson(drinkDbModel.getOccasions());

      contentValues.put(DrinksEntry.TOOLS, tools);

      contentValues.put(DrinksEntry.INGREDIANTS, ingrediants);

      contentValues.put(DrinksEntry.ACTIONS, actions);

      contentValues.put(DrinksEntry.TASTES, tastes);

      contentValues.put(DrinksEntry.SKILLS, skills);

      contentValues.put(DrinksEntry.SERVEIN, serveIn);

      contentValues.put(DrinksEntry.OCCASIONS, occasions);
      contentValues.put(DrinksEntry.BOOKMARK, 0);
      if (context.getContentResolver()
          .update(DrinksEntry.DRINKS_URI, contentValues,
              DrinksEntry.ID + " = '" + drinkDbModel.getId() + "'", null) <= 0) {
        context.getContentResolver().insert(DrinksContract.DrinksEntry.DRINKS_URI, contentValues);
      }
    }
    //} else {
    //context.getContentResolver()
    //    .insert(DrinksEntry.DRINKS_URI, contentValues, DrinksEntry.ID + "=" + drinkDbModel.getId(),
    //        null);
    // }
  }
}
