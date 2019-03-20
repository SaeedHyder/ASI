package com.app.asi.ui.adapters;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.asi.R;
import com.app.asi.activities.MainActivity;
import com.app.asi.entities.GameEnt;
import com.app.asi.helpers.ServiceHelper;
import com.app.asi.interfaces.WishListInterface;
import com.app.asi.ui.views.AnyTextView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

;

public class PopupPageAdapter extends PagerAdapter {
    private MainActivity context;
    private ArrayList<GameEnt> gameList;
    private LayoutInflater layoutInflater;
    protected ServiceHelper serviceHelper;
    private WishListInterface wishListInterface;
    private int pos = 0;

    public PopupPageAdapter(MainActivity context, ArrayList<GameEnt> list, WishListInterface wishListInterface) {
        this.context = context;
        this.gameList = list;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.wishListInterface = wishListInterface;


    }

    @Override
    public int getCount() {
        return gameList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public int getItemPosition(@NonNull Object object) {
        return pos;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.row_item_games_popup, container, false);
        container.addView(itemView);

        pos = position;
        ImageView ivCenterImage = (ImageView) itemView.findViewById(R.id.ivCenterImage);
        AnyTextView txtTitle = (AnyTextView) itemView.findViewById(R.id.txtTitle);
        AnyTextView txtDescription = (AnyTextView) itemView.findViewById(R.id.txtDescription);
        AnyTextView txtDimension = (AnyTextView) itemView.findViewById(R.id.txtDimension);

        ImageView btnCross = (ImageView) itemView.findViewById(R.id.btnCross);
        Button btnAddWishList = (Button) itemView.findViewById(R.id.btn_add);
        ImageView btnVideo = (ImageView) itemView.findViewById(R.id.btnVideo);

        txtTitle.setText(gameList.get(position).getTitle() + "");
        txtDescription.setText(gameList.get(position).getDescription() + "");
        txtDimension.setText(gameList.get(position).getDimension() + "");

        if (gameList.get(position).getImageUrls() != null && gameList.get(position).getImageUrls().size() > 0) {
            Picasso.get().load(gameList.get(position).getImageUrls().get(0)).into(ivCenterImage);
        }

        if (gameList.get(position).getFavourite()) {
            btnAddWishList.setText(R.string.remove_from_wish_list);
        } else {
            btnAddWishList.setText(R.string.add_to_wish_list);
        }
        btnCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.popFragment();
            }
        });
        btnAddWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wishListInterface.onWishClickAdapter(gameList.get(position), position, btnAddWishList);
            }
        });
        btnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openWebPage(gameList.get(position).getVideoUrl());
            }
        });

        txtDescription.setMovementMethod(new ScrollingMovementMethod());

        txtDescription.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                return false;
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

    public void openWebPage(String url) {
        try {
            Uri webpage = Uri.parse(url);

            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                webpage = Uri.parse("http://" + url);
            }
            context.startActivity(new Intent(Intent.ACTION_VIEW, webpage));
        } catch (ActivityNotFoundException e) {
            //TODO smth
        }
    }


}

