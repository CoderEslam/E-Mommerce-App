package com.doubleclick.e_commerceapp.Slider_Banner;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.doubleclick.e_commerceapp.Model.Advertisement;
import com.doubleclick.e_commerceapp.Model.HomePage;
import com.doubleclick.e_commerceapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderAdapter extends PagerAdapter {

    private List<Advertisement> sliderModelList;
    private List<HomePage> homePageModelList;
    public SliderAdapter(List<Advertisement> advertisementList) {
        this.sliderModelList = advertisementList;
    }


    @Override
    public int getCount() {
        return sliderModelList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((View) object);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.slider_image_layout,container,false);
        LinearLayout bannerContiner = view.findViewById(R.id.BannerContiner);
        ImageView banner = view.findViewById(R.id.banner_sliderImageView);
        Glide.with(view).load(sliderModelList.get(position).getImageAd()).into(banner);
//        Picasso.get().load(sliderModelList.get(position).getImageAd()).placeholder(R.drawable.parson).into(banner);
        container.addView(view,0);
        return view;
    }
}
