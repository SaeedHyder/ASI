package com.app.asi.ui.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.app.asi.R;
import com.app.asi.activities.MainActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;



public class HomePageAdapter extends PagerAdapter {
    MainActivity context;
    ArrayList<String> images;
    LayoutInflater layoutInflater;



    public HomePageAdapter(MainActivity context, ArrayList<String> images) {
        this.context = context;
        this.images = images;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.home_pager_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        //Picasso.get().load(R.drawable.home_header_img).placeholder(R.drawable.placeholder_thumb).into(imageView);
          imageView.setImageResource(R.drawable.home_header_img);
        container.addView(itemView);

       /* imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onWishClick(View v) {
                //Toast.makeText(context, "you clicked image " + (position + 1), Toast.LENGTH_LONG).show();
            }
        });*/

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}

