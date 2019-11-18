package com.furkanzayimoglu.btchallange.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.furkanzayimoglu.btchallange.R;
import com.furkanzayimoglu.btchallange.model.ImageDetail;
import com.furkanzayimoglu.btchallange.model.UniDepartmentDetail;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SlidingImageAdapter extends PagerAdapter {

    private ArrayList<ImageDetail>  imageList;
    private LayoutInflater layoutInflater;

    public SlidingImageAdapter(Context context, ArrayList<ImageDetail> list){
        this.imageList = list;
        layoutInflater = LayoutInflater.from(context);
    }


    @Override
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup view, int position) {

        View view1 = layoutInflater.inflate(R.layout.slidingimages_layout,view,false);

        ImageView imageView = view1.findViewById(R.id.image);
        Picasso.get().load(imageList.get(position).getUrl()).into(imageView);
        view.addView(view1,0);
        return view1;
    }

    @Override
    public void destroyItem(ViewGroup conteiner, int position, @NonNull  Object object){
        conteiner.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageList.size();
    }
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return object == view;
    }
}
