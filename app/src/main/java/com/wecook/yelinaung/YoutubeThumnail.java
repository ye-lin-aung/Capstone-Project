package com.wecook.yelinaung;

/**
 * Created by user on 5/24/16.
 */
public class YoutubeThumnail {
  String id;
  final String URL = "https://img.youtube.com/vi/";

  public YoutubeThumnail(String id) {
    this.id = id;
  }

  public String getSmallImage() {
    id = URL + id + "/1.jpg";
    return id;
  }

  public String getDefaultImage() {
    id = URL + id + "/2.jpg";
    return id;
  }

  public String getFullSize() {
    id = URL + id + "/0.jpg";
    return id;
  }

  public String getNormalSize() {
    id = URL + id + "3.jpg";
    return id;
  }
}
