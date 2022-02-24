package com.doubleclick.e_commerceapp.Fragments.Favorite;

import android.content.Context;
import android.widget.Toast;

import com.doubleclick.e_commerceapp.Model.Products;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DBQurieFavorite {
    public static FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private static Favorite favorite;
    public static List<Products> favoriteList = new ArrayList<>();
    public static List<Favorite> favorites = new ArrayList<>();

    public static void CheckFavorite(FavoriteAdapter favoriteAdapter ,Context context, String UserID) {
        firestore.collection("Users").document(UserID)
                .collection("Favorite").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot value, FirebaseFirestoreException error) {
                for (DocumentSnapshot ds : value.getDocuments()) {
                    favorite = ds.toObject(Favorite.class);
                    favorites.add(favorite);
                    firestore.collection("Productes").document(favorite.getPid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                Products products = documentSnapshot.toObject(Products.class);
                                favoriteList.add(products);
                                favoriteAdapter.notifyDataSetChanged();
                            }else {
                                firestore.collection("Users")
                                        .document(UserID)
                                        .collection("Favorite")
                                        .document(favorite.getFid())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        favoriteAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
        favoriteAdapter.notifyDataSetChanged();
    }

}
