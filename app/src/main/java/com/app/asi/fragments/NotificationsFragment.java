package com.app.asi.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.asi.R;
import com.app.asi.entities.NotificationEnt;
import com.app.asi.fragments.abstracts.BaseFragment;
import com.app.asi.global.AppConstants;
import com.app.asi.global.WebServiceConstants;
import com.app.asi.helpers.DateHelper;
import com.app.asi.helpers.UIHelper;
import com.app.asi.interfaces.LoadMoreListener;
import com.app.asi.interfaces.RecyclerClickListner;
import com.app.asi.ui.binders.NotificationBinder;
import com.app.asi.ui.views.AnyTextView;
import com.app.asi.ui.views.CustomRecyclerView;
import com.app.asi.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.app.asi.global.AppConstants.chatPush;
import static com.app.asi.global.WebServiceConstants.Notificaions;
import static com.app.asi.global.WebServiceConstants.NotificaionsPaging;

public class NotificationsFragment extends BaseFragment implements RecyclerClickListner {


    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.lv_notification)
    CustomRecyclerView lvNotification;
    Unbinder unbinder;


    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getString(R.string.notification));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        prefHelper.setNotificationCount(0);
     //   serviceHelper.enqueueCall(headerWebService.notifications(), Notificaions);
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {
            case Notificaions:
                ArrayList<NotificationEnt> data = (ArrayList<NotificationEnt>) result;

                if (data != null && data.size() > 0) {
                    lvNotification.setVisibility(View.VISIBLE);
                    txtNoData.setVisibility(View.GONE);

                    lvNotification.BindRecyclerView(new NotificationBinder(getDockActivity(), prefHelper, this), data,
                            new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false)
                            , new DefaultItemAnimator());

                } else {
                    lvNotification.setVisibility(View.GONE);
                    txtNoData.setVisibility(View.VISIBLE);
                }



                break;

        }
    }

    @Override
    public void onClick(Object entity, int position) {
        NotificationEnt data = (NotificationEnt) entity;
     /*   if (data.getActionType() != null && data.getActionType().equals(AppConstants.companyPush) && data.getRefId() != null) {

            getDockActivity().addDockableFragment(ServiceDetailFragment.newInstance(data.getRefId() + ""), "ServiceDetailFragment");

        } else if (data.getActionType() != null && data.getActionType().equals(AppConstants.chatPush) && data.getRefId() != null) {
            getDockActivity().addDockableFragment(ChatFragment.newInstance(data.getRefId() + ""), "ChatFragment");
        }*/
    }
}
