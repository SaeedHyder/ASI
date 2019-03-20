package com.app.asi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.asi.R;
import com.app.asi.fragments.abstracts.BaseFragment;
import com.app.asi.helpers.UIHelper;
import com.app.asi.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DealFragment extends BaseFragment {


    @BindView(R.id.d10)
    ImageView d10;
    @BindView(R.id.d34)
    ImageView d34;
    @BindView(R.id.d75)
    ImageView d75;
    @BindView(R.id.d09)
    ImageView d09;
    @BindView(R.id.d32)
    ImageView d32;
    @BindView(R.id.d73)
    ImageView d73;
    @BindView(R.id.d05)
    ImageView d05;
    @BindView(R.id.d28)
    ImageView d28;
    @BindView(R.id.d49)
    ImageView d49;
    @BindView(R.id.d70)
    ImageView d70;
    @BindView(R.id.d01)
    ImageView d01;
    Unbinder unbinder;

    public static DealFragment newInstance() {
        Bundle args = new Bundle();

        DealFragment fragment = new DealFragment();
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
        View view = inflater.inflate(R.layout.fragment_deal, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showLogo();
        titleBar.showBackButton();
        /*titleBar.showSearchButtonEnd(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIHelper.showShortToastInDialoge(getDockActivity(), getResString(R.string.will_be_implemented));
            }
        });*/
    }



    @OnClick({R.id.d10, R.id.d34, R.id.d75, R.id.d09, R.id.d32, R.id.d73, R.id.d05, R.id.d28, R.id.d49, R.id.d70, R.id.d01})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.d10:
                break;
            case R.id.d34:
                getDockActivity().replaceDockableFragment(D34Fragment.newInstance(),"D34Fragment");
                break;
            case R.id.d75:
                break;
            case R.id.d09:
                break;
            case R.id.d32:
                getDockActivity().replaceDockableFragment(D32Fragment.newInstance(),"D32Fragment");
                break;
            case R.id.d73:
                break;
            case R.id.d05:
                getDockActivity().replaceDockableFragment(D05Fragment.newInstance(),"D05Fragment");
                break;
            case R.id.d28:
                getDockActivity().replaceDockableFragment(D28Fragment.newInstance(),"D28Fragment");
                break;
            case R.id.d49:
                getDockActivity().replaceDockableFragment(D49Fragment.newInstance(),"D49Fragment");
                break;
            case R.id.d70:
                break;
            case R.id.d01:
                getDockActivity().replaceDockableFragment(D01Fragment.newInstance(),"D01Fragment");
                break;
        }
    }
}
