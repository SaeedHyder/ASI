package com.app.asi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.app.asi.R;
import com.app.asi.fragments.abstracts.BaseFragment;
import com.app.asi.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class WelcomeScreenFragment extends BaseFragment {
    @BindView(R.id.btnCross)
    FrameLayout btnCross;
    @BindView(R.id.btnSignUp)
    Button btnSignUp;
    Unbinder unbinder;

    public static WelcomeScreenFragment newInstance() {
        Bundle args = new Bundle();

        WelcomeScreenFragment fragment = new WelcomeScreenFragment();
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
        View view = inflater.inflate(R.layout.fragment_welcome_screen, container, false);
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
        titleBar.hideTitleBar();
    }



    @OnClick({R.id.btnCross, R.id.btnSignUp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnCross:
                getDockActivity().popFragment();
                getDockActivity().replaceDockableFragment(LoginFragment.newInstance(),"LoginFragment");
                break;
            case R.id.btnSignUp:
                getDockActivity().popFragment();
                getDockActivity().replaceDockableFragment(LoginFragment.newInstance(),"LoginFragment");
                getDockActivity().replaceDockableFragment(SignupFragment.newInstance(),"SignupFragment");
                break;
        }
    }
}
