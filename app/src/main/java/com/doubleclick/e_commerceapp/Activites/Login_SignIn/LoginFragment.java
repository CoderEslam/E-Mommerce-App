package com.doubleclick.e_commerceapp.Activites.Login_SignIn;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.doubleclick.e_commerceapp.Activites.Admin.AdminCategoryActivity;
import com.doubleclick.e_commerceapp.Activites.User.HomeActivity;
import com.doubleclick.e_commerceapp.Model.Admin;
import com.doubleclick.e_commerceapp.Model.Users;
import com.doubleclick.e_commerceapp.Prevalent.Prevalent;
import com.doubleclick.e_commerceapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import io.paperdb.Paper;


public class LoginFragment extends Fragment {



    private EditText InputEmailNumber, InputPassword;
    private Button LoginButton;
    private ImageView back;
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

    public LoginFragment() {
        // Required empty public constructor
    }


    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        back = view.findViewById(R.id.back);
        LoginButton = view.findViewById(R.id.login_btn);
        InputPassword = view.findViewById(R.id.login_password_input);
        InputEmailNumber = view.findViewById(R.id.login_phone_number_input);
//        AdminLink = view.findViewById(R.id.admin_panel_link);

        chkBoxI_Am_Admin = view.findViewById(R.id.AmAdmin);
//        NotAdminLink = findViewById(R.id.not_admin_panel_link);
        loadingBar = new ProgressDialog(getContext());
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser();
        RootRef = FirebaseDatabase.getInstance().getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();
        chkBoxRememberMe = view.findViewById(R.id.remember_me_chkb);
        IamDriver = view.findViewById(R.id.IamDriver);
        Paper.init(getContext());

        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });
        back.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), MainActivity.class);
            startActivity(intent);
        });

        return view;


    }

    private void LoginUser() {

        Email = InputEmailNumber.getText().toString();
        password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(Email)) {
            Toast.makeText(getContext(), "Please write your phone number...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Please write your password...", Toast.LENGTH_SHORT).show();
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
                            .addOnSuccessListener(documentSnapshot -> {
                                Users users = documentSnapshot.toObject(Users.class);
                                try {
                                    if (Objects.requireNonNull(users).getEmail() != null) {
                                        Intent intent = new Intent(getContext(), HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        RootRef = FirebaseDatabase.getInstance().getReference().child("Drivers").child(UserID);
                                        Toast.makeText(getContext(), "Welcome "+users.getName()+", you are logged in Successfully...", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();

                                    }
                                } catch (NullPointerException e) {
                                    Toast.makeText(getContext(), "Error Login ," + e.getMessage(), Toast.LENGTH_SHORT).show();

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
                                    .addOnSuccessListener(documentSnapshot -> {
                                        Admin admin = documentSnapshot.toObject(Admin.class);
                                        try {
                                            if (admin.getEmail() != null) {
//                                                    Toast.makeText(LoginActivity.this, "" + admin.toString(), Toast.LENGTH_SHORT).show();
                                                Toast.makeText(getContext(), "Welcome "+admin.getName()+", you are logged in Successfully...", Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                                Intent intent = new Intent(getContext(), AdminCategoryActivity.class);
                                                startActivity(intent);
                                            }
                                        } catch (NullPointerException e) {
                                            Toast.makeText(getContext(), "Error Login ," + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        }
                    });
        }
        if (parentDbName.equals("Users")) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            UserID = mAuth.getCurrentUser().getUid();
                            firebaseFirestore.collection("Users")
                                    .document(UserID)
                                    .get()
                                    .addOnSuccessListener(documentSnapshot -> {
                                        Users users = documentSnapshot.toObject(Users.class);
                                        try {
                                            if (users.getEmail() != null) {
//                                        Toast.makeText(LoginActivity.this, ""+users.toString(), Toast.LENGTH_SHORT).show();
                                                Toast.makeText(getContext(), "logged in Successfully ,Mr " + users.getName(), Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                                Intent intent = new Intent(getContext(), HomeActivity.class);
                                                intent.putExtra("email", email);
                                                Prevalent.currentOnlineUser = users;
                                                startActivity(intent);
                                            }
                                        } catch (NullPointerException e) {
                                            Toast.makeText(getContext(), "Error Login , " + e.getMessage(), Toast.LENGTH_SHORT).show();

                                        }

                                    });
                        }
                    });
        }
    }


}