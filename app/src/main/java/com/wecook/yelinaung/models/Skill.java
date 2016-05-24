
package com.wecook.yelinaung.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Skill {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("value")
    @Expose
    private Integer value;

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
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The value
     */
    public Integer getValue() {
        return value;
    }

    /**
     * 
     * @param value
     *     The value
     */
    public void setValue(Integer value) {
        this.value = value;
    }

}
