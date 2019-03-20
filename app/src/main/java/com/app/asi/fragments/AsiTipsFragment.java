package com.app.asi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.asi.R;
import com.app.asi.fragments.abstracts.BaseFragment;
import com.app.asi.helpers.UIHelper;
import com.app.asi.ui.views.AnyTextView;
import com.app.asi.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class AsiTipsFragment extends BaseFragment {

    @BindView(R.id.btnTrivia)
    AnyTextView btnTrivia;
    @BindView(R.id.btnTips)
    AnyTextView btnTips;
    Unbinder unbinder;

    public static AsiTipsFragment newInstance() {
        Bundle args = new Bundle();

        AsiTipsFragment fragment = new AsiTipsFragment();
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
        View view = inflater.inflate(R.layout.fragment_asi_tips, container, false);
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
        titleBar.showBackButton();
    }

    @OnClick({ R.id.btnTrivia, R.id.btnTips})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnTrivia:
                UIHelper.showShortToastInDialoge(getDockActivity(),getResString(R.string.will_be_implemented));
                break;
            case R.id.btnTips:
                getDockActivity().replaceDockableFragment(TipsFragment.newInstance(),"TipsFragment");
                break;
        }
    }
}
