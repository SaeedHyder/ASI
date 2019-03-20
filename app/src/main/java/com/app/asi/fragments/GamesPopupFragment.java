package com.app.asi.fragments;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.asi.R;
import com.app.asi.entities.GameEnt;
import com.app.asi.entities.UserEnt;
import com.app.asi.fragments.abstracts.BaseFragment;
import com.app.asi.helpers.DialogHelper;
import com.app.asi.helpers.ServiceHelper;
import com.app.asi.helpers.UIHelper;
import com.app.asi.interfaces.UpdateListData;
import com.app.asi.interfaces.WishListInterface;
import com.app.asi.ui.adapters.PopupPageAdapter;
import com.app.asi.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.app.asi.global.WebServiceConstants.AddWishList;

public class GamesPopupFragment extends BaseFragment implements WishListInterface {
    @BindView(R.id.viewpager)
    ViewPager viewPager;
    Unbinder unbinder;

    private PopupPageAdapter popupPageAdapter;
    private static String gameId;
    private static String gamesListKey = "gamesListKey";
    private static String gameFavKey = "gameFavKey";
    private ArrayList<GameEnt> gamesList;
    private GameEnt favGameEnt;
    private int position = 0;
    private Button btnAddWish;
    private static boolean isFromFav = false;
    private UpdateListData updateListData;


    public void setUpdateDataListner(UpdateListData updateListData, String tagId, ArrayList<GameEnt> list) {
        this.updateListData = updateListData;
        gameId = tagId;
        gamesList = list;
        isFromFav = false;
    }

    public void setUpdateDataListner(UpdateListData updateListData, String tagId, GameEnt data, boolean isFromFavorite) {
        this.updateListData = updateListData;
        gameId = tagId;
        favGameEnt = data;
        isFromFav = isFromFavorite;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_games_popup, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (serviceHelper == null) {
            serviceHelper = new ServiceHelper(this, getDockActivity(), webService);
        }

        if (isFromFav) {
            gamesList = new ArrayList<>();
            gamesList.add(favGameEnt);

        } else {
            for (int i = 0; i < gamesList.size(); i++) {
                if (String.valueOf(gamesList.get(i).getId()).equals(gameId)) {
                    position = i;
                }
            }
        }
        setViewPager();
    }

    private void setViewPager() {

        if (gamesList != null) {
            setPagerSetting();
            popupPageAdapter = new PopupPageAdapter(getMainActivity(), gamesList, this);
            viewPager.setAdapter(popupPageAdapter);

            viewPager.setCurrentItem(position);
        }
    }

    private void setPagerSetting() {
        if (viewPager != null) {
            viewPager.setClipToPadding(false);
            viewPager.setPageMargin(10);

            viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
                @Override
                public void transformPage(View page, float position) {
                    int pageWidth = viewPager.getMeasuredWidth() -
                            viewPager.getPaddingLeft() - viewPager.getPaddingRight();
                    int pageHeight = viewPager.getHeight();
                    int paddingLeft = viewPager.getPaddingLeft();
                    float transformPos = (float) (page.getLeft() -
                            (viewPager.getScrollX() + paddingLeft)) / pageWidth;
                    int max = pageHeight / 10;

                    if (transformPos < -1) { // [-Infinity,-1)
                        // This page is way off-screen to the left.
                        page.setAlpha(0.7f);// to make left transparent
                        page.setScaleY(0.9f);
                    } else if (transformPos <= 1) { // [-1,1]
                        page.setAlpha(1f);
                        page.setScaleY(1f);
                    } else { // (1,+Infinity]
                        // This page is way off-screen to the right.
                        page.setAlpha(0.7f);// to make right transparent
                        page.setScaleY(0.9f);
                    }
                }
            });
        }
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showLogo();
    }

    @Override
    public void onWishClick(Object entity, int pos) {

    }

    @Override
    public void onWishClickAdapter(Object entity, int pos, Button btnAddWishList) {

        GameEnt data = gamesList.get(viewPager.getCurrentItem());
        btnAddWish = btnAddWishList;
        if (data.getFavourite()) {
            DialogHelper dialoge = new DialogHelper(getDockActivity());
            dialoge.initRemoveDialoge(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    serviceHelper.enqueueCall(headerWebService.addWishListMap(data.getId() + "", false), AddWishList);
                    dialoge.hideDialog();
                }
            }, data.getTitle()+"", getResString(R.string.are_you_sure_you_want_to_remove_game));
            dialoge.showDialog();

        } else {
            serviceHelper.enqueueCall(headerWebService.addWishListMap(data.getId() + "", true), AddWishList);
        }
    }

    @Override
    public void ResponseSuccess(Object result, UserEnt userEnt, String Tag, String message) {
        switch (Tag) {
            case AddWishList:
                if (gamesList.get(viewPager.getCurrentItem()).getFavourite()) {
                    gamesList.get(viewPager.getCurrentItem()).setFavourite(false);
                    updateListData.update(false, viewPager.getCurrentItem());
                    btnAddWish.setText(R.string.add_to_wish_list);
                    UIHelper.showShortToastInCenter(getDockActivity(), getDockActivity().getResources().getString(R.string.removed_from_wish_list_successfully));
                    if (isFromFav) {
                        getDockActivity().popFragment();
                    }
                } else {
                    gamesList.get(viewPager.getCurrentItem()).setFavourite(true);
                    updateListData.update(true, viewPager.getCurrentItem());
                    btnAddWish.setText(R.string.remove_from_wish_list);
                    UIHelper.showShortToastInCenter(getDockActivity(), getDockActivity().getResources().getString(R.string.added_in_wish_list_successfully));
                    if (isFromFav) {
                        getDockActivity().popFragment();
                    }
                }

                break;
        }
    }

}
