package com.doubleclick.e_commerceapp.Activites.User.SettingsDataBase;

import android.app.Application;
import android.os.AsyncTask;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.doubleclick.e_commerceapp.Model.Users;


@Database(entities = Users.class,version = 1)
public abstract class UserRoomDB extends RoomDatabase   {

    private static UserRoomDB instance;
    public abstract UserDAO UserDAO();

    public static synchronized UserRoomDB getInstance(Application application){
         if (instance == null){
             instance = Room.databaseBuilder(application.getApplicationContext(),
                     UserRoomDB.class,
                     "UserDatabase")
                     .fallbackToDestructiveMigration()
                     .addCallback(roomCallBack)
                     .build();
         }
         return instance;
    }

    private static Callback roomCallBack = new Callback(){
        @Override
        public void onCreate(SupportSQLiteDatabase db) {
            super.onCreate(db);
            new UserAsyncTask(instance).execute();
        }
        @Override
        public void onOpen( SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
    private static class UserAsyncTask extends AsyncTask<Void,Void,Void>{
        private UserDAO userDAO;
        public UserAsyncTask(UserRoomDB db) {
            userDAO = db.UserDAO();
        }
        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }

}
