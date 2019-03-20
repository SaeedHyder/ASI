package com.app.asi.ui.binders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.app.asi.R;
import com.app.asi.activities.DockActivity;
import com.app.asi.entities.SideMenuEnt;
import com.app.asi.helpers.BasePreferenceHelper;
import com.app.asi.interfaces.RecyclerClickListner;
import com.app.asi.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.app.asi.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SideMenuBinder extends RecyclerViewBinder<SideMenuEnt> {

    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;

    private RecyclerClickListner clickListner;

    public SideMenuBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper, RecyclerClickListner clickListner) {
        super(R.layout.row_item_sidemenu);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
        this.clickListner = clickListner;
    }

    @Override
    public BaseViewHolder createViewHolder(View view,ViewGroup parent) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(SideMenuEnt entity, int position, Object viewHolder, Context context) {

        final ViewHolder holder = (ViewHolder) viewHolder;

        if(entity.getTitle().equals(context.getResources().getString(R.string.notifications))){
            holder.txtBadge.setVisibility(View.GONE);
        }else{
            holder.txtBadge.setVisibility(View.GONE);
        }
        if (entity.isSelected()) {
            holder.txtItemName.setText(entity.getTitle());
            holder.icon.setVisibility(View.VISIBLE);
        } else {
            holder.txtItemName.setText(entity.getTitle());
            holder.icon.setVisibility(View.GONE);
        }


        holder.llItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListner.onClick(entity, position);
            }
        });

    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.icon)
        ImageView icon;
        @BindView(R.id.txt_item_name)
        AnyTextView txtItemName;
        @BindView(R.id.ll_item)
        LinearLayout llItem;
        @BindView(R.id.ll_mainFrame)
        RelativeLayout llMainFrame;
        @BindView(R.id.txtBadge)
        AnyTextView txtBadge;


        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
