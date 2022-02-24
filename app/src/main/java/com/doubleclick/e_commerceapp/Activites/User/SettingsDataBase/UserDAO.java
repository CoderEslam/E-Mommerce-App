package com.doubleclick.e_commerceapp.Activites.User.SettingsDataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.doubleclick.e_commerceapp.Model.Users;
import com.google.firebase.firestore.auth.User;

import java.util.List;


@Dao
public interface UserDAO {

    @Insert
    void insert(Users user);

    @Update
    void update(Users user);

    @Delete
    void delete(Users user);

    @Query("SELECT * FROM Users")
    LiveData<Users>getUser();

}
