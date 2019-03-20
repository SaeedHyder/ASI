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

public class D32Fragment extends BaseFragment implements UpdateListData {


    @BindView(R.id.fishBow)
    ImageView fishBow;
    @BindView(R.id.spinsAhoy)
    ImageView spinsAhoy;
    @BindView(R.id.robortStrom)
    ImageView robortStrom;
    @BindView(R.id.wildTest)
    ImageView wildTest;
    @BindView(R.id.spaceBasketBall)
    ImageView spaceBasketBall;
    @BindView(R.id.whacker)
    ImageView whacker;
    @BindView(R.id.bugChuncher)
    ImageView bugChuncher;
    @BindView(R.id.xdSeater)
    ImageView xdSeater;
    @BindView(R.id.vrMaze)
    ImageView vrMaze;
    @BindView(R.id.typhoon)
    ImageView typhoon;
    @BindView(R.id.coconutBash)
    ImageView coconutBash;
    @BindView(R.id.tCover)
    ImageView tCover;
    @BindView(R.id.rightTable)
    ImageView rightTable;
    @BindView(R.id.babyAirHocky)
    ImageView babyAirHocky;
    @BindView(R.id.salom)
    ImageView salom;
    @BindView(R.id.magnoBison)
    ImageView magnoBison;
    @BindView(R.id.foosBall)
    ImageView foosBall;
    @BindView(R.id.airHocky)
    ImageView airHocky;
    @BindView(R.id.receptionCounter)
    ImageView receptionCounter;
    @BindView(R.id.leftTable)
    ImageView leftTable;
    @BindView(R.id.topTable)
    ImageView topTable;
    @BindView(R.id.bottomTable)
    ImageView bottomTable;
    @BindView(R.id.kbCobra)
    ImageView kbCobra;
    @BindView(R.id.bigBugBlast)
    ImageView bigBugBlast;
    @BindView(R.id.theRocket)
    ImageView theRocket;
    @BindView(R.id.princessFairyTest)
    ImageView princessFairyTest;
    @BindView(R.id.billyJumper)
    ImageView billyJumper;
    @BindView(R.id.kidsim)
    ImageView kidsim;
    @BindView(R.id.adventureBike)
    ImageView adventureBike;
    @BindView(R.id.rl_left)
    RelativeLayout rlLeft;
    @BindView(R.id.comboDx)
    ImageView comboDx;
    @BindView(R.id.fireFigmate)
    ImageView fireFigmate;
    @BindView(R.id.monsterCatcher)
    ImageView monsterCatcher;
    @BindView(R.id.monsterEnd)
    ImageView monsterEnd;
    @BindView(R.id.superBoxes)
    ImageView superBoxes;
    Unbinder unbinder;

    private ArrayList<GameEnt> gamesList;
    private GamesPopupFragment gamesPopupFragment;

    public static D32Fragment newInstance() {
        Bundle args = new Bundle();

        D32Fragment fragment = new D32Fragment();
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
        View view = inflater.inflate(R.layout.fragment_d32, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        serviceHelper.enqueueCall(headerWebService.gameSection(AppConstants.d32), GetGames);
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


    @OnClick({R.id.fishBow, R.id.spinsAhoy, R.id.robortStrom, R.id.wildTest, R.id.spaceBasketBall, R.id.whacker,
            R.id.bugChuncher, R.id.xdSeater, R.id.vrMaze, R.id.typhoon, R.id.coconutBash, R.id.tCover, R.id.babyAirHocky,
            R.id.salom, R.id.magnoBison, R.id.foosBall, R.id.airHocky, R.id.receptionCounter, R.id.kbCobra,
            R.id.bigBugBlast, R.id.theRocket, R.id.princessFairyTest, R.id.billyJumper, R.id.kidsim, R.id.adventureBike, R.id.comboDx, R.id.fireFigmate, R.id.monsterCatcher,
            R.id.monsterEnd, R.id.superBoxes})
    public void onViewClicked(View view) {
        if (prefHelper.isGuestUser()) {
            openGuestDialoge();
        } else {
            if (gamesList != null && gamesList.size() > 0) {
                switch (view.getId()) {
                    case R.id.fishBow:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.FISHBOWL_FRENZY, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.spinsAhoy:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.SPINS_AHOY, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.robortStrom:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.ROBOT_STORM, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.wildTest:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.WILD_WEST_SHOOTOUT, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.spaceBasketBall:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.SPACEBasketBall, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.whacker:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.GO_BANANAS, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.bugChuncher:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.BUG_CRUNCHER, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.xdSeater:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.XD_DARK_RIDE_4_SEATER, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.vrMaze:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.VR_MAZE, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.typhoon:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.TYPHOON, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.coconutBash:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.COCONUT_BASH, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.tCover:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.TREASURE_COVE, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;

                    case R.id.babyAirHocky:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.BABY_AIR_HOCKEY_EVO, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.salom:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.ASI_SLALOM_EVO, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.magnoBison:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.MagnoBison, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.foosBall:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.TECHNO_FLAME, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.airHocky:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.THE_JOKER_VS_BATMAN_LAUGHING_MADNESS, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.receptionCounter:
              /*  gamesPopupFragment=new GamesPopupFragment();
                gamesPopupFragment.setUpdateDataListner(this, AppConstants.RING_3_CIRCUS, gamesList);
                getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");*/
                        break;

                    case R.id.kbCobra:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.KC_COBRA, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.bigBugBlast:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.BIG_BUG_BLASTER, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.theRocket:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.THE_ROCKET, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.princessFairyTest:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.PRINCESS_FAIRY_TALES, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.billyJumper:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.BILLY_JUMPER, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.kidsim:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.KID_SIM_SIMULATOR, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.adventureBike:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.ADVENTURE_BIKE, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.comboDx:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.JUMBO_JUMPIN_DX, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.fireFigmate:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.FIRE_FIGHTER, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.monsterCatcher:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.MONSTER_CATCHER, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.monsterEnd:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.PACMAN_PIXEL_BASH, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.superBoxes:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.SUPER_BIKES_3, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                }
            }
        }
    }

    @Override
    public void update(boolean b, int position) {

        gamesList.get(position).setFavourite(b);
    }
}
