package com.app.asi.helpers;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.app.asi.R;
import com.app.asi.ui.views.AnyTextView;

/**
 * Created on 5/24/2017.
 */

public class DialogHelper {
    private Dialog dialog;
    private Context context;

    private RadioGroup rg;


    public DialogHelper(Context context) {
        this.context = context;
        this.dialog = new Dialog(context);
    }


    public Dialog toastDialoge(String text) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(R.layout.toast_dialoge);
        AnyTextView textView = (AnyTextView) dialog.findViewById(R.id.txt_text);
        textView.setText(text);
        return this.dialog;
    }

    public Dialog initDialoge(View.OnClickListener yesListner, String title,String description,String button1,String button2) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(R.layout.dialoge_layout);
        Button btnYes = (Button) dialog.findViewById(R.id.btn_yes);
        Button btnNo = (Button) dialog.findViewById(R.id.btn_No);
        AnyTextView txtTitle = (AnyTextView) dialog.findViewById(R.id.txtTitle);
        AnyTextView txtDescription = (AnyTextView) dialog.findViewById(R.id.txtDescription);
        txtTitle.setText(title);
        txtDescription.setText(description);
        btnYes.setText(button1);
        btnNo.setText(button2);
        btnYes.setOnClickListener(yesListner);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        return this.dialog;
    }
    public Dialog initRemoveDialoge(View.OnClickListener yesListner, String title,String description) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(R.layout.dialoge_remove_game);
        Button btnYes = (Button) dialog.findViewById(R.id.btn_yes);
        Button btnNo = (Button) dialog.findViewById(R.id.btn_No);
        AnyTextView txtDescription = (AnyTextView) dialog.findViewById(R.id.txtDescription);
        txtDescription.setText(description);
        btnYes.setOnClickListener(yesListner);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        return this.dialog;
    }


    public Dialog initSocialLink(View.OnClickListener fbListner, View.OnClickListener youtubeListner, View.OnClickListener twitterListner, View.OnClickListener instaListner, View.OnClickListener linkdinListner) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(R.layout.dialoge_social_links);
        ImageView btnFb = (ImageView) dialog.findViewById(R.id.btnFb);
        ImageView btnYoutube = (ImageView) dialog.findViewById(R.id.btnYoutube);
        ImageView btnTwitter = (ImageView) dialog.findViewById(R.id.btnTwitter);
        ImageView btnInsta = (ImageView) dialog.findViewById(R.id.btnInsta);
        ImageView btnLinkedin = (ImageView) dialog.findViewById(R.id.btnLinkedin);
        btnFb.setOnClickListener(fbListner);
        btnYoutube.setOnClickListener(youtubeListner);
        btnTwitter.setOnClickListener(twitterListner);
        btnInsta.setOnClickListener(instaListner);
        btnLinkedin.setOnClickListener(linkdinListner);
        return this.dialog;
    }



    public Dialog cameraPicker(View.OnClickListener cameraListner, View.OnClickListener galleryListner) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.dialog.setContentView(R.layout.dialoge_camera_options);
        Button btnCamera = (Button) dialog.findViewById(R.id.btn_camera);
        btnCamera.setOnClickListener(cameraListner);
        Button btnGallery = (Button) dialog.findViewById(R.id.btn_gallery);
        btnGallery.setOnClickListener(galleryListner);
        return this.dialog;
    }


    public void showDialog() {

        dialog.show();
    }

    public void setCancelable(boolean isCancelable) {
        dialog.setCancelable(isCancelable);
        dialog.setCanceledOnTouchOutside(isCancelable);
    }

    public void hideDialog() {
        dialog.dismiss();
    }
}
