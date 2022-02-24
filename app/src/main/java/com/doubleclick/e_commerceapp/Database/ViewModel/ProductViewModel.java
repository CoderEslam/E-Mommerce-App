package com.doubleclick.e_commerceapp.Database.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.doubleclick.e_commerceapp.Database.ProductRepositry;
import com.doubleclick.e_commerceapp.Model.Products;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {

    private ProductRepositry mProductRepositry;
    private LiveData<List<Products>> mAllProductes;

    public ProductViewModel( Application application) {
        super(application);
        mProductRepositry = new ProductRepositry(application);
        mAllProductes = mProductRepositry.getAllProducts();
    }


    public void insert(Products products){
        mProductRepositry.insert(products);
    }

    public void delete(Products products){
        mProductRepositry.delete(products);
    }

    public void update(Products products){
        mProductRepositry.update(products);
    }

    public LiveData<List<Products>> getAllProductes(){
        return mAllProductes;
    }

}
