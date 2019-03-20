package com.app.asi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

public class D01Fragment extends BaseFragment implements UpdateListData {
    @BindView(R.id.asiDesignSolution)
    ImageView asiDesignSolution;
    @BindView(R.id.buildingBlock)
    ImageView buildingBlock;
    @BindView(R.id.sparesSupport)
    ImageView sparesSupport;
    Unbinder unbinder;
    @BindView(R.id.connectGo)
    ImageView connectGo;

    private ArrayList<GameEnt> gamesList;
    private GamesPopupFragment gamesPopupFragment;

    public static D01Fragment newInstance() {
        Bundle args = new Bundle();

        D01Fragment fragment = new D01Fragment();
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
        View view = inflater.inflate(R.layout.fragment_d01, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        serviceHelper.enqueueCall(headerWebService.gameSection(AppConstants.d01), GetGames);
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


    @OnClick({R.id.asiDesignSolution, R.id.buildingBlock, R.id.sparesSupport,R.id.connectGo})
    public void onViewClicked(View view) {
        if (gamesList.size() > 0)
            switch (view.getId()) {

                case R.id.asiDesignSolution:
                /*gamesPopupFragment=new GamesPopupFragment();
                gamesPopupFragment.setUpdateDataListner(this, AppConstants.ASI_DESIGN_SOLUTION, gamesList);
                getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");*/
                    break;
                case R.id.buildingBlock:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.BUILDING_BLOCK_CARS, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.sparesSupport:
                  /*  gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.ASI_SUPPORT_SOLUTIONS, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");*/
                    break;

                case R.id.connectGo:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.CONNECT_GO, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
            }
    }

    @Override
    public void update(boolean b, int position) {
        gamesList.get(position).setFavourite(b);
    }




}
