package com.doubleclick.e_commerceapp.Activites.Admin.Control;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.doubleclick.e_commerceapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EditOnAdminActivity extends AppCompatActivity {

    DatabaseReference referenceAdmin;

    private EditText ID_Admin, MaxNum;
    private Button Done,Enroll;
    private Spinner spinner_Admain;
    private String TypeCustomer;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth mAuth;
    private String ID;
    private  EditText EmailAdmin,PasswordAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_on_admin);

        referenceAdmin = FirebaseDatabase.getInstance().getReference().child("Admins");
        firebaseFirestore = FirebaseFirestore.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        EmailAdmin = findViewById(R.id.EmailAdmin);
        PasswordAdmin = findViewById(R.id.passwordAdmin);
        ID_Admin = findViewById(R.id.ID_Admin);
        MaxNum = findViewById(R.id.MaxNum);
        Done = findViewById(R.id.Done);
        Enroll = findViewById(R.id.Enroll);
        spinner_Admain = findViewById(R.id.spinner_Admain);
        setupSpinner();
        Map<String,Object> map = new HashMap<>();
        Enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signInWithEmailAndPassword(EmailAdmin.getText().toString(),PasswordAdmin.getText().toString())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(EditOnAdminActivity.this,"Login Successfully",Toast.LENGTH_LONG).show();
                            ID  = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                            Map<String,String> mapAdmin = new HashMap<>();
                            mapAdmin.put("ID",ID);
                            if (TypeCustomer=="Seller"){
                                mapAdmin.put("max",MaxNum.getText().toString());
                            }
                            firebaseFirestore.collection(TypeCustomer).document(ID).set(mapAdmin)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    ID_Admin.setText(""+ID);
                                    MaxNum.setText("");
                                }
                            });

                        }
                    }
                });
            }
        });
        Done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ID = ID_Admin.getText().toString();
                String Max = MaxNum.getText().toString();
                map.put("max",Max);
                referenceAdmin.child(ID).updateChildren(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        ID_Admin.setText("");
                        MaxNum.setText("");
                        Toast.makeText(EditOnAdminActivity.this,"Done",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }

    private void setupSpinner() {
        // Create adapter for spinner. The list options are from the String array it will use
        // the spinner will use the default layout
        ArrayAdapter genderSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_Ad, android.R.layout.simple_spinner_item);

        // Specify dropdown layout style - simple list view with 1 item per line
        genderSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        // Apply the adapter to the spinner
        spinner_Admain.setAdapter(genderSpinnerAdapter);

        // Set the integer mSelected to the constant values
        spinner_Admain.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.Driver))) {
                        TypeCustomer = "Driver";
                    } else if (selection.equals(getString(R.string.Seller))) {
                        TypeCustomer = "Seller";
                    }
                }
                Toast.makeText(EditOnAdminActivity.this, "" + TypeCustomer, Toast.LENGTH_LONG).show();
            }
            // Because AdapterView is an abstract class, onNothingSelected must be defined
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //No thing.
                TypeCustomer = "Seller";
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.add_admin, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.addDriver) {
            Intent intent = new Intent(EditOnAdminActivity.this, LoginDriverActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.addSeller) {
            Intent intent = new Intent(EditOnAdminActivity.this, LoginSellerActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}