package com.app.asi.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CMSEnt {


    @SerializedName("aboutUs")
    @Expose
    private String aboutUs;
    @SerializedName("aboutUsAr")
    @Expose
    private String aboutUsAr;
    @SerializedName("privacyPolicy")
    @Expose
    private String privacyPolicy;
    @SerializedName("privacyPolicyAr")
    @Expose
    private String privacyPolicyAr;
    @SerializedName("termsCondition")
    @Expose
    private String termsCondition;
    @SerializedName("termsConditionAr")
    @Expose
    private String termsConditionAr;
    @SerializedName("cookiePolicy")
    @Expose
    private String cookiePolicy;
    @SerializedName("cookiePolicyAr")
    @Expose
    private String cookiePolicyAr;
    @SerializedName("acknowledgement")
    @Expose
    private String acknowledgement;
    @SerializedName("acknowledgementAr")
    @Expose
    private String acknowledgementAr;
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

    public String getAboutUs() {
        return aboutUs;
    }

    public void setAboutUs(String aboutUs) {
        this.aboutUs = aboutUs;
    }

    public String getAboutUsAr() {
        return aboutUsAr;
    }

    public void setAboutUsAr(String aboutUsAr) {
        this.aboutUsAr = aboutUsAr;
    }

    public String getPrivacyPolicy() {
        return privacyPolicy;
    }

    public void setPrivacyPolicy(String privacyPolicy) {
        this.privacyPolicy = privacyPolicy;
    }

    public String getPrivacyPolicyAr() {
        return privacyPolicyAr;
    }

    public void setPrivacyPolicyAr(String privacyPolicyAr) {
        this.privacyPolicyAr = privacyPolicyAr;
    }

    public String getTermsCondition() {
        return termsCondition;
    }

    public void setTermsCondition(String termsCondition) {
        this.termsCondition = termsCondition;
    }

    public String getTermsConditionAr() {
        return termsConditionAr;
    }

    public void setTermsConditionAr(String termsConditionAr) {
        this.termsConditionAr = termsConditionAr;
    }

    public String getCookiePolicy() {
        return cookiePolicy;
    }

    public void setCookiePolicy(String cookiePolicy) {
        this.cookiePolicy = cookiePolicy;
    }

    public String getCookiePolicyAr() {
        return cookiePolicyAr;
    }

    public void setCookiePolicyAr(String cookiePolicyAr) {
        this.cookiePolicyAr = cookiePolicyAr;
    }

    public String getAcknowledgement() {
        return acknowledgement;
    }

    public void setAcknowledgement(String acknowledgement) {
        this.acknowledgement = acknowledgement;
    }

    public String getAcknowledgementAr() {
        return acknowledgementAr;
    }

    public void setAcknowledgementAr(String acknowledgementAr) {
        this.acknowledgementAr = acknowledgementAr;
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
