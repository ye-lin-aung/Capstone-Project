
package com.wecook.yelinaung.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Video {

    @SerializedName("video")
    @Expose
    private String video;
    @SerializedName("type")
    @Expose
    private String type;

    /**
     * 
     * @return
     *     The video
     */
    public String getVideo() {
        return video;
    }

    /**
     * 
     * @param video
     *     The video
     */
    public void setVideo(String video) {
        this.video = video;
    }

    /**
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

}
