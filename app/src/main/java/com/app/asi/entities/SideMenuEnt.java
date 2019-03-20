package com.app.asi.entities;

public class SideMenuEnt {
    String title;

    boolean isSelected = false;

    public SideMenuEnt(String title) {
        this.title = title;

    }

    public SideMenuEnt(String title, boolean isSelected) {
        this.title = title;

        this.isSelected = isSelected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
