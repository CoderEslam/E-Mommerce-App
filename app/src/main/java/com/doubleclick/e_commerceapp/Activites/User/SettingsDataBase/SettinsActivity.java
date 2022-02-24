package com.doubleclick.e_commerceapp.Activites.User.SettingsDataBase;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.doubleclick.e_commerceapp.Activites.User.HomeActivity;
import com.doubleclick.e_commerceapp.Activites.User.UserRecentOrder.FireBaseRecentOrder;
import com.doubleclick.e_commerceapp.Model.Users;
import com.doubleclick.e_commerceapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettinsActivity extends AppCompatActivity {

    private CircleImageView profileImageView, AcountSettingbtn;
    private EditText fullNameEditText, userPhoneEditText, addressEditText;
    private TextView closeTextBtn, saveTextButton;
    private TextView AcountName, AcountEmail;
    private Uri imageUri;
    private String myUrl = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePrictureRef;
    private String checker = "";
    private final int GalleryPick = 1;
    private RecyclerView RecyclerRecentOrder;
    private FireBaseRecentOrder fireBaseRecentOrder;
    private FirebaseAuth mAuth;
    private String UserId;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settins);
        storageProfilePrictureRef = FirebaseStorage.getInstance().getReference().child("Profile pictures");
        profileImageView = findViewById(R.id.AcountImage);
        closeTextBtn = findViewById(R.id.close_settings_btn);
        saveTextButton = findViewById(R.id.update_account_settings_btn);
        AcountSettingbtn = findViewById(R.id.AcountSettingbtn);
        AcountName = findViewById(R.id.AcountName);
        AcountEmail = findViewById(R.id.AcountEmail);
        RecyclerRecentOrder = findViewById(R.id.RecyclerRecentOrder);
        mAuth = FirebaseAuth.getInstance();
        UserId = mAuth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        RecyclerRecentOrder.setLayoutManager(linearLayoutManager);
        fireBaseRecentOrder = new FireBaseRecentOrder();
        fireBaseRecentOrder.loadRecentOrder(SettinsActivity.this);
        RecyclerRecentOrder.setAdapter(fireBaseRecentOrder.getProductadapter());
        fireBaseRecentOrder.getProductadapter().startListening();
        AcountSettingbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                EditProfile();
            }
        });


        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checker = "clicked";
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GalleryPick);
            }
        });

        userInfoDisplay();

        closeTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        saveTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checker.equals("clicked")) {
                    userInfoSaved();
                } else {
                    updateOnlyUserInfo();
                }
            }
        });

    }

    private void EditProfile() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Edit Account");
        View view = LayoutInflater.from(SettinsActivity.this).inflate(R.layout.edit_profile_data, null);
        fullNameEditText = view.findViewById(R.id.settings_full_name);
        userPhoneEditText = view.findViewById(R.id.settings_phone_number);
        addressEditText = view.findViewById(R.id.settings_address);
        builder.setView(view);
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateOnlyUserInfo();
            }
        });

        builder.show();
    }

    private void updateOnlyUserInfo() {
        HashMap<String, Object> userMap = new HashMap<>();
        if (fullNameEditText.getText().toString() != null) {
            userMap.put("name", fullNameEditText.getText().toString());
        }
        if (addressEditText.getText().toString() != null) {
            userMap.put("address", addressEditText.getText().toString());
        }
        if (userPhoneEditText.getText().toString() != null) {
            userMap.put("phoneOrder", userPhoneEditText.getText().toString());
        }
        firebaseFirestore.collection("Users").document(UserId).update(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(SettinsActivity.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
            }
        });
        startActivity(new Intent(SettinsActivity.this, HomeActivity.class));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GalleryPick && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            profileImageView.setImageURI(data.getData());
        } else {
            Toast.makeText(this, "Error, Try Again.", Toast.LENGTH_SHORT).show();

            startActivity(new Intent(SettinsActivity.this, SettinsActivity.class));
            finish();
        }
    }

    private void userInfoSaved() {
        if (checker.equals("clicked")) {
            uploadImage();
        }
    }

    private void uploadImage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Update Profile");
        progressDialog.setMessage("Please wait, while we are updating your account information");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        if (imageUri != null) {
            final StorageReference fileRef = storageProfilePrictureRef
                    .child(UserId + ".jpg");

            uploadTask = fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUrl = task.getResult();
//                        Toast.makeText(SettinsActivity.this, ""+downloadUrl, Toast.LENGTH_SHORT).show();
                        myUrl = downloadUrl.toString();
                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("image", myUrl);
                        firebaseFirestore.collection("Users").document(UserId).update(userMap);
                        progressDialog.dismiss();
                        startActivity(new Intent(SettinsActivity.this, HomeActivity.class));
                        Toast.makeText(SettinsActivity.this, "Profile Info update successfully.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(SettinsActivity.this, "Error.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(this, "image is not selected.", Toast.LENGTH_SHORT).show();
        }
    }

    private void userInfoDisplay() {
        firebaseFirestore.collection("Users").document(UserId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Users users = documentSnapshot.toObject(Users.class);
                String image = users.getImage();
                String name = users.getName();
                String email = users.getEmail();
                String address = users.getAddress();
                String password = users.getPassword();
                String phoneOrder = users.getPhoneOrder();
                try {
                    AcountName.setText("" + name);
                    AcountEmail.setText(""+email);
                    try {
                        Picasso.get().load(image).placeholder(R.drawable.parson).into(profileImageView);
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(SettinsActivity.this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                    catch (NullPointerException e) {
                        Toast.makeText(SettinsActivity.this, "No name or Image " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                } catch (NullPointerException e) {
                    Toast.makeText(SettinsActivity.this, "No name or Image " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}