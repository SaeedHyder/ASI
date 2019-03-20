package com.app.asi.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.asi.R;
import com.app.asi.entities.UserEnt;
import com.app.asi.fragments.abstracts.BaseFragment;
import com.app.asi.global.AppConstants;
import com.app.asi.ui.views.AnyTextView;
import com.app.asi.ui.views.TitleBar;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.app.asi.global.WebServiceConstants.Login;
import static com.app.asi.global.WebServiceConstants.ResendCode;


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
                    JSONObject login = new JSONObject();
                    try {
                        login.put("email", edtEmail.getText().toString());
                        login.put("password", edtPassword.getText().toString());
                        login.put("deviceType", AppConstants.Device_Type);
                        login.put("deviceToken", FirebaseInstanceId.getInstance().getToken());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), login.toString());
                    serviceHelper.enqueueCall(webService.loginUser(body), Login);

                }
                break;

            case R.id.btn_signup:
                getDockActivity().replaceDockableFragment(SignupFragment.newInstance(), "SignupFragment");
                break;


        }
    }

    @Override
    public void ResponseSuccess(Object result, UserEnt userEnt, String Tag, String message) {
        super.ResponseSuccess(result, userEnt, Tag, message);
        switch (Tag) {
            case Login:
                if (userEnt != null) {
                    prefHelper.putUser(userEnt);
                    if (userEnt.getToken() != null)
                        prefHelper.set_TOKEN(userEnt.getToken());

                    if (userEnt.getVerified()) {
                        prefHelper.setLoginStatus(true);
                        getDockActivity().popBackStackTillEntry(0);
                        getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                        if (getMainActivity() != null) {
                            getMainActivity().refreshSideMenu();
                        }
                    } else {
                        serviceHelper.enqueueCall(webService.resendCode(edtEmail.getText().toString()), ResendCode);

                    }
                }
                break;
            case ResendCode:
                getDockActivity().replaceDockableFragment(EmailVerificationFragment.newInstance(edtEmail.getText().toString(), false), "EmailVerificationFragment");
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
