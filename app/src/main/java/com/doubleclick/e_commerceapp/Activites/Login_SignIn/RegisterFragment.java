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
import android.widget.EditText;
import android.widget.Toast;
import com.doubleclick.e_commerceapp.Activites.User.HomeActivity;
import com.doubleclick.e_commerceapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Objects;

public class RegisterFragment extends Fragment {


    private Button CreateAccountButton;
    private EditText InputName, InputEmail, InputPassword;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private String UserId;
    private DatabaseReference RootRef;
    private FirebaseFirestore firebaseFirestore;

    public RegisterFragment() {
        // Required empty public constructor
    }


    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        CreateAccountButton = view.findViewById(R.id.register_btn);
        InputName = view.findViewById(R.id.register_username_input);
        InputPassword = view.findViewById(R.id.register_password_input);
        InputEmail = view.findViewById(R.id.register_email_number_input);
        loadingBar = new ProgressDialog(getContext());
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();
        RootRef = FirebaseDatabase.getInstance().getReference();
        CreateAccountButton.setOnClickListener(v -> CreateAccount());

        return view;


    }

    private void CreateAccount() {
        String name = InputName.getText().toString();
        String email = InputEmail.getText().toString();
        String password = InputPassword.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(getContext(), "Please write your name...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(email)) {
            Toast.makeText(getContext(), "Please write your email number...", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(getContext(), "Please write your password...", Toast.LENGTH_SHORT).show();
        } else {
            loadingBar.setTitle("Create Account");
            loadingBar.setMessage("Please wait, while we are checking the credentials.");
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            ValidatephoneNumber(name, email, password);
        }
    }

    private void ValidatephoneNumber(final String name, final String email, final String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            HashMap<String, Object> userdataMap = new HashMap<>();
            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(name)) {
                userdataMap.put("email", email);
                userdataMap.put("password", password);
                userdataMap.put("name", name);
                userdataMap.put("address", "");
                userdataMap.put("phoneOrder", "");
                userdataMap.put("image", "");
                UserId = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                firebaseFirestore.collection("Users").document(UserId).set(userdataMap).addOnCompleteListener(task12 -> {
                    if (task12.isSuccessful()) {
                        Toast.makeText(getContext(), "Congratulations, your account has been created.", Toast.LENGTH_SHORT).show();
                        loadingBar.dismiss();
                        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()) {
                                Intent intent = new Intent(getContext(), HomeActivity.class);
                                startActivity(intent);
                            }
                        });

                    } else {
                        loadingBar.dismiss();
                        Toast.makeText(getContext(), "Network Error: Please try again after some time...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

}