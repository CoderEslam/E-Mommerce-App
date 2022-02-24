package com.doubleclick.e_commerceapp.Activites.Login_SignIn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.cuberto.liquid_swipe.LiquidPager;
import com.doubleclick.e_commerceapp.Activites.Admin.AdminCategoryActivity;
import com.doubleclick.e_commerceapp.Activites.LoginActivity;
import com.doubleclick.e_commerceapp.Activites.Login_SignIn.Fargments.ViewPager;
import com.doubleclick.e_commerceapp.Activites.RegisterActivity;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import org.jetbrains.annotations.NotNull;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private ImageView joinNowButton, loginButton;
    private ProgressDialog loadingBar;
    private ImageView buyProducat, myApplicathion, backgraund;
    private DatabaseReference RootRef;
    private FirebaseAuth mAuth;
    private String UserId;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    ////////////////////////////////////////////////////
    LiquidPager pager;
    ViewPager viewPager;
    ////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        joinNowButton = findViewById(R.id.main_join_now_btn);
        loginButton = findViewById(R.id.main_login_btn);
        loadingBar = new ProgressDialog(this);
        myApplicathion = findViewById(R.id.myApplicathion);
        backgraund = findViewById(R.id.backgraund);
        buyProducat = findViewById(R.id.buyProducat);
        RootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        ////////////////////////////////////////////////////
        pager = findViewById(R.id.pager);
        viewPager = new ViewPager(getSupportFragmentManager(),1);
        pager.setAdapter(viewPager);
        /////////////////////////////////////////////////////
        Threads threads = new Threads();
        threads.start();


        Animation anim1 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.lefttoright);
        Animation anim2 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fromdowntoup);
        Animation anim3 = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fadein);
        backgraund.setAnimation(anim3);
        buyProducat.setAnimation(anim1);
        myApplicathion.setAnimation(anim2);


        Paper.init(this);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        joinNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        String UserEmailKey = Paper.book().read(Prevalent.UserEmailKey);
        String UserPasswordKey = Paper.book().read(Prevalent.UserPasswordKey);

        if (UserEmailKey != "" && UserPasswordKey != "") {
            if (!TextUtils.isEmpty(UserEmailKey) && !TextUtils.isEmpty(UserPasswordKey)) {
                AllowAccess(UserEmailKey, UserPasswordKey);

                loadingBar.setTitle("Already Logged in");
                loadingBar.setMessage("Please wait.....");
                loadingBar.setCanceledOnTouchOutside(false);
//                loadingBar.show();
            }
        }
    }


    private void AllowAccess(final String email, final String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@androidx.annotation.NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    RootRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot dataSnapshot) {
                            UserId = mAuth.getCurrentUser().getUid();
                            Users usersData = dataSnapshot.child("Users").child(UserId).getValue(Users.class);
                            Toast.makeText(MainActivity.this, "Please wait, you are already logged in...", Toast.LENGTH_SHORT).show();
//                            loadingBar.dismiss();
                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                            startActivity(intent);
                            finish();
                            Prevalent.currentOnlineUser = usersData;
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {
                        }
                    });

                } else {
                    loadingBar.dismiss();
                    Toast.makeText(MainActivity.this, "Password is incorrect.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    class Threads extends Thread{
        @Override
        public void run() {
            super.run();
            try {
                firebaseAuthListener = firebaseAuth -> {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        String ID = user.getUid();
                        FirebaseFirestore.getInstance().collection("Admins").document(ID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(DocumentSnapshot value, FirebaseFirestoreException error) {
                                if (value.exists()) {
                                    try {
                                        Admin admin = value.toObject(Admin.class);
                                        if (!admin.getEmail().isEmpty()) {
                                            Intent intent = new Intent(MainActivity.this, AdminCategoryActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }catch (NullPointerException e){

                                    }

                                }
                            }
                        });
                        FirebaseFirestore.getInstance().collection("Users").document(ID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                            @Override
                            public void onEvent(DocumentSnapshot value, FirebaseFirestoreException error) {
                                if (value.exists()) {
                                    try {
                                        Users users = value.toObject(Users.class);
                                        if (!users.getEmail().isEmpty()) {
                                            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }catch (NullPointerException e){
                                    }
                                }
                            }
                        });
                    }
                };
            }catch (NullPointerException e){

            }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            mAuth.addAuthStateListener(firebaseAuthListener);
        }catch (NullPointerException e){

        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            mAuth.removeAuthStateListener(firebaseAuthListener);
        }catch (NullPointerException e){

        }
    }
}