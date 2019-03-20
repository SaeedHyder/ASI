package com.app.asi.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.asi.R;
import com.app.asi.entities.UserEnt;
import com.app.asi.fragments.abstracts.BaseFragment;
import com.app.asi.global.AppConstants;
import com.app.asi.helpers.UIHelper;
import com.app.asi.ui.views.AnyTextView;
import com.app.asi.ui.views.PinEntryEditText;
import com.app.asi.ui.views.TitleBar;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.RequestBody;

import static com.app.asi.global.WebServiceConstants.ResendCode;
import static com.app.asi.global.WebServiceConstants.VerifyCode;
import static com.app.asi.global.WebServiceConstants.VerifyForgotPassword;

public class EmailVerificationFragment extends BaseFragment {

    @BindView(R.id.txt_pin_entry)
    PinEntryEditText txtPinEntry;
    @BindView(R.id.tv_counter)
    AnyTextView tvCounter;
    @BindView(R.id.ll_counter)
    LinearLayout llCounter;
    @BindView(R.id.btnResend)
    AnyTextView btnResend;
    @BindView(R.id.rlResendCode)
    RelativeLayout rlResendCode;
    @BindView(R.id.btn_continue)
    Button btnContinue;
    Unbinder unbinder1;
    private CountDownTimer timer;
    Unbinder unbinder;

    private static boolean isFromForgot = false;
    private static String email;


    /*public static EmailVerificationFragment newInstance() {
        Bundle args = new Bundle();
        isFromForgot = false;
        EmailVerificationFragment fragment = new EmailVerificationFragment();
        fragment.setArguments(args);
        return fragment;
    }*/

    public static EmailVerificationFragment newInstance(String emailKey, boolean isForgot) {
        Bundle args = new Bundle();
        isFromForgot = isForgot;
        email = emailKey;
        EmailVerificationFragment fragment = new EmailVerificationFragment();
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
        View view = inflater.inflate(R.layout.fragment_email_verification, container, false);

        unbinder1 = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        counter();
        textWatcher();
    }

    private void textWatcher() {
        txtPinEntry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.toString().length() == 4) {
                     /*    if (timer != null) {
                        timer.cancel();
                    }
               if (isFromForgot) {
                        serviceHelper.enqueueCall(webService.verifyForgotCode(prefHelper.getUser().getUser().getCountryCode(), prefHelper.getUser().getUser().getPhone(), txtPinEntry.getText().toString()), VerifyForgotPassword);
                    } else {
                        serviceHelper.enqueueCall(webService.verifyCode(prefHelper.getUser().getUser().getCountryCode(), prefHelper.getUser().getUser().getPhone(), txtPinEntry.getText().toString()), VerifyCode);
                    }*/
                }
            }
        });
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
    //    titleBar.setSubHeading(getResString(R.string.email_verification));
    }


    @OnClick({R.id.btnResend, R.id.btn_continue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnResend:
                serviceHelper.enqueueCall(webService.resendCode(email), ResendCode);
                break;
            case R.id.btn_continue:
                if (isvalidated()) {

                    JSONObject verifyCode = new JSONObject();
                    try {
                        verifyCode.put("email", email);
                        verifyCode.put("code", txtPinEntry.getText().toString());
                        verifyCode.put("deviceType", AppConstants.Device_Type);
                        verifyCode.put("deviceToken", FirebaseInstanceId.getInstance().getToken());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), verifyCode.toString());
                    if (isFromForgot) {
                        serviceHelper.enqueueCall(webService.verifyCode(body), VerifyForgotPassword);
                    } else {
                        serviceHelper.enqueueCall(webService.verifyCode(body), VerifyCode);

                    }
                }
                break;
        }
    }

    private boolean isvalidated() {
        if (txtPinEntry.getText().toString().trim().isEmpty() || txtPinEntry.getText().toString().equals("")) {
            UIHelper.showShortToastInDialoge(getDockActivity(), getResString(R.string.enter_valid_pincode));
            return false;
        } else if (txtPinEntry.getText().toString().trim().length() < 4) {
            UIHelper.showShortToastInDialoge(getDockActivity(), getResString(R.string.enter_valid_pincode));
            return false;
        } else
            return true;

    }

    public void counter() {
        timer = new CountDownTimer(120000, 1000) {

            public void onTick(long millisUntilFinished) {

                String text = String.format(Locale.getDefault(), "%2d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                if (tvCounter != null) {
                    tvCounter.setText(text + "");
                    tvCounter.setTypeface(Typeface.DEFAULT_BOLD);
                }
            }

            public void onFinish() {
                if (tvCounter != null) {
                    llCounter.setVisibility(View.GONE);
                    rlResendCode.setVisibility(View.VISIBLE);
                }
            }
        }.start();
    }

    @Override
    public void ResponseSuccess(Object result, UserEnt userEnt, String Tag, String message) {
        super.ResponseSuccess(result, userEnt, Tag, message);
        switch (Tag) {
            case VerifyForgotPassword:
                if (userEnt != null) {
                    prefHelper.putUser(userEnt);
                    if (userEnt.getToken() != null)
                        prefHelper.set_TOKEN(userEnt.getToken());
                }
                if (timer != null) {
                    timer.cancel();
                }
                UIHelper.hideSoftKeyboard(getDockActivity(),txtPinEntry);
                getDockActivity().popFragment();
                getDockActivity().replaceDockableFragment(ResetPasswordFragment.newInstance(txtPinEntry.getText().toString()), "ResetPasswordFragment");
                break;

            case VerifyCode:
                if (timer != null) {
                    timer.cancel();
                }
                prefHelper.setLoginStatus(true);
                getDockActivity().popBackStackTillEntry(0);
                getMainActivity().refreshSideMenu();
                UIHelper.hideSoftKeyboard(getDockActivity(),txtPinEntry);
                getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");
                UIHelper.showShortToastInCenter(getDockActivity(), getResString(R.string.account_verify));
                break;

            case ResendCode:
                UIHelper.showShortToastInDialoge(getDockActivity(), message);
                counter();
                rlResendCode.setVisibility(View.GONE);
                llCounter.setVisibility(View.VISIBLE);
                break;
        }
    }


}
