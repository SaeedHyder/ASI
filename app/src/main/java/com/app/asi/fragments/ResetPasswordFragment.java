package com.app.asi.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ToggleButton;

import com.app.asi.R;
import com.app.asi.entities.UserEnt;
import com.app.asi.fragments.abstracts.BaseFragment;
import com.app.asi.global.AppConstants;
import com.app.asi.helpers.UIHelper;
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

import static com.app.asi.global.WebServiceConstants.ResetPassword;

public class ResetPasswordFragment extends BaseFragment {
    @BindView(R.id.edt_password)
    TextInputEditText edtPassword;
    @BindView(R.id.edt_confirpassword)
    TextInputEditText edtConfirpassword;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @BindView(R.id.eyeNewPass)
    ToggleButton eyeNewPass;
    @BindView(R.id.eyeConfPass)
    ToggleButton eyeConfPass;
    Unbinder unbinder;

    private static String pinCode;

    public static ResetPasswordFragment newInstance(String code) {
        Bundle args = new Bundle();
        pinCode = code;
        ResetPasswordFragment fragment = new ResetPasswordFragment();
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
        View view = inflater.inflate(R.layout.fragment_reset_pasword, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (edtPassword.requestFocus()) {
            setEditTextFocus(edtPassword);
        }
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
      //  titleBar.setSubHeading(getResString(R.string.reset_password));
    }

    private boolean isvalidate() {
        if (edtPassword.getText() == null || (edtPassword.getText().toString().trim().isEmpty())) {
            edtPassword.setError(getResString(R.string.enter_password));
            if (edtPassword.requestFocus()) {
                setEditTextFocus(edtPassword);
            }
            return false;
        } else if (edtPassword.getText().toString().trim().length() < 6) {
            edtPassword.setError(getResString(R.string.passwordLength));
            if (edtPassword.requestFocus()) {
                setEditTextFocus(edtPassword);
            }
            return false;
        } else if (edtConfirpassword.getText() == null || (edtConfirpassword.getText().toString().trim().isEmpty())) {
            edtConfirpassword.setError(getResString(R.string.enter_confirm_password));
            if (edtConfirpassword.requestFocus()) {
                setEditTextFocus(edtConfirpassword);
            }
            return false;
        } else if (edtConfirpassword.getText().toString().trim().length() < 6) {
            edtConfirpassword.setError(getResString(R.string.confirmpasswordLength));
            if (edtConfirpassword.requestFocus()) {
                setEditTextFocus(edtConfirpassword);
            }
            return false;
        } else if (!edtConfirpassword.getText().toString().trim().equals(edtPassword.getText().toString().trim())) {
            edtConfirpassword.setError(getResString(R.string.conform_password_error));
            if (edtConfirpassword.requestFocus()) {
                setEditTextFocus(edtConfirpassword);
            }
            return false;
        } else {
            return true;
        }
    }


    @OnClick({R.id.eyeNewPass, R.id.eyeConfPass, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.eyeNewPass:
                boolean isCheck = eyeNewPass.isChecked();

                if (isCheck) {
                    edtPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edtPassword.setSelection(edtPassword.getText().length());
                } else {
                    edtPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edtPassword.setSelection(edtPassword.getText().length());
                }
                break;
            case R.id.eyeConfPass:
                boolean isCheck1 = eyeConfPass.isChecked();

                if (isCheck1) {
                    edtConfirpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edtConfirpassword.setSelection(edtConfirpassword.getText().length());
                } else {
                    edtConfirpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edtConfirpassword.setSelection(edtConfirpassword.getText().length());
                }
                break;

            case R.id.btn_submit:
                if (isvalidate()) {

                    JSONObject updatePassword = new JSONObject();
                    try {
                        updatePassword.put("email", prefHelper.getUser().getEmail());
                        updatePassword.put("password", edtPassword.getText().toString());
                        updatePassword.put("deviceType", AppConstants.Device_Type);
                        updatePassword.put("deviceToken", FirebaseInstanceId.getInstance().getToken());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), updatePassword.toString());


                    serviceHelper.enqueueCall(headerWebService.updatePassword(body),ResetPassword);
                }
                break;
        }
    }

    @Override
    public void ResponseSuccess(Object result, UserEnt userEnt, String Tag, String message) {
        super.ResponseSuccess(result,userEnt, Tag, message);
        switch (Tag) {
            case ResetPassword:
                UIHelper.showShortToastInCenter(getDockActivity(), getResString(R.string.updated_succcessfully));
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                break;
        }
    }
}
