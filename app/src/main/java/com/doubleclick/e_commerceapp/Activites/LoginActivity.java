package com.doubleclick.e_commerceapp.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.doubleclick.e_commerceapp.Activites.Admin.AdminCategoryActivity;
import com.doubleclick.e_commerceapp.Activites.Login_SignIn.MainActivity;
import com.doubleclick.e_commerceapp.Activites.User.HomeActivity;
import com.doubleclick.e_commerceapp.Model.Admin;
import com.doubleclick.e_commerceapp.Model.Users;
import com.doubleclick.e_commerceapp.Prevalent.Prevalent;
import com.doubleclick.e_commerceapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private EditText InputEmailNumber, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;
    //    private TextView AdminLink, NotAdminLink;
    private String parentDbName = "Users";
    private CheckBox chkBoxRememberMe, chkBoxI_Am_Admin, IamDriver;
    private String Email, password;
    private FirebaseAuth mAuth;
    private FirebaseUser userId;
    private String UserID;
    private DatabaseReference RootRef;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        LoginButton = findViewById(R.id.login_btn);
        InputPassword = findViewById(R.id.login_password_input);
        InputEmailNumber = findViewById(R.id.login_phone_number_input);
//        AdminLink = findViewById(R.id.admin_panel_link);

        chkBoxI_Am_Admin = findViewById(R.id.AmAdmin);
//        NotAdminLink = findViewById(R.id.not_admin_panel_link);
        loadingBar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser();
        RootRef = FirebaseDatabase.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        chkBoxRememberMe = findViewById(R.id.remember_me_chkb);
        IamDriver = findViewById(R.id.IamDriver);
        Paper.init(this);


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });
    }


    private void LoginUser() {
        Email = InputEmailNumber.getText().toString();
        password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(this, "Please write your phone number...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please write your password...", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Login Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();


            AllowAccessToAccount(Email, password);
        }
    }


    private void AllowAccessToAccount(final String email, final String password) {
        if (chkBoxRememberMe.isChecked()) {//to save current user in paper.
            Paper.book().write(Prevalent.UserEmailKey, email);
            Paper.book().write(Prevalent.UserPasswordKey, password);
        }
        if (IamDriver.isChecked()) {
            parentDbName = "Drivers";
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(Task<AuthResult> task) {
                    UserID = mAuth.getCurrentUser().getUid();
                    firebaseFirestore.collection(parentDbName)
                            .document(UserID)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    Users users = documentSnapshot.toObject(Users.class);
                                    try {
                                        if (Objects.requireNonNull(users).getEmail() != null) {
                                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(intent);
                                            RootRef = FirebaseDatabase.getInstance().getReference().child("Drivers").child(UserID);
                                            Toast.makeText(LoginActivity.this, "Welcome "+users.getName()+", you are logged in Successfully...", Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();
                                            finish();
                                        }
                                    } catch (NullPointerException e) {
                                        Toast.makeText(LoginActivity.this, "Error Login ," + e.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            });
        }
        if (chkBoxI_Am_Admin.isChecked()) {
            LoginButton.setText("Login");
            parentDbName = "Admins";
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            UserID = mAuth.getCurrentUser().getUid();
                            firebaseFirestore.collection("Admins")
                                    .document(UserID)
                                    .get()
                                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            Admin admin = documentSnapshot.toObject(Admin.class);
                                            try {
                                                if (admin.getEmail() != null) {
//                                                    Toast.makeText(LoginActivity.this, "" + admin.toString(), Toast.LENGTH_SHORT).show();
                                                    Toast.makeText(LoginActivity.this, "Welcome "+admin.getName()+", you are logged in Successfully...", Toast.LENGTH_SHORT).show();
                                                    loadingBar.dismiss();
                                                    Intent intent = new Intent(LoginActivity.this, AdminCategoryActivity.class);
                                                    startActivity(intent);
                                                }
                                            } catch (NullPointerException e) {
                                                Toast.makeText(LoginActivity.this, "Error Login ," + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }
                    });
        }
        if (parentDbName.equals("Users")) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                UserID = mAuth.getCurrentUser().getUid();
                                firebaseFirestore.collection("Users")
                                        .document(UserID)
                                        .get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                Users users = documentSnapshot.toObject(Users.class);
                                                try {
                                                    if (users.getEmail() != null) {
//                                        Toast.makeText(LoginActivity.this, ""+users.toString(), Toast.LENGTH_SHORT).show();
                                                        Toast.makeText(LoginActivity.this, "logged in Successfully ,Mr " + users.getName(), Toast.LENGTH_SHORT).show();
                                                        loadingBar.dismiss();
                                                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                                        intent.putExtra("email", email);
                                                        Prevalent.currentOnlineUser = users;
                                                        startActivity(intent);
                                                    }
                                                } catch (NullPointerException e) {
                                                    Toast.makeText(LoginActivity.this, "Error Login , " + e.getMessage(), Toast.LENGTH_SHORT).show();

                                                }

                                            }
                                        });
                            }
                        }
                    });
        }
    }

    public void back(View view) {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);

    }
}