package com.doubleclick.e_commerceapp.Activites.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.doubleclick.e_commerceapp.Database.ViewHolder.ProductAdapter;
import com.doubleclick.e_commerceapp.Database.ViewModel.ProductViewModel;
import com.doubleclick.e_commerceapp.FragmentsToUploadProductes.ClothesAndShoses.ClothesFragment;
import com.doubleclick.e_commerceapp.Model.Admin;
import com.doubleclick.e_commerceapp.Model.Products;
import com.doubleclick.e_commerceapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewProductItem extends AppCompatActivity {

    private RecyclerView recyclerAdminItem;
    private TextView NoItem;
    private DatabaseReference ProductsRef;
    private String UserID;
    private ProductAdapter productAdapter;
    private ProductViewModel productViewModel;
    private DatabaseReference referenceAdmin;
    private int max, min;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_product_item);
        recyclerAdminItem = findViewById(R.id.recyclerAdminItem);
        NoItem = findViewById(R.id.NoItem);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        mAuth = FirebaseAuth.getInstance();
        UserID = getIntent().getStringExtra("UserID");
        UserID = mAuth.getCurrentUser().getUid();
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        referenceAdmin = FirebaseDatabase.getInstance().getReference().child("Admins").child(UserID);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerAdminItem.setLayoutManager(linearLayoutManager);
        recyclerAdminItem.setHasFixedSize(true);
        productAdapter = new ProductAdapter();
        recyclerAdminItem.setAdapter(productAdapter);
        List<Products> productsList = new ArrayList<>();


        productViewModel.getAllProductes().observe(this, new Observer<List<Products>>() {
            @Override
            public void onChanged(List<Products> products) {
                productAdapter.setProductsList(products);
            }
        });

        /*referenceAdmin.child("products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Products products = dataSnapshot.getValue(Products.class);
                    Toast.makeText(ViewProductItem.this, "View Product  = 82 " + products.toString(), Toast.LENGTH_LONG).show();
                    productsList.add(products);
                }
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });*/

        /*referenceAdmin.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                try {
                    max = Integer.parseInt(snapshot.getValue(Admin.class).getMax());
                    min = Integer.parseInt(snapshot.getValue(Admin.class).getMin());
                }catch (NullPointerException e){

                }



            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });*/


        /*to see an item is stored in data base
        * and when i am clicked show me in AdminAddNewProductActivity to edit
        * */
        productAdapter.OnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Products products) {
                Intent intent = new Intent(ViewProductItem.this, ClothesFragment.class); // => AdminAddNewProductActivity.class
                intent.putExtra(AdminAddNewProductActivity.EXTRA_PRIMARY_ID, products.getId());
                intent.putExtra(AdminAddNewProductActivity.EXTRA_ID, products.getPid());
                intent.putExtra(AdminAddNewProductActivity.EXTRA_NAME, products.getPname());
                intent.putExtra(AdminAddNewProductActivity.EXTRA_PRICE, products.getPrice());
                intent.putExtra(AdminAddNewProductActivity.EXTRA_IMAGE, products.getImage());
                intent.putExtra(AdminAddNewProductActivity.EXTRA_DESCRIPTION, products.getDescription());
                intent.putExtra(AdminAddNewProductActivity.EXTRA_PHONE, products.getIdAdmin());
                intent.putExtra(AdminAddNewProductActivity.EXTRA_ChildCATAGORY, products.getIdAdmin());
                intent.putExtra(AdminAddNewProductActivity.EXTRA_HeadCATAGORY, products.getIdAdmin());
                intent.putExtra("Edit", true);
                startActivity(intent);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
//                if (max != 0) {
                    productViewModel.delete(productAdapter.getProductAt(position));
                    //to delete product from Data
                    ProductsRef.child(productAdapter.getProductAt(position).getChildCategory())
                            .child(productAdapter.getProductAt(position).getPid())
                            .removeValue();
//                    Map<String, Object> map = new HashMap<>();
//                    min = min - 1;
//                    map.put("min", String.valueOf(min));
//                    referenceAdmin.updateChildren(map);
                }
//                if (max == 0) {
//                    Toast.makeText(ViewProductItem.this, "You are Blocked", Toast.LENGTH_LONG).show();
//                }
//            }
        }).attachToRecyclerView(recyclerAdminItem);

    }
}