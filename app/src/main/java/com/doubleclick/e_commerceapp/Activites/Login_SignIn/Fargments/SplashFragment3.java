package com.doubleclick.e_commerceapp.Activites.Login_SignIn.Fargments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doubleclick.e_commerceapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SplashFragment3#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SplashFragment3 extends Fragment {


    public SplashFragment3() {
        // Required empty public constructor
    }


    public static SplashFragment3 newInstance(String param1, String param2) {
        SplashFragment3 fragment = new SplashFragment3();
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
        return inflater.inflate(R.layout.fragment_splash3, container, false);
    }
}