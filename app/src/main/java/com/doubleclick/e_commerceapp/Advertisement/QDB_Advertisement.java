package com.doubleclick.e_commerceapp.Advertisement;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.doubleclick.e_commerceapp.Model.Advertisement;
import com.doubleclick.e_commerceapp.Model.HomePage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class QDB_Advertisement {

    private static FirebaseFirestore firestore;
    public static List<Advertisement> advertisements = new ArrayList<>();

    public static void loadAdvertisement(AdvertisementAdapter advertisementAdapter,Context context) {

        firestore = FirebaseFirestore.getInstance();
        firestore.collection("Advertisement")
                .document("Strip")
                .collection("Strips")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                            try {
                                Advertisement advertisement = documentSnapshot.toObject(Advertisement.class);
                                advertisements.add(advertisement);

                            } catch (NullPointerException e) {
                                Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                        advertisementAdapter.notifyDataSetChanged();
                    }
                });


    }


}
