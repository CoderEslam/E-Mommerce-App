package com.doubleclick.e_commerceapp.Activites.User;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.doubleclick.e_commerceapp.Activites.User.ProductDetails.ProductDetailsActivity;
import com.doubleclick.e_commerceapp.Model.Cart;
import com.doubleclick.e_commerceapp.R;
import com.doubleclick.e_commerceapp.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private Button NextProcessBtn, deleteItem, editItem;
    private TextView txtTotalAmount, txtMsg1, product_name;

    private int overTotalPrice = 0;
    private View view, VieweditCart;
    private ImageView product_image, ImageItem;
    private AlertDialog.Builder builder ;
    private FirebaseAuth mAuth;
    private String UserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.cart_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        NextProcessBtn = findViewById(R.id.next_process_btn);
        txtTotalAmount = findViewById(R.id.total_price);
        txtMsg1 = findViewById(R.id.msg1);
        mAuth = FirebaseAuth.getInstance();
        UserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
//        VieweditCart = LayoutInflater.from(CartActivity.this).inflate(R.layout.edit_item_cart, null, false);
//        view = LayoutInflater.from(CartActivity.this).inflate(R.layout.product_items_layout,null,false);
//        deleteItem = VieweditCart.findViewById(R.id.deleteItem);
//        editItem = VieweditCart.findViewById(R.id.settingItem);
//        ImageItem = VieweditCart.findViewById(R.id.ImageItem);
//        product_name = view.findViewById(R.id.product_name);
//        product_image = view.findViewById(R.id.product_image);
        builder = new AlertDialog.Builder(CartActivity.this);

        NextProcessBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vew) {

                txtTotalAmount.setText("Total Price = $ " + String.valueOf(overTotalPrice));
                Intent intent = new Intent(CartActivity.this, ConfirmFinalOrderActivity.class);
                intent.putExtra("Total Price", String.valueOf(overTotalPrice));
                startActivity(intent);
                finish();

            }
        });

//    @Override
//    protected void onStart() {
//        super.onStart();
        CheckOrderState();
        txtTotalAmount.setText("Total Price = $ " + String.valueOf(overTotalPrice));

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("CartList");

        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef.child("UserView")
                        .child(UserId).child("Products"), Cart.class)
                .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(CartViewHolder holder, int position, Cart model) {
                holder.txtProductQuantity.setText("Quantity = " + model.getQuantity());
                holder.txtProductPrice.setText("Price " + model.getPrice());
                holder.txtProductName.setText(model.getPname());


                int oneTypeProductTPrice = ((Integer.valueOf(model.getPrice()))) * Integer.valueOf(model.getQuantity());
                //int oneTypeProductTPrice = ((Math.round(Integer.valueOf(model.getPrice())))) * Math.round(Integer.valueOf(model.getQuantity()));
                overTotalPrice = overTotalPrice + oneTypeProductTPrice;
                txtTotalAmount.setText("Total Price = $ " + String.valueOf(overTotalPrice));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {
                        VieweditCart = LayoutInflater.from(CartActivity.this).inflate(R.layout.edit_item_cart, null, false);
                        deleteItem = VieweditCart.findViewById(R.id.deleteItem);
                        editItem = VieweditCart.findViewById(R.id.OrderItem);
                        ImageItem = VieweditCart.findViewById(R.id.ImageItem);
//                        CharSequence options[] = new CharSequence[]{
//                                "Edit",
//                                "Remove"
//                        };
//                        product_image.setImageResource(R.drawable.address_1);
                        Picasso.get().load(model.getpImage()).placeholder(R.drawable.address_1).into(ImageItem);
                        builder.setTitle("Cart Options");
                        builder.setView(VieweditCart);
                        editItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(CartActivity.this, ProductDetailsActivity.class);
                                intent.putExtra("pid", model.getPid());
                                intent.putExtra("quantity", model.getQuantity());
                                intent.putExtra("pPrice",model.getPrice());
                                intent.putExtra("pName",model.getPname());
                                intent.putExtra("pDescription",model.getDescription());
                                intent.putExtra("pImage",model.getpImage());
                                intent.putExtra("edit",true);
                                startActivity(intent);
                                finish();
                            }
                        });

                        deleteItem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DatabaseReference cartListRefDelet = FirebaseDatabase.getInstance().getReference()
                                        .child("CartList")
                                        .child("UserView")
                                        .child(UserId)
                                        .child("Products")
                                        .child(model.getPid());
                                cartListRefDelet.removeValue(new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(DatabaseError error, DatabaseReference ref) {
                                        Toast.makeText(CartActivity.this, "Item Removed Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(CartActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                        });
//                        builder.setItems(options, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int i) {
//                                if (i == 0) {
//                                    Intent intent = new Intent(CartActivity.this, ProductDetailsActivity.class);
//                                    intent.putExtra("pid", model.getPid());
//                                    intent.putExtra("quantity", model.getQuantity());
//                                    startActivity(intent);
//                                    finish();
//                                }
//                                if (i == 1) {
//                                    cartListRef.child("UserView")
//                                            .child(Prevalent.currentOnlineUser.getPhone())
//                                            .child(model.getPid())
//                                            .removeValue(new DatabaseReference.CompletionListener() {
//                                                @Override
//                                                public void onComplete(DatabaseError error, DatabaseReference ref) {
//                                                    ref.removeValue();
//                                                    Toast.makeText(CartActivity.this, "CartItem = " + model.getPid(), Toast.LENGTH_SHORT).show();
//
//                                                    Toast.makeText(CartActivity.this, "Item Removed Successfully", Toast.LENGTH_SHORT).show();
//                                                    Intent intent = new Intent(CartActivity.this, HomeActivity.class);
//                                                    startActivity(intent);
//                                                }
//                                            });
////                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
////                                                @Override
////                                                public void onComplete(@NonNull Task<Void> task) {
////                                                    if(task.isSuccessful()){
////                                                        Toast.makeText(CartActivity.this, "CartItem = "+ model.getPid(), Toast.LENGTH_SHORT).show();
////
////
////                                                        Toast.makeText(CartActivity.this, "Item Removed Successfully", Toast.LENGTH_SHORT).show();
////                                                        Intent intent = new Intent(CartActivity.this, HomeActivity.class);
////                                                        startActivity(intent);
////                                                    }
////                                                }
////                                            });
//                                }
//                            }
//                        });
                            builder.show();
                    }
                });

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent, false);
                CartViewHolder holder = new CartViewHolder(view);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
//    }
    }


    private void CheckOrderState() {
        DatabaseReference ordersRef;
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(UserId);
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    String shippingState = dataSnapshot.child("state").getValue().toString();
                    String userName = dataSnapshot.child("name").getValue().toString();

                    if (shippingState.equals("shipped")) {
                        txtTotalAmount.setText("Dear" + userName + "\n order is shipped successfully.");
//                        recyclerView.setVisibility(View.GONE);

//                        txtMsg1.setVisibility(View.VISIBLE);
//                        txtMsg1.setText("Congratulations, your final order has been SHIPPED successfully. Soon you will receive it from your doorstep");
//                        NextProcessBtn.setVisibility(View.GONE);

                        Toast.makeText(CartActivity.this, "You can purchase more products after you receive your first final order.",
                                Toast.LENGTH_SHORT).show();
                    } else if (shippingState.equals("not shipped")) {
                        txtTotalAmount.setText("Shipping State : Not Shipped");
//                        recyclerView.setVisibility(View.GONE);

//                        txtMsg1.setVisibility(View.VISIBLE);
//                        NextProcessBtn.setVisibility(View.GONE);

                        Toast.makeText(CartActivity.this, "You can purchase more products after you receive your first final order.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}