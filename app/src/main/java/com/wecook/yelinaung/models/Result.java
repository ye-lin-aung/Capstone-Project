
package com.wecook.yelinaung.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class Result {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("story")
    @Expose
    private String story;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("skill")
    @Expose
    private Skill skill;
    @SerializedName("videos")
    @Expose
    private List<Video> videos = new ArrayList<Video>();
    @SerializedName("isAlcoholic")
    @Expose
    private Boolean isAlcoholic;
    @SerializedName("isCarbonated")
    @Expose
    private Boolean isCarbonated;
    @SerializedName("isHot")
    @Expose
    private Boolean isHot;
    @SerializedName("tags")

    @Expose
    private List<Object> tags = new ArrayList<Object>();
    @SerializedName("servedIn")
    @Expose
    private ServedIn servedIn;
    @SerializedName("ingredients")
    @Expose
    private List<Ingredient> ingredients = new ArrayList<Ingredient>();
    @SerializedName("tastes")
    @Expose
    private List<Taste> tastes = new ArrayList<Taste>();
    @SerializedName("occasions")
    @Expose
    private List<Occasion> occasions = new ArrayList<Occasion>();
    @SerializedName("tools")
    @Expose
    private List<Tool> tools = new ArrayList<Tool>();
    @SerializedName("drinkTypes")
    @Expose
    private List<Object> drinkTypes = new ArrayList<Object>();
    @SerializedName("actions")
    @Expose
    private List<Action> actions = new ArrayList<Action>();
    @SerializedName("brands")
    @Expose
    private List<String> brands = new ArrayList<String>();
    @SerializedName("languageBranch")
    @Expose
    private String languageBranch;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("descriptionPlain")
    @Expose
    private String descriptionPlain;

    /**
     *
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     *     The story
     */
    public String getStory() {
        return story;
    }

    /**
     *
     * @param story
     *     The story
     */
    public void setStory(String story) {
        this.story = story;
    }

    /**
     *
     * @return
     *     The color
     */
    public String getColor() {
        return color;
    }

    /**
     *
     * @param color
     *     The color
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     *
     * @return
     *     The rating
     */
    public Integer getRating() {
        return rating;
    }

    /**
     *
     * @param rating
     *     The rating
     */
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    /**
     *
     * @return
     *     The skill
     */
    public Skill getSkill() {
        return skill;
    }

    /**
     *
     * @param skill
     *     The skill
     */
    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    /**
     *
     * @return
     *     The videos
     */
    public List<Video> getVideos() {
        return videos;
    }

    /**
     *
     * @param videos
     *     The videos
     */
    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

    /**
     *
     * @return
     *     The isAlcoholic
     */
    public Boolean getIsAlcoholic() {
        return isAlcoholic;
    }

    /**
     *
     * @param isAlcoholic
     *     The isAlcoholic
     */
    public void setIsAlcoholic(Boolean isAlcoholic) {
        this.isAlcoholic = isAlcoholic;
    }

    /**
     *
     * @return
     *     The isCarbonated
     */
    public Boolean getIsCarbonated() {
        return isCarbonated;
    }

    /**
     *
     * @param isCarbonated
     *     The isCarbonated
     */
    public void setIsCarbonated(Boolean isCarbonated) {
        this.isCarbonated = isCarbonated;
    }

    /**
     *
     * @return
     *     The isHot
     */
    public Boolean getIsHot() {
        return isHot;
    }

    /**
     *
     * @param isHot
     *     The isHot
     */
    public void setIsHot(Boolean isHot) {
        this.isHot = isHot;
    }

    /**
     *
     * @return
     *     The tags
     */
    public List<Object> getTags() {
        return tags;
    }

    /**
     *
     * @param tags
     *     The tags
     */
    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

    /**
     *
     * @return
     *     The servedIn
     */
    public ServedIn getServedIn() {
        return servedIn;
    }

    /**
     *
     * @param servedIn
     *     The servedIn
     */
    public void setServedIn(ServedIn servedIn) {
        this.servedIn = servedIn;
    }

    /**
     *
     * @return
     *     The ingredients
     */
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * 
     * @param ingredients
     *     The ingredients
     */
    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * 
     * @return
     *     The tastes
     */
    public List<Taste> getTastes() {
        return tastes;
    }

    /**
     * 
     * @param tastes
     *     The tastes
     */
    public void setTastes(List<Taste> tastes) {
        this.tastes = tastes;
    }

    /**
     * 
     * @return
     *     The occasions
     */
    public List<Occasion> getOccasions() {
        return occasions;
    }

    /**
     * 
     * @param occasions
     *     The occasions
     */
    public void setOccasions(List<Occasion> occasions) {
        this.occasions = occasions;
    }

    /**
     * 
     * @return
     *     The tools
     */
    public List<Tool> getTools() {
        return tools;
    }

    /**
     * 
     * @param tools
     *     The tools
     */
    public void setTools(List<Tool> tools) {
        this.tools = tools;
    }

    /**
     * 
     * @return
     *     The drinkTypes
     */
    public List<Object> getDrinkTypes() {
        return drinkTypes;
    }

    /**
     * 
     * @param drinkTypes
     *     The drinkTypes
     */
    public void setDrinkTypes(List<Object> drinkTypes) {
        this.drinkTypes = drinkTypes;
    }

    /**
     * 
     * @return
     *     The actions
     */
    public List<Action> getActions() {
        return actions;
    }

    /**
     * 
     * @param actions
     *     The actions
     */
    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    /**
     * 
     * @return
     *     The brands
     */
    public List<String> getBrands() {
        return brands;
    }

    /**
     * 
     * @param brands
     *     The brands
     */
    public void setBrands(List<String> brands) {
        this.brands = brands;
    }

    /**
     * 
     * @return
     *     The languageBranch
     */
    public String getLanguageBranch() {
        return languageBranch;
    }

    /**
     * 
     * @param languageBranch
     *     The languageBranch
     */
    public void setLanguageBranch(String languageBranch) {
        this.languageBranch = languageBranch;
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
     *     The descriptionPlain
     */
    public String getDescriptionPlain() {
        return descriptionPlain;
    }

    /**
     * 
     * @param descriptionPlain
     *     The descriptionPlain
     */
    public void setDescriptionPlain(String descriptionPlain) {
        this.descriptionPlain = descriptionPlain;
    }

}
