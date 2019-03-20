package com.app.asi.interfaces;

import android.widget.Button;

import com.app.asi.entities.WishListEnt;

public interface WishListInterface {

    void onWishClick(Object entity, int pos);
    void onWishClickAdapter(Object entity, int pos, Button btnAddWishList);
}
