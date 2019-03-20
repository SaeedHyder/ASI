package com.app.asi.ui.binders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.asi.R;
import com.app.asi.activities.DockActivity;
import com.app.asi.entities.HomeEnt;
import com.app.asi.helpers.BasePreferenceHelper;
import com.app.asi.interfaces.RecyclerClickListner;
import com.app.asi.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.app.asi.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeBinder extends RecyclerViewBinder<HomeEnt> {

    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;

    private RecyclerClickListner clickListner;
    private int recyclerHeight = 0;

    public HomeBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper, RecyclerClickListner clickListner) {
        super(R.layout.row_item_home);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
        this.clickListner = clickListner;
    }

    @Override
    public BaseViewHolder createViewHolder(View view, ViewGroup parent) {
        recyclerHeight = parent.getMeasuredHeight() / 4;
        view.setMinimumHeight(recyclerHeight-14);
        return new ViewHolder(view);
    }

    @Override
    public void bindView(HomeEnt entity, int position, Object viewHolder, Context context) {

        final ViewHolder holder = (ViewHolder) viewHolder;

        if (entity != null) {
            holder.ivIcon.setImageResource(entity.getIcon());
            holder.txtTitle.setText(entity.getTitle());
        }
        holder.llMainFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListner.onClick(entity,position);
            }
        });

    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.ivIcon)
        ImageView ivIcon;
        @BindView(R.id.txtTitle)
        AnyTextView txtTitle;
        @BindView(R.id.ll_mainFrame)
        LinearLayout llMainFrame;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
