package com.app.asi.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.app.asi.R;
import com.app.asi.entities.UserEnt;
import com.app.asi.fragments.abstracts.BaseFragment;
import com.app.asi.global.AppConstants;
import com.app.asi.ui.views.AnyTextView;
import com.app.asi.ui.views.TitleBar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.hbb20.CountryCodePicker;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.app.asi.global.WebServiceConstants.Signup;

public class SignupFragment extends BaseFragment {


    @BindView(R.id.edt_username)
    TextInputEditText edtUsername;
    @BindView(R.id.edt_email)
    TextInputEditText edtEmail;
    @BindView(R.id.edt_password)
    TextInputEditText edtPassword;
    @BindView(R.id.edt_confirpassword)
    TextInputEditText edtConfirpassword;
    @BindView(R.id.ll_password)
    LinearLayout llPassword;
    @BindView(R.id.Countrypicker)
    CountryCodePicker Countrypicker;
    @BindView(R.id.edt_phone)
    TextInputEditText edtPhone;
    @BindView(R.id.edt_companyName)
    TextInputEditText edtCompanyName;
    @BindView(R.id.edt_designation)
    TextInputEditText edtDesignation;
    @BindView(R.id.btnSignUp)
    Button btnSignUp;
    @BindView(R.id.btnSignIn)
    AnyTextView btnSignIn;
    Unbinder unbinder;
    private PhoneNumberUtil phoneUtil;

    public static SignupFragment newInstance() {
        Bundle args = new Bundle();

        SignupFragment fragment = new SignupFragment();
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
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        phoneUtil = PhoneNumberUtil.getInstance();
        edtPhone.setTransformationMethod(new NumericKeyBoardTransformationMethod());

        //  Countrypicker.registerCarrierNumberEditText(edtPhone);

    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();

    }

    private boolean isvalidated() {
        if (edtUsername.getText().toString().trim().isEmpty() || edtUsername.getText().toString().trim().length() < 3) {
            edtUsername.setError(getString(R.string.enter_fullname));
            if (edtUsername.requestFocus()) {
                setEditTextFocus(edtUsername);
            }
            return false;
        } else if (edtEmail.getText() == null || edtEmail.getText().toString().trim().isEmpty() ||
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
        } else if (edtPassword.getText().toString().trim().length() < 6) {
            edtPassword.setError(getString(R.string.passwordLength));
            if (edtPassword.requestFocus()) {
                setEditTextFocus(edtPassword);
            }
            return false;
        } else if (edtConfirpassword.getText().toString().trim().isEmpty()) {
            edtConfirpassword.setError(getString(R.string.enter_password));
            if (edtConfirpassword.requestFocus()) {
                setEditTextFocus(edtConfirpassword);
            }
            return false;
        } else if (!edtConfirpassword.getText().toString().trim().equals(edtPassword.getText().toString().trim())) {
            edtConfirpassword.setError(getString(R.string.confirm_password_error));
            if (edtConfirpassword.requestFocus()) {
                setEditTextFocus(edtConfirpassword);
            }
            return false;
        } else if (edtPhone.getText().toString().trim().isEmpty() || edtPhone.getText().toString().trim().length() < 3) {
            edtPhone.setError(getString(R.string.enter_phonenumber));
            if (edtPhone.requestFocus()) {
                setEditTextFocus(edtPhone);
            }
            return false;
        } else if (!isPhoneNumberValid()) {
            edtPhone.setError(getDockActivity().getResources().getString(R.string.enter_valid_number_error));
            if (edtPhone.requestFocus()) {
                setEditTextFocus(edtPhone);
            }
            return false;
        } else if (edtCompanyName.getText().toString().trim().isEmpty() || edtCompanyName.getText().toString().trim().length() < 3) {
            edtCompanyName.setError(getString(R.string.enter_company));
            if (edtCompanyName.requestFocus()) {
                setEditTextFocus(edtCompanyName);
            }
            return false;
        } else if (edtDesignation.getText().toString().trim().isEmpty() || edtDesignation.getText().toString().trim().length() < 3) {
            edtDesignation.setError(getString(R.string.enter_designation));
            if (edtDesignation.requestFocus()) {
                setEditTextFocus(edtDesignation);
            }
            return false;
        } else
            return true;

    }

    private void removeErrorsFromEditText() {
        edtUsername.setError(null);
        edtEmail.setError(null);
        edtPassword.setError(null);
        edtConfirpassword.setError(null);
        edtPhone.setError(null);
        edtEmail.setError(null);
    }

    private boolean isPhoneNumberValid() {

        try {
            Phonenumber.PhoneNumber number = phoneUtil.parse(edtPhone.getText().toString(), Countrypicker.getSelectedCountryNameCode());
            if (phoneUtil.isValidNumber(number)) {
                return true;
            } else {

                return false;
            }
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
            edtPhone.setError(getDockActivity().getResources().getString(R.string.enter_valid_number_error));
            return false;

        }
    }


    @OnClick({R.id.btnSignUp, R.id.btnSignIn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnSignIn:
                getDockActivity().popFragment();

                break;
            case R.id.btnSignUp:
                if (isvalidated()) {
                    JSONObject signUp = new JSONObject();
                    try {
                        signUp.put("email", edtEmail.getText().toString());
                        signUp.put("password", edtPassword.getText().toString());
                        signUp.put("phoneCode", Countrypicker.getSelectedCountryCodeWithPlus().toString());
                        signUp.put("phoneNo", edtPhone.getText().toString());
                        signUp.put("fullName", edtUsername.getText().toString());
                        signUp.put("imageUrl", "");
                        signUp.put("company", edtCompanyName.getText().toString());
                        signUp.put("designation", edtDesignation.getText().toString());
                        signUp.put("deviceType", AppConstants.Device_Type);
                        signUp.put("deviceToken", FirebaseInstanceId.getInstance().getToken());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), signUp.toString());

                    serviceHelper.enqueueCall(webService.registerUser(body), Signup);

                }
                break;
        }
    }


    @Override
    public void ResponseSuccess(Object result, UserEnt userEnt, String Tag, String message) {
        super.ResponseSuccess(result, userEnt, Tag, message);
        switch (Tag) {
            case Signup:
                if (userEnt != null) {
                    prefHelper.putUser(userEnt);
                    if (userEnt.getToken() != null)
                        prefHelper.set_TOKEN(userEnt.getToken());
                    getDockActivity().replaceDockableFragment(EmailVerificationFragment.newInstance(edtEmail.getText().toString(),false), "EmailVerificationFragment");
                }
                break;
        }
    }


}
