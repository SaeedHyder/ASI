package com.app.asi.helpers;

import android.util.Log;

import com.app.asi.R;
import com.app.asi.activities.DockActivity;
import com.app.asi.entities.ResponseWrapper;
import com.app.asi.interfaces.webServiceResponseLisener;
import com.app.asi.retrofit.WebService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created on 7/17/2017.
 */

public class ServiceHelper<T> {
    private webServiceResponseLisener serviceResponseLisener;
    private DockActivity context;
    private WebService webService;
    private JSONObject jObjError;

    public ServiceHelper(webServiceResponseLisener serviceResponseLisener, DockActivity conttext, WebService webService) {
        this.serviceResponseLisener = serviceResponseLisener;
        this.context = conttext;
        this.webService = webService;
    }

    public void enqueueCall(Call<ResponseWrapper<T>> call, final String tag) {
        if (InternetHelper.CheckInternetConectivityandShowToast(context)) {
            context.onLoadingStarted();
            call.enqueue(new Callback<ResponseWrapper<T>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<T>> call, Response<ResponseWrapper<T>> response) {
                    context.onLoadingFinished();
                    if (response != null && response.body() != null) {
                        if (response.body().isStatus()) {
                            serviceResponseLisener.ResponseSuccess(response.body().getData(),response.body().getUser(), tag, response.body().getMessage());
                        } else {
                            UIHelper.showShortToastInDialoge(context, response.body().getMessage());
                        }
                    } else {
                        try {
                            jObjError = new JSONObject(response.errorBody().string());
                            UIHelper.showShortToastInDialoge(context, jObjError.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onFailure(Call<ResponseWrapper<T>> call, Throwable t) {
                    context.onLoadingFinished();
                    t.printStackTrace();
                    Log.e(ServiceHelper.class.getSimpleName() + " by tag: " + tag, t.toString());
                }
            });
        }
    }

    public void enqueueCall(Call<ResponseWrapper<T>> call, final String tag,boolean isLoading) {
        if (InternetHelper.CheckInternetConectivityandShowToast(context)) {
            call.enqueue(new Callback<ResponseWrapper<T>>() {
                @Override
                public void onResponse(Call<ResponseWrapper<T>> call, Response<ResponseWrapper<T>> response) {
                    if (response != null && response.body() != null) {
                        if (response.body().isStatus()) {
                            serviceResponseLisener.ResponseSuccess(response.body().getData(),response.body().getUser(), tag, response.body().getMessage());
                        } else {
                            UIHelper.showShortToastInDialoge(context, response.body().getMessage());
                        }
                    } else {
                        UIHelper.showShortToastInDialoge(context, context.getResources().getString(R.string.no_response));
                    }

                }

                @Override
                public void onFailure(Call<ResponseWrapper<T>> call, Throwable t) {
                    t.printStackTrace();
                    Log.e(ServiceHelper.class.getSimpleName() + " by tag: " + tag, t.toString());
                }
            });
        }
    }

}
