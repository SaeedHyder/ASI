package com.app.asi.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.asi.R;
import com.app.asi.entities.HomeEnt;
import com.app.asi.entities.UserEnt;
import com.app.asi.fragments.abstracts.BaseFragment;
import com.app.asi.global.AppConstants;
import com.app.asi.helpers.DialogHelper;
import com.app.asi.helpers.UIHelper;
import com.app.asi.interfaces.BarCodeValue;
import com.app.asi.interfaces.RecyclerClickListner;
import com.app.asi.ui.adapters.HomePageAdapter;
import com.app.asi.ui.binders.HomeBinder;
import com.app.asi.ui.views.CustomRecyclerView;
import com.app.asi.ui.views.TitleBar;
import com.google.android.gms.vision.barcode.Barcode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.relex.circleindicator.CircleIndicator;

import static com.app.asi.global.WebServiceConstants.GamePlayedBarCode;


public class HomeFragment extends BaseFragment implements RecyclerClickListner, BarCodeValue {


    @BindView(R.id.rvHome)
    CustomRecyclerView rvHome;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.indicator)
    CircleIndicator indicator;
    Unbinder unbinder;

    private ArrayList<HomeEnt> collection;
    private HomePageAdapter customPageAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getMainActivity().refreshSideMenuData();
        setHomeData();
        setViewPager();

    }

    private void setViewPager() {

        ArrayList<String> imagesCollection = new ArrayList<>();
        imagesCollection.add("drawable://" + R.drawable.home_header_img);

        customPageAdapter = new HomePageAdapter(getMainActivity(), imagesCollection);
        viewpager.setAdapter(customPageAdapter);
        indicator.setViewPager(viewpager);
    }

    private void setHomeData() {

        collection = new ArrayList<>();
        collection.add(new HomeEnt(R.drawable.deal, getResString(R.string.deal_2019)));
        collection.add(new HomeEnt(R.drawable.games, getResString(R.string.all_games)));
        collection.add(new HomeEnt(R.drawable.scan, getResString(R.string.scan_qr_code)));
        collection.add(new HomeEnt(R.drawable.gallery, getResString(R.string.gallery)));
        collection.add(new HomeEnt(R.drawable.tips, getResString(R.string.asi_tips)));
        collection.add(new HomeEnt(R.drawable.news, getResString(R.string.news)));
        collection.add(new HomeEnt(R.drawable.blogs, getResString(R.string.blogs)));
        collection.add(new HomeEnt(R.drawable.offers, getResString(R.string.offers)));
        collection.add(new HomeEnt(R.drawable.topgames, getResString(R.string.top_10_games)));
        collection.add(new HomeEnt(R.drawable.social_media, getResString(R.string.socail_media)));
        collection.add(new HomeEnt(R.drawable.contact, getResString(R.string.contact_us)));
        collection.add(new HomeEnt(R.drawable.website, getResString(R.string.website)));

        rvHome.BindRecyclerView(new HomeBinder(getDockActivity(), prefHelper, this), collection,
                new GridLayoutManager(getDockActivity(), 3)
                , new DefaultItemAnimator());

    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showLogo();
        titleBar.showMenuButton();
    /*    titleBar.showSearchButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UIHelper.showShortToastInDialoge(getDockActivity(), getResString(R.string.will_be_implemented));
            }
        });*/
        titleBar.showNotificationButton(0, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDockActivity().replaceDockableFragment(NotificationsFragment.newInstance(false), "NotificationsFragment");
            }
        });
    }


    @Override
    public void onClick(Object entity, int position) {

        HomeEnt ent = (HomeEnt) entity;

        if (ent.getTitle().equals(getResString(R.string.deal_2019))) {
            getDockActivity().replaceDockableFragment(DealFragment.newInstance(), "DealFragment");

        } else if (ent.getTitle().equals(getResString(R.string.all_games))) {
            UIHelper.showShortToastInDialoge(getDockActivity(), getResString(R.string.will_be_implemented));

        } else if (ent.getTitle().equals(getResString(R.string.scan_qr_code))) {
            BarCodeScanFragment barCodeScanFragment = new BarCodeScanFragment();
            barCodeScanFragment.setBarCOdeListner(this);
            getDockActivity().replaceDockableFragment(barCodeScanFragment, "BarCodeScanFragment");

        } else if (ent.getTitle().equals(getResString(R.string.gallery))) {
            UIHelper.showShortToastInDialoge(getDockActivity(), getResString(R.string.will_be_implemented));

        } else if (ent.getTitle().equals(getResString(R.string.asi_tips))) {
            getDockActivity().replaceDockableFragment(AsiTipsFragment.newInstance(), "AsiTipsFragment");

        } else if (ent.getTitle().equals(getResString(R.string.news))) {
            UIHelper.showShortToastInDialoge(getDockActivity(), getResString(R.string.will_be_implemented));

        } else if (ent.getTitle().equals(getResString(R.string.blogs))) {
            UIHelper.showShortToastInDialoge(getDockActivity(), getResString(R.string.will_be_implemented));

        } else if (ent.getTitle().equals(getResString(R.string.offers))) {
            UIHelper.showShortToastInDialoge(getDockActivity(), getResString(R.string.will_be_implemented));

        } else if (ent.getTitle().equals(getResString(R.string.top_10_games))) {
            UIHelper.showShortToastInDialoge(getDockActivity(), getResString(R.string.will_be_implemented));

        } else if (ent.getTitle().equals(getResString(R.string.socail_media))) {
            openSocialDialoge();

        } else if (ent.getTitle().equals(getResString(R.string.contact_us))) {
            getDockActivity().replaceDockableFragment(ContactUsFragment.newInstance(), "ContactUsFragment");
        } else if (ent.getTitle().equals(getResString(R.string.website))) {
            openWebPage(AppConstants.asiDesignURL);
        }

    }

    private void openSocialDialoge() {


        DialogHelper dialogHelper = new DialogHelper(getDockActivity());
        dialogHelper.initSocialLink(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage("https://www.facebook.com/ASIDubai/");
                dialogHelper.hideDialog();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage("https://www.youtube.com/user/ASIDubai");
                dialogHelper.hideDialog();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage("https://twitter.com/ASIDubai123");
                dialogHelper.hideDialog();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage("https://www.instagram.com/asidubai/");
                dialogHelper.hideDialog();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage("https://www.linkedin.com/company/asi-world");
                dialogHelper.hideDialog();
            }
        });
        dialogHelper.showDialog();
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


    @Override
    public void getValue(Barcode barcode) {
        String id = barcode.displayValue.replaceAll("[^0-9]", "");
        serviceHelper.enqueueCall(headerWebService.gameSectionPlayed(id + ""), GamePlayedBarCode);
    }

    @Override
    public void ResponseSuccess(Object result, UserEnt userEnt, String Tag, String message) {
        super.ResponseSuccess(result, userEnt, Tag, message);
        switch (Tag) {
            case GamePlayedBarCode:
                UIHelper.showShortToastInDialoge(getDockActivity(), getResString(R.string.game_is_addedd));
                break;
        }
    }
}

