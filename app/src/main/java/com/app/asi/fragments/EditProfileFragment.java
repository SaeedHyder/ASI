package com.app.asi.fragments;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.asi.R;
import com.app.asi.entities.UserEnt;
import com.app.asi.fragments.abstracts.BaseFragment;
import com.app.asi.helpers.CameraHelper;
import com.app.asi.helpers.UIHelper;
import com.app.asi.interfaces.ImageSetter;
import com.app.asi.ui.views.TitleBar;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.hbb20.CountryCodePicker;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import id.zelory.compressor.Compressor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.app.asi.global.WebServiceConstants.UpdateProfileService;

public class EditProfileFragment extends BaseFragment implements ImageSetter {
    private static final String TAG = "EditProfileFragment";
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.btnUploadImage)
    RelativeLayout btnUploadImage;
    @BindView(R.id.edt_username)
    TextInputEditText edtUsername;
    @BindView(R.id.edt_email)
    TextView edtEmail;
    @BindView(R.id.Countrypicker)
    CountryCodePicker Countrypicker;
    @BindView(R.id.edt_phone)
    TextInputEditText edtPhone;
    @BindView(R.id.edt_companyName)
    TextInputEditText edtCompanyName;
    @BindView(R.id.edt_designation)
    TextInputEditText edtDesignation;
    @BindView(R.id.btn_edit)
    Button btnEdit;
    Unbinder unbinder;


    private PhoneNumberUtil phoneUtil;
    private ImageLoader imageLoader;
    private File profilePic;
    private String profilePath;
    private Double latitude;
    private Double longitude;
    private String Address;

    public static EditProfileFragment newInstance() {
        Bundle args = new Bundle();

        EditProfileFragment fragment = new EditProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageLoader = ImageLoader.getInstance();
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editprofile, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        phoneUtil = PhoneNumberUtil.getInstance();
        edtPhone.setTransformationMethod(new NumericKeyBoardTransformationMethod());
        getMainActivity().setImageSetter(this);
        setData();

    }


    private void setData() {

        if (prefHelper != null && prefHelper.getUser() != null) {
            if (prefHelper.getUser().getImageUrl() != null && !prefHelper.getUser().getImageUrl().equals("") && !prefHelper.getUser().getImageUrl().isEmpty()) {
                Picasso.get().load(prefHelper.getUser().getImageUrl()).placeholder(R.drawable.placeholder_thumb).into(profileImage);
                profilePath=prefHelper.getUser().getImageUrl();
            }
            if (prefHelper.getUser().getFullName() != null && !prefHelper.getUser().getFullName().equals("") && !prefHelper.getUser().getFullName().isEmpty()) {
                edtUsername.setText(prefHelper.getUser().getFullName());
            }
            if (prefHelper.getUser().getEmail() != null && !prefHelper.getUser().getEmail().equals("") && !prefHelper.getUser().getEmail().isEmpty()) {
                edtEmail.setText(prefHelper.getUser().getEmail());
            }
            if (prefHelper.getUser().getPhoneNo() != null && !prefHelper.getUser().getPhoneNo().equals("") && !prefHelper.getUser().getPhoneNo().isEmpty()) {
                Countrypicker.setCountryForPhoneCode(Integer.parseInt(prefHelper.getUser().getPhoneCode()));
                edtPhone.setText(prefHelper.getUser().getPhoneNo());
            }
            if (prefHelper.getUser().getCompany() != null && !prefHelper.getUser().getCompany().equals("") && !prefHelper.getUser().getCompany().isEmpty()) {
                edtCompanyName.setText(prefHelper.getUser().getCompany());
            }
            if (prefHelper.getUser().getDesignation() != null && !prefHelper.getUser().getDesignation().equals("") && !prefHelper.getUser().getDesignation().isEmpty()) {
                edtDesignation.setText(prefHelper.getUser().getPhoneCode() + prefHelper.getUser().getPhoneNo());
            }

        }
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.edit_profile));
    }


    private boolean isvalidated() {
        if (edtUsername.getText().toString().trim().isEmpty() || edtUsername.getText().toString().trim().length() < 3) {
            edtUsername.setError(getString(R.string.enter_fullname));
            if (edtUsername.requestFocus()) {
                setEditTextFocus(edtUsername);
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
        } else if (profilePath == null) {
            UIHelper.showShortToastInDialoge(getDockActivity(), getString(R.string.upload_image));
            return false;
        } else
            return true;

    }


    @OnClick({R.id.btnUploadImage, R.id.btn_edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnUploadImage:
                requestCameraPermission();
                break;
            case R.id.btn_edit:
                if (isvalidated()) {
                    MultipartBody.Part filePart;
                    if (profilePic != null) {
                        filePart = MultipartBody.Part.createFormData("Image",
                                profilePic.getName(), RequestBody.create(MediaType.parse("image/*"), profilePic));
                    } else {
                        filePart = MultipartBody.Part.createFormData("Image", "",
                                RequestBody.create(MediaType.parse("*/*"), ""));
                    }

                    serviceHelper.enqueueCall(headerWebService.updateProfile(
                            RequestBody.create(MediaType.parse("text/plain"), edtUsername.getText().toString()),
                            RequestBody.create(MediaType.parse("text/plain"), Countrypicker.getSelectedCountryCodeWithPlus().toString()),
                            RequestBody.create(MediaType.parse("text/plain"), edtPhone.getText().toString()),
                            RequestBody.create(MediaType.parse("text/plain"), edtDesignation.getText().toString()),
                            RequestBody.create(MediaType.parse("text/plain"), edtCompanyName.getText().toString()),
                            filePart), UpdateProfileService);

                }
                break;
        }
    }

    @Override
    public void ResponseSuccess(Object result, UserEnt userEnt, String Tag, String message) {
        super.ResponseSuccess(result, userEnt, Tag, message);
        switch (Tag) {
            case UpdateProfileService:
                UserEnt userObject = prefHelper.getUser();
                userObject.setFullName(userEnt.getFullName());
                userObject.setPhoneCode(userEnt.getPhoneCode());
                userObject.setPhoneNo(userEnt.getPhoneNo());
                userObject.setDesignation(userEnt.getDesignation());
                userObject.setCompany(userEnt.getCompany());
                userObject.setImageUrl(userEnt.getImageUrl());
                prefHelper.putUser(userObject);
                getMainActivity().refreshSideMenuData();
                UIHelper.showShortToastInCenter(getDockActivity(), getResString(R.string.profile_updated_successfully));
                getDockActivity().popFragment();
                break;
        }
    }

    @Override
    public void setImage(String imagePath) {
        try {
            profilePic = new Compressor(getDockActivity()).compressToFile(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        profilePath = imagePath;
        imageLoader.displayImage("file:///" + imagePath, profileImage);

    }

    private void requestCameraPermission() {
        Dexter.withActivity(getDockActivity())
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {

                        if (report.areAllPermissionsGranted()) {
                            CameraHelper.uploadPhotoDialog(getMainActivity());
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            requestCameraPermission();

                        } else if (report.getDeniedPermissionResponses().size() > 0) {
                            requestCameraPermission();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        UIHelper.showShortToastInCenter(getDockActivity(), "Grant Camera And Storage Permission to processed");
                        openSettings();
                    }
                })

                .onSameThread()
                .check();


    }


    private void openSettings() {

        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        Uri uri = Uri.fromParts("package", getDockActivity().getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
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


}
