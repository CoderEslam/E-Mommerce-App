package com.doubleclick.e_commerceapp.FragmentsToUploadProductes.ClothesAndShoses;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.doubleclick.e_commerceapp.Activites.Admin.AdminAddNewProductActivity;
import com.doubleclick.e_commerceapp.Database.ViewHolder.ProductAdapter;
import com.doubleclick.e_commerceapp.Database.ViewModel.ProductViewModel;
import com.doubleclick.e_commerceapp.Model.Products;
import com.doubleclick.e_commerceapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import static android.app.Activity.RESULT_OK;
import static com.doubleclick.e_commerceapp.Activites.Admin.AdminAddNewProductActivity.AddNewProductButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClothesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClothesFragment extends Fragment {


    // TODO: Rename and change types of parameters
    private Spinner spinner_trademark;
    private ImageView pImage1, pImage2, pImage3, pImage4;
    private Uri imageUri1, imageUri2, imageUri3, imageUri4;
    private final int GalleryPick = 1;
    private String Description, PriceAfter,PriceBefore, Pname, saveCurrentDate, saveCurrentTime ,matrial;
    private CheckBox XS, S, M, L, XL, XXL, XXXL;
    private ImageView InputProductImage;
    private EditText InputProductName, InputProductDescription, InputProductPriceAfter, InputProductPriceBefore,PMatrial;
    private String productRandomKey, downloadImageUrl;
    private StorageReference ProductImagesRef;
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
    private FragmentManager mFragmentManager;

    public ClothesFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ClothesFragment newInstance(String param1, String param2) {
        ClothesFragment fragment = new ClothesFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clothes, container, false);
        spinner_trademark = view.findViewById(R.id.spinner_trademark);
        pImage1 = view.findViewById(R.id.pImage1);
        pImage2 = view.findViewById(R.id.pImage2);
        pImage3 = view.findViewById(R.id.pImage3);
        pImage4 = view.findViewById(R.id.pImage4);
        pImage1.setOnClickListener(v -> {
            OpenGallery();
        });
        pImage2.setOnClickListener(v -> {
            OpenGallery();
        });
        pImage3.setOnClickListener(v -> {
            OpenGallery();
        });
        pImage4.setOnClickListener(v -> {
            OpenGallery();
        });
        InputProductName = view.findViewById(R.id.PName);
        InputProductDescription = view.findViewById(R.id.Pdescription);
        InputProductPriceBefore = view.findViewById(R.id.priceBefore);
        InputProductPriceAfter = view.findViewById(R.id.priceAfter);
        PMatrial = view.findViewById(R.id.PMatrial);
        XS = view.findViewById(R.id.XS);
        S = view.findViewById(R.id.S);
        M = view.findViewById(R.id.M);
        L = view.findViewById(R.id.L);
        XL = view.findViewById(R.id.XL);
        XXL = view.findViewById(R.id.XXL);
        XXXL = view.findViewById(R.id.XXXL);
        if (!Edit) {
            HeadCategory = getActivity().getIntent().getExtras().getString("HeadCategory", "اخرى");
            ChildCategory = getActivity().getIntent().getExtras().getString("ChildCategory", "اخرى");
        }
        if (Edit) {
            getActivity().setTitle("Edit Item");
            editMode = true;
            AddNewProductButton.setText("Update..");
            mID = intent.getIntExtra(EXTRA_PRIMARY_ID, -1);
            InputProductName.setText(intent.getStringExtra(EXTRA_NAME));
            InputProductDescription.setText(intent.getStringExtra(EXTRA_DESCRIPTION));
            InputProductPriceAfter.setText(intent.getStringExtra(EXTRA_PRICE));
            downloadImageUrl = getActivity().getIntent().getStringExtra(EXTRA_IMAGE);
            Picasso.get().load(intent.getStringExtra(EXTRA_IMAGE)).placeholder(R.drawable.parson).into(InputProductImage);
            KeyId = getActivity().getIntent().getStringExtra(EXTRA_ID); //Push Id
            phone = getActivity().getIntent().getStringExtra(EXTRA_PHONE);
        }
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Images");
        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        UserID = mAuth.getCurrentUser().getUid();
        Edit = getActivity().getIntent().getBooleanExtra("Edit", false);
        productAdapter = new ProductAdapter();
        intent = getActivity().getIntent();
        loadingBar = new ProgressDialog(getContext());
        mProductViewModel = ViewModelProviders.of(getActivity()).get(ProductViewModel.class);

        AddNewProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
            }
        });
        setupSpinner();
        return view;
    }

    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.TrademarkCloses, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        spinner_trademark.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        spinner_trademark.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                trademark = selection;

            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //No thing.

            }

        });
    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {
            if (imageUri1 == null) {
                imageUri1 = data.getData();
                pImage1.setImageURI(imageUri1);
            } else if (imageUri2 == null) {
                imageUri2 = data.getData();
                pImage2.setImageURI(imageUri2);
            } else if (imageUri3 == null) {
                imageUri3 = data.getData();
                pImage3.setImageURI(imageUri3);
            } else if (imageUri4 == null) {
                imageUri4 = data.getData();
                pImage4.setImageURI(imageUri4);
            }

        }
    }

    private void ValidateProductData() {
        Description = InputProductDescription.getText().toString();
        PriceAfter = InputProductPriceAfter.getText().toString();
        PriceBefore = InputProductPriceBefore.getText().toString();
        Pname = InputProductName.getText().toString();
        matrial = PMatrial.getText().toString();
        if (!Edit) {
            if (imageUri1 == null) {
                Toast.makeText(getContext(), "Product image is mandatory...", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(Description)) {
                Toast.makeText(getContext(), "Please write product description...", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(PriceAfter)) {
                Toast.makeText(getContext(), "Please write product Price...", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(Pname)) {
                Toast.makeText(getContext(), "Please write product name...", Toast.LENGTH_SHORT).show();
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
        if (!Edit || imageUri1 != null) { //for new Item

            final StorageReference filePath = ProductImagesRef.child(imageUri1.getLastPathSegment() + productRandomKey + ".jpg");
            final UploadTask uploadTask = filePath.putFile(imageUri1);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(Exception e) {
                    String message = e.toString();
                    Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

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

                                Toast.makeText(getContext(), "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();

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
        }
        String ProductId = saveCurrentTime + UserID; //KeyID ==> push_Id
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pname", Pname);
        productMap.put("description", Description);
        productMap.put("image", downloadImageUrl);
        productMap.put("priceAfter", PriceAfter);
        productMap.put("priceBefore", PriceBefore);
        productMap.put("HeadCategory", HeadCategory);
        productMap.put("ChildCategory", ChildCategory);
        productMap.put("pid", ProductId);
        productMap.put("idAdmin", UserID);
        productMap.put("trademark", trademark);
        productMap.put("time", saveCurrentTime);
        productMap.put("rate1", "0");
        productMap.put("rate2", "0");
        productMap.put("rate3", "0");
        productMap.put("rate4", "0");
        productMap.put("rate5", "0");
        Products products = new Products(ChildCategory,ProductId,saveCurrentDate,saveCurrentTime,UserID,HeadCategory,Pname,Description,PriceBefore,PriceAfter,trademark,"","","","",XS.isChecked(),S.isChecked(),M.isChecked(),L.isChecked(),XL.isChecked(), XXL.isChecked(), XXXL.isChecked(),matrial,"");
//        Products products = new Products(Pname, Description, downloadImageUrl, Price, HeadCategory, ChildCategory, ProductId, UserID, trademark, saveCurrentTime, "", "", "", "", "");
        if (Edit) {
            products.setId(mID);
            mProductViewModel.update(products);
            firestore.collection("Productes").document(ProductId).update(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        loadingBar.dismiss();
                        Toast.makeText(getContext(), "Product is added successfully..", Toast.LENGTH_SHORT).show();
                    } else {
                        loadingBar.dismiss();
                        String message = task.getException().toString();
                        Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getContext(), "Product is added successfully..", Toast.LENGTH_SHORT).show();
                    } else {
                        loadingBar.dismiss();
                        String message = task.getException().toString();
                        Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    class newThread extends Thread {

        private Uri ImageUri;

        public newThread(Uri imageUri) {
            ImageUri = imageUri;
        }

        @Override
        public void run() {
            super.run();
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
                        Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(getContext(), "Product Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

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

                                    Toast.makeText(getContext(), "got the Product image Url Successfully...", Toast.LENGTH_SHORT).show();

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
    }

}