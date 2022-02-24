package com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.e_commerceapp.Activites.Admin.AdminAddNewProductActivity;
import com.doubleclick.e_commerceapp.Activites.Admin.AdminCategoryActivity;
import com.doubleclick.e_commerceapp.Model.Category;
import com.doubleclick.e_commerceapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SeeAllCategoresAdapter { //extends RecyclerView.Adapter<SeeAllCategoresAdapter.ItemCaregory> {


    private DatabaseReference CategoryReference;
    List<Category> categoryList = new ArrayList<>();
    private int Determinde;
    FirebaseRecyclerAdapter<Category, ItemCaregory> adapter;

    public FirebaseRecyclerAdapter<Category, ItemCaregory> getAdapter() {
        return adapter;
    }




    public void loadData(Context context,int determinde) {
        CategoryReference = FirebaseDatabase.getInstance().getReference().child("Category");
        FirebaseRecyclerOptions<Category> Mainoptions = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(CategoryReference, Category.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Category, ItemCaregory>(Mainoptions) {
            @Override
            protected void onBindViewHolder(SeeAllCategoresAdapter.ItemCaregory holder, int position,Category model) {
//                Category category = categoryList.get(position);
//        Toast.makeText(context,""+category.toString(),Toast.LENGTH_LONG).show();
                holder.NameCategory.setText(model.getCategoryName());
                Picasso.get().load(model.getCategoryImage()).placeholder(R.drawable.parson).into(holder.IconCategory);
                if (determinde == 1) {
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, AdminAddNewProductActivity.class);
                            intent.putExtra("category", model.getCategoryName());
//                            intent.putExtra("phone", AdminCategoryActivity.email);
                            holder.itemView.getContext().startActivity(intent);
                        }
                    });
                }
            }

            @NonNull
            @NotNull
            @Override
            public ItemCaregory onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
                ItemCaregory holder = new ItemCaregory(view);
                return holder;

            }
        };

    }


//    @Override
//    public SeeAllCategoresAdapter.ItemCaregory onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
//
//        return new ItemCaregory(view);
//    }

//    @Override
//    public void onBindViewHolder(SeeAllCategoresAdapter.ItemCaregory holder, int position) {
//
//        Category category = categoryList.get(position);
////        Toast.makeText(context,""+category.toString(),Toast.LENGTH_LONG).show();
//        holder.NameCategory.setText(category.getCategoryName());
//        Picasso.get().load(category.getCategoryImage()).placeholder(R.drawable.parson).into(holder.IconCategory);
//        if (Determinde == 1) {
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = new Intent(context, AdminAddNewProductActivity.class);
//                    intent.putExtra("category", category.getCategoryName());
//                    intent.putExtra("phone", AdminCategoryActivity.phone);
//                    holder.itemView.getContext().startActivity(intent);
//                }
//            });
//        }
//
//    }

//    @Override
//    public int getItemCount() {
//        return categoryList.size();
//
//    }

    public static class ItemCaregory extends RecyclerView.ViewHolder {

        public ImageView IconCategory;
        public TextView NameCategory;

        public ItemCaregory(@NonNull @NotNull View itemView) {
            super(itemView);
            IconCategory = itemView.findViewById(R.id.IconCategory);
            NameCategory = itemView.findViewById(R.id.NameCategory);
        }

    }

}
