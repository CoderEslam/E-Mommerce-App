package com.doubleclick.e_commerceapp.Activites.Login_SignIn.Fargments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doubleclick.e_commerceapp.R;


public class SplashFragment1 extends Fragment {




    public SplashFragment1() {
        // Required empty public constructor
    }


    public static SplashFragment1 newInstance(String param1, String param2) {
        SplashFragment1 fragment = new SplashFragment1();
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
        return inflater.inflate(R.layout.fragment_splash1, container, false);
    }
}