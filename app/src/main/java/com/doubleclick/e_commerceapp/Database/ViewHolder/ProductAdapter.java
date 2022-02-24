package com.doubleclick.e_commerceapp.Database.ViewHolder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.e_commerceapp.Model.Products;
import com.doubleclick.e_commerceapp.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {


    private List<Products> productsList = new ArrayList<>();
    private Context context;
    private OnItemClickListener mListener;


    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_product_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ProductViewHolder holder, int position) {

        Products productsItem = productsList.get(position);
        holder.product_name.setText(productsItem.getPname());
        holder.product_category.setText(productsItem.getChildCategory());
        holder.product_price.setText(productsItem.getPrice());
        holder.product_description.setText(productsItem.getDescription());
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.fromdowntoup);

        holder.product_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.layout.setAnimation(animation);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        private TextView product_name, product_category, product_price, product_description;
        private LinearLayout layout;

        public ProductViewHolder(View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            product_category = itemView.findViewById(R.id.product_category);
            product_price = itemView.findViewById(R.id.product_price);
            product_description = itemView.findViewById(R.id.product_description);
            layout = itemView.findViewById(R.id.layout);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int index = getAdapterPosition();
                    if (mListener != null && index != RecyclerView.NO_POSITION){
                        mListener.onItemClick(productsList.get(index));
                    }
                }
            });
        }
    }

    public void setProductsList(List<Products> productsList){
        this.productsList = productsList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener{
        void onItemClick(Products products);
    }

    public void OnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }

    public Products getProductAt(int pos){
        return productsList.get(pos);
    }
}
