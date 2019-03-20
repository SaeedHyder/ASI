package com.app.asi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.app.asi.R;
import com.app.asi.entities.GameEnt;
import com.app.asi.entities.UserEnt;
import com.app.asi.fragments.abstracts.BaseFragment;
import com.app.asi.global.AppConstants;
import com.app.asi.interfaces.UpdateListData;
import com.app.asi.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.app.asi.global.WebServiceConstants.GetGames;

public class D49Fragment extends BaseFragment implements UpdateListData {
    @BindView(R.id.atvSlam)
    ImageView atvSlam;
    @BindView(R.id.targetBravoStandup)
    ImageView targetBravoStandup;
    @BindView(R.id.dayTona)
    ImageView dayTona;
    @BindView(R.id.transformerShadow)
    ImageView transformerShadow;
    @BindView(R.id.houseOfDeads)
    ImageView houseOfDeads;
    @BindView(R.id.rlLeft)
    RelativeLayout rlLeft;
    @BindView(R.id.piratesFalls)
    ImageView piratesFalls;
    @BindView(R.id.hotRacer)
    ImageView hotRacer;
    @BindView(R.id.sonicKidsBasketBall)
    ImageView sonicKidsBasketBall;
    @BindView(R.id.segaAllBasketBall)
    ImageView segaAllBasketBall;
    @BindView(R.id.ballRunna)
    ImageView ballRunna;
    @BindView(R.id.hoopla)
    ImageView hoopla;
    @BindView(R.id.pixelChase)
    ImageView pixelChase;
    @BindView(R.id.table1)
    ImageView table1;
    @BindView(R.id.table2)
    ImageView table2;
    @BindView(R.id.rl_right)
    RelativeLayout rlRight;
    Unbinder unbinder;

    private ArrayList<GameEnt> gamesList;
    private GamesPopupFragment gamesPopupFragment;

    public static D49Fragment newInstance() {
        Bundle args = new Bundle();

        D49Fragment fragment = new D49Fragment();
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
        View view = inflater.inflate(R.layout.fragment_d49, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        serviceHelper.enqueueCall(headerWebService.gameSection(AppConstants.d49), GetGames);
    }

    @Override
    public void ResponseSuccess(Object result, UserEnt userEnt, String Tag, String message) {
        super.ResponseSuccess(result, userEnt, Tag, message);
        switch (Tag) {
            case GetGames:
                gamesList = (ArrayList<GameEnt>) result;
                break;
        }
    }


    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.showBackButton();
        titleBar.showLogo();
    }


    @OnClick({R.id.atvSlam, R.id.targetBravoStandup, R.id.dayTona, R.id.transformerShadow, R.id.houseOfDeads, R.id.piratesFalls, R.id.hotRacer, R.id.sonicKidsBasketBall, R.id.segaAllBasketBall, R.id.ballRunna, R.id.hoopla, R.id.pixelChase})
    public void onViewClicked(View view) {
        if (gamesList.size() > 0)
            switch (view.getId()) {
                case R.id.atvSlam:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.ATV_SLAM, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.targetBravoStandup:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.TARGET_BRAVO_UPRIGHT, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.dayTona:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.DAY_TO_NAUSA_STREET_RACING_42, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.transformerShadow:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.TRANSFORMERS_SHADOWS_RISING, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.houseOfDeads:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.HOUSE_OF_THE_DEAD_SCARLET_DAWN, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.piratesFalls:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.PIRATE_FALLS, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.hotRacer:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.HOT_RACERS, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.sonicKidsBasketBall:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.SONIC_ALL_STAR_KIDS_BASKETBALL, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.segaAllBasketBall:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.SONIC_ALL_STARS_BASKETBALL, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.ballRunna:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.BALL_RUNNER, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.hoopla:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.HOOPLA, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.pixelChase:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.PIXEL_CHASE, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;

            }
    }

    @Override
    public void update(boolean b, int position) {
        gamesList.get(position).setFavourite(b);
    }
}
