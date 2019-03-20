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

public class D05Fragment extends BaseFragment implements UpdateListData {
    @BindView(R.id.connectForHoops)
    ImageView connectForHoops;
    @BindView(R.id.ticketMonster)
    ImageView ticketMonster;
    @BindView(R.id.villayCrash)
    ImageView villayCrash;
    @BindView(R.id.pianoKeySingle)
    ImageView pianoKeySingle;
    @BindView(R.id.scoobyDoo)
    ImageView scoobyDoo;
    @BindView(R.id.e_calw)
    ImageView eCaly;
    @BindView(R.id.ticketCircus)
    ImageView ticketCircus;
    @BindView(R.id.eClawCosmic)
    ImageView eClawCosmic;
    @BindView(R.id.eClawCosmicXL)
    ImageView eClawCosmicXL;
    @BindView(R.id.leftTable)
    ImageView leftTable;
    @BindView(R.id.flinStone)
    ImageView flinStone;
    @BindView(R.id.trolls)
    ImageView trolls;
    @BindView(R.id.topTable)
    ImageView topTable;
    @BindView(R.id.prizeCube)
    ImageView prizeCube;
    @BindView(R.id.eClaw600Harmoney)
    ImageView eClaw600Harmoney;
    @BindView(R.id.prizeCube3)
    ImageView prizeCube3;
    @BindView(R.id.choclateCrane)
    ImageView choclateCrane;
    @BindView(R.id.ticketZone)
    ImageView ticketZone;
    Unbinder unbinder;

    private ArrayList<GameEnt> gamesList;
    private GamesPopupFragment gamesPopupFragment;

    public static D05Fragment newInstance() {
        Bundle args = new Bundle();

        D05Fragment fragment = new D05Fragment();
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
        View view = inflater.inflate(R.layout.fragment_d05, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        serviceHelper.enqueueCall(headerWebService.gameSection(AppConstants.d05), GetGames);
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


    @OnClick({R.id.connectForHoops, R.id.ticketMonster, R.id.villayCrash, R.id.pianoKeySingle, R.id.scoobyDoo, R.id.e_calw, R.id.ticketCircus, R.id.eClawCosmic, R.id.eClawCosmicXL, R.id.flinStone, R.id.trolls, R.id.prizeCube, R.id.eClaw600Harmoney, R.id.prizeCube3, R.id.choclateCrane, R.id.ticketZone})
    public void onViewClicked(View view) {
        if (gamesList.size() > 0)
            switch (view.getId()) {
                case R.id.connectForHoops:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.CONNECT_4_HOOPS, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.ticketMonster:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.TICKET_MONSTER, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.villayCrash:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.WILLY_CRASH, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.pianoKeySingle:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.PIANO_KEYS, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.scoobyDoo:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.SCOOBY_DO_OWHEEL, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.e_calw:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.E_CLAW_900_2_PLAYER, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.ticketCircus:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.TICKET_CIRCUS, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.eClawCosmic:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.E_CLAW_COSMIC, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.eClawCosmicXL:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.COSMIC_XL, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.flinStone:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.FLINSTONES_QUARRY_QUEST, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.trolls:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.TROLLS, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.prizeCube:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.PRIZE_CUBE_60, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.eClaw600Harmoney:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.E_CLAW_600_HARMONY_3_PLAYER, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.prizeCube3:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.PRIZE_CUBE_38, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.choclateCrane:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.BELGIAN_FINEST_CHOCOLATE, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.ticketZone:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.TICKET_ZONE, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
            }
    }

    @Override
    public void update(boolean b, int position) {
        gamesList.get(position).setFavourite(b);
    }
}
