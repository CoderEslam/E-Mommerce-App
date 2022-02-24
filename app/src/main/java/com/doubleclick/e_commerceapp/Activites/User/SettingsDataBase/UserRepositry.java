package com.doubleclick.e_commerceapp.Activites.User.SettingsDataBase;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.doubleclick.e_commerceapp.Database.ProductsDAO;
import com.doubleclick.e_commerceapp.Database.ProductsRoomDB;
import com.doubleclick.e_commerceapp.Model.Products;
import com.doubleclick.e_commerceapp.Model.Users;
import com.google.firebase.firestore.auth.User;

import java.util.List;

public class UserRepositry {

    private UserDAO userDAO;
    private LiveData<Users> getUser;


    public UserRepositry(Application application) {
        UserRoomDB db = UserRoomDB.getInstance(application);
        userDAO = db.UserDAO();
        getUser = userDAO.getUser();

    }

    public void insert(Users user){
        new InsertAsyncTask(userDAO).execute(user);
    }

    public void delete(Users user){
        new DeletetAsyncTask(userDAO).execute(user);
    }

    public void update(Users user){
        new UpdatetAsyncTask(userDAO).execute(user);
    }

    public LiveData<Users> getUser(){
        return getUser;
    }

    private static class InsertAsyncTask extends AsyncTask<Users,Void,Void>{

        private UserDAO mUserDAO;

        public InsertAsyncTask(UserDAO userDAO){
            mUserDAO = userDAO;
        }

        @Override
        protected Void doInBackground(Users... users) {
            mUserDAO.insert(users[0]);
            return null;
        }
    }

    private static class DeletetAsyncTask extends AsyncTask<Users,Void,Void>{

        private UserDAO mUserDAO;

        public DeletetAsyncTask(UserDAO userDAO){
            mUserDAO = userDAO;
        }

        @Override
        protected Void doInBackground(Users... users) {
            mUserDAO.delete(users[0]);
            return null;
        }
    }

    private static class UpdatetAsyncTask extends AsyncTask<Users,Void,Void>{

        private UserDAO mUserDAO;

        public UpdatetAsyncTask(UserDAO userDAO){
            mUserDAO = userDAO;
        }

        @Override
        protected Void doInBackground(Users... users) {
            mUserDAO.update(users[0]);
            return null;
        }
    }


}
