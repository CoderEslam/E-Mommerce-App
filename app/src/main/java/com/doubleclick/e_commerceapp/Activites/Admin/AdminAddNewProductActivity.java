package com.doubleclick.e_commerceapp.Activites.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.doubleclick.e_commerceapp.Activites.User.HomeActivity;
import com.doubleclick.e_commerceapp.Advertisement.AdvertisementActivity;
import com.doubleclick.e_commerceapp.Database.ViewHolder.ProductAdapter;
import com.doubleclick.e_commerceapp.Database.ViewModel.ProductViewModel;
import com.doubleclick.e_commerceapp.FragmentsToUploadProductes.ClothesAndShoses.ClothesFragment;
import com.doubleclick.e_commerceapp.Model.Admin;
import com.doubleclick.e_commerceapp.Model.Products;
import com.doubleclick.e_commerceapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminAddNewProductActivity extends AppCompatActivity {

    private String CategoryName, Description, Price, Pname, saveCurrentDate, saveCurrentTime;
    public static Button AddNewProductButton;
    private ImageView InputProductImage;
    private EditText InputProductName, InputProductDescription, InputProductPrice;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String productRandomKey, downloadImageUrl;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductsRef;
    private ProgressDialog loadingBar;
    private LinearLayout UploadImage;
    private String phone;
    private boolean editMode;
    private ProductViewModel mProductViewModel;
    private int mID;
    private Intent intent;
    public static final String EXTRA_ID = "com.doubleclick.e_commerceapp_ID";
    public static final String EXTRA_PRIMARY_ID = "com.doubleclick.e_commerceapp_PRIMARY_ID";
    public static final String EXTRA_IMAGE = "com.doubleclick.e_commerceapp_IMAGE";
    public static final String EXTRA_NAME = "com.doubleclick.e_commerceapp_NAME";
    public static final String EXTRA_PRICE = "com.doubleclick.e_commerceapp_PRICE";
    public static final String EXTRA_DESCRIPTION = "com.doubleclick.e_commerceapp_DESCRIPTION";
    public static final String EXTRA_ChildCATAGORY = "com.doubleclick.e_commerceapp_ChildCATAGORY";
    public static final String EXTRA_HeadCATAGORY = "com.doubleclick.e_commerceapp_HeadCATAGORY";
    public static final String EXTRA_PHONE = "com.doubleclick.e_commerceapp_PHONE";
    private boolean Edit;
    private String KeyId = null;
    public static int max, min;
    ProductAdapter productAdapter;
    private int recyclerSize;
    private FirebaseAuth mAuth;
    private String UserID;
    private String HeadCategory, ChildCategory;
    private FirebaseFirestore firestore;
    private String trademark = "";
    private View clothes , anther;
    private Spinner spinner_Determinder;
    private FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_product);
        if (!Edit) {
            HeadCategory = getIntent().getExtras().getString("HeadCategory", "اخرى");
            ChildCategory = getIntent().getExtras().getString("ChildCategory", "اخرى");
        }
        if (Edit) {
            setTitle("Edit Item");
            editMode = true;
            AddNewProductButton.setText("Update..");
            mID = intent.getIntExtra(EXTRA_PRIMARY_ID, -1);
            InputProductName.setText(intent.getStringExtra(EXTRA_NAME));
            InputProductDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            InputProductPrice.setText(intent.getStringExtra(EXTRA_PRICE));
            downloadImageUrl = getIntent().getStringExtra(EXTRA_IMAGE);
            Picasso.get().load(intent.getStringExtra(EXTRA_IMAGE)).placeholder(R.drawable.parson).into(InputProductImage);
            KeyId = getIntent().getStringExtra(EXTRA_ID); //Push Id
            phone = getIntent().getStringExtra(EXTRA_PHONE);
        }
        mFragmentManager = getSupportFragmentManager();
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        ProductsRef = FirebaseDatabase.getInstance().getReference().child("Products");
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        UserID = mAuth.getCurrentUser().getUid();
        Edit = getIntent().getBooleanExtra("Edit", false);
        productAdapter = new ProductAdapter();
        intent = getIntent();
        AddNewProductButton = findViewById(R.id.add_new_product);
        /*InputProductImage = findViewById(R.id.select_product_image);
        InputProductName = findViewById(R.id.product_name);
        InputProductDescription = findViewById(R.id.product_description);
        InputProductPrice = findViewById(R.id.product_price);
        loadingBar = new ProgressDialog(this);
        UploadImage = findViewById(R.id.UploadImage);
        mProductViewModel = ViewModelProviders.of(AdminAddNewProductActivity.this).get(ProductViewModel.class);


        InputProductImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });

        UploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });*/

        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
            }
        });
        if(ChildCategory.equals("بنطلونات") ||ChildCategory.equals("شورتات")||ChildCategory.equals("جيبات")||ChildCategory.equals("بلوزات,تيشيرتات,قمصان")){
            showFragment(new ClothesFragment(),false);
        }else {

        }

    }

   /* private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {
            ImageUri = data.getData();
            InputProductImage.setImageURI(ImageUri);
        }
    }*/

    private void ValidateProductData() {
        Description = InputProductDescription.getText().toString();
        Price = InputProductPrice.getText().toString();
        Pname = InputProductName.getText().toString();
        if (!Edit) {
            if (ImageUri == null) {
                Toast.makeText(this, "Product image is mandatory...", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(Description)) {
                Toast.makeText(this, "Please write product description...", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(Price)) {
                Toast.makeText(this, "Please write product Price...", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(Pname)) {
                Toast.makeText(this, "Please write product name...", Toast.LENGTH_SHORT).show();
            } else {
                StoreProductInformation();
            }
        }
        if (intent.hasExtra(EXTRA_PRIMARY_ID)) {
            StoreProductInformation();
        }

    }

    private void StoreProductInformation() {
        loadingBar.setTitle("Add New Product");
        loadingBar.setMessage("Dear Admin, please wait while we are adding the new product.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        saveCurrentTime = currentTime.format(calendar.getTime());
        productRandomKey = saveCurrentDate + saveCurrentTime;
        if (!Edit || ImageUri != null) { //for new Item

            final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + productRandomKey + ".jpg");
            final UploadTask uploadTask = filePath.putFile(ImageUri);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    String message = e.toString();
                    Toast.makeText(AdminAddNewProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AdminAddNewProductActivity.this, "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                    Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            downloadImageUrl = filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(Task<Uri> task) {
                            if (task.isSuccessful()) {
                                downloadImageUrl = task.getResult().toString();

                                Toast.makeText(AdminAddNewProductActivity.this, "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();

                                SaveProductInfoToDatabase();
                            }
                        }
                    });
                }
            });
        } else {
            SaveProductInfoToDatabase();
        }
    }

    private void SaveProductInfoToDatabase() {

        if (!Edit) { // defulte value for Edit is false
            //intent.hasExtra(EXTRA_ID)
            KeyId = ProductsRef.push().getKey().toString();
        }
        String ProductId = saveCurrentTime + UserID; //KeyID ==> push_Id
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pname", Pname);
        productMap.put("description", Description);
        productMap.put("image", downloadImageUrl);
        productMap.put("price", Price);
        productMap.put("HeadCategory", HeadCategory);
        productMap.put("ChildCategory", ChildCategory);
        productMap.put("pid", ProductId);
        productMap.put("idAdmin", UserID);
        productMap.put("trademark", "");
        productMap.put("time", saveCurrentTime);
        productMap.put("rate1", "0");
        productMap.put("rate2", "0");
        productMap.put("rate3", "0");
        productMap.put("rate4", "0");
        productMap.put("rate5", "0");
        Products products = new Products(Pname, Description, downloadImageUrl, Price, HeadCategory, ChildCategory, ProductId, UserID, trademark, saveCurrentTime, "", "", "", "", "");
        if (Edit) {
            products.setId(mID);
            mProductViewModel.update(products);
            firestore.collection("Productes").document(ProductId).update(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        loadingBar.dismiss();
                        Toast.makeText(AdminAddNewProductActivity.this, "Product is added successfully..", Toast.LENGTH_SHORT).show();
                    } else {
                        loadingBar.dismiss();
                        String message = task.getException().toString();
                        Toast.makeText(AdminAddNewProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            mProductViewModel.insert(products);
            firestore.collection("Productes").document(ProductId).set(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        loadingBar.dismiss();
                        Toast.makeText(AdminAddNewProductActivity.this, "Product is added successfully..", Toast.LENGTH_SHORT).show();
                    } else {
                        loadingBar.dismiss();
                        String message = task.getException().toString();
                        Toast.makeText(AdminAddNewProductActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }



    private void showFragment(Fragment fragment, boolean allowStateLoss) {
        try {
            FragmentManager fm = mFragmentManager;
            if (allowStateLoss) {

            } else {
                fm.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.UplaodProducts, fragment).commit();
            }
            fm.executePendingTransactions();
        }catch (IllegalArgumentException e){
            Log.e("AdminActivityAt365"," == "+e.getMessage());
            Toast.makeText(AdminAddNewProductActivity.this,""+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

}