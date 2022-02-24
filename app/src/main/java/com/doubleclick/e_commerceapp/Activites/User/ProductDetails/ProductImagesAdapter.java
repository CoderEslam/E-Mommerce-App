package com.doubleclick.e_commerceapp.Activites.User.ProductDetails;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.doubleclick.e_commerceapp.R;

import java.util.List;

//PagerAdapter for ViewPager
public class ProductImagesAdapter extends PagerAdapter {

    public ProductImagesAdapter(List<Integer> productImages) {
        this.productImages = productImages;
    }

    private List<Integer> productImages;

    @Override
    public int getCount() {
        return productImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    //this method use to intialize the objects
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView productImage = new ImageView(container.getContext());
//        Glide.with(container.getContext()).load(productImages.get(position)).placeholder(R.drawable.parson).into(productImage);
        productImage.setImageResource(productImages.get(position));
        container.addView(productImage, 0);
        return productImage;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }
}
