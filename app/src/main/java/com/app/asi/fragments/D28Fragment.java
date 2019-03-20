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

public class D28Fragment extends BaseFragment implements UpdateListData {
    @BindView(R.id.ring3Circus)
    ImageView ring3Circus;
    @BindView(R.id.table1)
    ImageView table1;
    @BindView(R.id.fruitNinjaVirtual)
    ImageView fruitNinjaVirtual;
    @BindView(R.id.beatSaber)
    ImageView beatSaber;
    @BindView(R.id.mininoSoccer)
    ImageView mininoSoccer;
    @BindView(R.id.ringToss)
    ImageView ringToss;
    @BindView(R.id.letsBounce)
    ImageView letsBounce;
    @BindView(R.id.hyperShoot)
    ImageView hyperShoot;
    @BindView(R.id.table2)
    ImageView table2;
    @BindView(R.id.snapShot2)
    ImageView snapShot2;
    @BindView(R.id.vrRabbids)
    ImageView vrRabbids;
    Unbinder unbinder;

    private ArrayList<GameEnt> gamesList;
    private GamesPopupFragment gamesPopupFragment;

    public static D28Fragment newInstance() {
        Bundle args = new Bundle();

        D28Fragment fragment = new D28Fragment();
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
        View view = inflater.inflate(R.layout.fragment_d28, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        serviceHelper.enqueueCall(headerWebService.gameSection(AppConstants.d28), GetGames);
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

    @OnClick({R.id.ring3Circus, R.id.fruitNinjaVirtual, R.id.mininoSoccer, R.id.ringToss, R.id.letsBounce, R.id.hyperShoot, R.id.snapShot2, R.id.beatSaber, R.id.vrRabbids})
    public void onViewClicked(View view) {
        if (prefHelper.isGuestUser()) {
            openGuestDialoge();
        } else {
            if (gamesList != null && gamesList.size() > 0) {
                switch (view.getId()) {
                    case R.id.ring3Circus:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.RING_3_CIRCUS, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.fruitNinjaVirtual:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.FRUIT_NINJA_VR, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.mininoSoccer:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.MINIONS_SOCCER, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");

                        break;
                    case R.id.ringToss:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.RING_TOSS, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.letsBounce:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.LETS_BOUNCE, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.hyperShoot:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.HYPERSHOOT, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.snapShot2:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.SNAPSHOT_2, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;

                    case R.id.beatSaber:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.BEAT_SABER_VR, gamesList);
                        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
                        break;
                    case R.id.vrRabbids:
                        gamesPopupFragment = new GamesPopupFragment();
                        gamesPopupFragment.setUpdateDataListner(this, AppConstants.VIRTUALRABBIDSTHEBIGRIDE, gamesList);
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
