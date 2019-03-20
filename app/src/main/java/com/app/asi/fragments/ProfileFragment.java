package com.app.asi.fragments;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.asi.R;
import com.app.asi.fragments.abstracts.BaseFragment;
import com.app.asi.ui.views.TitleBar;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends BaseFragment {

    Unbinder unbinder;
    @BindView(R.id.profile_image)
    CircleImageView profileImage;
    @BindView(R.id.edt_username)
    TextInputEditText edtUsername;
    @BindView(R.id.edt_email)
    TextInputEditText edtEmail;
    @BindView(R.id.edt_phone)
    TextInputEditText edtPhone;
    @BindView(R.id.edt_companyName)
    TextInputEditText edtCompanyName;
    @BindView(R.id.edt_designation)
    TextInputEditText edtDesignation;
    @BindView(R.id.mainFrameLayout)
    LinearLayout mainFrameLayout;


    private ImageLoader imageLoader;

    public static ProfileFragment newInstance() {
        Bundle args = new Bundle();

        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        enableDisableViewGroup(mainFrameLayout, false);
        setData();

    }

    public static void enableDisableViewGroup(ViewGroup viewGroup, boolean enabled) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = viewGroup.getChildAt(i);
            view.setFocusable(enabled);
            view.setFocusableInTouchMode(enabled);
            if (view instanceof ViewGroup) {
                enableDisableViewGroup((ViewGroup) view, enabled);
            }
        }
    }

    private void setData() {
        if (prefHelper != null && prefHelper.getUser() != null) {
            if (prefHelper.getUser().getImageUrl() != null && !prefHelper.getUser().getImageUrl().equals("") && !prefHelper.getUser().getImageUrl().isEmpty()) {
                Picasso.get().load(prefHelper.getUser().getImageUrl()).placeholder(R.drawable.placeholder_thumb).into(profileImage);
            }
            if (prefHelper.getUser().getFullName() != null && !prefHelper.getUser().getFullName().equals("") && !prefHelper.getUser().getFullName().isEmpty()) {
                edtUsername.setText(prefHelper.getUser().getFullName());
            }
            if (prefHelper.getUser().getEmail() != null && !prefHelper.getUser().getEmail().equals("") && !prefHelper.getUser().getEmail().isEmpty()) {
                edtEmail.setText(prefHelper.getUser().getEmail());
            }
            if (prefHelper.getUser().getPhoneNo() != null && !prefHelper.getUser().getPhoneNo().equals("") && !prefHelper.getUser().getPhoneNo().isEmpty()) {
                edtPhone.setText(prefHelper.getUser().getPhoneCode() + prefHelper.getUser().getPhoneNo());
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
    public void onResume() {
        super.onResume();
        setData();
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.setSubHeading(getResString(R.string.profile));
        titleBar.showEdithButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDockActivity().replaceDockableFragment(EditProfileFragment.newInstance(), "EditProfileFragment");
            }
        });
    }


}
