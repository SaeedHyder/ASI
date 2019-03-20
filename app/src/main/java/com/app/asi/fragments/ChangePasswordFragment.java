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

import static com.app.asi.global.WebServiceConstants.ChangePassword;

public class ChangePasswordFragment extends BaseFragment {
    @BindView(R.id.edt_old_password)
    TextInputEditText edtOldPassword;
    @BindView(R.id.edt_new_password)
    TextInputEditText edtNewPassword;
    @BindView(R.id.edt_confirpassword)
    TextInputEditText edtConfirpassword;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    Unbinder unbinder;
    @BindView(R.id.eyeOldPass)
    ToggleButton eyeOldPass;
    @BindView(R.id.eyeNewPass)
    ToggleButton eyeNewPass;
    @BindView(R.id.eyeConfPass)
    ToggleButton eyeConfPass;

    public static ChangePasswordFragment newInstance() {
        Bundle args = new Bundle();

        ChangePasswordFragment fragment = new ChangePasswordFragment();
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
        View view = inflater.inflate(R.layout.fragment_changepassword, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (edtOldPassword.requestFocus()) {
            setEditTextFocus(edtOldPassword);
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.change_password));
    }

    private boolean isvalidate() {
        if (edtOldPassword.getText() == null || (edtOldPassword.getText().toString().trim().isEmpty())) {
            edtOldPassword.setError(getString(R.string.enter_password));
            if (edtOldPassword.requestFocus()) {
                setEditTextFocus(edtOldPassword);
            }
            return false;
        } else if (edtNewPassword.getText() == null || (edtNewPassword.getText().toString().trim().isEmpty())) {
            edtNewPassword.setError(getString(R.string.enter_password));
            if (edtNewPassword.requestFocus()) {
                setEditTextFocus(edtNewPassword);
            }
            return false;
        } else if (edtNewPassword.getText().toString().equals(edtOldPassword.getText().toString())) {
            edtNewPassword.setError(getString(R.string.samePassword));
            if (edtNewPassword.requestFocus()) {
                setEditTextFocus(edtNewPassword);
            }
            return false;
        } else if (edtNewPassword.getText().toString().trim().length() < 6) {
            edtNewPassword.setError(getString(R.string.passwordLength));
            if (edtNewPassword.requestFocus()) {
                setEditTextFocus(edtNewPassword);
            }
            return false;
        } else if (edtConfirpassword.getText() == null || (edtConfirpassword.getText().toString().trim().isEmpty())) {
            edtConfirpassword.setError(getString(R.string.enter_confirm_password));
            if (edtConfirpassword.requestFocus()) {
                setEditTextFocus(edtConfirpassword);
            }
            return false;
        } else if (edtConfirpassword.getText().toString().trim().length() < 6) {
            edtConfirpassword.setError(getString(R.string.confirmpasswordLength));
            if (edtConfirpassword.requestFocus()) {
                setEditTextFocus(edtConfirpassword);
            }
            return false;
        } else if (!edtConfirpassword.getText().toString().trim().equals(edtNewPassword.getText().toString().trim())) {
            edtConfirpassword.setError(getString(R.string.conform_password_error));
            if (edtConfirpassword.requestFocus()) {
                setEditTextFocus(edtConfirpassword);
            }
            return false;
        } else {
            return true;
        }
    }


    @Override
    public void ResponseSuccess(Object result, UserEnt userEnt, String Tag, String message) {
        super.ResponseSuccess(result, userEnt, Tag, message);
        switch (Tag) {
            case ChangePassword:
                UIHelper.showShortToastInCenter(getDockActivity(), getResString(R.string.password_changed));
                getDockActivity().popBackStackTillEntry(0);
                getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");

                break;
        }
    }


    @OnClick({R.id.eyeOldPass, R.id.eyeNewPass, R.id.eyeConfPass, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.eyeOldPass:
                boolean isCheck0 = eyeOldPass.isChecked();

                if (isCheck0) {
                    edtOldPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edtOldPassword.setSelection(edtOldPassword.getText().length());
                } else {
                    edtOldPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edtOldPassword.setSelection(edtOldPassword.getText().length());
                }
                break;
            case R.id.eyeNewPass:
                boolean isCheck = eyeNewPass.isChecked();

                if (isCheck) {
                    edtNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    edtNewPassword.setSelection(edtNewPassword.getText().length());
                } else {
                    edtNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    edtNewPassword.setSelection(edtNewPassword.getText().length());
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
                        updatePassword.put("currentPassword", edtOldPassword.getText().toString());
                        updatePassword.put("newPassword", edtNewPassword.getText().toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), updatePassword.toString());
                    serviceHelper.enqueueCall(headerWebService.changePassword(body), ChangePassword);


                }
                break;
        }
    }
}
