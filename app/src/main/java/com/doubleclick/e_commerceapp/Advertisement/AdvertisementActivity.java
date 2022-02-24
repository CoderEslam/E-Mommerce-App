package com.doubleclick.e_commerceapp.Advertisement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.doubleclick.e_commerceapp.Model.Advertisement;
import com.doubleclick.e_commerceapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.doubleclick.e_commerceapp.Advertisement.QDB_Advertisement.advertisements;

public class AdvertisementActivity extends AppCompatActivity {

    private static final int GalleryPick = 3;
    private Uri ImageUri;
    private ImageView IconAd;
    private Spinner mAdSpinner;
    private String TypeAd;
    private RecyclerView RecyclerAd;
    private Button UplaodAd;
    private Map<String, Object> map;
    private StorageReference storageAd;
    private String downloadImageUrl;
    private ProgressDialog loadingBar;
    private StorageTask uploadTask;
    private FirebaseFirestore firestoreAdvertis;
    private String saveCurrentDate, saveCurrentTime, RandomKey;
    private AdvertisementAdapter advertisementAdapter;
    private QDB_Advertisement qdb_advertisement;
    private Calendar calendar;


    //https://firebase.google.com/docs/cloud-messaging/android/first-message

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertisement);
        IconAd = findViewById(R.id.ImageAd);
        RecyclerAd = findViewById(R.id.RecyclerAd);
        UplaodAd = findViewById(R.id.UploadAd);
        storageAd = FirebaseStorage.getInstance().getReference().child("Advertisement");
        firestoreAdvertis = FirebaseFirestore.getInstance();


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        RecyclerAd.setLayoutManager(linearLayoutManager);
        advertisementAdapter = new AdvertisementAdapter();
        RecyclerAd.setAdapter(advertisementAdapter);
        qdb_advertisement = new QDB_Advertisement();
        getAd();
        map = new HashMap<>();
        calendar = Calendar.getInstance();
        mAdSpinner = findViewById(R.id.spinner_Ad);
        setupSpinner();
        IconAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallery();
            }
        });

        UplaodAd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UploadImage();
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                int pos = viewHolder.getAdapterPosition();
                Advertisement advertisement = advertisements.get(pos);
                firestoreAdvertis.collection("Advertisement")
                        .document("Strip")
                        .collection("Strips").document(advertisement.getRandomKey())
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AdvertisementActivity.this, "Deleted", Toast.LENGTH_LONG).show();
                                advertisements.clear();
                                getAd();
                                advertisementAdapter.notifyDataSetChanged();
                            }
                        });

            }
        }).attachToRecyclerView(RecyclerAd);

    }

    private void getAd() {
        if (advertisements.size() == 0) {
            qdb_advertisement.loadAdvertisement(advertisementAdapter, AdvertisementActivity.this);
        } else {
            advertisementAdapter.notifyDataSetChanged();
        }
    }

    private void OpenGallery() {
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
            Toast.makeText(AdvertisementActivity.this, "Image Uri = " + ImageUri.toString(), Toast.LENGTH_LONG).show();
            IconAd.setImageURI(ImageUri);
        }
    }


    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_Ad, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        mAdSpinner.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        mAdSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.Strip))) {
                        TypeAd = "Strip";
                    } else if (selection.equals(getString(R.string.Banner))) {
                        TypeAd = "Banner";
                    }
                }
                Toast.makeText(AdvertisementActivity.this, "" + TypeAd, Toast.LENGTH_LONG).show();
            }

            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //No thing.
                TypeAd = "Strip";
            }

        });
    }

    private void UploadImage() {
        if (ImageUri != null) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("MMM,dd,yyyy");
            saveCurrentDate = currentDate.format(calendar.getTime());
            @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
            saveCurrentTime = currentTime.format(calendar.getTime());
            RandomKey = saveCurrentDate + saveCurrentTime;
            loadingBar = new ProgressDialog(this);
            loadingBar.setTitle("Uplaod Ad");
            loadingBar.setMessage("Please wait.......");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();
            final StorageReference fileRef = storageAd.child(ImageUri.getLastPathSegment() + ".jpg");
            uploadTask = fileRef.putFile(ImageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull @NotNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull @NotNull Task<Uri> task) {

                    if (task.isSuccessful()) {
                        Uri downloadUrl = task.getResult();
//                        Toast.makeText(SettinsActivity.this, ""+downloadUrl, Toast.LENGTH_SHORT).show();
                        downloadImageUrl = downloadUrl.toString();
//                        Toast.makeText(AdvertisementActivity.this, "Image Url = " + downloadImageUrl, Toast.LENGTH_LONG).show();
                        UplaodAd(TypeAd, downloadImageUrl);
                    }

                }
            });
            fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
//                    downloadImageUrl = uri.toString();
//                    Toast.makeText(AdvertisementActivity.this, "Image Url = " + downloadImageUrl, Toast.LENGTH_LONG).show();
//                    UplaodAd(TypeAd, downloadImageUrl);
                }
            });
        } else {
            Toast.makeText(this, "image is not selected.", Toast.LENGTH_SHORT).show();
        }
    }

    private void UplaodAd(String typeAd, String downloadImageUrl) {
        map.put("TypeAd", typeAd);
        map.put("ImageAd", downloadImageUrl);
        map.put("RandomKey", RandomKey);
        if (typeAd == "Banner") {
            firestoreAdvertis.collection("Advertisement")
                    .document(typeAd)
                    .update(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            Toast.makeText(AdvertisementActivity.this, "Done Banner", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        if (typeAd == "Strip") {
            firestoreAdvertis.collection("Advertisement")
                    .document("Strip")
                    .collection("Strips")
                    .document(RandomKey)
                    .set(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<Void> task) {
                            advertisements.clear();
                            getAd();
                            Toast.makeText(AdvertisementActivity.this, "Done Strip", Toast.LENGTH_LONG).show();
                        }
                    });
        }
        loadingBar.dismiss();
    }
}