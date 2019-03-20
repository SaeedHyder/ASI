package com.app.asi.helpers;

import android.content.Context;
import android.util.Log;

import com.app.asi.entities.ResponseWrapper;
import com.app.asi.global.AppConstants;
import com.app.asi.global.WebServiceConstants;
import com.app.asi.retrofit.WebService;
import com.app.asi.retrofit.WebServiceFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 5/15/2017.
 */

public class TokenUpdater {
    private static final TokenUpdater tokenUpdater = new TokenUpdater();
    private WebService webservice;

    private TokenUpdater() {
    }

    public static TokenUpdater getInstance() {
        return tokenUpdater;
    }

    public void UpdateToken(Context context, String Token) {
        if (Token.isEmpty()) {
            Log.e("Token Updater", "Token is Empty");
        }
        webservice = WebServiceFactory.getWebServiceInstanceWithCustomInterceptorandheader(context,
                WebServiceConstants.Local_SERVICE_URL);

      /*  Call<ResponseWrapper> call = webservice.updateToken(AppConstants.Device_Type,Token);
        call.enqueue(new Callback<ResponseWrapper>() {
            @Override
            public void onResponse(Call<ResponseWrapper> call, Response<ResponseWrapper> response) {
                if (response.body()!=null)
                Log.i("UPDATETOKEN", response.body().getMessage());
            }

            @Override
            public void onFailure(Call<ResponseWrapper> call, Throwable t) {
                Log.e("UPDATETOKEN", t.toString());
            }
        });*/
    }

}
