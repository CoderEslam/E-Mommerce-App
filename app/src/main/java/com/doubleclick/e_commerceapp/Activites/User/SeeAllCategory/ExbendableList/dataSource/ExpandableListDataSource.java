package com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.ExbendableList.dataSource;


import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.doubleclick.e_commerceapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ExpandableListDataSource {

    private static DatabaseReference CategoryReference;
    private static Map<String, List<String>> expandableListData;
    private static Context mContext;
    private static FirebaseFirestore firestoreCategory;
    private static List<String> listCategory = new ArrayList<>();
    private static List<String> categoryList;


    public static Map<String, List<String>> getData(Context context) {
        mContext = context;
        firestoreCategory = FirebaseFirestore.getInstance();
        expandableListData = new TreeMap<>();

        /*firestoreCategory.collection("Category")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> documentSnapshots  = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot documentSnapshot:documentSnapshots){
                            listCategory.add(documentSnapshot.getId());
                            firestoreCategory.collection("Category")
                                    .document(documentSnapshot.getId())
                                    .get()
                                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull @NotNull Task<DocumentSnapshot> task) {
                                            try {
                                                DocumentSnapshot document = task.getResult();
//                                                List<String > modellist  = (List<String>) document.getData();
//                                                model.setList(modellist);
                                                String[] data = document.getData().keySet().toArray(new String[document.getData().size()]);
                                                Set<String> list = document.getData().keySet();

                                                Toast.makeText(context,""+list.toArray(),Toast.LENGTH_LONG).show();

                                            }catch (ClassCastException e){

                                            }

                                        }
                                    });
//                            Toast.makeText(context,""+documentSnapshot.getId(),Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(context,""+e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                });*/

        categoryList = Arrays.asList(context.getResources().getStringArray(R.array.array_categoey));
        List<String> shoes_and_clothes = Arrays.asList(context.getResources().getStringArray(R.array.shoes_and_clothes));
        List<String> Watches = Arrays.asList(context.getResources().getStringArray(R.array.Watches));
        List<String> perfume = Arrays.asList(context.getResources().getStringArray(R.array.perfume));
        List<String> optics = Arrays.asList(context.getResources().getStringArray(R.array.optics));
        List<String> SleepingEssentials = Arrays.asList(context.getResources().getStringArray(R.array.SleepingEssentials));
        List<String> ShowerEssentials = Arrays.asList(context.getResources().getStringArray(R.array.ShowerEssentials));
        List<String> cameras = Arrays.asList(context.getResources().getStringArray(R.array.cameras));
        List<String> electronics = Arrays.asList(context.getResources().getStringArray(R.array.electronics));
        List<String> Video_games = Arrays.asList(context.getResources().getStringArray(R.array.Video_games));
        List<String> health_and_beauty = Arrays.asList(context.getResources().getStringArray(R.array.health_and_beauty));
        List<String> crafts_and_arts = Arrays.asList(context.getResources().getStringArray(R.array.crafts_and_arts));
        List<String> Jewelry_and_accessories = Arrays.asList(context.getResources().getStringArray(R.array.Jewelry_and_accessories));
        List<String> Mobiles_and_tablets = Arrays.asList(context.getResources().getStringArray(R.array.Mobiles_and_tablets));
        List<String> Home_improvement_tools_and_equipment = Arrays.asList(context.getResources().getStringArray(R.array.Home_improvement_tools_and_equipment));
        List<String> gardens_Accessories = Arrays.asList(context.getResources().getStringArray(R.array.gardens_Accessories));
        List<String> household_appliance = Arrays.asList(context.getResources().getStringArray(R.array.household_appliance));
        expandableListData.put(categoryList.get(0), shoes_and_clothes);
        expandableListData.put(categoryList.get(1), Watches);
        expandableListData.put(categoryList.get(2), perfume);
        expandableListData.put(categoryList.get(3), health_and_beauty);
        expandableListData.put(categoryList.get(4), Jewelry_and_accessories);
        expandableListData.put(categoryList.get(5), SleepingEssentials);
        expandableListData.put(categoryList.get(6), ShowerEssentials);
        expandableListData.put(categoryList.get(7), cameras);
        expandableListData.put(categoryList.get(8), electronics);
        expandableListData.put(categoryList.get(10), Mobiles_and_tablets);
        expandableListData.put(categoryList.get(11), Video_games);
        expandableListData.put(categoryList.get(12), gardens_Accessories);
        expandableListData.put(categoryList.get(13), household_appliance);
        expandableListData.put(categoryList.get(14), Home_improvement_tools_and_equipment);
        expandableListData.put(categoryList.get(20), crafts_and_arts);
        expandableListData.put(categoryList.get(21), optics);
        return expandableListData;

    }


}
