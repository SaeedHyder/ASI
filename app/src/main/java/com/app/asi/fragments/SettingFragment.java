package com.app.asi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.app.asi.R;
import com.app.asi.entities.UserEnt;
import com.app.asi.fragments.abstracts.BaseFragment;
import com.app.asi.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.app.asi.global.WebServiceConstants.ChangePush;

public class SettingFragment extends BaseFragment {


    @BindView(R.id.btnNotifications)
    Switch btnNotifications;
    @BindView(R.id.btn_change_password)
    LinearLayout btnChangePassword;
    @BindView(R.id.changePassworView)
    View changePassworView;
    Unbinder unbinder;
    private boolean isPushCheck;

    public static SettingFragment newInstance() {
        Bundle args = new Bundle();

        SettingFragment fragment = new SettingFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (prefHelper.getUser().getNotification()) {
            btnNotifications.setChecked(true);
        } else {
            btnNotifications.setChecked(false);
        }
        listner();


    }

    private void listner() {
        btnNotifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isPushCheck = b;
                serviceHelper.enqueueCall(headerWebService.isNotification(b), ChangePush);
            }
        });
    }

    @Override
    public void ResponseSuccess(Object result, UserEnt userEnt, String Tag, String message) {
        super.ResponseSuccess(result, userEnt, Tag, message);
        switch (Tag) {
            case ChangePush:
                UserEnt userObject = prefHelper.getUser();
                userObject.setNotification(userEnt.getNotification());
                prefHelper.putUser(userEnt);

                break;
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getResString(R.string.settings));
        titleBar.showMenuButton();
    }


    @OnClick({R.id.btn_change_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.btn_change_password:
                getDockActivity().replaceDockableFragment(ChangePasswordFragment.newInstance(), "ChangePasswordFragment");
                break;

        }
    }

}
