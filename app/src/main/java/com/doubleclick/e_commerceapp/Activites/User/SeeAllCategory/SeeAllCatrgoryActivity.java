package com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.ExbendableList.Navigation.FragmentNavigationManager;
import com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.ExbendableList.Navigation.NavigationManager;
import com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.ExbendableList.adapter.ExpandableCategoryListAdapter;
import com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.ExbendableList.dataSource.ExpandableListDataSource;
import com.doubleclick.e_commerceapp.Model.Category;
import com.doubleclick.e_commerceapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SeeAllCatrgoryActivity extends AppCompatActivity {

    private DatabaseReference CategoryReference;
    List<Category> categoryList = new ArrayList<>();
    SeeAllCategoresAdapter categoryAdapter;
//    private RecyclerView RecyclerCategory;
    private DrawerLayout mDrawerLayout;
    private ExpandableListView navExpandableList;
    private ExpandableCategoryListAdapter expandableCategoryListAdapter;
    private NavigationManager navigationManager;
    private List<String> mExpandableListTitle;
    private Map<String, List<String>> mExpandableListData;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private String[] items;
    private Toolbar category_toolbar;
    private String selectedItem = "";
    private FirebaseFirestore firestoreCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_catrgory);
        navExpandableList  = findViewById(R.id.navExpandableList);
        mDrawerLayout = findViewById(R.id.drawer_layout_category);
        navigationManager = FragmentNavigationManager.obtain(this);
        mActivityTitle = getTitle().toString();
        mExpandableListData = ExpandableListDataSource.getData(this);
//        Toast.makeText(SeeAllCatrgoryActivity.this,"Size = "+mExpandableListData.size(),Toast.LENGTH_LONG).show();
        mExpandableListTitle = new ArrayList(mExpandableListData.keySet());
        firestoreCategory = FirebaseFirestore.getInstance();

        initItems();


        addDrawerItems();
        setupDrawer();




//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(AllCatrgoryActivity.this, mDrawerLayout, category_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        mDrawerLayout.addDrawerListener(toggle);
//        toggle.syncState();

//        NavigationView navigationView = findViewById(R.id.nav_view_category);
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
//                int id = item.getItemId();
//                Toast.makeText(AllCatrgoryActivity.this,""+id,Toast.LENGTH_LONG).show();
//                return false;
//            }
//        });

//        new LoadDataAsyncTask(CategoryReference).execute();

        category_toolbar = findViewById(R.id.category_toolbar);
        category_toolbar.setTitle("Category");
        setSupportActionBar(category_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void initItems() {
        items = getResources().getStringArray(R.array.array_categoey);
    }

    private void addDrawerItems() {
        expandableCategoryListAdapter = new ExpandableCategoryListAdapter(this, mExpandableListTitle, mExpandableListData);
        navExpandableList.setAdapter(expandableCategoryListAdapter);
        navExpandableList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                getSupportActionBar().setTitle(mExpandableListTitle.get(groupPosition).toString());
            }
        });

        navExpandableList.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                // getSupportActionBar().setTitle(R.string.film_genres);
            }
        });

        navExpandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                selectedItem = ((List) (mExpandableListData.get(mExpandableListTitle.get(groupPosition)))).get(childPosition).toString();
                getSupportActionBar().setTitle(selectedItem);
                String HeadCategory = navExpandableList.getExpandableListAdapter().getGroup(groupPosition).toString();
                Toast.makeText(SeeAllCatrgoryActivity.this,""+selectedItem,Toast.LENGTH_LONG).show();
                //////here....
                navigationManager.showCategoty(HeadCategory,selectedItem);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle(R.string.film_genres);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(selectedItem);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerToggle.setDrawerSlideAnimationEnabled(true);
        mDrawerToggle.setDrawerArrowDrawable((DrawerArrowDrawable) getResources().getDrawable(R.drawable.adress));
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}