package com.doubleclick.e_commerceapp.Activites.User.ProductDetails;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.doubleclick.e_commerceapp.Activites.User.CartActivity;
import com.doubleclick.e_commerceapp.Activites.User.HomeActivity;
import com.doubleclick.e_commerceapp.Fragments.Favorite.Favorite;
import com.doubleclick.e_commerceapp.Model.Products;
import com.doubleclick.e_commerceapp.Prevalent.Prevalent;
import com.doubleclick.e_commerceapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class ProductDetailsActivity extends AppCompatActivity {

    // private FloatingActionButton addToCartBtn;
    private ViewPager productImageViewPager;
    private TabLayout viewPagerIndicator;
    private List<String> ImageViewPagerList;
    private int currentPage = 0;
    private final long DELAY_TIME = 3000;
    private final long PERIOD_TIME = 3000;
    private TextView addToCartButton;
    private ImageView productImage;
    private LinearLayout RateNow;
    private TextView productPrice, productDescription, productName, NumbersText;
    private String productID = "", state = "normal", quantity = "", pPrice;
    private String pName, pDescription, pImage, Category;
    private int Numbers;
    private String ImageUrl;
    private boolean edit;
    private FloatingActionButton addToWishListBtn;
    private Boolean AlreadyaddedToWishList = true;
    private DatabaseReference FavoriteReference;
    private FirebaseAuth mAuth;
    private String UserId;
    private String push = null;
    private FirebaseFirestore firestore;
    private String saveCurrentTime;
    private String HeadCategory, ChildCategory;
    private List<Favorite> favoriteList = new ArrayList<>();
    private Favorite favorite;
    private String Fid; // favorite Id
    private boolean FromFavoriteAdapter = false;
    private String Trademark = "", rate1 = "", rate2 = "", rate3 = "", rate4 = "", rate5 = "";
    private int rate1Num, rate2Num, rate3Num, rate4Num, rate5Num, totalRateNum;
    private TextView tvRate1, tvRate2, tvRate3, tvRate4, tvRate5, TotalRating;
    private ProgressBar progressBar1, progressBar2, progressBar3, progressBar4, progressBar5;

    private int setRate;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        NumbersText = findViewById(R.id.NumbersText);
        RateNow = findViewById(R.id.rate_now_container);
        productID = getIntent().getStringExtra("pid");
        pPrice = getIntent().getStringExtra("pPrice");
        pName = getIntent().getStringExtra("pName");
        pDescription = getIntent().getStringExtra("pDescription");
        pImage = getIntent().getStringExtra("pImage");
        Category = getIntent().getStringExtra("Category");
        ImageViewPagerList = new ArrayList<>();
        //ViewPager .......
        final List<Integer> productImageView = new ArrayList<>();
        productImageView.add(R.drawable.profile);
        productImageView.add(R.drawable.parson);
        productImageView.add(R.drawable.group);
        productImageView.add(R.drawable.group_login);
        productImageViewPager = findViewById(R.id.productImageViewPager);
        viewPagerIndicator = findViewById(R.id.viewPagerIndicator);
        viewPagerIndicator.setupWithViewPager(productImageViewPager,true);
        ProductImagesAdapter productImagesAdapter = new ProductImagesAdapter(productImageView);
        RunSlider(productImageViewPager,productImageView);
        productImageViewPager.setAdapter(productImagesAdapter);
        //////////////////////////////////////////Rating
        tvRate1 = findViewById(R.id.tvRate1);
        tvRate2 = findViewById(R.id.tvRate2);
        tvRate3 = findViewById(R.id.tvRate3);
        tvRate4 = findViewById(R.id.tvRate4);
        tvRate5 = findViewById(R.id.tvRate5);
        TotalRating = findViewById(R.id.TotalRating);
        try {
            rate1 = getIntent().getStringExtra("rate1");
            rate2 = getIntent().getStringExtra("rate2");
            rate3 = getIntent().getStringExtra("rate3");
            rate4 = getIntent().getStringExtra("rate4");
            rate5 = getIntent().getStringExtra("rate5");
        } catch (NullPointerException e) {

        }
        tvRate1.setText(rate1);
        tvRate2.setText(rate2);
        tvRate3.setText(rate3);
        tvRate4.setText(rate4);
        tvRate5.setText(rate5);
        try {
            rate1Num = Integer.parseInt(rate1);
            rate2Num = Integer.parseInt(rate2);
            rate3Num = Integer.parseInt(rate3);
            rate4Num = Integer.parseInt(rate4);
            rate5Num = Integer.parseInt(rate5);
            totalRateNum = (Integer.parseInt(rate1) +
                    Integer.parseInt(rate2) +
                    Integer.parseInt(rate3) +
                    Integer.parseInt(rate4) +
                    Integer.parseInt(rate5));
            TotalRating.setText("" + totalRateNum);
        } catch (NullPointerException e) {

        } catch (NumberFormatException e) {

        }
        progressBar1 = findViewById(R.id.progressBar1);
        progressBar2 = findViewById(R.id.progressBar2);
        progressBar3 = findViewById(R.id.progressBar3);
        progressBar4 = findViewById(R.id.progressBar4);
        progressBar5 = findViewById(R.id.progressBar5);

        try {
            progressBar1.setProgress((int) ((double) rate1Num * 100) / totalRateNum);
            progressBar2.setProgress((int) ((double) rate2Num * 100) / totalRateNum);
            progressBar3.setProgress((int) ((double) rate3Num * 100) / totalRateNum);
            progressBar4.setProgress((int) ((double) rate4Num * 100) / totalRateNum);
            progressBar5.setProgress((int) ((double) rate5Num * 100) / totalRateNum);
        } catch (ArithmeticException e) {

        }


        Map<String, Object> mapReting = new HashMap<>();
        for (int x = 0; x < RateNow.getChildCount(); x++) {
            final int statPosition = x;
            RateNow.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onClick(View v) {
                    setRating(statPosition);
                    setRate = statPosition + 1;
                    switch (setRate) {
                        case 1:
                            UpdateRateing(rate1Num++, 1, mapReting);
                            break;
                        case 2:
                            UpdateRateing(rate2Num++, 2, mapReting);
                            break;
                        case 3:
                            UpdateRateing(rate3Num++, 3, mapReting);
                            break;
                        case 4:
                            UpdateRateing(rate4Num++, 4, mapReting);
                            break;
                        case 5:
                            UpdateRateing(rate5Num++, 5, mapReting);
                            break;
                    }
                    Toast.makeText(ProductDetailsActivity.this, "ProductDetailsActivity = 182 = " + setRate, Toast.LENGTH_LONG).show();
                }
            });
        }
        ////////////////////////////////////////////////////ratings
        edit = getIntent().getBooleanExtra("edit", false);
        HeadCategory = getIntent().getStringExtra("HeadCategory");
        ChildCategory = getIntent().getStringExtra("ChildCategory");
        mAuth = FirebaseAuth.getInstance();
        UserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        firestore = FirebaseFirestore.getInstance();
        FavoriteReference = FirebaseDatabase.getInstance().getReference().child("Users").child(UserId).child("Favorite");
//        push = FavoriteReference.push().getKey();
        Toast.makeText(ProductDetailsActivity.this, "" + pPrice, Toast.LENGTH_LONG).show();
        quantity = getIntent().getStringExtra("quantity");
        if (!Objects.equals(quantity, "") && quantity != null) {
            Numbers = (Integer.parseInt(quantity));
            NumbersText.setText("" + quantity);
        }
        FromFavoriteAdapter = getIntent().getBooleanExtra("FromFavoriteAdapter", false);
        if (FromFavoriteAdapter) {
            pPrice = getIntent().getStringExtra("Price");
            pName = getIntent().getStringExtra("Name");
            pDescription = getIntent().getStringExtra("Description");
            pImage = getIntent().getStringExtra("Image");
            productID = getIntent().getStringExtra("Pid");
            HeadCategory = getIntent().getStringExtra("HeadCategory");
            ChildCategory = getIntent().getStringExtra("ChildCategory");
            Trademark = getIntent().getStringExtra("Trademark");
            rate1 = getIntent().getStringExtra("rate1");
            rate2 = getIntent().getStringExtra("rate2");
            rate3 = getIntent().getStringExtra("rate3");
            rate4 = getIntent().getStringExtra("rate4");
            rate5 = getIntent().getStringExtra("rate5");
        }

        ChickFavorite();

        addToWishListBtn = findViewById(R.id.addToWishListBtn);
        addToCartButton = findViewById(R.id.pd_add_to_cart_button);
//        numberButton = findViewById(R.id.number_btn);
        productImage = findViewById(R.id.product_image_details);
        productName = findViewById(R.id.product_name_details);
        productDescription = findViewById(R.id.product_description_details);
        productPrice = findViewById(R.id.product_price_details);
        productName.setText("" + pName);
        productPrice.setText("" + pPrice);
        productDescription.setText("" + pDescription);
        Picasso.get().load(pImage).placeholder(R.drawable.parson).into(productImage);

//        getProductDetails(productID);

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addToCartButton.setText("Added");

                if (state.equals("Order Placed") || state.equals("Order Shipped")) {
                    Toast.makeText(ProductDetailsActivity.this, "You can purchase more products once your order is shipped or confirmed.", Toast.LENGTH_LONG).show();
                } else {
                    addingToCartList();
                }
                addingToCartList();
            }
        });


        addToWishListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AlreadyaddedToWishList) {
                    AlreadyaddedToWishList = false;
                    addToWishListBtn.setBackgroundTintList((ColorStateList.valueOf(getResources().getColor(R.color.red))));
                    AddToFavorite();

                } else {
                    AlreadyaddedToWishList = true;
                    addToWishListBtn.setBackgroundTintList((ColorStateList.valueOf(getResources().getColor(R.color.white))));
                    DeleteFromFavorite();
                }

            }
        });


    }

    private void UpdateRateing(int rate, int numStar, Map<String, Object> mapReting) {
        mapReting.put("rate" + numStar, String.valueOf(rate));
        firestore.collection("Productes").document(productID).update(mapReting);
    }

    private void DeleteFromFavorite() {

        firestore.collection("Users").document(UserId)
                .collection("Favorite").document(Fid)
                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ProductDetailsActivity.this, "Deleted from favorite", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void AddToFavorite() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currentTime.format(calendar.getTime());
        Map<String, Object> mapFavorite = new HashMap<>();
        mapFavorite.put("HeadCategory", HeadCategory);
        mapFavorite.put("ChildCategory", ChildCategory);
        mapFavorite.put("Pid", productID);
        mapFavorite.put("Fid", saveCurrentTime + UserId);
        firestore.collection("Users").document(UserId)
                .collection("Favorite").document(saveCurrentTime + UserId)
                .set(mapFavorite).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(ProductDetailsActivity.this, "Added to favorite", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void ChickFavorite() {
        firestore.collection("Users").document(UserId)
                .collection("Favorite").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot value, FirebaseFirestoreException error) {
                for (DocumentSnapshot ds : value.getDocuments()) {
                    favorite = ds.toObject(Favorite.class);
                    if (Objects.requireNonNull(favorite).getPid().equals(productID)) {
                        AlreadyaddedToWishList = false;
                        addToWishListBtn.setBackgroundTintList((ColorStateList.valueOf(getResources().getColor(R.color.red))));
                        Fid = favorite.getFid();
                    }
                }
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        CheckOrderState();
    }

    private void addingToCartList() {
        String saveCurrentTime, saveCurrentDate;
        Calendar calForDate = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("CartList");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("pid", productID);
        cartMap.put("pname", pName);
//        cartMap.put("price", productPrice.getText().toString());
        cartMap.put("price", pPrice);
        cartMap.put("pImage", pImage);
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("description", pDescription);
        //ToDo send a number to data as string
        cartMap.put("quantity", String.valueOf(Numbers));
        cartMap.put("discount", "");

        cartListRef.child("UserView").child(Prevalent.currentOnlineUser.getEmail())
                .child("Products").child(productID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            cartListRef.child("AdminView").child(Prevalent.currentOnlineUser.getEmail())
                                    .child("Products").child(productID)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(ProductDetailsActivity.this, "Added to the Cart List", Toast.LENGTH_SHORT).show();

                                                Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                        }

                    }
                });

    }

    private void getProductDetails(String productID) {
        DatabaseReference productsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        productsRef.child(productID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Products products = dataSnapshot.getValue(Products.class);
//                    productName.setText(products.getPname());
//                    productPrice.setText(products.getPrice());
//                    productPrice.setText(""+pPrice);
//                    productDescription.setText(products.getDescription());
//                    ImageUrl = products.getImage();
//                    Picasso.get().load(products.getImage()).into(productImage);
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });
    }

    private void CheckOrderState() {
        DatabaseReference ordersRef;
        ordersRef = FirebaseDatabase.getInstance().getReference().child("Orders")
                .child(UserId);
        ordersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {
                    String shippingState = dataSnapshot.child("state").getValue().toString();


                    if (shippingState.equals("shipped")) {
                        state = "Order Shipped";
                    } else if (shippingState.equals("not shipped")) {
                        state = "Order Placed";
                    }
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError databaseError) {

            }
        });
    }

    @SuppressLint("SetTextI18n")
    public void plusOne(View view) {

        Numbers = Numbers + 1;
        NumbersText.setText("" + Numbers);
    }

    @SuppressLint("SetTextI18n")
    public void mincOne(View view) {
        Numbers = Numbers - 1;
        if (Numbers <= 0) {
            Numbers = 0;
            NumbersText.setText("" + Numbers);
        }
        NumbersText.setText("" + Numbers);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setRating(int statPosition) {
        for (int x = 0; x < RateNow.getChildCount(); x++) {
            ImageView starBtn = (ImageView) RateNow.getChildAt(x);
            starBtn.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
            if (x <= statPosition) {
                starBtn.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
//                starBtn.setBackground(getDrawable(R.drawable.green_button));
            }
//            if (starBtn.getImageTintList()==ColorStateList.valueOf(getResources().getColor(R.color.yellow))){
//                if (statPosition==0){
//                    starBtn.setImageTintList(ColorStateList.valueOf(getResources().getColor(R.color.gray)));
//                }
//            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (edit) {
            Intent intent = new Intent(ProductDetailsActivity.this, CartActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(ProductDetailsActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    //this method is resbonsible to Slider in View Pager
    private void RunSlider(final ViewPager productImageView, final List<Integer> productImage ){
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //to return from index zero if arrived to the end
                if (currentPage >= productImage.size()) {
                    currentPage = 0;
                }
                productImageView.setCurrentItem(currentPage++);
            }
        };
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        },DELAY_TIME, PERIOD_TIME);
    }

}