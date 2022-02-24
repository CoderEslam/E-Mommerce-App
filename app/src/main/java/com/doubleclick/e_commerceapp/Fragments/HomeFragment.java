package com.doubleclick.e_commerceapp.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.doubleclick.e_commerceapp.Activites.User.CartActivity;
import com.doubleclick.e_commerceapp.Activites.User.HomeActivity;
import com.doubleclick.e_commerceapp.FirebaseAdapter.CategoryAdapter;
import com.doubleclick.e_commerceapp.FirebaseAdapter.ProductAdapter;
import com.doubleclick.e_commerceapp.HomePage.HomePageAdapter;
import com.doubleclick.e_commerceapp.Model.HomePage;
import com.doubleclick.e_commerceapp.Model.Products;
import com.doubleclick.e_commerceapp.Model.Users;
import com.doubleclick.e_commerceapp.R;
import com.doubleclick.e_commerceapp.Slider_Banner.SliderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

import static com.doubleclick.e_commerceapp.FirebaseAdapter.DBQuries.homePageModels;
import static com.doubleclick.e_commerceapp.FirebaseAdapter.DBQuries.loadAdveristement;
import static com.doubleclick.e_commerceapp.FirebaseAdapter.DBQuries.loadDataFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener {

    private DatabaseReference referenceAdvertis, ProductsReference;
    private RecyclerView HomePageRecyclerView;
    RecyclerView.LayoutManager layoutManager1;
    private SwipeRefreshLayout SwiperHomeLayout;
    List<Products> productsList = new ArrayList<>();
    private CategoryAdapter categoryAdapter;
    private List<HomePage> list;
    private HomePageAdapter pageAdapter;
    private List<SliderModel> sliderModelList = new ArrayList<>();
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private SearchView Search;
    private FirebaseAuth mAuth;
    private boolean main = false;
    private List<String> CategoryList;
    private Toolbar toolbar;
    private FirebaseFirestore firebaseFirestore;

    public HomeFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait........");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Search = view.findViewById(R.id.contactSearchView);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        list = new ArrayList<>();
        //to get the header of navigationView
//        headerView = navigationView.getHeaderView(0);
//        userNameTextView = headerView.findViewById(R.id.user_profile_name);
//        profileImageView = headerView.findViewById(R.id.user_profile_image);
        toolbar = view.findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        referenceAdvertis = FirebaseDatabase.getInstance().getReference().child("Advertisement").child("Strip");
        ProductsReference = FirebaseDatabase.getInstance().getReference().child("Products");
        CategoryList = Arrays.asList(getResources().getStringArray(R.array.array_categoey));
        referenceAdvertis.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshotAd : dataSnapshot.getChildren()) {
                    SliderModel sliderModel = dataSnapshotAd.getValue(SliderModel.class);
//                    Toast.makeText(HomeActivity.this, "" + sliderModel.toString(), Toast.LENGTH_LONG).show();
                    sliderModelList.add(sliderModel);
                }
            }
        });

        categoryAdapter = new CategoryAdapter();
        Paper.init(getContext());
        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), CartActivity.class);
                startActivity(intent);
            }
        });
        HomePageRecyclerView = view.findViewById(R.id.HomePageRecyclerView);
        SwiperHomeLayout = view.findViewById(R.id.SwiperHomeLayout);
        HomePageRecyclerView.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        HomePageRecyclerView.setLayoutManager(layoutManager1);
        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                SwiperHomeLayout.setRefreshing(false);
                relaod();
            }
        };
        SwiperHomeLayout.setOnRefreshListener(refreshListener);

        list.clear();
        if (sliderModelList.size() != 0) {
            list.add(new HomePage(0, sliderModelList));
        }

        pageAdapter = new HomePageAdapter(homePageModels);
        HomePageRecyclerView.setAdapter(pageAdapter);
        if (homePageModels.size() == 0) {
            loadAdveristement(pageAdapter, getContext());
            loadDataFragment(pageAdapter, CategoryList.get(0), getContext());

        } else {
            pageAdapter.notifyDataSetChanged();
        }
        progressDialog.dismiss();

        Search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                productsList.clear();
                firebaseFirestore.collection("Productes")
                        .orderBy("pname")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(Task<QuerySnapshot> task) {
                                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                    Products products = documentSnapshot.toObject(Products.class);
                                    if (products.getPname().contains(query)) {
                                        productsList.add(products);
                                    }
                                }
                                if (query == null) {
                                    if (homePageModels.size() == 0) {
                                        loadDataFragment(pageAdapter, CategoryList.get(0), getContext()); // watches
                                    } else {
                                        pageAdapter.notifyDataSetChanged();
                                    }
                                } else {
                                    homePageModels.clear();
                                    ProductAdapter productAdapter = new ProductAdapter(productsList, getContext());
                                    HomePageRecyclerView.setHasFixedSize(true);
                                    layoutManager1 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                    HomePageRecyclerView.setLayoutManager(layoutManager1);
                                    HomePageRecyclerView.setAdapter(productAdapter);
                                    productAdapter.notifyDataSetChanged();
                                }
                            }
                        });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void relaod() {
        list.clear();
        if (sliderModelList.size() != 0) {
            list.add(new HomePage(0, sliderModelList));
        }

        pageAdapter = new HomePageAdapter(homePageModels);
        HomePageRecyclerView.setAdapter(pageAdapter);
        if (homePageModels.size() == 0) {
//            for (String c:CategoryList) {
            loadDataFragment(pageAdapter, CategoryList.get(0), getContext()); // watches
//            loadDataFragment(pageAdapter, CategoryList.get(1) , getContext());
//            }
        } else {
            pageAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

   /* @Override
    public void onDestroy() {
        super.onDestroy();
        homePageModels.clear();
    }*/
}