package com.doubleclick.e_commerceapp.FirebaseAdapter;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;

import com.doubleclick.e_commerceapp.Model.Products;
import com.doubleclick.e_commerceapp.Activites.User.ProductDetails.ProductDetailsActivity;
import com.doubleclick.e_commerceapp.R;
import com.doubleclick.e_commerceapp.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

public class CategoryAdapter {

    private DatabaseReference ProductsRef;
    FirebaseRecyclerAdapter<Products, ProductViewHolder> Productadapter;
    FirebaseRecyclerOptions<Products> Mainoptions;
    private FirebaseFirestore firestore;


    public FirebaseRecyclerAdapter<Products, ProductViewHolder> getAdapter() {
        return Productadapter;
    }

    public void setAdapter(FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter) {
        this.Productadapter = adapter;
    }


    public void loadData(String child, Context context) {

        firestore = FirebaseFirestore.getInstance();

        if (child == "") {
            ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
            ProductsRef.keepSynced(false);
        } else {
            if (child != null) {
                ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products").child(child);
                ProductsRef.keepSynced(false);

            }
        }

//        ProductsRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(Task<DataSnapshot> task) {
//                Toast.makeText(context,"CatgoryAdapter 80 = "+task.getResult(),Toast.LENGTH_LONG).show();
//
//            }
//        });



        Mainoptions = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(ProductsRef, Products.class)
//                .setIndexedQuery(ProductsRef,ProductsRef,Products.class)
                .build();


        Productadapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(Mainoptions) {
            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Products products) {
                holder.txtProductName.setText(products.getPname());
                holder.txtProductDescription.setText(products.getDescription());
                holder.txtProductPrice.setText("Price = " + products.getPrice() + "$");
                Picasso.get().load(products.getImage()).into(holder.imageView);
                holder.layout_hint.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ProductDetailsActivity.class);
                        intent.putExtra("pid", products.getPid());
                        intent.putExtra("pPrice", products.getPrice());
                        intent.putExtra("pName", products.getPname());
                        intent.putExtra("pDescription", products.getDescription());
                        intent.putExtra("pImage", products.getImage());
                        intent.putExtra("Category",products.getChildCategory());
                        context.startActivity(intent);
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

            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_items_layout, parent, false);
                ProductViewHolder holder = new ProductViewHolder(view);
                return holder;
            }
        };

    }
}

