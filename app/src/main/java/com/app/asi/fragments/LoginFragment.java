package com.app.asi.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.asi.R;
import com.app.asi.fragments.abstracts.BaseFragment;
import com.app.asi.ui.views.AnyTextView;
import com.app.asi.ui.views.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.app.asi.global.WebServiceConstants.Login;


public class LoginFragment extends BaseFragment {


    @BindView(R.id.edt_email)
    TextInputEditText edtEmail;
    @BindView(R.id.edt_password)
    TextInputEditText edtPassword;
    @BindView(R.id.btn_forgot_pass)
    AnyTextView btnForgotPass;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_signup)
    AnyTextView btnSignup;
    Unbinder unbinder;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        // TODO Auto-generated method stub
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;

    }


    @OnClick({R.id.btn_forgot_pass, R.id.btn_login, R.id.btn_signup})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_forgot_pass:
                getDockActivity().replaceDockableFragment(ForgotPasswordFragment.newInstance(), "ForgotPasswordFragment");
                break;
            case R.id.btn_login:
                if (isvalidated()) {
                    //      serviceHelper.enqueueCall(webService.loginUser(edtEmail.getText().toString(), edtPassword.getText().toString(), AppConstants.Device_Type, FirebaseInstanceId.getInstance().getToken()), Login);

                }
                break;

            case R.id.btn_signup:
                getDockActivity().replaceDockableFragment(SignupFragment.newInstance(), "SignupFragment");
                break;
        }
    }

    @Override
    public void ResponseSuccess(Object result, String Tag, String message) {
        super.ResponseSuccess(result, Tag, message);
        switch (Tag) {
            case Login:
             /*   UserEnt userEnt = (UserEnt) result;
                prefHelper.putUser(userEnt);
                prefHelper.set_TOKEN(userEnt.getUser().getAccessToken());

                if (userEnt.getUser().getIs_verified() == 1) {
                    prefHelper.setLoginStatus(true);
                    getDockActivity().popBackStackTillEntry(0);
                    getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                    if (getMainActivity() != null) {
                        getMainActivity().refreshSideMenu();
                    }
                } else {
                    getDockActivity().replaceDockableFragment(EmailVerificationFragment.newInstance(), "EmailVerificationFragment");
                }*/
                break;


        }
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    private boolean isvalidated() {
        if (edtEmail.getText() == null || edtEmail.getText().toString().trim().isEmpty() ||
                !Patterns.EMAIL_ADDRESS.matcher(edtEmail.getText().toString()).matches()) {
            edtEmail.setError(getString(R.string.enter_valid_email));
            if (edtEmail.requestFocus()) {
                setEditTextFocus(edtEmail);
            }
            return false;
        } else if (edtPassword.getText().toString().trim().isEmpty()) {
            edtPassword.setError(getString(R.string.enter_password));
            if (edtPassword.requestFocus()) {
                setEditTextFocus(edtPassword);
            }
            return false;
        } else
            return true;

    }


}
