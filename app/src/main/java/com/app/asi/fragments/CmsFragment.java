package com.app.asi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.asi.R;
import com.app.asi.entities.CMSEnt;
import com.app.asi.entities.UserEnt;
import com.app.asi.fragments.abstracts.BaseFragment;
import com.app.asi.global.WebServiceConstants;
import com.app.asi.ui.views.AnyTextView;
import com.app.asi.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.app.asi.global.WebServiceConstants.CmsService;

public class CmsFragment extends BaseFragment {
    @BindView(R.id.txt_text)
    AnyTextView txtText;
    Unbinder unbinder;
    private static String title = "";

    public static CmsFragment newInstance(String text) {
        Bundle args = new Bundle();
        title = text;
        CmsFragment fragment = new CmsFragment();
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
        View view = inflater.inflate(R.layout.fragment_cms, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        serviceHelper.enqueueCall(headerWebService.getCms(), WebServiceConstants.CmsService);
/*
        if(title.equals(getResString(R.string.TermsCondition))){

            serviceHelper.enqueueCall(headerWebService.Cms("3"), CmsService);

        }else if(title.equals(getResString(R.string.termsOfServices))){
            serviceHelper.enqueueCall(headerWebService.Cms("2"), CmsService);

        }else if(title.equals(getResString(R.string.aboutUs))){
            serviceHelper.enqueueCall(headerWebService.Cms("1"), CmsService);
        }*/

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(title);
        if (title.equals(getResString(R.string.termsCondition))) {
            titleBar.showMenuButton();
        } else {
            titleBar.showMenuButton();
        }
    }

    @Override
    public void ResponseSuccess(Object result, UserEnt userEnt, String Tag, String message) {
        super.ResponseSuccess(result, userEnt, Tag, message);
        switch (Tag) {
            case CmsService:
                CMSEnt data = (CMSEnt) result;
                txtText.setText(data.getTermsCondition() + "");
                break;
        }
    }
}
