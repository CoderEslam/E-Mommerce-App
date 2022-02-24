package com.doubleclick.e_commerceapp.Activites;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import static com.doubleclick.e_commerceapp.FirebaseAdapter.DBQuries.AllProductesLists;
import static com.doubleclick.e_commerceapp.FirebaseAdapter.DBQuries.homePageModels;

public class onAppKilled  extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        homePageModels.clear();
        homePageModels.subList(0, homePageModels.size() - 1).clear();
        AllProductesLists.clear();
    }


}
