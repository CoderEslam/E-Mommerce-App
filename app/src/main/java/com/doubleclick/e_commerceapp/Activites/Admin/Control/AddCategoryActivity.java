package com.doubleclick.e_commerceapp.Activites.Admin.Control;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.doubleclick.e_commerceapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCategoryActivity extends AppCompatActivity {

    private EditText NameCategory ;
    private Button AddCategory;
    private static final int GalleryPick = 2;
    private Uri ImageUri;
    private StorageReference ProductImagesRef;
    private String downloadImageUrl;
    private ImageView Icon;
    private ProgressDialog loadingBar;
    private Map<String, Object> map;
    private DatabaseReference ReferenceCategory;
    private Spinner mCategorySpinner;
    private String TypeCategory;
    private FirebaseFirestore firestoreCategory;
    private AutoCompleteTextView autoCompleteNameCategoty;
    private List<String> listCategory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        autoCompleteNameCategoty = findViewById(R.id.autoCompleteTextView);
        NameCategory = findViewById(R.id.NameCategory);
        AddCategory = findViewById(R.id.AddCategory);
        loadingBar = new ProgressDialog(this);
        listCategory = new ArrayList<>();
        Icon = findViewById(R.id.Icon);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.select_dialog_item,listCategory);
        autoCompleteNameCategoty.setThreshold(1);//will start working from first character
        autoCompleteNameCategoty.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        autoCompleteNameCategoty.setTextColor(Color.RED);
        firestoreCategory = FirebaseFirestore.getInstance();
        ReferenceCategory = FirebaseDatabase.getInstance().getReference().child("Category");
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("CategoryImage");
        map = new HashMap<>();



        AddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Name = autoCompleteNameCategoty.getText().toString();
                if (ImageUri != null && Name != null && !Name.isEmpty()) {
                    loadingBar.setTitle("Add New Product");
                    loadingBar.setMessage("Dear Admin, please wait while we are adding the new product.");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();


                    final StorageReference filePath = ProductImagesRef.child(ImageUri.getLastPathSegment() + ".jpg");
                    filePath.putFile(ImageUri);
                    filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadImageUrl = uri.toString();
                            UploadData(Name,downloadImageUrl);

                        }
                    });
                } else {
                    if (ImageUri == null) {
                        Toast.makeText(AddCategoryActivity.this, "Image not exist", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (NameCategory.getText().toString() == null || NameCategory.getText().equals("")) {
                        Toast.makeText(AddCategoryActivity.this, "Input Name", Toast.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        });


        Icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });
//        setupSpinner();
    }


    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_categoey, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mCategorySpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.Strip))) {
                        TypeCategory = "Strip";
                    } else if (selection.equals(getString(R.string.Banner))) {
                        TypeCategory = "Banner";
                    }
                }
//                Toast.makeText(CategoryActivity.this, "" + TypeAd, Toast.LENGTH_LONG).show();
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //No thing.
                TypeCategory = "Strip";
            }

        });
    }


    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }

    private void UploadData(String name,String Url) {
        map.put("CategoryName", name);
        map.put("CategoryImage", Url);
        firestoreCategory.collection("Category").document(name).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Void> task) {

            }
        });
//        ReferenceCategory.child(name).updateChildren(map);
        Icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_image_24));
        NameCategory.setText("");
        loadingBar.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {
            ImageUri = data.getData();
            Icon.setImageURI(ImageUri);

        }
    }

}