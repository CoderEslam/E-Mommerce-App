package com.doubleclick.e_commerceapp.Activites.Login_SignIn.Fargments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.doubleclick.e_commerceapp.Activites.Login_SignIn.LoginFragment;

public class ViewPager extends FragmentPagerAdapter {


    public ViewPager(@NonNull FragmentManager fm, int b) {
        super(fm,b);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new SplashFragment1();
            case 1:
                return new SplashFragment2();
            case 2:
                return  new SplashFragment3();
            case 3:
                return new LoginFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}