package com.wecook.yelinaung.font;

import android.graphics.Typeface;
import com.wecook.yelinaung.MyApp;
import java.util.HashMap;

/**
 * Created by user on 5/24/16.
 */
public class CustomFont {
  static CustomFont INSTANCE;

  HashMap<String, String> fontMap = new HashMap<>();
  HashMap<String, Typeface> fontCache = new HashMap<>();

  private CustomFont() {

  }

  public static CustomFont getInstance() {
    if (INSTANCE == null) {
      INSTANCE = new CustomFont();
    }
    return INSTANCE;
  }

  public void addFont(String alias, String fontName) {
    fontMap.put(alias, fontName);
  }

  public Typeface getFont(String alias) {
    String fontFilename = fontMap.get(alias);
    if (fontFilename == null) {
      throw new UnsupportedOperationException("No Such Font");
    } else {
      if (fontCache.containsKey(alias)) {
        return fontCache.get(alias);
      } else {
        Typeface typeface =
            Typeface.createFromAsset(MyApp.getContext().getAssets(), "fonts/" + fontFilename);
        fontCache.put(alias, typeface);
        return typeface;
      }
    }
  }
}
