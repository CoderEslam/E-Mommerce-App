package com.doubleclick.e_commerceapp.Activites.User;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.doubleclick.e_commerceapp.Activites.Login_SignIn.MainActivity;
import com.doubleclick.e_commerceapp.Activites.onAppKilled;
import com.doubleclick.e_commerceapp.DeliveryDrivers.DriversMapFragment;
import com.doubleclick.e_commerceapp.DeliveryDrivers.SellerMapFragment;
import com.doubleclick.e_commerceapp.Fragments.Favorite.FavoriteFragment;
import com.doubleclick.e_commerceapp.Fragments.HomeFragment;
import com.doubleclick.e_commerceapp.Fragments.SeeAllCatergoryFragment;
import com.doubleclick.e_commerceapp.Fragments.SettingsFragment;
import com.doubleclick.e_commerceapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;
import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity {// implements SearchView.OnQueryTextListener {

    @SuppressLint("StaticFieldLeak")
    public static SNavigationDrawer sNavigationDrawer;
    Class fragmentClass;
    public static Fragment fragment;

    /*private DatabaseReference referenceAdvertis, ProductsReference;
    private RecyclerView HomePageRecyclerView;
    RecyclerView.LayoutManager layoutManager1;
    private SwipeRefreshLayout SwiperHomeLayout;
    List<Products> productsList = new ArrayList<>();
    RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;
    private List<HomePage> list;
    private HomePageAdapter pageAdapter;
    private List<SliderModel> sliderModelList = new ArrayList<>();
    private String BannerAd;
    private final int TimeLoad = 5000;
    private ProgressDialog progressDialog;
    private SwipeRefreshLayout.OnRefreshListener refreshListener;
    private SearchView Search;
    private FirebaseAuth mAuth;
    private String UserId;
    private String name, image;
    private boolean main = false;
//    private UserViewModel userViewModel;
    private View headerView;
    private TextView userNameTextView;
    private CircleImageView profileImageView;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;
    private FirebaseFirestore firebaseFirestore;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        /*progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading data");
        progressDialog.setMessage("Please wait........");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Search = findViewById(R.id.contactSearchView);
        mAuth = FirebaseAuth.getInstance();
        UserId = mAuth.getCurrentUser().getUid();
        main = getIntent().getBooleanExtra("main", false);
        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);
        firebaseFirestore = FirebaseFirestore.getInstance();
        //to get the header of navigationView
        headerView = navigationView.getHeaderView(0);
        userNameTextView = headerView.findViewById(R.id.user_profile_name);
        profileImageView = headerView.findViewById(R.id.user_profile_image);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);*/

        //Inside onCreate()
        sNavigationDrawer = findViewById(R.id.navigationDrawer);
        //Creating a list of menu Items
        List<MenuItem> menuItems = new ArrayList<>();
        //Use the MenuItem given by this library and not the default one.
        //First parameter is the title of the menu item and then the second parameter is the image which will be the background of the menu item.
        menuItems.add(new MenuItem("Home", R.drawable.news_bg));
        menuItems.add(new MenuItem("Category", R.drawable.feed_bg));
        menuItems.add(new MenuItem("Settings", R.drawable.message_bg));
        menuItems.add(new MenuItem("Favorite", R.drawable.music_bg));
        menuItems.add(new MenuItem("Call car",R.mipmap.ic_pickup));
        menuItems.add(new MenuItem("Driver",R.mipmap.ic_car));
        menuItems.add(new MenuItem("Logout",R.drawable.back_btn));

        //then add them to navigation drawer

        sNavigationDrawer.setMenuItemList(menuItems);
        fragmentClass = HomeFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
        }

        //Listener to handle the menu item click. It returns the position of the menu item clicked. Based on that you can switch between the fragments.

        sNavigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {
                switch (position) {
                    case 0: {
                        fragmentClass = HomeFragment.class;
                        break;
                    }
                    case 1: {
                        fragmentClass = SeeAllCatergoryFragment.class;
                        break;
                    }
                    case 2: {
                        fragmentClass = SettingsFragment.class;
                        break;
                    }
                    case 3: {
                        fragmentClass = FavoriteFragment.class;
                        break;
                    }
                    case 4:{
                        fragmentClass = SellerMapFragment.class;
                        /*Intent intent = new Intent(HomeActivity.this, SellerMapActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();*/
                        break;
                    }
                    case 5:{
                        fragmentClass = DriversMapFragment.class;
                        /*Intent intent = new Intent(HomeActivity.this, DriverMapActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();*/
                        break;
                    }
                    case 6:{
                        Paper.book().destroy();
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                        break;
                    }

                }

                //Listener for drawer events such as opening and closing.
                sNavigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {

                    @Override
                    public void onDrawerOpened() {

                    }

                    @Override
                    public void onDrawerOpening() {

                    }

                    @Override
                    public void onDrawerClosing() {
                        System.out.println("Drawer closed");
                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (ClassCastException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                        if (fragment != null) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();

                        }
                    }
                    @Override
                    public void onDrawerClosed() { }
                    @Override
                    public void onDrawerStateChanged(int newState) { // show , you are in what item it Drawer, by position
//                        Toast.makeText(HomeActivity.this,""+newState,Toast.LENGTH_LONG).show();
//                        System.out.println("State " + newState);

                    }
                });
            }
        });

        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_NETWORK_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.VIBRATE,
                        Manifest.permission.INTERNET
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
                checkLocationPermission();
            }
            @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        }).check();

    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                new AlertDialog.Builder(this)
                        .setTitle("give permission")
                        .setMessage("give permission message")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

        /*firebaseFirestore.collection("Users").document(UserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Users user = documentSnapshot.toObject(Users.class);
                try {
                    userNameTextView.setText("" + user.getName());
                    try {
                        Picasso.get().load(user.getImage()).placeholder(R.drawable.parson).into(profileImageView);
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(HomeActivity.this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    catch (NullPointerException e) {
                        Toast.makeText(HomeActivity.this, "No name or Image " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } catch (NullPointerException e) {
                    Toast.makeText(HomeActivity.this, "No name or Image " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });


        referenceAdvertis = FirebaseDatabase.getInstance().getReference().child("Advertisement").child("Strip");

//        ProductsReference = FirebaseDatabase.getInstance().getReference().child("Products").child("Laptops");
        ProductsReference = FirebaseDatabase.getInstance().getReference().child("Products");
        ProductsReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<DataSnapshot> task) {
//                Toast.makeText(HomeActivity.this,""+task.getResult(),Toast.LENGTH_LONG).show();

//                Search.setText(""+task.getResult().toString());
            }
        });


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

        referenceAdvertis = FirebaseDatabase.getInstance().getReference().child("Advertisement").child("Banner");
        firebaseFirestore.collection("Advertisement").document("Banner").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                SliderModel sliderModel = documentSnapshot.toObject(SliderModel.class);
                BannerAd = sliderModel.getImageAd();
            }
        });
        referenceAdvertis.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {

            }
        });

        categoryAdapter = new CategoryAdapter();

        Paper.init(this);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });


        toggle = new ActionBarDrawerToggle(HomeActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                // Handle navigation view item clicks here.
                int id = item.getItemId();

                if (id == R.id.nav_cart) {
                    Intent intent = new Intent(HomeActivity.this, CartActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_categories) {
                    Intent intent = new Intent(HomeActivity.this, SeeAllCatrgoryActivity.class);
                    startActivity(intent);

                } else if (id == R.id.nav_settings) {
                    Intent intent = new Intent(HomeActivity.this, SettinsActivity.class);
                    startActivity(intent);
                } else if (id == R.id.nav_logout) {
                    Paper.book().destroy();
                    Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                else if (id==R.id.car){
                    Intent intent = new Intent(HomeActivity.this, SellerMapActivity.class);
                    startActivity(intent);
                    finish();
                }
                else if (id==R.id.driver){
                    Intent intent = new Intent(HomeActivity.this, DriverMapActivity.class);
                    startActivity(intent);
                    finish();
                }

                drawer.closeDrawer(GravityCompat.START);
                return true;

            }
        });

        try {
            if (name != null || image != null) {

            }
        } catch (NullPointerException e) {
        }


        HomePageRecyclerView = findViewById(R.id.HomePageRecyclerView);
        SwiperHomeLayout = findViewById(R.id.SwiperHomeLayout);
        HomePageRecyclerView.setHasFixedSize(true);
        layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        HomePageRecyclerView.setLayoutManager(layoutManager1);

        refreshListener = new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new loadAsyncTask().execute();
                SwiperHomeLayout.setRefreshing(false);
                onStart();
            }
        };

        SwiperHomeLayout.setOnRefreshListener(refreshListener);

        new loadAsyncTask().execute();


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(HomeActivity.this, SettinsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    ////////////////////////////
    @Override
    public boolean onQueryTextSubmit(String query) {

        ProductsReference.orderByChild("pname")
                //.endAt(String.valueOf(s))
                //.endBefore(String.valueOf(s))
                .startAt(String.valueOf(query))
                //.startAfter(String.valueOf(s))
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                        Toast.makeText(HomeActivity.this, "" + snapshot.getValue(Products.class).getPname(), Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onChildChanged(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                    }

                    @Override
                    public void onChildRemoved(@NonNull @NotNull DataSnapshot snapshot) {
                    }

                    @Override
                    public void onChildMoved(@NonNull @NotNull DataSnapshot snapshot, @Nullable @org.jetbrains.annotations.Nullable String previousChildName) {
                    }

                    @Override
                    public void onCancelled(@NonNull @NotNull DatabaseError error) {
                    }

                });

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
    /////////////////////////////////////////////////

    public class loadAsyncTask extends AsyncTask<Void, Void, Void> {


        public loadAsyncTask() {
        }

        @Override
        protected Void doInBackground(Void... voids) {




//            try {
//                Thread.sleep(TimeLoad);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
////                    load();
//                    progressDialog.dismiss();
//
//                }
//            });
//            thread.start();


            return null;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        list = new ArrayList<>();
        list.clear();
        if (sliderModelList.size() != 0) {
            list.add(new HomePage(0, sliderModelList));
        }
        if (BannerAd != null) {
            list.add(new HomePage(1, "#000", BannerAd));
        }
        list.add(new HomePage(2, "Laptops"));
        list.add(new HomePage(2, "dress \uD83D\uDC57"));
        pageAdapter = new HomePageAdapter();
        pageAdapter.setHomePageList(list);
        HomePageRecyclerView.setAdapter(pageAdapter);
        pageAdapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
        list = new ArrayList<>();
        list.clear();
        if (sliderModelList.size() != 0) {
            list.add(new HomePage(0, sliderModelList));
        }
        if (BannerAd != null) {
            list.add(new HomePage(1, "#000", BannerAd));
        }
        list.add(new HomePage(2, "Laptops"));
        list.add(new HomePage(2, "dress \uD83D\uDC57"));
        pageAdapter = new HomePageAdapter();
        pageAdapter.setHomePageList(list);
        HomePageRecyclerView.setAdapter(pageAdapter);
        pageAdapter.notifyDataSetChanged();
        progressDialog.dismiss();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        list = new ArrayList<>();
        list.clear();
        if (sliderModelList.size() != 0) {
            list.add(new HomePage(0, sliderModelList));
        }
        if (BannerAd != null) {
            list.add(new HomePage(1, "#000", BannerAd));
        }
        list.add(new HomePage(2, "Laptops"));
        list.add(new HomePage(2, "dress \uD83D\uDC57"));
        pageAdapter = new HomePageAdapter();
        pageAdapter.setHomePageList(list);
        HomePageRecyclerView.setAdapter(pageAdapter);
        pageAdapter.notifyDataSetChanged();
        progressDialog.dismiss();

    }

    @Override
    protected void onPause() throws RuntimeException {
        super.onPause();

        try {
            HomePageAdapter.categoryAdapter.getAdapter().stopListening();
        }catch (NullPointerException e){
            Toast.makeText(HomeActivity.this,""+e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }*/

    //to remove all data in ArrayList when app is stop or Destroy.
    @Override
    protected void onStop() {
        super.onStop();
        startService(new Intent(HomeActivity.this, onAppKilled.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startService(new Intent(HomeActivity.this, onAppKilled.class));
    }



}