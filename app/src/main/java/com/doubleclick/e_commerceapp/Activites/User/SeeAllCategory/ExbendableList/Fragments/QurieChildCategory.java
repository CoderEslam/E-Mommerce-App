package com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.ExbendableList.Fragments;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.doubleclick.e_commerceapp.FirebaseAdapter.ProductAdapter;
import com.doubleclick.e_commerceapp.Model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QurieChildCategory {

    private static FirebaseFirestore firestore;
    public static List<Products> productsListChildren = new ArrayList<>();

    public static void loadChildren(ProductAdapter productAdapter, String Head, String Child, Context context) {

        firestore = FirebaseFirestore.getInstance();
        firestore.collection("Productes")
                .orderBy("ChildCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            //for (QueryDocumentSnapshot documentSnapshot : task.getResult())
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                Products products = documentSnapshot.toObject(Products.class);
                                if (Objects.requireNonNull(products).getChildCategory().equals(Child)) {
//                                    Toast.makeText(context, "QurieChildCategory = " + products.toString(), Toast.LENGTH_LONG).show();
                                    productsListChildren.add(products);
                                }
                            }
                            productAdapter.notifyDataSetChanged();
                        }
                    }
                });
                /*.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot value, FirebaseFirestoreException error) {

                for (DocumentSnapshot documentSnapshot:value.getDocuments()){
                    Products products = documentSnapshot.toObject(Products.class);
                    Toast.makeText(context,"QurieChildCategory = "+products.toString(),Toast.LENGTH_LONG).show();
                    productsListChildren.add(products);
                }
                productAdapter.notifyDataSetChanged();

            }
        });*/

    }

}
