
package com.wecook.yelinaung.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Occasion {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("text")
    @Expose
    private String text;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The text
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text
     *     The text
     */
    public void setText(String text) {
        this.text = text;
    }

}
