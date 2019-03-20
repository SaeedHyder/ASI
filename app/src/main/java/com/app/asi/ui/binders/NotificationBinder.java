package com.app.asi.ui.binders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.app.asi.R;
import com.app.asi.activities.DockActivity;
import com.app.asi.entities.NotificationEnt;
import com.app.asi.helpers.BasePreferenceHelper;
import com.app.asi.helpers.DateHelper;
import com.app.asi.interfaces.RecyclerClickListner;
import com.app.asi.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.app.asi.ui.views.AnyTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationBinder extends RecyclerViewBinder<NotificationEnt> {

    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;

    private RecyclerClickListner clickListner;

    public NotificationBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper, RecyclerClickListner clickListner) {
        super(R.layout.row_item_notificaiton);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
        this.clickListner = clickListner;
    }

    @Override
    public BaseViewHolder createViewHolder(View view, ViewGroup parent) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(NotificationEnt entity, int position, Object viewHolder, Context context) {

        final ViewHolder holder = (ViewHolder) viewHolder;

        //imageLoader.displayImage(entity, holder.logo);
        //holder.txtDetail.setText(entity.getTitle() + "");
       // holder.txt_date.setText(DateHelper.getLocalTimeDate(entity.getDate()));

        holder.mainFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListner.onClick(entity, position);
            }
        });

    }

    static class ViewHolder extends BaseViewHolder {
        @BindView(R.id.txt_detail)
        AnyTextView txtDetail;
        @BindView(R.id.txt_date)
        AnyTextView txt_date;
        @BindView(R.id.mainFrame)
        LinearLayout mainFrame;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
