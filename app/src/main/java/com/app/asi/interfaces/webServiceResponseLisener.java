package com.app.asi.interfaces;

import android.nfc.Tag;

import com.app.asi.entities.UserEnt;

/**
 * Created on 7/17/2017.
 */

public interface webServiceResponseLisener<T> {
    public void ResponseSuccess(T result, UserEnt userEnt, String Tag, String message);
    public void  ResponseFailure(String tag);
}
