package com.app.asi.helpers;

import android.content.Context;
import android.net.ConnectivityManager;

import com.app.asi.R;
import com.app.asi.activities.DockActivity;


/**
 * Created by khan_muhammad on 3/27/2017.
 */

public class InternetHelper {

    public static boolean CheckInternetConectivityandShowToast(DockActivity activity) {

        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo() != null) {
            return true;
        } else {
            // text.setText("Look your not online");
            UIHelper.showShortToastInDialoge(activity, activity.getString(R.string.connection_lost));
            return false;
        }


    }
    public static boolean CheckInternetConectivityand(DockActivity activity) {

        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo() != null) {
            return true;
        } else {
            // text.setText("Look your not online");
            //UIHelper.showShortToastInDialoge(activity, activity.getString(R.string.connection_lost));
            return false;
        }


    }

}
