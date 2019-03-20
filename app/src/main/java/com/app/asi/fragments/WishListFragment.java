package com.app.asi.fragments;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.app.asi.R;
import com.app.asi.entities.GameEnt;
import com.app.asi.entities.UserEnt;
import com.app.asi.entities.WishListEnt;
import com.app.asi.fragments.abstracts.BaseFragment;
import com.app.asi.helpers.UIHelper;
import com.app.asi.interfaces.LoadMoreListener;
import com.app.asi.interfaces.RecyclerClickListner;
import com.app.asi.interfaces.UpdateListData;
import com.app.asi.interfaces.WishListInterface;
import com.app.asi.ui.binders.WishListBinder;
import com.app.asi.ui.views.AnyTextView;
import com.app.asi.ui.views.CustomRecyclerView;
import com.app.asi.ui.views.TitleBar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.app.asi.global.WebServiceConstants.GetWishList;
import static com.app.asi.global.WebServiceConstants.RemoveWishList;

public class WishListFragment extends BaseFragment implements RecyclerClickListner, WishListInterface, UpdateListData {
    @BindView(R.id.txt_no_data)
    AnyTextView txtNoData;
    @BindView(R.id.rv_favorites)
    CustomRecyclerView rvWishList;
    Unbinder unbinder;

    boolean canCallForMore = true;
    boolean isOnCall;
    int pageNumber = 1;
    int rowPerPage = 1000;
    boolean firstTime = true;
    private LinearLayoutManager linearLayoutManager;
    private int positionList = 0;

    public static WishListFragment newInstance() {
        Bundle args = new Bundle();

        WishListFragment fragment = new WishListFragment();
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
        View view = inflater.inflate(R.layout.fragment_wishlist, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pageNumber = 1;
        firstTime = true;
        canCallForMore = true;
        serviceHelper.enqueueCall(headerWebService.getWishList(pageNumber, rowPerPage), GetWishList);

    }


    @Override
    public void ResponseSuccess(Object result, UserEnt userEnt, String Tag, String message) {
        super.ResponseSuccess(result, userEnt, Tag, message);
        switch (Tag) {
            case GetWishList:
                ArrayList<WishListEnt> collection = (ArrayList<WishListEnt>) result;

                rvWishList.setVisibility(View.VISIBLE);
                txtNoData.setVisibility(View.GONE);

                if (collection != null && collection.size() > 0) {

                    rvWishList.BindRecyclerView(new WishListBinder(getDockActivity(), prefHelper, this, this), collection,
                            new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false)
                            , new DefaultItemAnimator());
                  /*  if (firstTime) {
                        linearLayoutManager = new LinearLayoutManager(getDockActivity(), LinearLayoutManager.VERTICAL, false);
                        rvWishList.BindRecyclerView(new WishListBinder(getDockActivity(), prefHelper, this, this), collection,
                                linearLayoutManager
                                , new DefaultItemAnimator());
                        firstTime = false;
                    } else {

                        isOnCall = false;
                        if (collection.size() > 0) {
                            rvWishList.addAll(collection);
                            rvWishList.notifyItemRangeChanged(linearLayoutManager.findLastVisibleItemPosition(), rvWishList.getList().size());
                        } else {
                            canCallForMore = false;
                        }

                }*/
                // onLoadMoreListner();

        } else{
            rvWishList.setVisibility(View.GONE);
            txtNoData.setVisibility(View.VISIBLE);
        }
        break;

        case RemoveWishList:
        UIHelper.showShortToastInCenter(getDockActivity(), getDockActivity().getResources().getString(R.string.removed_from_wish_list_successfully));
        rvWishList.remove(positionList);
        break;

    }

}

    private void onLoadMoreListner() {

        if (rvWishList.getAdapter() != null) {
            rvWishList.getAdapter().setOnLoadMoreListener(new LoadMoreListener() {
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
        titleBar.setSubHeading(getResString(R.string.wish_list));
        titleBar.showMenuButton();
    }


    @Override
    public void onClick(Object entity, int position) {
        WishListEnt data = (WishListEnt) entity;
        GameEnt gameEnt = new GameEnt(data.getTitle(), data.getDescription(), data.getTitleAr(), data.getDescriptionAr(), data.getDimension(), data.getVideoUrl(),
                data.getCodeImageUrl(), data.getImageUrls(), data.getShareUrl(), data.getFavourite(), data.getPlayed(), data.getId(), data.getCreatedOn());

        GamesPopupFragment gamesPopupFragment = new GamesPopupFragment();
        gamesPopupFragment.setUpdateDataListner(this, data.getId() + "", gameEnt, true);
        getDockActivity().addDockableFragment(gamesPopupFragment, "GamesPopupFragment");
    }

    @Override
    public void onWishClick(Object entity, int pos) {
        positionList = pos;
        WishListEnt data = (WishListEnt) entity;
        serviceHelper.enqueueCall(headerWebService.addWishListMap(data.getId() + "", false), RemoveWishList);
    }

    @Override
    public void onWishClickAdapter(Object entity, int pos, Button btnAddWishList) {

    }

    @Override
    public void update(boolean b, int position) {

        serviceHelper.enqueueCall(headerWebService.getWishList(pageNumber, rowPerPage), GetWishList);

    }
}
