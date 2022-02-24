package com.doubleclick.e_commerceapp.Fragments.Favorite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleclick.e_commerceapp.Activites.User.ProductDetails.ProductDetailsActivity;
import com.doubleclick.e_commerceapp.Model.Products;
import com.doubleclick.e_commerceapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.doubleclick.e_commerceapp.Fragments.Favorite.DBQurieFavorite.favorites;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoritViewHolder> {

    private List<Products> favoriteList = new ArrayList<>();
    private Context context;
    private View VieweditCart;
    private Button deleteItem, OrderItem;
    private ImageView ImageItem;
    private AlertDialog.Builder builder;
    private Favorite favorite;
    private FirebaseFirestore firestore;
    private List<Favorite> favoritesList = favorites;
    private FavoriteAdapter favoriteAdapter ;

    public void setUserID(String userID) {
        UserID = userID;
    }

    private String UserID;

    public String getFid() {
        return Fid;
    }

    public void setFid(String fid) {
        Fid = fid;
    }

    private String Fid;

    public FavoriteAdapter() {
    }

    public FavoriteAdapter(List<Products> favoriteList) {
        this.favoriteList = favoriteList;
    }

    public FavoriteAdapter(List<Products> favoriteList, String UserID) {
        this.favoriteList = favoriteList;
        this.favoriteAdapter = favoriteAdapter;
        this.UserID = UserID;

    }


    @NonNull
    @NotNull
    @Override
    public FavoriteAdapter.FavoritViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_favorite_inflater, parent, false);
        return new FavoritViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull FavoriteAdapter.FavoritViewHolder holder, int position) {

//        Toast.makeText(holder.itemView.getContext(), "FavoriteAdapter Done", Toast.LENGTH_LONG).show();

        firestore = FirebaseFirestore.getInstance();
        Products products = favoriteList.get(position);
        holder.product_name.setText(products.getPname());
        holder.product_category.setText(products.getChildCategory());
        holder.product_description.setText(products.getDescription());
        holder.product_price.setText(products.getPrice());
        Picasso.get().load(products.getImage()).placeholder(R.drawable.parson).into(holder.FavoritImage);
        builder = new AlertDialog.Builder(holder.itemView.getContext());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VieweditCart = LayoutInflater.from(holder.itemView.getContext()).inflate(R.layout.cart_favorite_item, null, false);
                deleteItem = VieweditCart.findViewById(R.id.deleteItem);
                OrderItem = VieweditCart.findViewById(R.id.OrderItem);
                ImageItem = VieweditCart.findViewById(R.id.ImageItem);
                Picasso.get().load(products.getImage()).placeholder(R.drawable.parson).into(ImageItem);
                builder.setTitle("Favorite Options");
                builder.setView(VieweditCart);

                deleteItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firestore.collection("Users").document(UserID)
                                .collection("Favorite").document(favoritesList.get(position).getFid())
                                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                DBQurieFavorite.favoriteList.clear();
                                notifyDataSetChanged();
                                Toast.makeText(holder.itemView.getContext(), "Deleted from favorite", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

                OrderItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(holder.itemView.getContext(),ProductDetailsActivity.class);
                        intent.putExtra("FromFavoriteAdapter",true);
                        intent.putExtra("Price",products.getPrice());
                        intent.putExtra("Pid",products.getPid());
                        intent.putExtra("Image",products.getImage());
                        intent.putExtra("Name",products.getPname());
                        intent.putExtra("Trademark",products.getTrademark());
                        intent.putExtra("Description",products.getDescription());
                        intent.putExtra("HeadCategory",products.getHeadCategory());
                        intent.putExtra("ChildCategory",products.getChildCategory());
                        intent.putExtra("rate1",products.getRate1());
                        intent.putExtra("rate2",products.getRate2());
                        intent.putExtra("rate3",products.getRate3());
                        intent.putExtra("rate4",products.getRate4());
                        intent.putExtra("rate5",products.getRate5());
                        holder.itemView.getContext().startActivity(intent);

                    }
                });

                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }



    public class FavoritViewHolder extends RecyclerView.ViewHolder {
        //form => layout_favorite_inflater
        private TextView product_name, product_category, product_price, product_description;
        private CircleImageView FavoritImage;

        public FavoritViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            product_name = itemView.findViewById(R.id.product_name);
            product_category = itemView.findViewById(R.id.product_category);
            product_price = itemView.findViewById(R.id.product_price);
            product_description = itemView.findViewById(R.id.product_description);
            FavoritImage = itemView.findViewById(R.id.FavoritImage);
        }
    }
}
