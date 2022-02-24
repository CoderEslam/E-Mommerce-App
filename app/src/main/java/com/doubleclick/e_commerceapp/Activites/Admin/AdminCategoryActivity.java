package com.doubleclick.e_commerceapp.Activites.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.doubleclick.e_commerceapp.Activites.Admin.Control.AddCategoryActivity;
import com.doubleclick.e_commerceapp.Activites.Admin.Control.EditOnAdminActivity;
import com.doubleclick.e_commerceapp.Activites.Login_SignIn.MainActivity;
import com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.ExbendableList.adapter.ExpandableCategoryListAdapter;
import com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.ExbendableList.dataSource.ExpandableListDataSource;
import com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.SeeAllCategoresAdapter;
import com.doubleclick.e_commerceapp.Advertisement.AdvertisementActivity;
import com.doubleclick.e_commerceapp.DeliveryDrivers.SellerMapActivity;
import com.doubleclick.e_commerceapp.Model.Category;
import com.doubleclick.e_commerceapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AdminCategoryActivity extends AppCompatActivity {

//    private ImageView tShirts, sportsTShirts, femaleDresses, sweathers;
//    private ImageView glasses, hatsCaps, walletsBagsPurses, shoes;
//    private ImageView headPhonesHandFree, Laptops, watches, mobilePhones;

    private Button View_my_Product, CheckOrdersBtn;
    public static String UserID;
    private RecyclerView AdminCategory;
    List<Category> categoryList = new ArrayList<>();
    private SeeAllCategoresAdapter categoryAdapter;
    private FirebaseAuth mAuth;
    private ExpandableListView extentedCategory;
    private Map<String, List<String>> mExpandableListData;
    private List<String> mExpandableListTitle;
    private ExpandableCategoryListAdapter expandableCategoryListAdapter;
    private String selectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_category);

        View_my_Product = findViewById(R.id.View_my_Product);
        CheckOrdersBtn = findViewById(R.id.check_orders_btn);
        AdminCategory = findViewById(R.id.AdminCategory);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        AdminCategory.setLayoutManager(linearLayoutManager);
        DatabaseReference CategoryReference = FirebaseDatabase.getInstance().getReference().child("Category");
        extentedCategory = findViewById(R.id.extentedCategory);
        mAuth = FirebaseAuth.getInstance();
        UserID = getIntent().getStringExtra("UserID");
        UserID = mAuth.getCurrentUser().getUid();
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        mExpandableListData = ExpandableListDataSource.getData(this);
        mExpandableListTitle = new ArrayList(mExpandableListData.keySet());
        expandableCategoryListAdapter = new ExpandableCategoryListAdapter(this, mExpandableListTitle, mExpandableListData);
        extentedCategory.setAdapter(expandableCategoryListAdapter);

        View_my_Product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, ViewProductItem.class);
                intent.putExtra("UserID", UserID);
                startActivity(intent);
            }
        });

        CheckOrdersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminCategoryActivity.this, AdminNewOrdersActivity.class);
                startActivity(intent);

            }
        });

        extentedCategory.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                selectedItem = ((List) (mExpandableListData.get(mExpandableListTitle.get(groupPosition)))).get(childPosition).toString();
                String HeadCategory = extentedCategory.getExpandableListAdapter().getGroup(groupPosition).toString();
                getSupportActionBar().setTitle(selectedItem);
                Intent intent = new Intent(AdminCategoryActivity.this, AdminAddNewProductActivity.class);
                intent.putExtra("HeadCategory", HeadCategory);
                intent.putExtra("ChildCategory", selectedItem);
                startActivity(intent);
                return true;
            }
        });

//        new LoadDataAsyncTask(CategoryReference).execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_on, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addCategory) {
            Intent intent = new Intent(AdminCategoryActivity.this, AddCategoryActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.EditOnAdmin) {
            Intent intent = new Intent(AdminCategoryActivity.this, EditOnAdminActivity.class);
            startActivity(intent);
        }
        if(id==R.id.Advertisement){
            Intent intent = new Intent(AdminCategoryActivity.this, AdvertisementActivity.class);
            startActivity(intent);
        }
        if (id == R.id.logout_btn) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(AdminCategoryActivity.this, MainActivity.class);
            intent.putExtra("UserID", UserID);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        if(id == R.id.callCar){
            Intent intent = new Intent(AdminCategoryActivity.this, SellerMapActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


}