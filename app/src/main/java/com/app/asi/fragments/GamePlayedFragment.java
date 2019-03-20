package com.app.asi.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.asi.R;
import com.app.asi.entities.GameEnt;
import com.app.asi.entities.UserEnt;
import com.app.asi.entities.WishListEnt;
import com.app.asi.fragments.abstracts.BaseFragment;
import com.app.asi.helpers.UIHelper;
import com.app.asi.interfaces.LoadMoreListener;
import com.app.asi.interfaces.RecyclerClickListner;
import com.app.asi.interfaces.UpdateListData;
import com.app.asi.ui.binders.GamePlayedBinder;
import com.app.asi.ui.binders.WishListBinder;
import com.app.asi.ui.views.AnyTextView;
import com.app.asi.ui.views.CustomRecyclerView;
import com.app.asi.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.app.asi.global.WebServiceConstants.GetPlayedList;
import static com.app.asi.global.WebServiceConstants.GetWishList;
import static com.app.asi.global.WebServiceConstants.RemoveWishList;

public class GamePlayedFragment extends BaseFragment implements RecyclerClickListner,UpdateListData {
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.rv_Games)
    CustomRecyclerView rvGames;
    Unbinder unbinder;

    boolean canCallForMore = true;
    boolean isOnCall;
    int pageNumber = 1;
    int rowPerPage = 1000;
    boolean firstTime = true;
    private LinearLayoutManager linearLayoutManager;
    private int positionList = 0;

    public static GamePlayedFragment newInstance() {
        Bundle args = new Bundle();

        GamePlayedFragment fragment = new GamePlayedFragment();
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
        View view = inflater.inflate(R.layout.fragment_game_played, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pageNumber = 1;
        firstTime = true;
        canCallForMore = true;
        serviceHelper.enqueueCall(headerWebService.getPlayedList(pageNumber, rowPerPage), GetPlayedList);

    }

    @Override
    public void ResponseSuccess(Object result, UserEnt userEnt, String Tag, String message) {
        super.ResponseSuccess(result, userEnt, Tag, message);
        switch (Tag) {
            case GetPlayedList:
                ArrayList<GameEnt> data = (ArrayList<GameEnt>) result;

                rvGames.setVisibility(View.VISIBLE);
                txtNoData.setVisibility(View.GONE);

                if (data != null && data.size() > 0) {

                    rvGames.BindRecyclerView(new GamePlayedBinder(getDockActivity(), prefHelper, this), data,
                            new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false)
                            , new DefaultItemAnimator());
                   /* if (firstTime) {
                        linearLayoutManager = new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false);
                        rvGames.BindRecyclerView(new GamePlayedBinder(getDockActivity(), prefHelper, this), data,
                                linearLayoutManager
                                , new DefaultItemAnimator());
                        firstTime = false;
                    } else {

                        isOnCall = false;
                        if (data.size() > 0) {
                            rvGames.addAll(data);
                            rvGames.notifyItemRangeChanged(linearLayoutManager.findLastVisibleItemPosition(), rvGames.getList().size());
                        } else {
                            canCallForMore = false;
                        }

                    }*/
                //    onLoadMoreListner();

                } else {
                    rvGames.setVisibility(View.GONE);
                    txtNoData.setVisibility(View.VISIBLE);
                }
                break;


        }
    }

    private void onLoadMoreListner() {

        if (rvGames.getAdapter() != null) {
            rvGames.getAdapter().setOnLoadMoreListener(new LoadMoreListener() {
                @Override
                public void onLoadMoreItem(int position) {
                    if (canCallForMore) {
                        pageNumber++;
                        isOnCall = true;
                        serviceHelper.enqueueCall(headerWebService.getWishList(pageNumber, rowPerPage), GetWishList);
                    }
                }
            });
        }
    }

    @Override
    public void setTitleBar(TitleBar titleBar) {
        super.setTitleBar(titleBar);
        titleBar.hideButtons();
        titleBar.setSubHeading(getResString(R.string.games_played));
        titleBar.showMenuButton();
    }


    @Override
    public void onClick(Object entity, int position) {

        GameEnt data = (GameEnt) entity;

        GamesPopupFragment gamesPopupFragment = new GamesPopupFragment();
        gamesPopupFragment.setUpdateDataListner(this, data.getId() + "", data, true);

        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");

    }

    @Override
    public void update(boolean b, int position) {

        serviceHelper.enqueueCall(headerWebService.getPlayedList(pageNumber, rowPerPage), GetPlayedList);
    }
}
