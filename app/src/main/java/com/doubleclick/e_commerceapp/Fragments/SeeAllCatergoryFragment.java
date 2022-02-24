package com.doubleclick.e_commerceapp.Fragments;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ExpandableListView;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.doubleclick.e_commerceapp.Activites.User.HomeActivity;
import com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.ExbendableList.Fragments.ShowCategoryFragment;
import com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.ExbendableList.Navigation.FragmentNavigationManager;
import com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.ExbendableList.Navigation.NavigationManager;
import com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.ExbendableList.adapter.ExpandableCategoryListAdapter;
import com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.ExbendableList.dataSource.ExpandableListDataSource;
import com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.SeeAllCategoresAdapter;
import com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.SeeAllCatrgoryActivity;
import com.doubleclick.e_commerceapp.Model.Category;
import com.doubleclick.e_commerceapp.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.ExbendableList.Fragments.QurieChildCategory.productsListChildren;
import static com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.ExbendableList.Fragments.ShowCategoryFragment.loadproductsListChildren;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SeeAllCatergoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeeAllCatergoryFragment extends Fragment implements NavigationManager {

    private DatabaseReference CategoryReference;
    List<Category> categoryList = new ArrayList<>();
    SeeAllCategoresAdapter categoryAdapter;
    //    private RecyclerView RecyclerCategory;
    private DrawerLayout mDrawerLayout;
    private ExpandableListView navExpandableList;
    private ExpandableCategoryListAdapter expandableCategoryListAdapter;
    private List<String> mExpandableListTitle;
    private Map<String, List<String>> mExpandableListData;
    private ActionBarDrawerToggle mDrawerToggle;
    private String mActivityTitle;
    private String[] items;
    private Toolbar category_toolbar;
    private String selectedItem = "";
    private FirebaseFirestore firestoreCategory;
    private FragmentManager mFragmentManager;
    private ImageView openDrawer;
    private boolean isOpen = false;
    private TextView TheTitle;
    public static GridView GridViewShowAll;
    public static AppBarLayout appBarCategory;

    public SeeAllCatergoryFragment() {
        // Required empty public constructor
    }


    public static SeeAllCatergoryFragment newInstance(String param1, String param2) {
        SeeAllCatergoryFragment fragment = new SeeAllCatergoryFragment();
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
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        navExpandableList = view.findViewById(R.id.navExpandableList);
        mDrawerLayout = view.findViewById(R.id.drawer_layout_category);
        mFragmentManager = getChildFragmentManager();
        mActivityTitle = getActivity().getTitle().toString();
        mExpandableListData = ExpandableListDataSource.getData(getContext());
        mExpandableListTitle = new ArrayList(mExpandableListData.keySet());
        firestoreCategory = FirebaseFirestore.getInstance();
        CategoryReference = FirebaseDatabase.getInstance().getReference().child("Category");
        category_toolbar = view.findViewById(R.id.category_toolbar);
        GridViewShowAll = view.findViewById(R.id.GridViewShowAll);
        appBarCategory = view.findViewById(R.id.appBarCategory);
        category_toolbar.setTitle("Category");
        openDrawer = view.findViewById(R.id.openDrawer);
        TheTitle = view.findViewById(R.id.TheTitle);
        ((AppCompatActivity) getContext()).setSupportActionBar(category_toolbar);
        /*((AppCompatActivity)getContext()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity)getContext()).getSupportActionBar().setHomeButtonEnabled(true);*/

        openDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOpen) {
                   OnCloseDrawer();
                } else {
                   OnOpenDrawer();
                }
            }
        });

        return view;
    }

    private void OnOpenDrawer() {
        // To open Drawer
        isOpen = true;
        openDrawer.animate().rotation(180).start();
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.lefttoright);
        navExpandableList.setAnimation(animation);
        navExpandableList.setVisibility(View.VISIBLE);
    }

    private void OnCloseDrawer() {
        // To Close Drawer
        isOpen = false;
        openDrawer.animate().rotation(0).start();
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.righttoleft);
        navExpandableList.setAnimation(animation);
        navExpandableList.setVisibility(View.GONE);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initItems();
        addDrawerItems();
        setupDrawer();
    }

    private void initItems() {
        items = getResources().getStringArray(R.array.array_categoey);
    }

    private void addDrawerItems() {
        expandableCategoryListAdapter = new ExpandableCategoryListAdapter(getContext(), mExpandableListTitle, mExpandableListData);
        navExpandableList.setAdapter(expandableCategoryListAdapter);
        navExpandableList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(mExpandableListTitle.get(groupPosition).toString());
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
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                selectedItem = ((List) (mExpandableListData.get(mExpandableListTitle.get(groupPosition)))).get(childPosition).toString();
                String HeadCategory = navExpandableList.getExpandableListAdapter().getGroup(groupPosition).toString();
                TheTitle.setText(HeadCategory+":"+selectedItem);
                productsListChildren.clear();
                Toast.makeText(getContext(), "" + selectedItem, Toast.LENGTH_LONG).show();
                //////here....
                GridViewShowAll.setVisibility(View.GONE);
                showCategoty(HeadCategory,selectedItem); // Error
                OnCloseDrawer();
//                mDrawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                ((AppCompatActivity) getContext()).getSupportActionBar().setTitle(R.string.film_genres);
                ((AppCompatActivity) getContext()).invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(selectedItem);
                ((AppCompatActivity) getActivity()).invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

//        mDrawerToggle.setDrawerIndicatorEnabled(true);
//        mDrawerToggle.setDrawerSlideAnimationEnabled(true);
//        mDrawerToggle.setDrawerArrowDrawable((DrawerArrowDrawable) getContext().getResources().getDrawable(R.drawable.adress));
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    @Override
    public void onStart() {
        super.onStart();
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull @NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        int id = item.getItemId();

        // Activate the navigation drawer toggle
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showCategoty(String Head, String child) {
        showFragment(ShowCategoryFragment.newInstance(Head,child), false);
    }

    private void showFragment(Fragment fragment, boolean allowStateLoss) {
        FragmentManager fm = mFragmentManager;
        @SuppressLint("CommitTransaction")
        FragmentTransaction ft = fm.beginTransaction().replace(R.id.Categorycontainer, fragment);

        ft.addToBackStack(null);
        if (allowStateLoss) { // || !BuildConfig.DEBUG) {
            ft.commitAllowingStateLoss();
        } else {
            ft.commit();
        }
        fm.executePendingTransactions();
    }



}