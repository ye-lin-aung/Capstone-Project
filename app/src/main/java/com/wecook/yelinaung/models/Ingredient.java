
package com.wecook.yelinaung.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredient {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("textPlain")
    @Expose
    private String textPlain;

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

    /**
     * 
     * @return
     *     The textPlain
     */
    public String getTextPlain() {
        return textPlain;
    }

    /**
     * 
     * @param textPlain
     *     The textPlain
     */
    public void setTextPlain(String textPlain) {
        this.textPlain = textPlain;
    }

}
