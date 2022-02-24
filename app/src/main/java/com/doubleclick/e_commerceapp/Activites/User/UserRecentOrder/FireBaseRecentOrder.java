package com.doubleclick.e_commerceapp.Activites.User.UserRecentOrder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.e_commerceapp.Model.Products;
import com.doubleclick.e_commerceapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class FireBaseRecentOrder {

    private DatabaseReference referenceRecentOrder;

    public FirebaseRecyclerAdapter<Products, RecentOrderViewHolder> getProductadapter() {
        return Productadapter;
    }

    FirebaseRecyclerAdapter<Products, RecentOrderViewHolder> Productadapter;
    FirebaseRecyclerOptions<Products> Recentoptions;

    public void loadRecentOrder(Context context) {
//        referenceRecentOrder = FirebaseDatabase.getInstance().getReference().child("RecentOrder");
        referenceRecentOrder = FirebaseDatabase.getInstance().getReference().child("Products");
        Recentoptions = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(referenceRecentOrder, Products.class)
                .build();

        Productadapter = new FirebaseRecyclerAdapter<Products, RecentOrderViewHolder>(Recentoptions) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull FireBaseRecentOrder.RecentOrderViewHolder holder, int position, @NonNull @NotNull Products products) {
                holder.NameOrder.setText(products.getPname());
                Picasso.get().load(products.getImage()).into(holder.OrderImage);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context,"FirebaseRecenrOrder At = 50 "+ "position  = "+position,Toast.LENGTH_LONG).show();
                    }
                });

            }
            @NonNull
            @NotNull
            @Override
            public RecentOrderViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(context).inflate(R.layout.recent_order,parent,false);
                return new RecentOrderViewHolder(view);
            }
        };

    }
    public class RecentOrderViewHolder extends RecyclerView.ViewHolder{
        ImageView OrderImage;
        TextView NameOrder;
        public RecentOrderViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            OrderImage = itemView.findViewById(R.id.ImageOrder);
            NameOrder = itemView.findViewById(R.id.NameOrder);
        }
    }

}
