package com.app.asi.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GameEnt {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("titleAr")
    @Expose
    private String titleAr;
    @SerializedName("descriptionAr")
    @Expose
    private String descriptionAr;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("categoryAr")
    @Expose
    private String categoryAr;
    @SerializedName("sectionCode")
    @Expose
    private String sectionCode;
    @SerializedName("dimension")
    @Expose
    private String dimension;
    @SerializedName("videoUrl")
    @Expose
    private String videoUrl;
    @SerializedName("codeImageUrl")
    @Expose
    private String codeImageUrl;
    @SerializedName("imageUrls")
    @Expose
    private ArrayList<String> imageUrls = new ArrayList<>();
    @SerializedName("shareUrl")
    @Expose
    private String shareUrl;
    @SerializedName("isFavourite")
    @Expose
    private Boolean isFavourite;
    @SerializedName("isPlayed")
    @Expose
    private Boolean isPlayed;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("createdOn")
    @Expose
    private String createdOn;
    @SerializedName("updatedOn")
    @Expose
    private String updatedOn;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("playedOn")
    @Expose
    private String playedOn;

    public String getPlayedOn() {
        return playedOn;
    }

    public void setPlayedOn(String playedOn) {
        this.playedOn = playedOn;
    }

    public GameEnt(String title, String description, String titleAr, String descriptionAr, String dimension, String videoUrl, String codeImageUrl, ArrayList<String> imageUrls, String shareUrl, Boolean isFavourite, Boolean isPlayed, Integer id, String createdOn,String playedOn) {
        this.title = title;
        this.description = description;
        this.titleAr = titleAr;
        this.descriptionAr = descriptionAr;
        this.dimension = dimension;
        this.videoUrl = videoUrl;
        this.codeImageUrl = codeImageUrl;
        this.imageUrls = imageUrls;
        this.shareUrl = shareUrl;
        this.isFavourite = isFavourite;
        this.isPlayed = isPlayed;
        this.id = id;
        this.createdOn = createdOn;
        this.playedOn = playedOn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitleAr() {
        return titleAr;
    }

    public void setTitleAr(String titleAr) {
        this.titleAr = titleAr;
    }

    public String getDescriptionAr() {
        return descriptionAr;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryAr() {
        return categoryAr;
    }

    public void setCategoryAr(String categoryAr) {
        this.categoryAr = categoryAr;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getCodeImageUrl() {
        return codeImageUrl;
    }

    public void setCodeImageUrl(String codeImageUrl) {
        this.codeImageUrl = codeImageUrl;
    }

    public ArrayList<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(ArrayList<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public Boolean getFavourite() {
        return isFavourite;
    }

    public void setFavourite(Boolean favourite) {
        isFavourite = favourite;
    }

    public Boolean getPlayed() {
        return isPlayed;
    }

    public void setPlayed(Boolean played) {
        isPlayed = played;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
