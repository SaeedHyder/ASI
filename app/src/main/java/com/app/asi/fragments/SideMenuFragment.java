package com.app.asi.fragments;

import android.app.NotificationManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.asi.R;
import com.app.asi.entities.SideMenuEnt;
import com.app.asi.entities.UserEnt;
import com.app.asi.fragments.abstracts.BaseFragment;
import com.app.asi.global.AppConstants;
import com.app.asi.helpers.DialogHelper;
import com.app.asi.helpers.UIHelper;
import com.app.asi.interfaces.RecyclerClickListner;
import com.app.asi.ui.binders.SideMenuBinder;
import com.app.asi.ui.views.AnyTextView;
import com.app.asi.ui.views.CustomRecyclerView;
import com.app.asi.ui.views.TitleBar;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.app.asi.global.WebServiceConstants.Logout;

public class SideMenuFragment extends BaseFragment implements RecyclerClickListner {


    @BindView(R.id.SideMenu)
    CustomRecyclerView SideMenu;
    @BindView(R.id.btnProfile)
    RelativeLayout btnProfile;
    Unbinder unbinder;
    @BindView(R.id.txtName)
    AnyTextView txtName;
    @BindView(R.id.txtCompany)
    AnyTextView txtCompany;
    @BindView(R.id.btnFb)
    ImageView btnFb;
    @BindView(R.id.btnYoutube)
    ImageView btnYoutube;
    @BindView(R.id.btnTwitter)
    ImageView btnTwitter;
    @BindView(R.id.btnInsta)
    ImageView btnInsta;
    @BindView(R.id.btnLinkedin)
    ImageView btnLinkedin;
    @BindView(R.id.txtWebsite)
    AnyTextView txtWebsite;
    @BindView(R.id.ivBackground)
    ImageView ivBackground;
    @BindView(R.id.ivImage)
    CircleImageView ivImage;
    private ArrayList<SideMenuEnt> collection;
    private long mLastClickTime = 0;
    private int previousSelectedPos = 0;

    public static SideMenuFragment newInstance() {
        return new SideMenuFragment();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sidemenu, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtWebsite.setPaintFlags(txtWebsite.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        setProfile();
        setData();

    }

    public void setProfile() {
        if (prefHelper != null && prefHelper.getUser() != null) {
            if (prefHelper.getUser() != null && prefHelper.getUser().getImageUrl() != null && !prefHelper.getUser().getImageUrl().equals("") && !prefHelper.getUser().getImageUrl().isEmpty()) {
                ivBackground.setVisibility(View.VISIBLE);
                Picasso.get().load(prefHelper.getUser().getImageUrl()).placeholder(R.drawable.placeholder).into(ivImage);
                Picasso.get().load(prefHelper.getUser().getImageUrl()).placeholder(R.drawable.placeholder_thumb).into(ivBackground);
            } else {
                ivBackground.setVisibility(View.GONE);
            }
            if (prefHelper.getUser().getFullName() != null && !prefHelper.getUser().getFullName().equals("") && !prefHelper.getUser().getFullName().isEmpty()) {
                txtName.setVisibility(View.VISIBLE);
                txtName.setText(prefHelper.getUser().getFullName());
            } else {
                txtName.setVisibility(View.GONE);
            }
            if (prefHelper.getUser().getCompany() != null && !prefHelper.getUser().getCompany().equals("") && !prefHelper.getUser().getCompany().isEmpty()) {
                txtCompany.setVisibility(View.VISIBLE);
                txtCompany.setText(prefHelper.getUser().getCompany());
            } else {
                txtCompany.setVisibility(View.GONE);
            }
        }
    }


    private void setData() {

        collection = new ArrayList<>();
        collection.add(new SideMenuEnt(getResString(R.string.home), true));
        collection.add(new SideMenuEnt(getResString(R.string.games_played)));
        collection.add(new SideMenuEnt(getResString(R.string.wish_list)));
        collection.add(new SideMenuEnt(getResString(R.string.settings)));
        collection.add(new SideMenuEnt(getResString(R.string.notifications)));
        collection.add(new SideMenuEnt(getResString(R.string.termsCondition)));
        collection.add(new SideMenuEnt(getResString(R.string.logout)));


        SideMenu.BindRecyclerView(new SideMenuBinder(getDockActivity(), prefHelper, this), collection,
                new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false)
                , new DefaultItemAnimator());
        SideMenu.setNestedScrollingEnabled(false);
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideTitleBar();
    }


    @Override
    public void onClick(Object entity, int position) {


        collection.get(position).setSelected(true);
        SideMenu.notifyItemChanged(position);
        if (previousSelectedPos != position) {
            collection.get(previousSelectedPos).setSelected(false);
            SideMenu.notifyItemChanged(previousSelectedPos);
        }
        previousSelectedPos = position;


        SideMenuEnt ent = (SideMenuEnt) entity;

        if (ent.getTitle().equals(getResString(R.string.home))) {
            getDockActivity().popFragment();
            getDockActivity().replaceDockableFragment(HomeFragment.newInstance(), "HomeFragment");

        } else if (ent.getTitle().equals(getResString(R.string.wish_list))) {
            if (prefHelper.isGuestUser()) {
                openGuestDialoge();
            } else {
                getDockActivity().popFragment();
                getDockActivity().replaceDockableFragment(WishListFragment.newInstance(), "WishListFragment");
            }

        } else if (ent.getTitle().equals(getResString(R.string.games_played))) {
            if (prefHelper.isGuestUser()) {
                openGuestDialoge();
            } else {
                getDockActivity().popFragment();
                getDockActivity().replaceDockableFragment(GamePlayedFragment.newInstance(), "GamePlayedFragment");
            }

        } else if (ent.getTitle().equals(getResString(R.string.settings))) {
            if (prefHelper.isGuestUser()) {
                openGuestDialoge();
            } else {
                getDockActivity().popFragment();
                getDockActivity().replaceDockableFragment(SettingFragment.newInstance(), "SettingFragment");
            }


        } else if (ent.getTitle().equals(getResString(R.string.notifications))) {
            if (prefHelper.isGuestUser()) {
                openGuestDialoge();
            } else {
                getDockActivity().popFragment();
                getDockActivity().replaceDockableFragment(NotificationsFragment.newInstance(true), "NotificationsFragment");
            }

        } else if (ent.getTitle().equals(getResString(R.string.termsCondition))) {
            getDockActivity().popFragment();
            getDockActivity().replaceDockableFragment(CmsFragment.newInstance(getResString(R.string.termsCondition)), "CmsFragment");

        } else if (ent.getTitle().equals(getResString(R.string.logout))) {
            DialogHelper dialoge = new DialogHelper(getDockActivity());
            dialoge.initDialoge(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    serviceHelper.enqueueCall(headerWebService.logout(AppConstants.Device_Type, FirebaseInstanceId.getInstance().getToken()), Logout);
                    dialoge.hideDialog();
                }
            }, getResString(R.string.logout), getResString(R.string.are_you_sure_you_want_to_logout), getResString(R.string.yes), getResString(R.string.no));
            dialoge.showDialog();
        } else if (ent.getTitle().equals(getResString(R.string.sign_in))) {
            prefHelper.setGuestStatus(false);
            getDockActivity().popBackStackTillEntry(0);
            getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");
        }
    }

    @Override
    public void ResponseSuccess(Object result, UserEnt userEnt, String Tag, String message) {
        super.ResponseSuccess(result, userEnt, Tag, message);
        switch (Tag) {
            case Logout:
                getDockActivity().popBackStackTillEntry(0);
                prefHelper.setLoginStatus(false);
                prefHelper.setGuestStatus(false);
               /* if (AccessToken.getCurrentAccessToken() != null) {
                    LoginManager.getInstance().logOut();
                }*/
                NotificationManager notificationManager = (NotificationManager) getDockActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.cancelAll();
                getDockActivity().replaceDockableFragment(LoginFragment.newInstance(), "LoginFragment");
                UIHelper.showShortToastInCenter(getDockActivity(), getResString(R.string.logout_successfully));
                break;

        }
    }

    public void refreshSideMenuData() {

        previousSelectedPos = 0;
        collection = new ArrayList<>();

        collection.add(new SideMenuEnt(getResString(R.string.home), true));
        collection.add(new SideMenuEnt(getResString(R.string.games_played)));
        collection.add(new SideMenuEnt(getResString(R.string.wish_list)));
        collection.add(new SideMenuEnt(getResString(R.string.settings)));
        collection.add(new SideMenuEnt(getResString(R.string.notifications)));
        collection.add(new SideMenuEnt(getResString(R.string.termsCondition)));
        collection.add(new SideMenuEnt(getResString(R.string.logout)));


        SideMenu.BindRecyclerView(new SideMenuBinder(getDockActivity(), prefHelper, this), collection,
                new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false)
                , new DefaultItemAnimator());
    }

    public void setGuestSideMenu() {
        previousSelectedPos = 0;
        collection = new ArrayList<>();

        collection.add(new SideMenuEnt(getResString(R.string.home), true));
        collection.add(new SideMenuEnt(getResString(R.string.games_played)));
        collection.add(new SideMenuEnt(getResString(R.string.wish_list)));
        collection.add(new SideMenuEnt(getResString(R.string.settings)));
        collection.add(new SideMenuEnt(getResString(R.string.notifications)));
        collection.add(new SideMenuEnt(getResString(R.string.termsCondition)));
        collection.add(new SideMenuEnt(getResString(R.string.sign_in)));


        SideMenu.BindRecyclerView(new SideMenuBinder(getDockActivity(), prefHelper, this), collection,
                new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false)
                , new DefaultItemAnimator());


        ivBackground.setVisibility(View.GONE);
        txtName.setText(R.string.guest_user);
        txtCompany.setVisibility(View.GONE);
    }


    @OnClick({R.id.btnProfile, R.id.btnFb, R.id.btnYoutube, R.id.btnTwitter, R.id.btnInsta, R.id.btnLinkedin, R.id.txtWebsite})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnProfile:
                if (!prefHelper.isGuestUser()) {
                    getDockActivity().replaceDockableFragment(ProfileFragment.newInstance(), "ProfileFragment");
                }
                break;
            case R.id.btnFb:
                openWebPage("https://www.facebook.com/ASIDubai/");
                break;
            case R.id.btnYoutube:
                openWebPage("https://www.youtube.com/user/ASIDubai");
                break;
            case R.id.btnTwitter:
                openWebPage("https://twitter.com/ASIDubai123");
                break;
            case R.id.btnInsta:
                openWebPage("https://www.instagram.com/asidubai/");
                break;
            case R.id.btnLinkedin:
                openWebPage("https://www.linkedin.com/company/asi-world");
                break;
            case R.id.txtWebsite:
                openWebPage("www.asi-world.com");
                break;


        }
    }

    public void openWebPage(String url) {
        try {
            Uri webpage = Uri.parse(url);

            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                webpage = Uri.parse("http://" + url);
            }
            startActivity(new Intent(Intent.ACTION_VIEW, webpage));
        } catch (ActivityNotFoundException e) {
            //TODO smth
        }
    }

}
