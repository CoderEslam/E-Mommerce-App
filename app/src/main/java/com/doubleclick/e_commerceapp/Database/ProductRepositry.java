package com.doubleclick.e_commerceapp.Database;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.doubleclick.e_commerceapp.Model.Products;

import java.util.List;

public class ProductRepositry {

    private ProductsDAO mProductsDAO;
    private LiveData<List<Products>> getAllProducts;


    public ProductRepositry(Application application) {
        ProductsRoomDB db = ProductsRoomDB.getInstance(application);
        mProductsDAO = db.productsDAO();
        getAllProducts = mProductsDAO.getAllProducts();

    }

    public void insert(Products products){
        new InsertAsyncTask(mProductsDAO).execute(products);
    }

    public void delete(Products products){
        new DeletetAsyncTask(mProductsDAO).execute(products);
    }

    public void update(Products products){
        new UpdatetAsyncTask(mProductsDAO).execute(products);
    }

    public LiveData<List<Products>> getAllProducts(){
        return getAllProducts;
    }

    private static class InsertAsyncTask extends AsyncTask<Products,Void,Void>{

        private ProductsDAO mProductsDAO;

        public InsertAsyncTask(ProductsDAO productsDAO){
            mProductsDAO = productsDAO;
        }

        @Override
        protected Void doInBackground(Products... products) {
            mProductsDAO.insert(products[0]);
            return null;
        }
    }

    private static class DeletetAsyncTask extends AsyncTask<Products,Void,Void>{

        private ProductsDAO mProductsDAO;

        public DeletetAsyncTask(ProductsDAO productsDAO){
            mProductsDAO = productsDAO;
        }

        @Override
        protected Void doInBackground(Products... products) {
            mProductsDAO.delete(products[0]);
            return null;
        }
    }

    private static class UpdatetAsyncTask extends AsyncTask<Products,Void,Void>{

        private ProductsDAO mProductsDAO;

        public UpdatetAsyncTask(ProductsDAO productsDAO){
            mProductsDAO = productsDAO;
        }

        @Override
        protected Void doInBackground(Products... products) {
            mProductsDAO.update(products[0]);
            return null;
        }
    }


}
