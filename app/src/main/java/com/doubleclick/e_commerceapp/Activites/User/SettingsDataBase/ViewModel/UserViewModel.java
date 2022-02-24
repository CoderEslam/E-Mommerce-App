package com.doubleclick.e_commerceapp.Activites.User.SettingsDataBase.ViewModel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.doubleclick.e_commerceapp.Activites.User.SettingsDataBase.UserRepositry;
import com.doubleclick.e_commerceapp.Model.Users;

public class UserViewModel extends AndroidViewModel {

    private UserRepositry mUserRepositry;
    private LiveData<Users> mUser;

    public UserViewModel(Application application) {
        super(application);
        mUserRepositry = new UserRepositry(application);
        mUser = mUserRepositry.getUser();
    }


    public void insert(Users user){
        mUserRepositry.insert(user);
    }

    public void delete(Users user){
        mUserRepositry.delete(user);
    }

    public void update(Users user){
        mUserRepositry.update(user);
    }

    public LiveData<Users> getUser(){
        return mUser;
    }

}
