package com.doubleclick.e_commerceapp.FirebaseAdapter;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.doubleclick.e_commerceapp.HomePage.HomePageAdapter;
import com.doubleclick.e_commerceapp.Model.Advertisement;
import com.doubleclick.e_commerceapp.Model.HomePage;
import com.doubleclick.e_commerceapp.Model.Products;
import com.doubleclick.e_commerceapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DBQuries {

    public static List<HomePage> homePageModels = new ArrayList<>();
    public static List<Advertisement> ConstantAdBanner = new ArrayList<>();
    public static List<Advertisement> advertisementList = new ArrayList<>();
    public static List<List<Products>> AllProductesLists = new ArrayList<>();
    public static List<Products> productsListColses = new ArrayList<>();
    public static List<Products> productsListWatches = new ArrayList<>();
    public static List<Products> productsListperfume = new ArrayList<>();
    public static List<Products> productsListhealth_and_beauty = new ArrayList<>();
    public static List<Products> productsListJewelry_and_accessories = new ArrayList<>();
    public static List<Products> productsListSleepingEssentials = new ArrayList<>();
    public static List<Products> productsListShowerEssentials = new ArrayList<>();
    public static List<Products> productsListcameras = new ArrayList<>();
    public static List<Products> productsListelectronics = new ArrayList<>();
    public static List<Products> productsListComputer = new ArrayList<>();
    public static List<Products> productsListMobiles_and_tablets = new ArrayList<>();
    public static List<Products> productsListVideo_games = new ArrayList<>();
    public static List<Products> productsListgardens_Accessories = new ArrayList<>();
    public static List<Products> productsListhousehold_appliance = new ArrayList<>();
    public static List<Products> productsListHome_improvement_tools_and_equipment = new ArrayList<>();
    public static List<Products> productsListFoodsAndDrinks = new ArrayList<>();
    public static List<Products> productsListApplicanceKitchen = new ArrayList<>();
    public static List<Products> productsListFilms = new ArrayList<>();
    public static List<Products> productsListDisktops = new ArrayList<>();
    public static List<Products> productsListSports = new ArrayList<>();
    public static List<Products> productsListcrafts_and_arts = new ArrayList<>();
    public static List<Products> productsListOptice = new ArrayList<>();
    public static List<Products> productsListOthers = new ArrayList<>();
    private static FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private static List<String> CategoryList;
    private static Products products;

    public static void loadAdveristement(HomePageAdapter adapter, Context context) {

        firebaseFirestore.collection("Advertisement")
                .document("Banner")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                try {
                    Advertisement advertisementConstant = documentSnapshot.toObject(Advertisement.class);
                    ConstantAdBanner.add(advertisementConstant);
                    homePageModels.add(1,new HomePage(1, "#000", ConstantAdBanner.get(0).getImageAd()));
                } catch (NullPointerException e) {
                    Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
                adapter.notifyDataSetChanged();
            }
        });

        firebaseFirestore.collection("Advertisement")
                .document("Strip")
                .collection("Strips")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        try {
                            for (DocumentSnapshot documentSnapshot:task.getResult().getDocuments()){
                                Advertisement advertisementSlider = documentSnapshot.toObject(Advertisement.class);
//                                Toast.makeText(context, "" +advertisementSlider.toString(), Toast.LENGTH_LONG).show();
                                advertisementList.add(advertisementSlider);
                            }
                            homePageModels.add(0,new HomePage(0,advertisementList,"Strip"));
                        }catch (NullPointerException e){
                            Toast.makeText(context,""+e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

    }

    public static void loadDataFragment(HomePageAdapter adapter, String child, final Context context) {
        CategoryList = Arrays.asList(context.getResources().getStringArray(R.array.array_categoey));
        firebaseFirestore.collection("Productes").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot value, FirebaseFirestoreException error) {

//                for (String c : CategoryList) {
                for (DocumentSnapshot ds : value.getDocuments()) {
                    products = ds.toObject(Products.class);
                    try {
                        if (products.getHeadCategory()!=null&&products.getChildCategory()!=null) {
                            if (Objects.requireNonNull(products).getHeadCategory().equals(CategoryList.get(0))) {
                                productsListColses.add(products);
                            } else if (products.getHeadCategory().equals(CategoryList.get(1))) {
                                productsListWatches.add(products);
                            } else if (products.getHeadCategory().equals(CategoryList.get(2))) {
                                productsListperfume.add(products);
                            } else if (products.getHeadCategory().equals(CategoryList.get(3))) {
                                productsListhealth_and_beauty.add(products);
                            } else if (products.getHeadCategory().equals(CategoryList.get(4))) {
                                productsListJewelry_and_accessories.add(products);
                            } else if (products.getHeadCategory().equals(CategoryList.get(5))) {
                                productsListSleepingEssentials.add(products);
                            } else if (products.getHeadCategory().equals(CategoryList.get(6))) {
                                productsListShowerEssentials.add(products);
                            } else if (products.getHeadCategory().equals(CategoryList.get(7))) {
                                productsListcameras.add(products);
                            } else if (products.getHeadCategory().equals(CategoryList.get(8))) {
                                productsListelectronics.add(products);
                            } else if (products.getHeadCategory().equals(CategoryList.get(9))) {
                                productsListComputer.add(products);
                            } else if (products.getHeadCategory().equals(CategoryList.get(10))) {
                                productsListMobiles_and_tablets.add(products);
                            } else if (products.getHeadCategory().equals(CategoryList.get(11))) {
                                productsListVideo_games.add(products);
                            } else if (products.getHeadCategory().equals(CategoryList.get(12))) {
                                productsListgardens_Accessories.add(products);
                            } else if (products.getHeadCategory().equals(CategoryList.get(13))) {
                                productsListhousehold_appliance.add(products);
                            } else if (products.getHeadCategory().equals(CategoryList.get(14))) {
                                productsListHome_improvement_tools_and_equipment.add(products);
                            } else if (products.getHeadCategory().equals(CategoryList.get(15))) {
                                productsListFoodsAndDrinks.add(products);
                            } else if (products.getHeadCategory().equals(CategoryList.get(16))) {
                                productsListApplicanceKitchen.add(products);
                            } else if (products.getHeadCategory().equals(CategoryList.get(17))) {
                                productsListFilms.add(products);
                            } else if (products.getHeadCategory().equals(CategoryList.get(18))) {
                                productsListDisktops.add(products);
                            } else if (products.getHeadCategory().equals(CategoryList.get(19))) {
                                productsListSports.add(products);
                            } else if (products.getHeadCategory().equals(CategoryList.get(20))) {
                                productsListcrafts_and_arts.add(products);
                            } else if (products.getHeadCategory().equals(CategoryList.get(21))) {
                                productsListOptice.add(products);
                            } else if (products.getHeadCategory().equals(CategoryList.get(22))) {
                                productsListOthers.add(products);
                            }
                        }
                    }catch (NullPointerException e){
                      e.printStackTrace();
                    }

                }
                AllProductesLists.add(productsListColses);//0
                AllProductesLists.add(productsListWatches);//1
                AllProductesLists.add(productsListperfume);//2
                AllProductesLists.add(productsListhealth_and_beauty);//3
                AllProductesLists.add(productsListJewelry_and_accessories);//4
                AllProductesLists.add(productsListSleepingEssentials);//5
                AllProductesLists.add(productsListShowerEssentials);//6
                AllProductesLists.add(productsListcameras);//7
                AllProductesLists.add(productsListelectronics);//8
                AllProductesLists.add(productsListComputer);//9
                AllProductesLists.add(productsListMobiles_and_tablets);//10
                AllProductesLists.add(productsListVideo_games);//11
                AllProductesLists.add(productsListgardens_Accessories);//12
                AllProductesLists.add(productsListhousehold_appliance);//13
                AllProductesLists.add(productsListHome_improvement_tools_and_equipment);//14
                AllProductesLists.add(productsListFoodsAndDrinks);//15
                AllProductesLists.add(productsListApplicanceKitchen);//16
                AllProductesLists.add(productsListFilms);//17
                AllProductesLists.add(productsListDisktops);//18
                AllProductesLists.add(productsListSports);//19
                AllProductesLists.add(productsListcrafts_and_arts);//20
                AllProductesLists.add(productsListOptice);//21
                AllProductesLists.add(productsListOthers);//22

                for (int i = 0;i<CategoryList.size();i++){
                    homePageModels.add(new HomePage(CategoryList.get(i), AllProductesLists.get(i), 2));
                }
                /*homePageModels.add(new HomePage(CategoryList.get(0), AllProductesLists.get(0), 2));
                homePageModels.add(new HomePage(CategoryList.get(1), AllProductesLists.get(1), 2));
                homePageModels.add(new HomePage(CategoryList.get(2), AllProductesLists.get(2), 2));
                homePageModels.add(new HomePage(CategoryList.get(3), AllProductesLists.get(3), 2));
                homePageModels.add(new HomePage(CategoryList.get(4), AllProductesLists.get(4), 2));
                homePageModels.add(new HomePage(CategoryList.get(5), AllProductesLists.get(5), 2));
                homePageModels.add(new HomePage(CategoryList.get(6), AllProductesLists.get(6), 2));
                homePageModels.add(new HomePage(CategoryList.get(7), AllProductesLists.get(7), 2));
                homePageModels.add(new HomePage(CategoryList.get(8), AllProductesLists.get(8), 2));
                homePageModels.add(new HomePage(CategoryList.get(9), AllProductesLists.get(9), 2));
                homePageModels.add(new HomePage(CategoryList.get(10), AllProductesLists.get(10), 2));
                homePageModels.add(new HomePage(CategoryList.get(11), AllProductesLists.get(11), 2));
                homePageModels.add(new HomePage(CategoryList.get(12), AllProductesLists.get(12), 2));
                homePageModels.add(new HomePage(CategoryList.get(13), AllProductesLists.get(13), 2));
                homePageModels.add(new HomePage(CategoryList.get(14), AllProductesLists.get(14), 2));
                homePageModels.add(new HomePage(CategoryList.get(15), AllProductesLists.get(15), 2));
                homePageModels.add(new HomePage(CategoryList.get(16), AllProductesLists.get(16), 2));
                homePageModels.add(new HomePage(CategoryList.get(17), AllProductesLists.get(17), 2));
                homePageModels.add(new HomePage(CategoryList.get(18), AllProductesLists.get(18), 2));
                homePageModels.add(new HomePage(CategoryList.get(19), AllProductesLists.get(19), 2));
                homePageModels.add(new HomePage(CategoryList.get(20), AllProductesLists.get(20), 2));
                homePageModels.add(new HomePage(CategoryList.get(21), AllProductesLists.get(21), 2));*/
                adapter.notifyDataSetChanged();

//                }

            }
        });

    }

}
