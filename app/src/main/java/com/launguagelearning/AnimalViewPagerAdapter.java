package com.launguagelearning;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.launguagelearning.model.Lessons;

import java.util.List;
import java.util.Objects;

class AnimalViewPagerAdapter extends PagerAdapter {
    Context context;

    LayoutInflater mLayoutInflater;
    List<Lessons> lessions;
    public AnimalViewPagerAdapter(Context context, List<Lessons> lessions) {
        this.context = context;
       this.lessions =lessions;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lessions.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((RelativeLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        View itemView = mLayoutInflater.inflate(R.layout.item, container, false);
        Lessons lession=lessions.get(position);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageViewMain);
        TextView title = (TextView) itemView.findViewById(R.id.title);
        title.setText(lession.getCname());
        Glide.with(context).load(lession.getImage_url()).into(imageView);
        Objects.requireNonNull(container).addView(itemView);


        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((RelativeLayout) object);
    }
}
