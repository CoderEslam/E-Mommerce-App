package com.doubleclick.e_commerceapp.FirebaseAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.doubleclick.e_commerceapp.Activites.User.ProductDetails.ProductDetailsActivity;
import com.doubleclick.e_commerceapp.Model.HomePage;
import com.doubleclick.e_commerceapp.Model.Products;
import com.doubleclick.e_commerceapp.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductsViewHolder> {


    private List<Products> productsList = new ArrayList<>();
    private Context context;
    private DatabaseReference ProductsRef;
    private FirebaseFirestore firestore;
    public static List<HomePage> homePageModel = new ArrayList<>();

    public ProductAdapter() {
    }

    public ProductAdapter(List<Products> productsList, Context context) {
        this.productsList = productsList;
        this.context = context;
    }

    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }

    @Override
    public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
        ProductsViewHolder productViewHolder = new ProductsViewHolder(view);
        return productViewHolder;
    }


    @Override
    public void onViewDetachedFromWindow(ProductAdapter.ProductsViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.fromuptodown);
        holder.layout_hint.setAnimation(animation);
        holder.layout_hint.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBindViewHolder(ProductAdapter.ProductsViewHolder holder, int position) {
        Products products = productsList.get(position);
        holder.txtProductName.setText(products.getPname());
        holder.txtProductDescription.setText(products.getDescription());
        holder.txtProductPrice.setText("Price = " + products.getPrice() + "$");
        Glide.with(holder.itemView).load(products.getImage()).into(holder.imageView);
//        Picasso.get().load(products.getImage()).placeholder(R.drawable.parson).into(holder.imageView);

        holder.layout_hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("HeadCategory",products.getHeadCategory());
                intent.putExtra("ChildCategory",products.getChildCategory());
                intent.putExtra("pid", products.getPid());
                intent.putExtra("pPrice", products.getPrice());
                intent.putExtra("pName", products.getPname());
                intent.putExtra("pDescription", products.getDescription());
                intent.putExtra("pImage", products.getImage());
                intent.putExtra("rate1", products.getRate1());
                intent.putExtra("rate2", products.getRate2());
                intent.putExtra("rate3", products.getRate3());
                intent.putExtra("rate4", products.getRate4());
                intent.putExtra("rate5", products.getRate5());
                holder.itemView.getContext().startActivity(intent);
                holder.layout_hint.setVisibility(View.INVISIBLE);
            }
        });
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.fromdowntoup);
                holder.layout_hint.setAnimation(animation);
                holder.layout_hint.setVisibility(View.VISIBLE);

            }
        });

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }


    public class ProductsViewHolder extends RecyclerView.ViewHolder {

        private TextView txtProductName, txtProductDescription, txtProductPrice;
        private ImageView imageView;
        private LinearLayout layout_hint;

        public ProductsViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.product_image);
            txtProductName = itemView.findViewById(R.id.product_name);
            txtProductDescription = itemView.findViewById(R.id.product_description);
            txtProductPrice = itemView.findViewById(R.id.product_price);
            layout_hint = itemView.findViewById(R.id.layout_hint);

        }
    }
}
