package com.doubleclick.e_commerceapp.Fragments.GridProduct;

import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.doubleclick.e_commerceapp.Activites.User.ProductDetails.ProductDetailsActivity;
import com.doubleclick.e_commerceapp.Model.Products;
import com.doubleclick.e_commerceapp.R;

import java.util.List;

public class GridProductAdaper extends BaseAdapter {

    public GridProductAdaper(List<Products> grid_productModelList) {
        this.grid_productModelList = grid_productModelList;
    }

    List<Products> grid_productModelList;

    @Override
    public int getCount() {
        //to return 4 item only to grid then edit to return size of list
        return grid_productModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        final View view;
        if (convertView == null) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item_layout, null, false);
            Products products = grid_productModelList.get(position);
            ImageView productImage = view.findViewById(R.id.g_s_productImage);
            TextView productTitel = view.findViewById(R.id.g_s_product_name);
            TextView priceProduct = view.findViewById(R.id.g_s_product_price);
            TextView DesProduct = view.findViewById(R.id.g_s_product_Description);
            Glide.with(view).load(grid_productModelList.get(position).getImage()).into(productImage);
            productTitel.setText(grid_productModelList.get(position).getPname());
            priceProduct.setText(grid_productModelList.get(position).getPrice());
            DesProduct.setText(grid_productModelList.get(position).getDescription());
            view.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    Intent productDetilsIntent = new Intent(parent.getContext(), ProductDetailsActivity.class);
                    productDetilsIntent.putExtra("HeadCategory",products.getHeadCategory());
                    productDetilsIntent.putExtra("ChildCategory",products.getChildCategory());
                    productDetilsIntent.putExtra("pid", products.getPid());
                    productDetilsIntent.putExtra("pPrice", products.getPrice());
                    productDetilsIntent.putExtra("pName", products.getPname());
                    productDetilsIntent.putExtra("pDescription", products.getDescription());
                    productDetilsIntent.putExtra("pImage", products.getImage());
                    productDetilsIntent.putExtra("rate1", products.getRate1());
                    productDetilsIntent.putExtra("rate2", products.getRate2());
                    productDetilsIntent.putExtra("rate3", products.getRate3());
                    productDetilsIntent.putExtra("rate4", products.getRate4());
                    productDetilsIntent.putExtra("rate5", products.getRate5());
                    parent.getContext().startActivity(productDetilsIntent);
                }
            });
            return view;
        } else {
             view =convertView;
        }
        return view;

    }
}
