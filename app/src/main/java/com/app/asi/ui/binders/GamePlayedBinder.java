package com.app.asi.ui.binders;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.asi.R;
import com.app.asi.activities.DockActivity;
import com.app.asi.entities.GameEnt;
import com.app.asi.helpers.BasePreferenceHelper;
import com.app.asi.helpers.DateHelper;
import com.app.asi.interfaces.RecyclerClickListner;
import com.app.asi.ui.viewbinders.abstracts.RecyclerViewBinder;
import com.app.asi.ui.views.AnyTextView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;


public class GamePlayedBinder extends RecyclerViewBinder<GameEnt> {

    private DockActivity dockActivity;
    private BasePreferenceHelper prefHelper;
    private ImageLoader imageLoader;
    private RecyclerClickListner clickListner;

    public GamePlayedBinder(DockActivity dockActivity, BasePreferenceHelper prefHelper, RecyclerClickListner clickListner) {
        super(R.layout.row_item_wishlist);
        this.dockActivity = dockActivity;
        this.prefHelper = prefHelper;
        this.imageLoader = ImageLoader.getInstance();
        this.clickListner = clickListner;
    }

    @Override
    public BaseViewHolder createViewHolder(View view, ViewGroup parent) {
        return new ViewHolder(view);
    }

    @Override
    public void bindView(GameEnt entity, int position, Object viewHolder, Context context) {

        final ViewHolder holder = (ViewHolder) viewHolder;

        holder.llGamePlayed.setVisibility(View.VISIBLE);
        holder.btnFav.setVisibility(View.GONE);

        if (entity != null) {
            holder.txtTitle.setText(entity.getTitle() + "");
            holder.txtDetail.setText(entity.getDescription() + "");
            holder.txtDimensions.setText(entity.getDimension() + "");
            holder.txtDimensions.setText(entity.getDimension() + "");
            holder.txtDate.setText(DateHelper.getFormatedDate("yyyy-MM-dd'T'HH:mm:ss.SSS","hh:mm a dd MMM,yyyy",entity.getCreatedOn()));

            if (entity.getImageUrls() != null && entity.getImageUrls().size() > 0) {
                Picasso.get().load(entity.getImageUrls().get(0)).placeholder(R.drawable.placeholder_thumb).into(holder.ivIcon);
            }

            holder.mainFrame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListner.onClick(entity, position);
                }
            });

        }



    }

   static class ViewHolder extends BaseViewHolder{
        @BindView(R.id.ivIcon)
        CircleImageView ivIcon;
        @BindView(R.id.txt_title)
        AnyTextView txtTitle;
       @BindView(R.id.txtDate)
       AnyTextView txtDate;
        @BindView(R.id.txt_detail)
        AnyTextView txtDetail;
        @BindView(R.id.txt_dimensions)
        AnyTextView txtDimensions;
        @BindView(R.id.btnFav)
        ImageView btnFav;
        @BindView(R.id.ll_game_played)
        LinearLayout llGamePlayed;
        @BindView(R.id.mainFrame)
        LinearLayout mainFrame;



        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
