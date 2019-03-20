package com.app.asi.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class D34Fragment extends BaseFragment implements UpdateListData {
    @BindView(R.id.floor)
    ImageView floor;
    @BindView(R.id.candyWaterGame)
    ImageView candyWaterGame;
    @BindView(R.id.bazookaBlast3Player)
    ImageView bazookaBlast3Player;
    @BindView(R.id.bazookaBlastFrontCounter)
    ImageView bazookaBlastFrontCounter;
    @BindView(R.id.rlTop)
    LinearLayout rlTop;
    @BindView(R.id.hangTime)
    ImageView hangTime;
    @BindView(R.id.darts)
    ImageView darts;
    @BindView(R.id.pinball)
    ImageView pinball;
    Unbinder unbinder;
    @BindView(R.id.pongArcade)
    ImageView pongArcade;
    @BindView(R.id.pongCocktail)
    ImageView pongCocktail;
    @BindView(R.id.fastTrackEvo)
    ImageView fastTrackEvo;
    @BindView(R.id.superKixx)
    ImageView superKixx;
    @BindView(R.id.rightTable)
    ImageView rightTable;
    @BindView(R.id.leftTable)
    ImageView leftTable;
    @BindView(R.id.rl_bottom)
    RelativeLayout rlBottom;
    @BindView(R.id.pinball_gold)
    ImageView pinballGold;


    private ArrayList<GameEnt> gamesList;
    private GamesPopupFragment gamesPopupFragment;

    public static D34Fragment newInstance() {
        Bundle args = new Bundle();

        D34Fragment fragment = new D34Fragment();
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
        View view = inflater.inflate(R.layout.fragment_d34, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        serviceHelper.enqueueCall(headerWebService.gameSection(AppConstants.d34), GetGames);
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


    @OnClick({R.id.candyWaterGame, R.id.bazookaBlast3Player, R.id.bazookaBlastFrontCounter, R.id.hangTime, R.id.darts, R.id.pinball, R.id.superKixx, R.id.fastTrackEvo, R.id.pongCocktail, R.id.pongArcade,R.id.pinball_gold})
    public void onViewClicked(View view) {
        if (gamesList!=null && gamesList.size() > 0)
            switch (view.getId()) {

                case R.id.candyWaterGame:
                gamesPopupFragment=new GamesPopupFragment();
                gamesPopupFragment.setUpdateDataListner(this, AppConstants.WATER_GUN_FUN_8_PLAYER, gamesList);
                getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.bazookaBlast3Player:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.BAZOOKA_BLAST, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.bazookaBlastFrontCounter:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.BALLOON_BUST, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.hangTime:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.HANG_TIME, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.darts:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.I_DARTS, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.pinball:
                gamesPopupFragment=new GamesPopupFragment();
                gamesPopupFragment.setUpdateDataListner(this, AppConstants.DEAD_POOL_PRO, gamesList);
                getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.pongArcade:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.ATARI_PONG_ARCADE_TABLE, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.pongCocktail:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.ATARI_PONG_COCKTAIL_TABLE, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.fastTrackEvo:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.ASI_FAST_TRACK_EVO_2, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;
                case R.id.superKixx:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.SUPER_KIXX_PRO, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;

                case R.id.pinball_gold:
                    gamesPopupFragment = new GamesPopupFragment();
                    gamesPopupFragment.setUpdateDataListner(this, AppConstants.THE_BEATLES_GOLD, gamesList);
                    getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                    break;

            }
    }

    @Override
    public void update(boolean b, int position) {

        gamesList.get(position).setFavourite(b);
    }


}
