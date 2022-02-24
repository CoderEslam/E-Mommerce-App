package com.doubleclick.e_commerceapp.Database;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.doubleclick.e_commerceapp.Model.Products;

@Database(entities =Products.class,version = 1)
public abstract class ProductsRoomDB extends RoomDatabase   {

    private static ProductsRoomDB instance;
    public abstract ProductsDAO productsDAO();

    public static synchronized ProductsRoomDB getInstance(Application application){
         if (instance == null){
             instance = Room.databaseBuilder(application.getApplicationContext(),
                     ProductsRoomDB.class,
                     "ProductsDatabase")
                     .fallbackToDestructiveMigration()
                     .addCallback(roomCallBack)
                     .build();
         }
         return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(SupportSQLiteDatabase db) {
            super.onCreate(db);
            new ProductAsyncTask(instance).execute();
        }

        @Override
        public void onOpen( SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    private static class ProductAsyncTask extends AsyncTask<Void,Void,Void>{

        private ProductsDAO mProductsDAO;

        public ProductAsyncTask(ProductsRoomDB db) {
            mProductsDAO = db.productsDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }
    }

}
