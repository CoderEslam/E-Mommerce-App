package com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.ExbendableList.Navigation;

import android.annotation.SuppressLint;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.SeeAllCatrgoryActivity;
import com.doubleclick.e_commerceapp.Activites.User.SeeAllCategory.ExbendableList.Fragments.ShowCategoryFragment;
import com.doubleclick.e_commerceapp.R;


/**
 * @author msahakyan
 */

public class FragmentNavigationManager implements NavigationManager {

    private static FragmentNavigationManager sInstance;

    private FragmentManager mFragmentManager;
    private SeeAllCatrgoryActivity mActivity;

    public static FragmentNavigationManager obtain(SeeAllCatrgoryActivity activity) {
        if (sInstance == null) {
            sInstance = new FragmentNavigationManager();
        }
        sInstance.configure(activity);
        return sInstance;
    }

    private void configure(SeeAllCatrgoryActivity activity) {
        mActivity = activity;
        mFragmentManager = mActivity.getSupportFragmentManager();
    }

//    @Override
//    public void showFragmentAction(String title) {
//        showFragment(FragmentACtion.newInstance(title), false);
//    }
//
//    @Override
//    public void showFragmentComedy(String title) {
//        showFragment(FragmentComedy.newInstance(title), false);
//    }
//
//    @Override
//    public void showFragmentDrama(String title) {
//        showFragment(FragmentDrama.newInstance(title), false);
//    }
//
//    @Override
//    public void showFragmentMusical(String title) {
//        showFragment(FragmentMusical.newInstance(title), false);
//    }
//
//    @Override
//    public void showFragmentThriller(String title) {
//        showFragment(FragmentThriller.newInstance(title), false);
//    }


    private void showFragment(Fragment fragment, boolean allowStateLoss) {
        FragmentManager fm = mFragmentManager;

        @SuppressLint("CommitTransaction")
        FragmentTransaction ft =  fm.beginTransaction().replace(R.id.container, fragment);

        ft.addToBackStack(null);

        if (allowStateLoss ){ // || !BuildConfig.DEBUG) {
            ft.commitAllowingStateLoss();
        } else {
            ft.commit();
        }

        fm.executePendingTransactions();
    }


    @Override
    public void showCategoty(String Head, String child) {
        showFragment(ShowCategoryFragment.newInstance(Head,child),false);
    }
}
